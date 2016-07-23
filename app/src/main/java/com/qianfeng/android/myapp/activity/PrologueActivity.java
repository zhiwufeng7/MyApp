package com.qianfeng.android.myapp.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qianfeng.android.myapp.R;

public class PrologueActivity extends AppCompatActivity {

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                Intent intent = new Intent(PrologueActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prologue);
        mHandler.sendEmptyMessageDelayed(1,2000);
    }
}
