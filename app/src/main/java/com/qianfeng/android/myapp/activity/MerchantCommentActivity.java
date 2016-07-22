package com.qianfeng.android.myapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qianfeng.android.myapp.R;

public class MerchantCommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_comment);

    }

    public void onBack(View v){
        finish();
    }
}
