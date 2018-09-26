package com.wookii.gomvp.demo.api;

import com.wookii.gomvp.demo.bean.HttpResult;
import com.wookii.gomvp.demo.bean.NoticeCountBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by wuchen on 2018/1/5.
 */

public interface ApiServer {

    String YOU_URL_CONTRACT_NET = "";

    @FormUrlEncoded
    @POST("gateway")
    Observable<HttpResult<Object>> getMethods(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("gateway")
    Observable<HttpResult<NoticeCountBean>> getNoticeCountBean(@FieldMap Map<String,Object> map);


}
