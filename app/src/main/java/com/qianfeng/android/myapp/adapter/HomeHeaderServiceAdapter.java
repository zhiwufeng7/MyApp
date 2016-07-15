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
import com.qianfeng.android.myapp.bean.HomeServiceInfo;

/**
 * Created by my on 2016/7/13.
 */
public class HomeHeaderServiceAdapter extends BaseAdapter{

    private HomeServiceInfo data;
    private LayoutInflater inflater;
    private Context context;

    public HomeHeaderServiceAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context=context;
    }
    public void setData(HomeServiceInfo data){
        this.data=data;
    }

    @Override
    public int getCount() {
        return data==null? 0:10;
    }

    @Override
    public Object getItem(int position) {
        return data.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.home_header_mygrid_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mLeftTxt = (TextView) view.findViewById(R.id.home_page_header_my_grid_tv);
            viewHolder.imageView= (ImageView) view.findViewById(R.id.home_page_header_my_grid_iv);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        HomeServiceInfo.DataBean item=data.getData().get(position);
        viewHolder.mLeftTxt.setText(item.getName());
        Glide.with(context).load(item.getIconUrl2()).into(viewHolder.imageView);
        return view;
    }

    class ViewHolder {
        TextView mLeftTxt;
        ImageView imageView;
    }
}
