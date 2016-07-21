package com.qianfeng.android.myapp.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.activity.ServiceDetailsActivity;
import com.qianfeng.android.myapp.bean.MaybeYouLikeInfo;
import com.qianfeng.android.myapp.dao.DaoMaster;
import com.qianfeng.android.myapp.dao.DaoSession;
import com.qianfeng.android.myapp.dao.ShoppingCart;
import com.qianfeng.android.myapp.dao.ShoppingCartDao;

import java.util.List;

/**
 * Created by admin on 2016/7/20.
 */
public class MaybeYuoLikeAdapter extends RecyclerView.Adapter<MaybeYuoLikeAdapter.MyViewHolder>implements View.OnClickListener {

    private List<MaybeYouLikeInfo.DataBean> data;
    private Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private TextView catNum;
    private String mShopName;

    private int buyNum = 0;
    private ShoppingCartDao shoppingCartDao;

    public MaybeYuoLikeAdapter(List<MaybeYouLikeInfo.DataBean> data, Context context,TextView catNum,String mShopName) {
        this.data = data;
        this.context = context;
        this.catNum = catNum;
        this.mShopName = mShopName;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.maybeyoulike_content, parent, false);
        //创建一个开发环境的Helper类，如果是正式环境调用DaoMaster.OpenHelper
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(context,"liuxiao",null);
        //通过Handler类获得数据库对象
        SQLiteDatabase readableDatabase = mHelper.getReadableDatabase();
        //通过数据库对象生成DaoMaster对象
        DaoMaster daoMaster = new DaoMaster(readableDatabase);
        //获取DaoSession对象
        DaoSession daoSession = daoMaster.newSession();
        //通过DaoSeesion对象获得CustomerDao对象
        shoppingCartDao = daoSession.getShoppingCartDao();
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        double iPrice = data.get(position).getPrice();
        double iOriginalPrice = data.get(position).getOriginalPrice();
        String price;
        String originalPrice;


        if (iPrice % 1.0 == 0) {
            price = String.valueOf((int) iPrice);
        } else {
            price = String.valueOf(iPrice);
        }

        if (iOriginalPrice % 1.0 == 0) {
            originalPrice = String.valueOf((int) iOriginalPrice);
        } else {
            originalPrice = String.valueOf(iOriginalPrice);
        }

        if (data.get(position).getPrice() != data.get(position).getOriginalPrice()) {
            holder.originalPrice.setText(originalPrice +" "+data.get(position).getPriceUnit());
        } else {
            holder.originalPrice.setText("");
        }
        final String name = data.get(position).getName();
        holder.name.setText(data.get(position).getName());
        holder.description.setText(data.get(position).getDescription());
        holder.price.setText(price+" "+data.get(position).getPriceUnit());
        Glide.with(context).load(data.get(position).getPicUrl()).into(holder.picUrl);
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(data.get(position));

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.buy.setVisibility(View.VISIBLE);
                holder.reduce.setVisibility(View.VISIBLE);
                String text = holder.buy.getText().toString().trim();
                int itemNum = Integer.parseInt(text);
                int minBuyNum= data.get(position).getMinBuyNum();
                if (itemNum==0){
                    itemNum=minBuyNum;
                }else {
                    itemNum+=1;
                }
                String mItemNum = itemNum +"";
                holder.buy.setText(mItemNum);
                shoppingCartDao.insertOrReplace(new ShoppingCart(Long.valueOf(data.get(position).getId()),
                        mShopName,data.get(position).getName(),data.get(position).getPrice()+"",data.get(position).getPicUrl(),
                        itemNum,minBuyNum
                ));
                ((ServiceDetailsActivity)context).refresh();
            }
        });

        holder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = holder.buy.getText().toString().trim();
                int itemNum = Integer.parseInt(text);
                int minBuyNum= data.get(position).getMinBuyNum();

                if (itemNum>minBuyNum){
                    itemNum-=1;
                    shoppingCartDao.insertOrReplace(new ShoppingCart(Long.valueOf(data.get(position).getId()),
                            mShopName,data.get(position).getName(),data.get(position).getPrice()+"",data.get(position).getPicUrl(),
                            itemNum,minBuyNum
                    ));
                }else {
                    holder.buy.setVisibility(View.GONE);
                    holder.reduce.setVisibility(View.GONE);
                    itemNum=0;
                    shoppingCartDao.deleteByKey(Long.valueOf(data.get(position).getId()));
                }
                holder.buy.setText(itemNum+"");
                ((ServiceDetailsActivity)context).refresh();

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size()==0?0:data.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (MaybeYouLikeInfo.DataBean) v.getTag());
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        TextView price;
        TextView originalPrice;
        ImageView picUrl;
        ImageView add;
        ImageView reduce;
        TextView buy;
        public MyViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.maybeyoulike_name_tv);
            description = (TextView) itemView.findViewById(R.id.maybeyoulike_content_tv);
            price = (TextView) itemView.findViewById(R.id.maybeyoulike_price_tv);
            originalPrice = (TextView) itemView.findViewById(R.id.maybeyoulike_originalPrice_tv);
            picUrl = (ImageView) itemView.findViewById(R.id.maybeyoulike_pic);
            add=(ImageView) itemView.findViewById(R.id.maybeyoulike_add_tv);
            reduce = (ImageView)itemView.findViewById(R.id.maybeyoulike_reduce_tv);
            buy = (TextView) itemView.findViewById(R.id.maybeyoulike_buy_tv);
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , MaybeYouLikeInfo.DataBean dataBean);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
