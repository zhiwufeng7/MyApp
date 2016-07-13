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
import com.qianfeng.android.myapp.bean.HomePageEVInfo;

/**
 * Created by my on 2016/7/13.
 */
public class HomeGridViewAdapter extends BaseAdapter {

    private HomePageEVInfo.DataBean data;
    private LayoutInflater inflater;
    private Context context;

    public HomeGridViewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context=context;
    }
    public void setData(HomePageEVInfo.DataBean data){
        this.data=data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.getItems().size();
    }

    @Override
    public Object getItem(int position) {
        return data.getItems().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        ViewHolder viewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.home_child_gridview_item, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView= (ImageView) view.findViewById(R.id.child_item_iv);
            viewHolder.name= (TextView) view.findViewById(R.id.child_item_tv_name);
            viewHolder.unit = (TextView) view.findViewById(R.id.child_item_tv_unit);
            viewHolder.price=(TextView) view.findViewById(R.id.child_item_tv_price);
            viewHolder.serviceTitle= (TextView) view.findViewById(R.id.child_item_tv_serviceTitle);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        HomePageEVInfo.DataBean.ItemsBean item=data.getItems().get(position);
        Glide.with(context).load(item.getPic_url()).into(viewHolder.imageView);
        viewHolder.name.setText(item.getName());
        viewHolder.serviceTitle.setText(item.getServiceTitle());
        viewHolder.unit.setText(item.getPrice_unit());
        viewHolder.price.setText(item.getPrice()+"  ");
        return view;
    }
    class ViewHolder {
        ImageView imageView;
        TextView price;
        TextView unit;
        TextView serviceTitle;
        TextView name;
    }
}
