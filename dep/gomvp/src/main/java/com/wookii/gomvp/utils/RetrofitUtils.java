package com.wookii.gomvp.utils;

import android.util.Base64;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit utils ,singleton
 * Created by wuchen on 2017/11/9.
 */

public class RetrofitUtils {

    private static RetrofitUtils instance;
    private static String BASE_URL = "";
    private Retrofit mRetrofit;

    private RetrofitUtils() {
        configRetrofit();
    }

    public synchronized static RetrofitUtils getInstance() {
        if(instance == null) {
            instance = new RetrofitUtils();
        }
        return instance;
    }

    /**
     * build retrofit
     */
    private void configRetrofit() {
        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(buildClient())
        .build();
    }

    public OkHttpClient getCallFactory() {

        return (OkHttpClient) mRetrofit.callFactory();
    }

//    public String getCurrentRquestApi(OkHttpClient client) {
//        if (client == null) {
//            return "";
//        } else {
//            client.
//        }
//    }

    /**
     * build okhttp client
     * @return
     */
    private OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(logging())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    private HttpLoggingInterceptor logging() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }


    /**
     * 和风天气签名生成算法-JAVA版本
     * @param  params 请求参数集，所有参数必须已转换为字符串类型
     * @param  secret 签名密钥
     * @return 签名
     * @throws IOException
     */
    public static String getSignature(HashMap<String,String> params, String secret) throws IOException {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String,String> sortedParams = new TreeMap<>(params);
        Set<Map.Entry<String,String>> entrySet = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起

        StringBuilder baseString = new StringBuilder();
        for (Map.Entry param : entrySet) {

            //sign参数 和 空值参数 不加入算法
            String trimKey = ((String) param.getKey()).trim();
            String trimValue = ((String) param.getValue()).trim();

            if(param.getValue()!=null && !"".equals(trimKey) && !"sign".equals(trimKey) && !"".equals(trimValue)) {
                baseString.append(trimKey).append("=").append(trimValue).append("&");
            }
        }
        if(baseString.length() > 0 ) {
            baseString.deleteCharAt(baseString.length() - 1).append(secret);
        }

        // 使用MD5对待签名串求签
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(baseString.toString().getBytes("UTF-8"));
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (GeneralSecurityException ex) {
            throw new IOException(ex);
        }
    }

    public <T> T create(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }
}
