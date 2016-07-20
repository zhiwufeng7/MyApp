package com.qianfeng.android.myapp.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.activity.MainActivity;
import com.qianfeng.android.myapp.dao.DaoMaster;
import com.qianfeng.android.myapp.dao.DaoSession;
import com.qianfeng.android.myapp.dao.ShoppingCart;
import com.qianfeng.android.myapp.dao.ShoppingCartDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ShoppingCartEVAdapter extends BaseExpandableListAdapter {

    private final DaoMaster daoMaster;
    private List<String> groupList;
    private Map<String, List<ShoppingCart>> childData;
    private Context mContext;
    private LayoutInflater inflater;
    private List<Boolean> groupFlag;
    private Map<Integer, List<Boolean>> childFlag;
    private int sum = 0;
    private int isChange = -1;
    private List<ShoppingCart> data;
    private ShoppingCartDao shoppingCartDao;

    public ShoppingCartEVAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(mContext, "liuxiao", null);
        //通过Handler类获得数据库对象
        SQLiteDatabase readableDatabase = mHelper.getReadableDatabase();
        //通过数据库对象生成DaoMaster对象
        daoMaster = new DaoMaster(readableDatabase);
        //获取DaoSession对象

        initData();


    }

    public void initData() {
        DaoSession daoSession = daoMaster.newSession();
        //通过DaoSeesion对象获得CustomerDao对象
        shoppingCartDao = daoSession.getShoppingCartDao();
        data = shoppingCartDao.loadAll();

        getGroupList(data);
        getChildList(data, groupList);

        //刷新购物车角标
        MainActivity activity = (MainActivity) mContext;
        activity.initShoppingCart();
        initFlag();
    }


    private void initFlag() {
        int groupLength = groupList.size();
        if (groupFlag == null) {
            groupFlag = new ArrayList<>();
        } else {
            groupFlag.clear();
        }

        for (int i = 0; i < groupLength; i++) {
            groupFlag.add(i, true);
        }
        childFlag = new HashMap<>();
        for (int i = 0; i < groupLength; i++) {
            int childLength = childData.get(groupList.get(i)).size();
            List<Boolean> child = new ArrayList<>();
            for (int j = 0; j < childLength; j++) {
                child.add(j, true);
            }
            childFlag.put(i, child);
        }


    }

    private void getChildList(List<ShoppingCart> data, List<String> groupList) {
        if (childData == null) {
            childData = new HashMap<>();
        } else {
            childData.clear();
        }
        for (String str : groupList) {
            List<ShoppingCart> carts = new ArrayList<>();

            for (ShoppingCart shoppingCart : data) {

                String title = shoppingCart.getServiceTitle();

                if (str.equals(title)) {

                    carts.add(shoppingCart);
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

                }
                if (flag) {
                    groupList.add(title);
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
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
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

        groupViewHolder.checkBox.setChecked(groupFlag.get(groupPosition));

        groupViewHolder.title.setText(" " + groupList.get(groupPosition));


        groupViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击group的checkbox的时候让checked改变
                CheckBox checkBox = (CheckBox) v;
                isChange = groupPosition;
                groupFlag.add(groupPosition, checkBox.isChecked());
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
        if (childPosition == 0) {
            sum = 0;
            if (isChange == groupPosition) {
                boolean flag = groupFlag.get(groupPosition);
                List<Boolean> child = childFlag.get(groupPosition);
                for (int i = 0; i < child.size(); i++) {
                    child.add(i, flag);
                }
                isChange = -1;
            }
        }


        View view =null;
        ViewHolder viewHolder;


        List<ShoppingCart> list = childData.get(groupList.get(groupPosition));
        if (isLastChild) {
            view = inflater.inflate(R.layout.shopping_child_foot_view, parent, false);
            TextView total = (TextView) view.findViewById(R.id.shopping_child_total);
            Button pay = (Button) view.findViewById(R.id.shopping_child_foot_btn);
            total.setText("¥ " + sum + "");
            return view;
        }


        if (view == null || view.getTag() == null) {
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
        viewHolder.price.setText("¥ " + item.getOriginalPrice() + "");
        viewHolder.num.setText(item.getBuyNum() + "");
        viewHolder.checkBox.setChecked(childFlag.get(groupPosition).get(childPosition));

        if (viewHolder.checkBox.isChecked()) {
            double num = Double.valueOf(item.getOriginalPrice());
            sum += (num * item.getBuyNum());

        }

        //点击 + 按钮的监听
        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShoppingCart shoppingCart = childData.get(groupList.get(groupPosition)).get(childPosition);
                shoppingCart.setBuyNum(shoppingCart.getBuyNum() + 1);
                shoppingCartDao.insertOrReplace(shoppingCart);
                initData();
                notifyDataSetChanged();
            }
        });
        //点击 - 按钮的监听
        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ShoppingCart shoppingCart = childData.get(groupList.get(groupPosition)).get(childPosition);
                int min = shoppingCart.getMinBuyNum();
                if (min == shoppingCart.getBuyNum()) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("是否删除该商品")
                            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    shoppingCartDao.deleteByKey(Long.valueOf(shoppingCart.getId()));

                                    initData();
                                    notifyDataSetChanged();
                                }
                            });

                    builder.create().show();

                } else {

                    shoppingCart.setBuyNum(shoppingCart.getBuyNum() - 1);
                    shoppingCartDao.insertOrReplace(shoppingCart);
                    initData();
                    notifyDataSetChanged();
                }


            }
        });

        //左边的选中按钮的监听
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                boolean isCheck = checkBox.isChecked();
                List<Boolean> child = childFlag.get(groupPosition);
                if (isCheck) {
                    groupFlag.add(groupPosition, true);
                    child.add(childPosition, true);
                } else {
                    child.add(childPosition, false);
                    for (boolean boo : child) {
                        if (boo) {
                            isCheck = true;
                            break;
                        }
                    }
                    if (!isCheck) {
                        groupFlag.add(groupPosition, false);
                    }
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
