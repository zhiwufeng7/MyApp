package com.qianfeng.android.myapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.activity.MainActivity;
import com.qianfeng.android.myapp.dao.ShoppingCart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ShoppingCartEVAdapter extends BaseExpandableListAdapter {

    private List<String> groupList;
    private Map<String, List<ShoppingCart>> childData;
    private Context mContext;
    private LayoutInflater inflater;
    private boolean[] groupFlag;
    private boolean[] childFlag;
    private int sum = 0;
    private  int isChange=-1;


    public ShoppingCartEVAdapter(Context context, List<ShoppingCart> data) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        getGroupList(data);
        getChildList(data, groupList);
        groupFlag = new boolean[groupList.size()];

        for (int i = 0; i < groupFlag.length; i++) {
            groupFlag[i] = true;
        }

    MainActivity activity=(MainActivity)mContext;
        activity.initShoppingCart();
    }

    private void getChildList(List<ShoppingCart> data, List<String> groupList) {
        if (childData == null) {
            childData = new HashMap<>();

        }
        for (String str : groupList) {
            List<ShoppingCart> carts = new ArrayList<>();

            for (ShoppingCart shoppingCart : data) {

                String title = shoppingCart.getServiceTitle();

                if (str.equals(title)) {

                    carts.add(shoppingCart);
                    break;
                }
            }

            childData.put(str, carts);
        }
    }

    private void getGroupList(List<ShoppingCart> data) {
        if (groupList == null) {
            groupList = new ArrayList<>();
        } else {
            groupList.clear();
        }
        for (ShoppingCart shoppingCart : data) {
            String title = shoppingCart.getServiceTitle();
            if (groupList.size() == 0) {
                groupList.add(title);
            } else {
                boolean flag = true;
                for (String str : groupList) {

                    if (title.equals(str)) {
                        flag = false;
                        break;
                    }
                    if (flag) {
                        groupList.add(title);
                    }
                }
            }
        }
    }

    @Override
    public int getGroupCount() {

        return groupList == null ? 0 : groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        List<ShoppingCart> list = childData.get(groupList.get(groupPosition));
        return list == null ? 0 : list.size() + 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupList.get(groupPosition));
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
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

        groupViewHolder.checkBox.setChecked(groupFlag[groupPosition]);

        groupViewHolder.title.setText(" " + groupList.get(groupPosition));
        groupViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                groupFlag[groupPosition] = isChecked;
                if (isChecked) {
                    isChange = groupPosition;
                }
                notifyDataSetChanged();
            }
        });


        return view;
    }

    class GroupViewHolder {
        TextView title;
        CheckBox checkBox;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
      if (childPosition==0){
          childFlag=new boolean[childData.get(groupList.get(groupPosition)).size()];
          for (int i = 0; i < childFlag.length; i++) {
              childFlag[i] = true;
          }

      }

        TextView total = null;
        View view = convertView;
        ViewHolder viewHolder;


        List<ShoppingCart> list = childData.get(groupList.get(groupPosition));
        if (isLastChild) {
            view = inflater.inflate(R.layout.shopping_child_foot_view, parent, false);
            total = (TextView) view.findViewById(R.id.shopping_child_total);
            Button pay = (Button) view.findViewById(R.id.shopping_child_foot_btn);
            total.setText("¥ "+sum + "");
            return view;
        }

        if (view == null) {
            view = inflater.inflate(R.layout.shopping_child_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.shopping_child_cb);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.shopping_child_iv);
            viewHolder.title = (TextView) view.findViewById(R.id.shopping_child_title_tv);
            viewHolder.price = (TextView) view.findViewById(R.id.shopping_child_price_tv);
            viewHolder.num = (TextView) view.findViewById(R.id.shopping_child_num);
            viewHolder.add = (Button) view.findViewById(R.id.shopping_child_add);
            viewHolder.minus = (Button) view.findViewById(R.id.shopping_child_minus);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ShoppingCart item = list.get(childPosition);
        Glide.with(mContext).load(item.getImageUrl()).into(viewHolder.imageView);
        viewHolder.title.setText(item.getName());
        viewHolder.price.setText(item.getOriginalPrice() + "");
        viewHolder.num.setText(item.getBuyNum() + "");




        if (groupFlag[groupPosition]) {
            if (viewHolder.checkBox.isChecked()) {
                int num = Integer.valueOf(item.getOriginalPrice());
                sum += (num * item.getBuyNum());

            }

        } else {
            viewHolder.checkBox.setChecked(false);
        }
        if (isChange==groupPosition){
            viewHolder.checkBox.setChecked(true);
            isChange=-1;
        }

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (! isChecked){
                childFlag[childPosition]=false;
                for (boolean boo: childFlag){
                    if (boo){
                        break;
                    }
                    groupFlag[groupPosition]=false;
                }

                }else {
                        groupFlag[groupPosition]=true;
                }
                notifyDataSetChanged();
            }
        });
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

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
