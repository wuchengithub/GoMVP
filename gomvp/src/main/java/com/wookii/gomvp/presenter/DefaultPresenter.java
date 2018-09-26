package com.wookii.gomvp.presenter;

import com.wookii.gomvp.respository.GoDataSource;

/**
 * Created by wuchen on 2017/12/6.
 */

public class DefaultPresenter extends GoPresenterImpl {
    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public GoDataSource onModel() {
        return getModel();
    }
}
