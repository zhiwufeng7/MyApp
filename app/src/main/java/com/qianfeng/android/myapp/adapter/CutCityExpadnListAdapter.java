package com.qianfeng.android.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.bean.City;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/6/27.
 */
public class CutCityExpadnListAdapter extends BaseExpandableListAdapter {
    private Map<String,ArrayList<City>> datas = new HashMap<>();
    private List<String> groupNameDatas = new ArrayList<>();
    private Context mContext;

    public CutCityExpadnListAdapter(Context mContext, List<String> groupNameDatas, HashMap<String,ArrayList<City>> datas) {
        this.mContext=mContext;
        this.groupNameDatas=groupNameDatas;
        this.datas=datas;
    }

    /**
     * 返回分组的数量
     * @return
     */
    @Override
    public int getGroupCount() {
        return groupNameDatas == null ? 0 : groupNameDatas.size();
    }

    /**
     * 返回每一个组中的item的个数
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        String key = groupNameDatas.get(groupPosition);
        List<City> list = datas.get(key);
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupNameDatas.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return datas.get(groupNameDatas.get(groupPosition));
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

    /**
     * 创建分组的视图
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (convertView == null) {
            //给convertView赋值
            convertView= LayoutInflater.from(mContext).inflate(R.layout.city_list_group, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        //更新视图
        groupViewHolder.tv.setText(groupNameDatas.get(groupPosition));
        return convertView;
    }

    class GroupViewHolder {
        TextView tv;
        public GroupViewHolder(View view) {
            tv = (TextView) view.findViewById(R.id.city_list_group_tv);
        }

    }

    /**
     * 创建分组中的Item的视图
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            //给convertView赋值
            convertView= LayoutInflater.from(mContext).inflate(R.layout.city_list_child, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        //更新视图
        childViewHolder .tv.setText(datas.get(groupNameDatas.get(groupPosition)).get(childPosition).getName());
        return convertView;
    }

    class ChildViewHolder {
        TextView tv;
        public ChildViewHolder(View view) {
            tv = (TextView) view.findViewById(R.id.city_list_child_tv);
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
