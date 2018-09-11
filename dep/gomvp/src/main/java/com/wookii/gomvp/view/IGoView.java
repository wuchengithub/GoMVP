package com.wookii.gomvp.view;

import com.wookii.gomvp.presenter.GoPresenter;

/**
 * Created by wuchen on 2017/11/10.
 */

public interface IGoView<P extends GoPresenter> {
    void receiverData(P p);

    void showDataError(P p, String error);
}
