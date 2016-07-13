package com.qianfeng.android.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.bean.HomeRecommendInfo;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by my on 2016/7/13.
 */
public class HomeRecommendAdapter extends BaseAdapter {
    private HomeRecommendInfo data;
    private LayoutInflater inflater;
    private Context context;

    public HomeRecommendAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setData(HomeRecommendInfo data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return data.getData();
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
            view = inflater.inflate(R.layout.home_page_header_recommend_grid_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (CircleImageView) view.findViewById(R.id.home_page_header_recommend_cv);
            viewHolder.title = (TextView) view.findViewById(R.id.home_page_header_recommend_title);
            viewHolder.service = (TextView) view.findViewById(R.id.home_page_header_recommend_server);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        HomeRecommendInfo.DataBean item = data.getData().get(position);
       Glide.with(context).load(item.getImg()).into(viewHolder.imageView);
        viewHolder.title.setText(item.getTitle());
        viewHolder.service.setText(item.getSubject());
        return view;
    }

    class ViewHolder {
        CircleImageView imageView;
        TextView title;
        TextView service;
    }
}
