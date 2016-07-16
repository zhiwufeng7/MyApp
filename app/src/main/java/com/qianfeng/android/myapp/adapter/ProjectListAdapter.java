package com.qianfeng.android.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.bean.AssortmentRightLV;

import java.util.List;

/**
 * Created by chengxiao on 2016/7/13.
 */
public class ProjectListAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater inflater;
    private List<AssortmentRightLV.DataBean.ItemsBean> items;

    public ProjectListAdapter(Context context, List<AssortmentRightLV.DataBean.ItemsBean> items) {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size()==0?0:items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderEvent viewHolderEvent = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.assortment_right_listview_content, parent, false);
            viewHolderEvent = new ViewHolderEvent();
            viewHolderEvent.nullView =(TextView)convertView.findViewById(R.id.assortment_right_content_null_view);
            viewHolderEvent.name = (TextView) convertView.findViewById(R.id.assortment_right_content_name_tv);
            viewHolderEvent.description = (TextView) convertView.findViewById(R.id.assortment_right_content_description_tv);
            viewHolderEvent.price = (TextView) convertView.findViewById(R.id.assortment_right_content_price_tv);
            viewHolderEvent.price_unit = (TextView) convertView.findViewById(R.id.assortment_right_content_price_unit_tv);
            viewHolderEvent.originalPrice = (TextView) convertView.findViewById(R.id.assortment_right_content_originalPrice_tv);
            viewHolderEvent.saledNum = (TextView) convertView.findViewById(R.id.assortment_right_content_salednum_tv);
            viewHolderEvent.serviceTitle = (TextView) convertView.findViewById(R.id.assortment_right_content_serviceTitle_tv);
            viewHolderEvent.positiveCommentRate = (TextView) convertView.findViewById(R.id.assortment_right_content_positiveCommentRate_tv);
            viewHolderEvent.ticket1= (ImageView) convertView.findViewById(R.id.assortment_right_content_ticket1_iv);
            viewHolderEvent.ticket2= (ImageView) convertView.findViewById(R.id.assortment_right_content_ticket2_iv);
            viewHolderEvent.icon= (ImageView) convertView.findViewById(R.id.assortment_right_content_icon_iv);
            convertView.setTag(viewHolderEvent);
        } else {
            viewHolderEvent = (ViewHolderEvent) convertView.getTag();
        }
        //价格若为小数点后显示小数，若无小数，显示整数。
        double iPrice = items.get(position).getPrice();
        double iOriginalPrice = items.get(position).getOriginalPrice();
        String price;
        String originalPrice;
        if(iPrice % 1.0 == 0){
            price = String.valueOf((int) iPrice);
        }else {
            price = String.valueOf(iPrice);
        }

        if(iOriginalPrice % 1.0 == 0){
            originalPrice = String.valueOf((int) iOriginalPrice);
        }else {
            originalPrice = String.valueOf(iOriginalPrice);
        }

        if (items.get(position).getPrice()!=items.get(position).getOriginalPrice()){
            viewHolderEvent.originalPrice.setText("￥"+originalPrice);
        }else{
            viewHolderEvent.originalPrice.setText("");
        }

        viewHolderEvent.name.setText(items.get(position).getName());
        viewHolderEvent.description.setText(items.get(position).getDescription());
        viewHolderEvent.price.setText(price);
        viewHolderEvent.price_unit.setText(items.get(position).getPrice_unit());
        viewHolderEvent.saledNum.setText(items.get(position).getBooster().getSaledNum()+"");
        viewHolderEvent.serviceTitle.setText(items.get(position).getServiceTitle());
        viewHolderEvent.positiveCommentRate.setText("好评"+items.get(position).getPositiveCommentRate());
        int size = items.get(position).getTagIcons().size();
        switch (size){
            case 0:
                viewHolderEvent.ticket1.setImageDrawable(null);
                viewHolderEvent.ticket2.setImageDrawable(null);
                break;
            case 1:
                Glide.with(context).load(items.get(position).getTagIcons().get(0)).into(viewHolderEvent.ticket1);
                viewHolderEvent.ticket2.setImageDrawable(null);
                break;
            case 2:
                Glide.with(context).load(items.get(position).getTagIcons().get(0)).into(viewHolderEvent.ticket1);
                Glide.with(context).load(items.get(position).getTagIcons().get(1)).into(viewHolderEvent.ticket2);
                break;

        }
        Glide.with(context).load(items.get(position).getPic_url()).into(viewHolderEvent.icon);
        return convertView;
    }


    static class ViewHolderEvent{
        TextView nullView;
        TextView name;
        TextView description;
        TextView price;
        TextView price_unit;
        TextView originalPrice;
        TextView saledNum;
        TextView serviceTitle;
        TextView positiveCommentRate;
        ImageView icon;
        ImageView ticket1;
        ImageView ticket2;

    }

}
