package com.qianfeng.android.myapp.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.fragment.CommonLocationFragment;
import com.qianfeng.android.myapp.fragment.HomePageFragment;
import com.qianfeng.android.myapp.fragment.NearbyPlotFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private List<Fragment> fragmentList;
    private Button search;
    private Button message;
    private Button addres;
    private SharedPreferences sharedPreferences;
    private String village;
    private FragmentManager manager;
    private int curIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initFragment();

        initListener();

        initData();

    }

    private void initData() {
        sharedPreferences = getSharedPreferences("location", Context.MODE_PRIVATE);
        village = sharedPreferences.getString("plot", "北京");
        addres.setText(village);
    }


    private void initListener() {
        //底部的radioGroup设置监听  实现fragment的切换
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.main_bottom_home_rb:
                        //点击首页按钮切换第一个fragment
                        switchFragment(0);
                        break;
                    case R.id.main_bottom_assortment_rb:
                        //点击分类按钮切换第二个fragment
                        //switchFragment(1);
                        break;
                    case R.id.main_bottom_shopping_rb:
                        //点击购物车按钮切换第三个fragment

                       // switchFragment(2);
                        break;
                    case R.id.main_bottom_order_form_rb:
                        //点击订单按钮切换第四个fragment
                       // switchFragment(3);
                        break;
                    case R.id.main_bottom_my_rb:
                        //点击我的按钮切换第五个fragment
                        //switchFragment(4);
                        break;
                }
            }
        });


    }

    private void initFragment() {

        //初始化fragment
        manager = getSupportFragmentManager();
        FragmentTransaction transition = manager.beginTransaction();
        HomePageFragment homePageFragment = new HomePageFragment();
        transition.add(R.id.main_content_fl, homePageFragment);
        transition.commit();


        /**
         * 在这里添加new fragment 并添加到 fragmentList
         */
        fragmentList = new ArrayList<>();
        fragmentList.add(homePageFragment);
        //fragmentList.add(homePageFragment);
       // fragmentList.add(homePageFragment);
       // fragmentList.add(homePageFragment);
       // fragmentList.add(homePageFragment);


    }

    private void initView() {

        radioGroup = (RadioGroup) findViewById(R.id.main_bottom_rg);
        search = (Button) findViewById(R.id.main_top_search_btn);
        message = (Button) findViewById(R.id.main_top_msg_btn);
        addres = (Button) findViewById(R.id.main_top_address_btn);

    }

    /**
     * 首页头部的监听
     *
     * @param view
     */
    public void onClick(View view) {
        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.main_top_address_btn:
//                点击地址按钮，跳转mapActivity
                intent.setClass(this, MapActivity.class);
                startActivityForResult(intent, 3);
                break;
            case R.id.main_top_msg_btn:

                break;

            case R.id.main_top_search_btn:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3 && resultCode == 1) {
            initData();
        }
    }

    protected void switchFragment(int i) {
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = fragmentList.get(i);
        if (!fragment.isAdded()) {
            transaction.hide(fragmentList.get(curIndex)).add(R.id.main_content_fl, fragment);
        } else {
            transaction.hide(fragmentList.get(curIndex)).show(fragment);
        }
        transaction.commit();

        curIndex = i;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }
}
