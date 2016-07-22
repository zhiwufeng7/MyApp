package com.qianfeng.android.myapp.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
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

	public MyProgressDialog(Context context, int id) {
		super(context);
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

		mImageView.setBackgroundResource(mResid);
		mAnimation = (AnimationDrawable) mImageView.getBackground();
		mImageView.post(new Runnable() {
			@Override
			public void run() {
				mAnimation.start();
			}
		});
	}

	private void initView() {
		setContentView(R.layout.dialog_progress);
		mImageView = (ImageView) findViewById(R.id.iv_show);
	}

}
