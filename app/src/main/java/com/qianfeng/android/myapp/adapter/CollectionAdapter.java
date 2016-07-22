package com.qianfeng.android.myapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.dao.CollectionInfo;

import java.util.List;

/**
 * Created by my on 2016/7/22.
 */
public class CollectionAdapter extends BaseAdapter {
    private List<CollectionInfo> data;
    private LayoutInflater inflater;
    private Context context;

    public CollectionAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setData(List<CollectionInfo> data) {
        this.data = data;
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
            view = inflater.inflate(R.layout.collection_gv_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.collection_top_iv);
            viewHolder.tag = new ImageView[2];
            viewHolder.tag[0] = (ImageView) view.findViewById(R.id.collection_tagIcon1);
            viewHolder.tag[1] = (ImageView) view.findViewById(R.id.collection_tagIcon2);
            viewHolder.title = (TextView) view.findViewById(R.id.collection_title);
            viewHolder.count = (TextView) view.findViewById(R.id.collection_count);
            viewHolder.evaluate = (TextView) view.findViewById(R.id.collection_evaluate);
            viewHolder.imageButton = (ImageButton) view.findViewById(R.id.collection_ib);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        CollectionInfo item = data.get(position);
        Glide.with(context).load(item.getImage()).into(viewHolder.imageView);

        viewHolder.title.setText(" " + item.getServiceTitle());
        viewHolder.count.setText("已接" + item.getCount() + "单");
        viewHolder.evaluate.setText("好评" + item.getEvaluate());

        String url1 = item.getTag1();
        String url2 = item.getTag2();

        if (TextUtils.isEmpty(url1)) {
            Glide.with(context).load(url1).into(viewHolder.tag[0]);
        }
        if (TextUtils.isEmpty(url2)) {
            Glide.with(context).load(url2).into(viewHolder.tag[1]);
        }
        viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "点什么点", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    class ViewHolder {
        ImageView imageView;
        ImageView[] tag;
        TextView title;
        TextView count;
        TextView evaluate;
        ImageButton imageButton;
    }
}
