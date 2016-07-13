package com.qianfeng.android.myapp.activity;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.qianfeng.android.myapp.R;


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


    }

    private void initFragment() {


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
