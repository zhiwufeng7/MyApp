package com.qianfeng.android.myapp.model;

import android.util.Log;

import com.qianfeng.android.myapp.handle.MainCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by my on 2016/7/12.
 */
public class OnMainModel implements MainModel {
    @Override
    public void loadData(final String url, final MainCallBack callBack) {
        //可以加载异步任务
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    //成功回调成功的方法
                    callBack.onSuccess(url);
                } catch (InterruptedException e) {
                    //失败回调失败的方法
                    callBack.onError();
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
