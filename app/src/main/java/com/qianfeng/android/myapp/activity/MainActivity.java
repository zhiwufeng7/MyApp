package com.qianfeng.android.myapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qianfeng.android.myapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        finish();
    }
}
