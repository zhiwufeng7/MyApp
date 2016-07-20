package com.qianfeng.android.myapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.bean.MaybeYouLikeInfo;

import java.util.List;

/**
 * Created by admin on 2016/7/20.
 */
public class MaybeYuoLikeAdapter extends RecyclerView.Adapter<MaybeYuoLikeAdapter.MyViewHolder>implements View.OnClickListener {

    private List<MaybeYouLikeInfo.DataBean> data;
    private Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public MaybeYuoLikeAdapter(List<MaybeYouLikeInfo.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.maybeyoulike_content, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
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

        holder.name.setText(data.get(position).getName());
        holder.description.setText(data.get(position).getDescription());
        holder.price.setText(price+" "+data.get(position).getPriceUnit());
        Glide.with(context).load(data.get(position).getPicUrl()).into(holder.picUrl);
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(data.get(position));

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
        public MyViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.maybeyoulike_name_tv);
            description = (TextView) itemView.findViewById(R.id.maybeyoulike_content_tv);
            price = (TextView) itemView.findViewById(R.id.maybeyoulike_price_tv);
            originalPrice = (TextView) itemView.findViewById(R.id.maybeyoulike_originalPrice_tv);
            picUrl = (ImageView) itemView.findViewById(R.id.maybeyoulike_pic);
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , MaybeYouLikeInfo.DataBean dataBean);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
