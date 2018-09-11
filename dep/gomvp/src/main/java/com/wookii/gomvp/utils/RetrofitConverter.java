package com.wookii.gomvp.utils;

/**
 * Created by wuchen on 2018/3/14.
 */

public interface RetrofitConverter {

    String host();

    <T>T create(Class<T> t);
}
