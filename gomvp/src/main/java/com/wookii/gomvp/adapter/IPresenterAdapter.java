package com.wookii.gomvp.adapter;

import android.content.Context;
import android.util.Pair;

import com.wookii.gomvp.utils.RetrofitConverter;

import io.reactivex.Observable;

public interface IPresenterAdapter {

    Observable onCreateObservable(Context context, RetrofitConverter retrofit);

    Pair onSuccessCodePair();

    String onErrorMessageKey();

}
