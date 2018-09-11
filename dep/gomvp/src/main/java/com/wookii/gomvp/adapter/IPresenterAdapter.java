package com.wookii.gomvp.adapter;

import com.wookii.gomvp.utils.RetrofitConverter;

import io.reactivex.Observable;

public interface IPresenterAdapter {

    Observable onCreateObservable(RetrofitConverter retrofit);

}
