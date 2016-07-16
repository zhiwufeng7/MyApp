package com.qianfeng.android.myapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.bean.PlotSearch;
import com.qianfeng.android.myapp.bean.SearchList;

import java.util.List;

public class SearchListAdapter extends CommonAdapter<SearchList.DataBean.ItemsBean> {

	private Context context;

	public SearchListAdapter(Context context, int layoutId, List<SearchList.DataBean.ItemsBean> list) {
		super(context, list, layoutId);
		this.context = context;
	}

	@Override
	public void convert(ViewHolder holderM, SearchList.DataBean.ItemsBean bean) {
		ImageView icon = (ImageView)holderM.getView(R.id.assortment_right_content_icon_iv);
		Glide.with(context).load(bean.getPic_url()).into(icon);
		TextView name = (TextView)holderM.getView(R.id.assortment_right_content_name_tv);
		name.setText(bean.getName());
		TextView descriptiond = (TextView)holderM.getView(R.id.assortment_right_content_description_tv);
		descriptiond.setText(bean.getDescription());
		TextView price = (TextView)holderM.getView(R.id.assortment_right_content_price_tv);
		String priceNum = String.valueOf(bean.getPrice());
		String originalPriceNum = String.valueOf(bean.getOriginalPrice());
		price.setText(String.valueOf(priceNum));
		TextView originalPrice = (TextView)holderM.getView(R.id.assortment_right_content_originalPrice_tv);
		if (!originalPriceNum.equals(priceNum)){
			originalPrice.setText("￥" +String.valueOf(originalPriceNum));
		}else {
			originalPrice.setText("");
		}
		TextView price_unit = (TextView)holderM.getView(R.id.assortment_right_content_price_unit_tv);
		price_unit.setText(bean.getPrice_unit());
		TextView serviceTitle = (TextView)holderM.getView(R.id.assortment_right_content_serviceTitle_tv);
		serviceTitle.setText(bean.getServiceTitle());
		TextView minBuyNum = (TextView)holderM.getView(R.id.assortment_right_content_salednum_tv);
		minBuyNum.setText(bean.getSalesNum()+"");
		TextView positiveCommentRate = (TextView)holderM.getView(R.id.assortment_right_content_positiveCommentRate_tv);
		if (bean.getSalesNum()==0){
			positiveCommentRate.setText("暂无评价");
		}else {
			positiveCommentRate.setText("好评"+bean.getPositiveCommentRate());

		}
	List<?> tagIcons = bean.getTagIcons();
		ImageView ticket1 = (ImageView)holderM.getView(R.id.assortment_right_content_ticket1_iv);
		ImageView ticket2 = (ImageView)holderM.getView(R.id.assortment_right_content_ticket2_iv);
		switch (tagIcons.size()) {
			case 0:
				ticket1.setImageDrawable(null);
				ticket2.setImageDrawable(null);
				break;
			case 1:
				Glide.with(context).load(tagIcons.get(0)).into(ticket1);
				ticket2.setImageDrawable(null);
				break;
			case 2:
				Glide.with(context).load(tagIcons.get(0)).into(ticket1);
				Glide.with(context).load(tagIcons.get(1)).into(ticket2);
				break;
		}



	}
}
