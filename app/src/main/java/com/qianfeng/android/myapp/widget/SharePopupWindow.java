package com.qianfeng.android.myapp.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.qianfeng.android.myapp.R;

/**
 * Created by admin on 2016/7/19.
 */
public class SharePopupWindow extends PopupWindow {
    private View mainView;
    private LinearLayout layoutShare, layoutCopy;

    public SharePopupWindow(Context context, View.OnClickListener paramOnClickListener, int paramInt1, int paramInt2){
        super(context);
        //窗口布局
        mainView = LayoutInflater.from(context).inflate(R.layout.share_popupwindow_layout, null);
        //分享布局
        layoutShare = ((LinearLayout)mainView.findViewById(R.id.connection_seller_layout));
        //复制布局
        layoutCopy = (LinearLayout)mainView.findViewById(R.id.share_layout);
        //设置每个子布局的事件监听器
        if (paramOnClickListener != null){
            layoutShare.setOnClickListener(paramOnClickListener);
            layoutCopy.setOnClickListener(paramOnClickListener);
        }
        setContentView(mainView);
        //设置宽度
        setWidth(paramInt1);
        //设置高度
        setHeight(paramInt2);
        //设置显示隐藏动画
        setAnimationStyle(R.style.AnimTools);
        //设置背景透明
        setBackgroundDrawable(new ColorDrawable(0));
    }
}
