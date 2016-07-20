package com.qianfeng.android.myapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.bean.MerchantDetails;
import com.qianfeng.android.myapp.bean.SearchList;

import java.util.List;

public class MerchantLVListAdapter extends CommonAdapter<MerchantDetails.DataBean.PricesBean> {

	private Context context;

	public MerchantLVListAdapter(Context context, int layoutId, List<MerchantDetails.DataBean.PricesBean> list) {
		super(context, list, layoutId);
		this.context = context;
	}

	@Override
	public void convert(ViewHolder holderM, MerchantDetails.DataBean.PricesBean bean) {
		ImageView icon = (ImageView)holderM.getView(R.id.merchant_lv_iv);
		Glide.with(mContext).load(bean.getPic_url()).into(icon);
		TextView tv_name = (TextView)holderM.getView(R.id.merchant_lv_tv_name);
		tv_name.setText(bean.getName());
		TextView tv_content = (TextView)holderM.getView(R.id.merchant_lv_tv_content);
		tv_content.setText(bean.getDescription());
		TextView tv_salesNum = (TextView)holderM.getView(R.id.merchant_lv_tv_salesNum);
		tv_salesNum.setText("已售:"+bean.getSalesNum());
		TextView price_unit = (TextView)holderM.getView(R.id.merchant_lv_tv_price_unit);
		price_unit.setText(bean.getPrice_unit());
		TextView price = (TextView)holderM.getView(R.id.merchant_lv_tv_price);
		String priceNum = String.valueOf(bean.getPrice());
		String originalPriceNum = String.valueOf(bean.getOriginalPrice());
		price.setText(String.valueOf(priceNum));
		TextView originalPrice = (TextView)holderM.getView(R.id.merchant_lv_tv_originalPrice);
		if (!originalPriceNum.equals(priceNum)){
			originalPrice.setText(String.valueOf(originalPriceNum)+"元");
		}else {
			originalPrice.setText("");
		}
	}
}
