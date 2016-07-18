package com.qianfeng.android.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.dao.ShoppingCart;

import java.util.List;

/**
 * Created by my on 2016/7/18.
 */
public class ShoppingCartEVAdapter extends BaseExpandableListAdapter {

    private List<String> groupList;
    private List<List<ShoppingCart>> childList;
    private Context mContext;
    private LayoutInflater inflater;


    public ShoppingCartEVAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getGroupCount() {
        return groupList == null ? 0 : groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition) == null ? 0 : 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
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
        GroupViewHolder groupViewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.shopping_group_view, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.title = (TextView) view.findViewById(R.id.shopping_group_title);
            groupViewHolder.checkBox = (CheckBox) view.findViewById(R.id.shopping_group_cb);
            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }

        groupViewHolder.title.setText(groupList.get(groupPosition));
        groupViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //在这里写CheckBox的监听
            }
        });


        return view;
    }

    class GroupViewHolder {
        TextView title;
        CheckBox checkBox;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        ChildViewHolder childViewHolder;
//        HomeGridViewAdapter adapter;
        if (view == null) {
            view = inflater.inflate(R.layout.shopping_child_view, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.listView = (ListView) view.findViewById(R.id.shopping_child_lv);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
//        adapter = new HomeGridViewAdapter(mContext);
//        adapter.setData(data.getData().get(groupPosition));
//        childViewHolder.listView.setAdapter(adapter);

        return view;
    }

    class ChildViewHolder {
       ListView listView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
