package com.qianfeng.android.myapp.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.bean.CommentList;
import com.qianfeng.android.myapp.bean.MerchantDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class CommentListAdapter extends CommonAdapter<CommentList.DataBean.CommentsBean> {

    private Context context;

    public CommentListAdapter(Context context, int layoutId, List<CommentList.DataBean.CommentsBean> list) {
        super(context, list, layoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holderM, CommentList.DataBean.CommentsBean bean) {
        ImageView icon = (ImageView) holderM.getView(R.id.comment_icon);
        Glide.with(mContext).load(bean.getIconUrl()).placeholder(R.drawable.icon_head_comment).into(icon);
        TextView tv_name = (TextView) holderM.getView(R.id.comment_name);
        tv_name.setText(bean.getNick());
        TextView tv_content = (TextView) holderM.getView(R.id.comment_content);
        tv_content.setText(bean.getComment());
        TextView tv_time = (TextView) holderM.getView(R.id.comment_time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        tv_time.setText(sdf.format(bean.getCreatetime()));
        ArrayList<ImageView> stars = new ArrayList<>();
        stars.add((ImageView) holderM.getView(R.id.comment_star1));
        stars.add((ImageView) holderM.getView(R.id.comment_star2));
        stars.add((ImageView) holderM.getView(R.id.comment_star3));
        stars.add((ImageView) holderM.getView(R.id.comment_star4));
        stars.add((ImageView) holderM.getView(R.id.comment_star5));
        for (int i = 0; i < bean.getStar(); i++) {
            stars.get(i).setImageDrawable(mContext.getResources().getDrawable(R.drawable.img_star_3));
        }
    }
}
