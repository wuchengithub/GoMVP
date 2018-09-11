package com.wookii.gomvp.adapter;

import com.wookii.gomvp.presenter.GoPresenterImpl;

public abstract class PresenterAdapter implements IPresenterAdapter{
    private GoPresenterImpl presenter;

    public GoPresenterImpl getPresenter() {
        return presenter;
    }

    public <T> void setPresenter(GoPresenterImpl<T> presenter) {
        this.presenter = presenter;
    }
}
