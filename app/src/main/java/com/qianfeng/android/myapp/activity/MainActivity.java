package com.qianfeng.android.myapp.activity;

import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.qianfeng.android.myapp.MyApplication;
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
        ((MyApplication)getApplication()).mLocationClient.start();
    }

    private void initListener() {

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
