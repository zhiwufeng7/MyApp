package com.qianfeng.android.myapp.adapter;

import android.content.Context;
import android.widget.Button;

import com.baidu.mapapi.map.Text;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.bean.City;

import java.util.List;

public class HotSearchAdapter extends CommonAdapter<String> {

	private Context context;

	public HotSearchAdapter(Context context, int layoutId, List<String> list) {
		super(context, list, layoutId);
		this.context = context;
	}

	@Override
	public void convert(ViewHolder holderM, String bean) {
		Button name = (Button) holderM.getView(R.id.hot_city_btn);
		name.setText(bean);
	}
}
