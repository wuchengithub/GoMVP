package com.wookii.gomvp.demo.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.wookii.gomvp.respository.DataSourceInjection;
import com.wookii.gomvp.respository.GoDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class RepositoryInjection implements DataSourceInjection {
    @Override
    public GoDataSource provideRepository(@NonNull Context context) {
        checkNotNull(context);
        return new SimpleRepository();
    }
}
