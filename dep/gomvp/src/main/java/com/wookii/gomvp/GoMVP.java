package com.wookii.gomvp;

import android.support.annotation.Nullable;

import com.wookii.gomvp.presenter.GoPresenterImpl;
import com.wookii.gomvp.presenter.DefaultPresenter;
import com.wookii.gomvp.presenter.GoPresenter;
import com.wookii.gomvp.respository.GoDataSource;
import com.wookii.gomvp.view.IGoView;

import io.reactivex.Observer;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by wuchen on 2017/11/10.
 */

public final class GoMVP {
    private GoPresenter presenter;

    private GoMVP(IGoView view, GoPresenter presenter, GoDataSource model, @Nullable Observer observer, GoLog log) {
        this.presenter = presenter;
        if (presenter == null) {
            presenter = new DefaultPresenter();
        }
        if(view != null) {
            presenter.setView(view);
        }
        if(model != null) {
            presenter.setRepository(model);
        }
        if(observer != null) {
            presenter.setObserver(observer);
        }
        if(log != null) {
            presenter.setLog(log);
        }
    }

    public static final class Builder {
        private IGoView view;
        private GoPresenter presenter;
        private GoDataSource model;
        private Observer observer;
        private GoLog log;

        public Builder() {

        }
        public <T extends GoPresenterImpl> Builder presenter(T presenter) {
            this.presenter = presenter;
            return this;
        }

        public Builder view(IGoView view) {
            this.view = view;
            return this;
        }

        public Builder repository(GoDataSource model) {
            this.model = model;
            return this;
        }

        public Builder log(GoLog log) {
            this.log = log;
            return this;
        }

        public Builder observer(Observer observer) {
            this.observer = observer;
            return this;
        }

        public GoMVP build() {
            checkNotNull(presenter);
            return new GoMVP(view,presenter,model,observer,log);
        }
    }

    public <T extends GoPresenterImpl> T getPresenter() {
        return (T) this.presenter;
    }
}
