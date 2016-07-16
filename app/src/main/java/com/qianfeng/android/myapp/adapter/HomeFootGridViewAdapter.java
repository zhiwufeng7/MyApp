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
import com.qianfeng.android.myapp.bean.FootInfo;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/13.
 */
public class HomeFootGridViewAdapter extends BaseAdapter {

    private List<FootInfo.DataBean> data;
    private LayoutInflater inflater;
    private Context context;

    public HomeFootGridViewAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    public void upData(List<FootInfo.DataBean> list) {
        if (data ==null){
        data=new ArrayList<>();
        }
        data.addAll(list);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
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
            view = inflater.inflate(R.layout.home_foot_view_item, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.imageView = (ImageView) view.findViewById(R.id.home_page_foot_iv);
            viewHolder.tag = new ImageView[2];
            viewHolder.tag[0] = (ImageView) view.findViewById(R.id.home_page_foot_tagIcon1);
            viewHolder.tag[1] = (ImageView) view.findViewById(R.id.home_page_foot_tagIcon2);
            viewHolder.title = (TextView) view.findViewById(R.id.home_page_foot_title);
            viewHolder.count = (TextView) view.findViewById(R.id.home_page_foot_count);
            viewHolder.evaluate = (TextView) view.findViewById(R.id.home_page_foot_evaluate);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        FootInfo .DataBean item = data.get(position);
        Glide.with(context).load(item.getImgUrl()).into(viewHolder.imageView);
        viewHolder.title.setText(" "+item.getTitle());
        viewHolder.count.setText("已接" + item.getOrderTakingCount() + "单");
        viewHolder.evaluate.setText("好评" + item.getPositiveCommentRate());
        List list = item.getTagIcons();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                String str = (String) list.get(i);
                Glide.with(context).load(str).into(viewHolder.tag[i]);
            }
        }
        return view;
    }

    class ViewHolder {
        ImageView imageView;
        ImageView[] tag;
        TextView title;
        TextView count;
        TextView evaluate;
    }
}
