package com.wookii.gomvp.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;

import com.google.gson.Gson;
import com.wookii.gomvp.GoLog;
import com.wookii.gomvp.Jumper;
import com.wookii.gomvp.utils.RetrofitConverter;
import com.wookii.gomvp.adapter.PresenterAdapter;
import com.wookii.gomvp.respository.GoDataSource;
import com.wookii.gomvp.view.IGoView;
import com.wookii.gomvp.view.ILoadingView;

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
    private IGoView view;
    private GoDataSource model;
    protected T value;
    private PresenterAdapter createLoaderListener;
    private ILoadingView loadingView;
    protected GoLog logger;
    private String jsonString;

    public PresenterAdapter getPresenterAdapter() {
        return presenterAdapter;
    }

    protected void setPresenterAdapter(PresenterAdapter presenterAdapter) {
        this.presenterAdapter = presenterAdapter;
    }

    private PresenterAdapter presenterAdapter;
    private boolean cacheError;
    protected LifecycleListener lifecycleListener;

    protected GoPresenterImpl(){
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
            String s = (String)model.getGoCache().onGet(clazz.getName());
            Gson gson = new Gson();
            return gson.fromJson(s, clazz);
        }
        return null;
    }

    @Override
    public void setLog(GoLog log) {
        this.logger = log;
    }

    public void registerLoadingView(ILoadingView loadingView) {
        this.loadingView = loadingView;
    }
    @Override
    public void setCreateAdapter(PresenterAdapter createLoaderListener) {
        this.createLoaderListener = createLoaderListener;
    }

    public PresenterAdapter getCreateAdapter() {
        return createLoaderListener;
    }

    @Override
    public void setRepository(GoDataSource model) {
        this.model = model;
    }

    /**
     * create a customer observer
     * @param observer
     */
    @Override
    public void setObserver(Observer observer) {
        this.model.onObserver(observer);
    }

    @Override
    public GoDataSource loadData() {
        if(loadingView != null) {
            loadingView.onLoading();
        }
        return model.loadDataFromRepository();
    }

    @Override
    public String getResponseString() {
        return jsonString;
    }

    @Override
    public void onNext(final T value) {
        this.value = value;
        Jumper.trueJump(new Jumper.JumperStation() {
            @Override
            public void onJump() {
                Pair<String, String> success = model.onSuccessCode();
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
        if(loadingView != null) {
            loadingView.onLoadingFinish();
        }
        //通知view
        view.showDataError(GoPresenterImpl.this, getFieldValue(json, "message"));
        T data = getValue();
        String className = value.getClass().getName();
        model = getModel();
        GoDataSource.GoCache goCache = model.getGoCache();
        //属否缓存
        if(goCache != null && isCacheError()) {
            goCache.onAdd(className, json);
        }
    }

    private void successful(String json, T value) {
        if(loadingView != null) {
            loadingView.onLoadingFinish();
        }
        //通知view
        view.receiverData(GoPresenterImpl.this);
        T data = getValue();
        String className = value.getClass().getName();
        model = getModel();
        GoDataSource.GoCache goCache = model.getGoCache();
        //属否缓存
        if(goCache != null) {
            goCache.onAdd(className, json);
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
        GoLog.E(e.getMessage());
        view.showDataError(GoPresenterImpl.this, "请求失败");
    }

    @Override
    public void onComplete() {

    }

    @Override
    public GoPresenter bindPresenterAdapter(PresenterAdapter presenterAdapter) {
        this.presenterAdapter = presenterAdapter;
        this.presenterAdapter.setPresenter(this);
        return this;
    }

    @Override
    public void request(final Context context) {
        checkNotNull(presenterAdapter);
        if(this.model ==  null) {
            this.model = onModel();
        }
        checkNotNull(model);
        RetrofitConverter retrofitConverter = this.model.onCreateRetrofitConverter();
        checkNotNull(retrofitConverter);
        Observable observable = presenterAdapter.onCreateObservable(retrofitConverter);
        if(observable == null) {
            //do nothing
            return;
        }
        setRepository(model);
        getModel().onObservable(observable);
        getModel().onObserver(this);
        loadData();
    }

    private GoDataSource getModel() {

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

    public interface LifecycleListener {
        void onCreate(Context context);
    }
}
