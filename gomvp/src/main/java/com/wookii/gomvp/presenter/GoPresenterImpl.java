package com.wookii.gomvp.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;

import com.google.gson.Gson;
import com.wookii.gomvp.GoLog;
import com.wookii.gomvp.Jumper;
import com.wookii.gomvp.adapter.AdapterManager;
import com.wookii.gomvp.adapter.InterceptGoBack;
import com.wookii.gomvp.adapter.PresenterAdapter;
import com.wookii.gomvp.respository.GoDataSource;
import com.wookii.gomvp.utils.RetrofitConverter;
import com.wookii.gomvp.view.IGoView;
import com.wookii.gomvp.view.ExecuteStatusView;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by wuchen on 2017/11/11.
 */

public abstract class GoPresenterImpl<T> implements GoPresenter<T,IGoView,GoDataSource>, Observer<T> {

    private static final String TAG = "BasePresenter";
    private final AdapterManager adapterManager;
    protected Context context;
    private IGoView view;
    private GoDataSource model;
    protected T value;
    private ExecuteStatusView executeStatusListener;
    protected GoLog logger;
    private String jsonString;
    private InterceptGoBack<T> interceptGoBack;
    private Observer observer;

    protected GoPresenterImpl(Context context) {
        this();
        this.context = context;
    }

    public PresenterAdapter getCurrentPresenterAdapter() {
        return presenterAdapter;
    }

    private PresenterAdapter presenterAdapter;
    private boolean cacheError;
    protected LifecycleListener lifecycleListener;

    protected GoPresenterImpl(){
        GoLog.I("GoPresenterImpl: AdapterManager crate!");
        adapterManager = new AdapterManager();
    }

    @Override
    public void setView(IGoView view) {
        this.view = view;
    }

    @Override
    public IGoView getView() {
        return view;
    }

    @Override
    public T getCache(Class<T> clazz) {
        checkNotNull(clazz);
        if(model != null) {
            String s = (String)model.getGoCache(context).onGet(clazz.getName());
            Gson gson = new Gson();
            return gson.fromJson(s, clazz);
        }
        return null;
    }

    @Override
    public void setLog(GoLog log) {
        this.logger = log;
    }

    public void registerExecuteStatus(ExecuteStatusView executeStatusListener) {
        this.executeStatusListener = executeStatusListener;
    }

    @Override
    public void setRepository(GoDataSource model) {
        this.model = model;
    }

    /**
     * createRetrofit a customer observer
     * @param observer
     */
    @Override
    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    @Override
    public GoDataSource goWork(Observable observable, Observer observer) {
        if(executeStatusListener != null) {
            executeStatusListener.onExecuteBegin();
        }
        return model.loadDataFromRepository(observable, observer);
    }

    @Override
    public String getResponseString() {
        return jsonString;
    }

    @Override
    public void onNext(final T value) {
        this.value = value;
        boolean isIntercept = false;
        if(interceptGoBack != null) {
            isIntercept = interceptGoBack.intercept(value);
        }

        if(isIntercept) return;

        Jumper.trueJump(new Jumper.JumperStation() {
            @Override
            public void onJump() {
                Pair<String, String> success = presenterAdapter.onSuccessCodePair();
                String json = valueToJsonString(value);
                jsonString = json;
                String successCode = getFieldValue(json, success.first);
                if(TextUtils.equals(successCode, success.second)) {
                    successful(json, value);
                } else {
                    error(json, value);
                }
            }
        }).filter(new Jumper.JumperFilter(){

            @Override
            public boolean onFilter() {
                //if true trueJump, or falseJump
                return presenterAdapter != null;
            }
        }).falseJump(new Jumper.JumperStation() {
            @Override
            public void onJump() {
                String json = valueToJsonString(value);
                successful(json, value);
            }

        });

        GoLog.I(valueToJsonString(value));

    }

    private void error(String json, T value) {
        String className = value.getClass().getName();
        GoDataSource.GoCache goCache = model.getGoCache(context);
        //属否缓存
        if(goCache != null && isCacheError()) {
            goCache.onAdd(className, json);
        }
        //通知view
        view.showDataError(GoPresenterImpl.this, getFieldValue(json, presenterAdapter.onErrorMessageKey()));
        if(executeStatusListener != null) {
            executeStatusListener.onExecuteFinish();
        }
    }

    private void successful(String json, T value) {
        String className = value.getClass().getName();
        GoDataSource.GoCache goCache = model.getGoCache(context);
        if(goCache != null) {
            goCache.onAdd(className, json);
        }
        //通知view
        view.receiverData(GoPresenterImpl.this);
        if(executeStatusListener != null) {
            executeStatusListener.onExecuteFinish();
        }

    }

    private String valueToJsonString(T value) {
        return new Gson().toJson(value);
    }

    private String getFieldValue(String json, String key) {
        try {
            JSONObject object = new JSONObject(json);
            return object.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void onSubscribe(Disposable d) {
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(d);

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        view.showDataError(GoPresenterImpl.this, "请求失败");
    }

    @Override
    public void onComplete() {

    }

    @Override
    public GoPresenter bindPresenterAdapter(PresenterAdapter presenterAdapter) {
        if(this.presenterAdapter == presenterAdapter) return this;
        this.presenterAdapter = presenterAdapter;
        this.presenterAdapter.setPresenter(this);
        if(presenterAdapter instanceof InterceptGoBack) {
            this.interceptGoBack = (InterceptGoBack<T>) this.presenterAdapter;
        }
        return this;
    }

    @Override
    public GoPresenter unbindPresenterAdapter() {
        this.presenterAdapter = null;
        return this;
    }

    protected void request() {
        request(presenterAdapter);
    }

    protected void request(PresenterAdapter pd) {
        Observable observable = getObservable(pd);
        if(observable == null) {
            //do nothing
            return;
        }
        setRepository(model);
        if(observer == null) {
            observer = this;
        }
        onModel().targetClazz(pd.targetBeanType());
        goWork(observable,observer);
    }
    protected GoDataSource getModel() {

        return model;
    }

    /**
     * 默认值为false。不会缓存错误的数据，如果设置为true，当返回的信息为错误的，会
     * 缓存起来，并把上一次正确的信息覆盖掉
     * @param b
     */
    public void setCacheError(boolean b) {
        this.cacheError = b;
    }

    private boolean isCacheError() {
        return cacheError;
    }

    public void addLifecycleListener(LifecycleListener lifecycleListener) {
        this.lifecycleListener = lifecycleListener;
    }

    private Observable getObservable(PresenterAdapter presenterAdapter) {
        if(presenterAdapter == null) {
            throw new RuntimeException("no match presenterAdapter, please check execute(class)");
        }
        if(this.model ==  null) {
            this.model = onModel();
        }
        RetrofitConverter retrofitConverter = model.onCreateRetrofitConverter();
        checkNotNull(retrofitConverter);
        bindPresenterAdapter(presenterAdapter);
        return presenterAdapter.onCreateObservable(context, retrofitConverter);
    }


    public interface LifecycleListener {
        void onCreate(Context context);
    }
}
