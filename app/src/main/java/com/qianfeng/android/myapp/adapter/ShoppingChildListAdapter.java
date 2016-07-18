package com.qianfeng.android.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.dao.ShoppingCart;

import java.util.List;

/**
 * Created by my on 2016/7/18.
 */
public class ShoppingChildListAdapter extends BaseAdapter {

    private List<ShoppingCart> data;
    private Context mContext;
    private LayoutInflater inflater;

    public ShoppingChildListAdapter(Context context, List<ShoppingCart> data) {
        this.mContext = context;
        this.data = data;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView total=null;
        View view = convertView;
        if (position == data.size()) {
            view = inflater.inflate(R.layout.shopping_child_foot_view, parent, false);
            total = (TextView) view.findViewById(R.id.shopping_child_total);
            Button pay = (Button) view.findViewById(R.id.shopping_child_foot_btn);
            return view;
        }

        if (view == null) {
            view = inflater.inflate(R.layout.shopping_child_item, parent, false);
            
        }

        return view;
    }

    class ViewHolder {
        CheckBox checkBox;
        ImageView imageView;
        TextView title;
        TextView price;
        TextView num;
        Button add;
        Button minus;


    }
}
