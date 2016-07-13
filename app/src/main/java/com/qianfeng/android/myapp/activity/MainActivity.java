package com.qianfeng.android.myapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.qianfeng.android.myapp.Callback.LocationCallback;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.listener.MyLocationListener;

public class MainActivity extends AppCompatActivity{

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFragment();
    }



    }

    private void initFragment() {


    private void initLocation() {
        sharedPreferences = getSharedPreferences("location", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
            mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
            mLocationClient.registerLocationListener( myListener );    //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(false);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    private void initView() {

        radioGroup = (RadioGroup) findViewById(R.id.main_bottom_rg);
        setUpRadioGroup();
    }

    private void setUpRadioGroup() {

        int size = radioGroup.getChildCount();
        RadioButton[] radioButtons = new RadioButton[size];

        for (int i = 0; i < size; i++) {
            radioButtons[i] = (RadioButton) radioGroup.getChildAt(i);
        }

        radioButtons[0].setChecked(true);
    }


}
