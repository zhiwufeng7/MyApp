package com.qianfeng.android.myapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.dao.CollectionInfo;
import com.qianfeng.android.myapp.dao.CollectionInfoDao;
import com.qianfeng.android.myapp.dao.DaoMaster;
import com.qianfeng.android.myapp.dao.DaoSession;

import java.util.List;

/**
 * Created by my on 2016/7/22.
 */
public class CollectionAdapter extends BaseAdapter {
    private final DaoSession daoSession;
    private List<CollectionInfo> data;
    private LayoutInflater inflater;
    private Context context;
    private CollectionInfoDao collectionInfoDao;

    public CollectionAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(context, "liuxiao", null);
        //通过Handler类获得数据库对象
        SQLiteDatabase readableDatabase = mHelper.getReadableDatabase();
        //通过数据库对象生成DaoMaster对象
        DaoMaster daoMaster = new DaoMaster(readableDatabase);
        daoSession = daoMaster.newSession();
        //通过DaoSeesion对象获得CustomerDao对象
        initData();
    }

    private void initData() {

        collectionInfoDao = daoSession.getCollectionInfoDao();
        data = collectionInfoDao.loadAll();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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

        if (!TextUtils.isEmpty(url1)) {
            Glide.with(context).load(url1).into(viewHolder.tag[0]);
        }
        if (!TextUtils.isEmpty(url2)) {
            Glide.with(context).load(url2).into(viewHolder.tag[1]);
        }
        viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("是否删除该商品")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                collectionInfoDao.deleteByKey(data.get(position).getId());

                                initData();
                                notifyDataSetChanged();
                            }
                        });
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
