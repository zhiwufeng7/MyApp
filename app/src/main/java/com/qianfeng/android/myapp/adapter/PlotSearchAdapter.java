package com.qianfeng.android.myapp.adapter;

import android.content.Context;
import android.widget.TextView;

import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.bean.PlotSearch;
import com.qianfeng.android.myapp.bean.Plots;

import java.util.List;

public class PlotSearchAdapter extends CommonAdapter<PlotSearch.DataBean> {

	private Context context;

	public PlotSearchAdapter(Context context, int layoutId, List<PlotSearch.DataBean> list) {
		super(context, list, layoutId);
		this.context = context;
	}

	@Override
	public void convert(ViewHolder holderM, PlotSearch.DataBean bean) {
		TextView name = (TextView) holderM.getView(R.id.nearby_plot_lv_item_name);
		TextView price = (TextView) holderM.getView(R.id.nearby_plot_lv_item_location);
		name.setText(bean.getName());
		price.setText(bean.getAddr());

	}
}
