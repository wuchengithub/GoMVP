package com.wookii.gomvp.view;

import com.wookii.gomvp.GoLog;
import com.wookii.gomvp.base.GoView;
import com.wookii.gomvp.base.LifecyclePresenter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DefaultView<T> implements GoView<T>{
    private final Object host;
    private HashMap<String, Method> goBackMethods;

    private HashMap<String, Method> goErrorMethods;
    private HashMap<String, Method> goActionBack;


    public DefaultView(Object host) {
        this.host = host;
    }

    @Override
    public void receiverData(LifecyclePresenter<T> tLifecyclePresenter) {
        T value = tLifecyclePresenter.getValue();
        //JavaBean类型name
        String beanName = value.getClass().getName();
        if(goBackMethods != null && goBackMethods.size() != 0) {
            Method method = goBackMethods.get(beanName);
            if(method != null) {
                try {
                    method.invoke(host,value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        }


        if(goActionBack != null && goActionBack.size() != 0) {
            Set<Map.Entry<String, Method>> entries = goActionBack.entrySet();
            Iterator<Map.Entry<String, Method>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Method> next = iterator.next();
                Method method = next.getValue();
                String methodParameterName = method.getParameterTypes()[0].getName();
                if(beanName.equals(methodParameterName)){
                    String targetAction = next.getKey();
                    String currentAction = tLifecyclePresenter.getCurrentPresenterAdapter().action();
                    if(targetAction.equals(currentAction)) {
                        try {
                            method.invoke(host,value);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }


    }

    @Override
    public void showDataError(LifecyclePresenter<T> tLifecyclePresenter, String error) {
        GoLog.D("DefaultView,showDataError:" + error);
        T value = tLifecyclePresenter.getValue();

        //没有类型的
        Method noType = goErrorMethods.get("String_all");
        if(noType != null) {
            try {
                noType.invoke(host, error);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }


        if(value == null || goErrorMethods == null || goErrorMethods.size() == 0) {return;}
        String name = value.getClass().getName();
        Method method = goErrorMethods.get(name);
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

    public void addGoActionBack(HashMap<String, Method> actionBack) {
        this.goActionBack = actionBack;
    }
}
