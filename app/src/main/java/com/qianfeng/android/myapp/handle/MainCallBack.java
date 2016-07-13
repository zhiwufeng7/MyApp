package com.qianfeng.android.myapp.handle;

/**
 * Created by my on 2016/7/12.
 */
public interface MainCallBack {

        /**
         * 成功时回调
         */
        void onSuccess(String str);
        /**
         * 失败时回调
         */
        void onError();


}
