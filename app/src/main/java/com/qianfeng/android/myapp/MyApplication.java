package com.qianfeng.android.myapp;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;


/**
 * Created by Administrator on 2016/7/11.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initLocation();

    }

    private void initLocation() {
        SDKInitializer.initialize(getApplicationContext());
    }
}
