/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wookii.gomvp.demo.repository;

import android.util.Pair;

import com.wookii.gomvp.utils.RetrofitConverter;
import com.wookii.gomvp.respository.GoDataSource;

import io.reactivex.Observable;
import io.reactivex.Observer;


public class BaseRepository extends BaseDataSource {

    private static BaseRepository INSTANCE = null;
    private final BaseDataSource mAssistantRemoteDataSource;

    private final BaseDataSource mAssistantLocalDataSource;

    private synchronized Class getClazz() {
        return clazz;
    }

    public synchronized void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    private BaseRepository(BaseDataSource assistantRemoteDataSource,
                           BaseDataSource assistantLocalDataSource) {
        this.mAssistantRemoteDataSource = assistantRemoteDataSource;
        this.mAssistantLocalDataSource = assistantLocalDataSource;
    }
    public static synchronized BaseRepository getInstance(BaseDataSource assistantRemoteDataSource,
                                                          BaseDataSource assistantLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new BaseRepository(assistantRemoteDataSource, assistantLocalDataSource);
        }
        return INSTANCE;
    }

    @Override
    protected <T, B> void getObject(Observable<T> o, Observer s, Class<B> clazz) {
        /*
          可以在这里添加业务具体使用的DataSource
         */
        if(o == null) {
            mAssistantLocalDataSource.getObject(o,s,clazz);
        } else {
            mAssistantRemoteDataSource.getObject(o,s,clazz);
        }
    }

    /**
     * 最终，GoMVP会调用loadDataFromRepository方法，所以具体实现要由业务人员去完成
     * @return
     */
    @Override
    public synchronized GoDataSource loadDataFromRepository() {
        getObject(observable,observer,getClazz());
        return this;
    }

    /**
     * 具体的Retrofit对象，由RetrofitConverter封装，业务上需要实现一个RetrofitConverter
     * 接口，具体看MainRetrofit，这里必须实现，GoMVP不提供具体Retrofit。
     * @return
     */
    @Override
    public RetrofitConverter onCreateRetrofitConverter() {
        return new MainRetrofit();
    }

    /**
     * 这里可以设置一个success code，业务上，认为如果请求结果中上success 字段返回true，认为业务正确，可以不设置
     * @return
     */
    @Override
    public Pair onSuccessCode() {
        return new Pair("success","true");
    }


    @Override
    public GoCache getGoCache() {
        return mAssistantRemoteDataSource.getGoCache();
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
