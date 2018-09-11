package com.wookii.gomvp.demo.components;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wookii.gomvp.base.GoActivity;
import com.wookii.gomvp.demo.repository.RepositoryInjection;

public abstract class BaseActivity<T> extends GoActivity<T> {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置数据仓库
        getPresenter().setRepository(RepositoryInjection.provideAssistantRepository(getApplicationContext()));
    }
}
