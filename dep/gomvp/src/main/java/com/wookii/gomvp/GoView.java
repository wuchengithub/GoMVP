package com.wookii.gomvp;

/**
 * Created by wuchen on 2017/11/10.
 */

public interface GoView<P extends GoPresenter> {
    void receiverData(P t);

    void showDataError(P t);
}
