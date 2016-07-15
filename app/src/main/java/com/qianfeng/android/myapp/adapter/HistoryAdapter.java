package com.qianfeng.android.myapp.adapter;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.qianfeng.android.myapp.R;

import java.util.List;

public class HistoryAdapter extends CommonAdapter<String> {

	private Context context;

	public HistoryAdapter(Context context, int layoutId, List<String> list) {
		super(context, list, layoutId);
		this.context = context;
	}

	@Override
	public void convert(ViewHolder holderM, String bean) {
		TextView name = (TextView) holderM.getView(R.id.history_item_tv);
		name.setText(bean);
	}
}
