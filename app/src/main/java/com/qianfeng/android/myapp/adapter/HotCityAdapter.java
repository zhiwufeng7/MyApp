package com.qianfeng.android.myapp.adapter;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.bean.City;
import com.qianfeng.android.myapp.bean.Plots;

import java.util.List;

public class HotCityAdapter extends CommonAdapter<City> {

	private Context context;

	public HotCityAdapter(Context context, int layoutId, List<City> list) {
		super(context, list, layoutId);
		this.context = context;
	}

	@Override
	public void convert(ViewHolder holderM, City bean) {
		Button name = (Button) holderM.getView(R.id.hot_city_btn);
		name.setText(bean.getName());
	}
}
