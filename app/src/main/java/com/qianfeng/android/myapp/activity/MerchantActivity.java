package com.qianfeng.android.myapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.ecloud.pulltozoomview.TransparentToolBar;
import com.qianfeng.android.myapp.R;


public class MerchantActivity extends AppCompatActivity {


    private TransparentToolBar mTransparentToolBar;
    private ConvenientBanner mConvenientBanner;
    private PullToZoomScrollViewEx scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        scrollView = (PullToZoomScrollViewEx) findViewById(R.id.merchant_scroll_view);
        View zoomView = LayoutInflater.from(this).inflate(R.layout.activity_merchant_zoom, null, false);
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_merchant_content, null, false);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth,mScreenWidth);
        scrollView.setHeaderLayoutParams(localObject);
        ScrollView pullRootView = scrollView.getPullRootView();
        PullToZoomScrollViewEx.InternalScrollView internalScrollView = (PullToZoomScrollViewEx.InternalScrollView) pullRootView;
        mTransparentToolBar = (TransparentToolBar) findViewById(R.id.merchant_bar);

        //必须设置ToolBar颜色值，也就是0~1透明度变化的颜色值
        mTransparentToolBar.setBgColor(getResources().getColor(R.color.black));

        //必须设置ToolBar最大偏移量，这会影响到0~1透明度变化的范围
        mTransparentToolBar.setOffset(mScreenWidth);
        internalScrollView.setTitleBar(mTransparentToolBar);
    }
}
