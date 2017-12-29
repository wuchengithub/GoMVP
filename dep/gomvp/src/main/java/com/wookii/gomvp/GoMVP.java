package com.wookii.gomvp;

import android.support.annotation.Nullable;

import io.reactivex.Observer;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by wuchen on 2017/11/10.
 */

public final class GoMVP {
    private GoView view;
    private GoPresenter presenter;
    private GoDataSource model;
    private Observer observer;

    private GoMVP(GoView view, GoPresenter presenter, GoDataSource model, @Nullable Observer observer) {
        this.view = view;
        this.presenter = presenter;
        this.model = model;
        this.observer = observer;
        if (presenter == null) {
            presenter = new DefaultPresenter();
        }
        presenter.setView(view);
        if(model != null) {
            presenter.setModel(model);
        }
        presenter.setObserver(observer);
    }

    public static final class Builder {
        private GoView view;
        private GoPresenter presenter;
        private GoDataSource model;
        private Observer observer;

        public Builder() {

        }
        public Builder presenter(GoPresenter presenter) {
            this.presenter = presenter;
            return this;
        }

        public Builder view(GoView view) {
            this.view = view;
            return this;
        }

        public Builder model(GoDataSource model) {
            this.model = model;
            return this;
        }

        public Builder observer(Observer observer) {
            this.observer = observer;
            return this;
        }

        public GoMVP build() {
            checkNotNull(view);
            checkNotNull(presenter);
            return new GoMVP (view,presenter,model,observer);
        }
    }

    public GoPresenter getPresenter() {
        return this.presenter;
    }
}
