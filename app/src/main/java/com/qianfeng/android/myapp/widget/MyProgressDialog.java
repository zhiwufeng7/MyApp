package com.qianfeng.android.myapp.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;

import com.qianfeng.android.myapp.R;



/**
 * @Description:自定义对话框
 * @author http://blog.csdn.net/finddreams
 */
public class MyProgressDialog extends ProgressDialog {

	private AnimationDrawable mAnimation;
	private Context mContext;
	private ImageView mImageView;
	private int mResid;
	private Handler handler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			MyProgressDialog.this.dismiss();
			return false;
		}
	});

	public MyProgressDialog(Context context, int id) {
		super(context, R.style.Dialog_Fullscreen);
		setOwnerActivity((Activity) context);
		this.mContext = context;
		this.mResid = id;
		setCanceledOnTouchOutside(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}


	private void initData() {

		mImageView.setImageDrawable(mContext.getResources().getDrawable(mResid));
		mAnimation = (AnimationDrawable) mImageView.getDrawable();
		mImageView.post(new Runnable() {
			@Override
			public void run() {
				mAnimation.start();
				handler.sendEmptyMessageDelayed(0,2000);
			}
		});
	}

	private void initView() {
		setContentView(R.layout.dialog_progress);
		mImageView = (ImageView) findViewById(R.id.iv_show);
	}
}
