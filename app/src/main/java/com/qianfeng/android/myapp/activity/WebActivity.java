package com.qianfeng.android.myapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.qianfeng.android.myapp.R;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
        String url = intent.getStringExtra("web");
        WebView webView = (WebView) findViewById(R.id.web_wv);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);


    }
}
