package com.qianfeng.android.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.bean.HomePageEVInfo;

/**
 * Created by my on 2016/7/13.
 */
public class HomePullToRefreshExpandListViewAdapter extends BaseExpandableListAdapter {
    private HomePageEVInfo data;
    private Context mContext;
    private LayoutInflater inflater;

    public HomePullToRefreshExpandListViewAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(HomePageEVInfo data) {
        this.data = data;

    }


    @Override
    public int getGroupCount() {
        return data == null ? 0 : data.getData().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data == null ? 0 : 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.getData().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.getData().get(groupPosition).getItems().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        GroupViewHolder groupViewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.home_page_group_view, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.mLeftTxt = (TextView) view.findViewById(R.id.home_page_group_left);
            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        groupViewHolder.mLeftTxt.setText(data.getData().get(groupPosition).getCategoryName());
        return view;
    }

    class GroupViewHolder {
        TextView mLeftTxt;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        View view = convertView;
        ChildViewHolder childViewHolder;
        HomeGridViewAdapter adapter;
        if (view == null) {
            view = inflater.inflate(R.layout.home_page_child_view, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.gridView = (GridView) view.findViewById(R.id.home_page_child_gv);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        adapter = new HomeGridViewAdapter(mContext);
        adapter.setData(data.getData().get(groupPosition));
        childViewHolder.gridView.setAdapter(adapter);

        return view;
    }

    class ChildViewHolder {
        GridView gridView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
