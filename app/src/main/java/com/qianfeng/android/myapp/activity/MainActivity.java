package com.qianfeng.android.myapp.activity;


import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.fragment.HomePageFragment;


public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initFragment();

        initListener();


    }

    private void initListener() {
        //底部的radioGroup设置监听  实现fragment的切换
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.main_bottom_home_rb:
                        //点击首页按钮切换第一个fragment

                        break;
                    case R.id.main_bottom_assortment_rb:
                        //点击分类按钮切换第二个fragment

                        break;
                    case R.id.main_bottom_shopping_rb:
                        //点击购物车按钮切换第三个fragment


                        break;
                    case R.id.main_bottom_order_form_rb:
                        //点击订单按钮切换第四个fragment

                        break;
                    case R.id.main_bottom_my_rb:
                        //点击我的按钮切换第五个fragment

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
     *
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
