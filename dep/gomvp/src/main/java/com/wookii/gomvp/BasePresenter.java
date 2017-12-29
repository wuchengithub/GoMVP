package com.wookii.gomvp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;

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

public abstract class BasePresenter<T> implements GoPresenter<T,GoView,GoDataSource>, Observer<T> {

    private static final String TAG = "BasePresenter";
    private GoView view;
    private GoDataSource model;
    protected T value;
    private PresenterAdapter createLoaderListener;
    private PresenterAdapter presenterAdapter;
    protected RetrofitUtils retrofitUtils;
    private boolean cacheError;

    protected BasePresenter(){
        this.model = onModel();
        retrofitUtils = RetrofitUtils.getInstance();
    }

    protected abstract GoDataSource onModel();
    @Override
    public void setView(GoView view) {
        this.view = view;
    }

    @Override
    public GoView getView() {
        return view;
    }

    @Override
    public T getCache(Class<T> clazz) {
        checkNotNull(clazz);
        if(model != null) {
            String s = (String)model.getCacher().onGet(clazz.getName());
            Gson gson = new Gson();
            return gson.fromJson(s, clazz);
        }
        return null;
    }

    @Override
    public void setCreateAdapter(PresenterAdapter createLoaderListener) {
        this.createLoaderListener = createLoaderListener;
    }

    public PresenterAdapter getCreateAdapter() {
        return createLoaderListener;
    }

    @Override
    public void setModel(GoDataSource model) {
        this.model = model;
        model.addContentObserver(this);
    }

    /**
     * create a customer observer
     * @param observer
     */
    @Override
    public void setObserver(Observer observer) {
        this.model.addContentObserver(observer);
    }

    @Override
    public GoDataSource createModel(Observer observer) {
        return new GoRepository(observer);
    }

    @Override
    public GoDataSource loadData() {

        return model.loadDataFromRepository();
    }

    @Override
    public void onNext(final T value) {
        this.value = value;
        Jumper.trueJump(new Jumper.JumperStation() {
            @Override
            public void onJump() {
                Pair<String, String> success = presenterAdapter.onSuccessCode();
                String json = valueToJsonString(value);
                String successCode = getSuccessCode(json, success.first);
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
        Log.i(TAG, valueToJsonString(value));

    }

    private void error(String json, T value) {
        //通知view
        view.showDataError(BasePresenter.this);
        T data = getValue();
        String className = value.getClass().getName().toString();
        model = getModel();
        GoDataSource.Cacher cacher = model.getCacher();
        //属否缓存
        if(cacher != null && isCacheError()) {
            cacher.onAdd(className, json);
        }
    }

    private void successful(String json, T value) {
        //通知view
        view.receiverData(BasePresenter.this);
        T data = getValue();
        String className = value.getClass().getName().toString();
        model = getModel();
        GoDataSource.Cacher cacher = model.getCacher();
        //属否缓存
        if(cacher != null) {
            cacher.onAdd(className, json);
        }
    }

    public String valueToJsonString(T value) {
        return new Gson().toJson(value);
    }

    public String getSuccessCode(String json, String key) {
        try {
            JSONObject object = new JSONObject(json);
            return object.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public GoDataSource createModel() {
        return null;
    }

    @Override
    public void onSubscribe(Disposable d) {
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(d);

    }

    @Override
    public void onError(Throwable e) {
        //view.showDataError(e.getMessage());
    }

    @Override
    public void onComplete() {

    }

    @Override
    public GoPresenter bindPresenterAdapter(PresenterAdapter presenterAdapter) {
        this.presenterAdapter = presenterAdapter;
        return this;
    }

    @Override
    public void request(final Context context) {
        checkNotNull(presenterAdapter);
        checkNotNull(retrofitUtils);
        checkNotNull(model);
        Observable observable = presenterAdapter.onCreateObservable(retrofitUtils);
        if(observable == null) {
            //do nothing
            return;
        }
        GoRepository goRepository = new GoRepository.Builder<T>()
                .retrofit(observable)
                .cache(new DefaultCacher(context))
                .build();
        setModel(goRepository);
        loadData();
    }

    public GoDataSource getModel() {

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

    public boolean isCacheError() {
        return cacheError;
    }
}
