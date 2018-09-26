package com.wookii.gomvp.adapter;

import com.wookii.gomvp.GoLog;

import java.util.HashMap;

public class AdapterManager {
    private final HashMap<Class, PresenterAdapter> adapterHashMap;

    public AdapterManager() {
        adapterHashMap = new HashMap<>();
    }
    public void addAdapter(PresenterAdapter presenterAdapter) {
        Class beanType = presenterAdapter.targetBeanType();
        adapterHashMap.put(beanType, presenterAdapter);
        GoLog.I("AdapterManager add beanType:" + beanType);
    }

    public <T> PresenterAdapter getAdapter(Class<T> clazz) {
        return adapterHashMap.get(clazz);
    }
}
