package com.qianfeng.android.myapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import com.qianfeng.android.myapp.MyApplication;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.fragment.HomePageFragment;


public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initFragment();

        initListener();

        ((MyApplication) getApplication()).mLocationClient.start();
        sharedPreferences = getSharedPreferences("location", Context.MODE_PRIVATE);
        sharedPreferences.getString("cityName", "北京");
    }

    private void initListener() {
            //底部的radioGroup设置监听  实现fragment的切换
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_bottom_order_form_rb:
                        break;
                    case R.id.main_bottom_shopping_rb:
                        break;
                    case R.id.main_bottom_my_rb:
                        break;
                    case R.id.main_bottom_assortment_rb:
                        break;
                    case R.id.main_bottom_home_rb:
                        break;
                }
            }
        });


    }

    private void initFragment() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transition = manager.beginTransaction();
        HomePageFragment homePageFragment = new HomePageFragment();
        transition.add(R.id.main_content_fl, homePageFragment);
        transition.commit();

    }

    private void initView() {

        radioGroup = (RadioGroup) findViewById(R.id.main_bottom_rg);

    }

    /**
     * 首页头部的监听
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_top_address_btn:
                break;
            case R.id.main_top_msg_btn:
                break;
            case R.id.main_top_search_btn:
                break;

        }

    }
}
