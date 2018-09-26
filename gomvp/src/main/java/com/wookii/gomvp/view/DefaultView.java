package com.wookii.gomvp.view;

import com.wookii.gomvp.GoLog;
import com.wookii.gomvp.base.GoView;
import com.wookii.gomvp.base.LifecyclePresenter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class DefaultView<T> implements GoView<T>{
    private final Object host;
    private HashMap<String, Method> goBackMethods;

    private HashMap<String, Method> goErrorMethods;


    public DefaultView(Object host) {
        this.host = host;
    }

    @Override
    public void receiverData(LifecyclePresenter<T> tLifecyclePresenter) {
        T value = tLifecyclePresenter.getValue();
        String name = value.getClass().getName();
        if(goBackMethods == null || goBackMethods.size() == 0) {return;}
        Method method = goBackMethods.get(name);
        if(method == null) {
            GoLog.I("DefaultView,receiverData method is null, return!");
            return;
        }
        try {
            method.invoke(host,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showDataError(LifecyclePresenter<T> tLifecyclePresenter, String error) {
        GoLog.D("DefaultView,showDataError:" + error);
        T value = tLifecyclePresenter.getValue();

        //没有类型的
        Method noType = goBackMethods.get("String_all");
        if(noType != null) {
            try {
                noType.invoke(host, error);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }


        if(value == null || goBackMethods == null || goBackMethods.size() == 0) {return;}
        String name = value.getClass().getName();
        Method method = goBackMethods.get(name);
        if(method == null) {
            return;
        }
        try {
            method.invoke(host, value, error);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void addGoBackMethods(HashMap<String, Method> methods) {
        this.goBackMethods = methods;
    }

    public void addGoErrorMethods(HashMap<String, Method> onError) {
        this.goErrorMethods = onError;
    }
}
