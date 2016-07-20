package com.qianfeng.android.myapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.bean.MerchantDetails;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/6/29.
 */
public class MerchantRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder>{
    Context mContext;
    ArrayList<MerchantDetails.DataBean.PricesBean> datas;

    public MerchantRecyclerAdapter(Context mContext, ArrayList<MerchantDetails.DataBean.PricesBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.merchant_lv_item, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MerchantDetails.DataBean.PricesBean data = datas.get(position);
        Glide.with(mContext).load(data.getPic_url()).into(holder.icon);
        holder.tv_name.setText(data.getName());
        holder.tv_content.setText(data.getDescription());
        holder.tv_salesNum.setText("已售:"+data.getSalesNum());
        holder.price_unit.setText(data.getPrice_unit());
        String priceNum = String.valueOf(data.getPrice());
        String originalPriceNum = String.valueOf(data.getOriginalPrice());
        holder.price.setText(String.valueOf(priceNum));
        if (!originalPriceNum.equals(priceNum)){
            holder.originalPrice.setText(String.valueOf(originalPriceNum)+"元");
        }else {
            holder. originalPrice.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

}

class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView icon;
    TextView tv_name;
    TextView tv_content;
    TextView tv_salesNum;
    TextView price_unit;
    TextView price;
    TextView originalPrice;
    public MyViewHolder(View itemView) {
        super(itemView);
        icon = (ImageView)itemView.findViewById(R.id.merchant_lv_iv);
        tv_name = (TextView)itemView.findViewById(R.id.merchant_lv_tv_name);
        tv_content = (TextView)itemView.findViewById(R.id.merchant_lv_tv_content);
        tv_salesNum = (TextView)itemView.findViewById(R.id.merchant_lv_tv_salesNum);
        price_unit = (TextView)itemView.findViewById(R.id.merchant_lv_tv_price_unit);
        price = (TextView)itemView.findViewById(R.id.merchant_lv_tv_price);
        originalPrice = (TextView)itemView.findViewById(R.id.merchant_lv_tv_originalPrice);
    }
}

