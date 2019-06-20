package com.wookii.gomvp.demo.bean;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wuchen on 2018/3/20.
 */

public class NoticeCountBean {

    /**
     * success : true
     * value : 32
     * totalElements : 0
     */

    private boolean success;
    private int value;
    private int totalElements;

    public static NoticeCountBean objectFromData(String str) {

        return new Gson().fromJson(str, NoticeCountBean.class);
    }

    public static NoticeCountBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), NoticeCountBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
}
