package com.qianfeng.android.myapp.adapter;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.activity.MerchantActivity;
import com.qianfeng.android.myapp.activity.ServiceDetailsActivity;
import com.qianfeng.android.myapp.bean.MerchantDetails;
import com.qianfeng.android.myapp.dao.DaoMaster;
import com.qianfeng.android.myapp.dao.DaoSession;
import com.qianfeng.android.myapp.dao.ShoppingCart;
import com.qianfeng.android.myapp.dao.ShoppingCartDao;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/6/29.
 */
public class MerchantRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private String title;
    private String id;
    Context mContext;
    ArrayList<MerchantDetails.DataBean.PricesBean> datas;
    private ShoppingCartDao shoppingCartDao;
    private ImageView ball;

    public MerchantRecyclerAdapter(Context mContext, ArrayList<MerchantDetails.DataBean.PricesBean> datas,
                                   String id,String title) {
        this.mContext = mContext;
        this.datas = datas;
        this.id=id;
        this.title=title;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.merchant_lv_item, null);
        //创建一个开发环境的Helper类，如果是正式环境调用DaoMaster.OpenHelper
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(mContext,"liuxiao",null);
        //通过Handler类获得数据库对象
        SQLiteDatabase readableDatabase = mHelper.getReadableDatabase();
        //通过数据库对象生成DaoMaster对象
        DaoMaster daoMaster = new DaoMaster(readableDatabase);
        //获取DaoSession对象
        DaoSession daoSession = daoMaster.newSession();
        //通过DaoSeesion对象获得CustomerDao对象
        shoppingCartDao = daoSession.getShoppingCartDao();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
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

        ShoppingCart shoppingCart = shoppingCartDao.loadByRowId(Long.valueOf(data.getId()));
        if (shoppingCart!=null){
            holder.num.setText(shoppingCart.getBuyNum()+"");
        }else {
            holder.num.setText("0");
        }
        //图片点击跳转

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag =position;
                Intent intent = new Intent(mContext, ServiceDetailsActivity.class);
                String mId = datas.get(tag).getId();
                String serviceId = id;
                intent.putExtra("id",mId);
                intent.putExtra("serviceId",serviceId);
                mContext.startActivity(intent);
            }
        });


        //购物点击
        holder.add.setTag(position);
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag =(int) v.getTag();
                MerchantDetails.DataBean.PricesBean pricesBean = datas.get(tag);
                String string = holder.num.getText().toString();
                Integer num = Integer.valueOf(string);
                String minBuyNum = pricesBean.getMinBuyNum();
                Integer min = Integer.valueOf(minBuyNum);
                if (num==0){
                    num=min;
                }else {
                    num+=1;
                }
                holder.num.setText(num+"");
                shoppingCartDao.insertOrReplace(new ShoppingCart(Long.valueOf(pricesBean.getId()),
                        title,pricesBean.getName(),pricesBean.getPrice()+"",pricesBean.getPic_url(),
                        num,min
                        ));


                int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                holder.num.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                ball = new ImageView(mContext);// buyImg是动画的图片，我的是一个小球（R.drawable.sign）
                ball.setImageResource(R.drawable.unread_count_bg);// 设置buyImg的图片
                setAnim(ball, startLocation);// 开始执行动画
                holder.num.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.text_plus));
            }
        });

        holder.cut.setTag(position);
        holder.cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag =(int) v.getTag();
                MerchantDetails.DataBean.PricesBean pricesBean = datas.get(tag);
                String string = holder.num.getText().toString();
                Integer num = Integer.valueOf(string);
                String minBuyNum = pricesBean.getMinBuyNum();
                Integer min = Integer.valueOf(minBuyNum);
                if (num>min){
                    num-=1;
                    shoppingCartDao.insertOrReplace(new ShoppingCart(Long.valueOf(pricesBean.getId()),
                            title,pricesBean.getName(),pricesBean.getPrice()+"",pricesBean.getPic_url(),
                            num,min
                    ));
                }else {
                    num=0;
                    shoppingCartDao.deleteByKey(Long.valueOf(pricesBean.getId()));
                }
                holder.num.setText(num+"");
                ((MerchantActivity)mContext).refresh();

            }
        });
    }

    /**
     * @Description: 创建动画层
     * @param
     * @return void
     * @throws
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) ((MerchantActivity)mContext).getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(0);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    private void setAnim(final View v, int[] startLocation) {
        ViewGroup anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v,
                startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        ((MerchantActivity)mContext).getCarView().getLocationInWindow(endLocation);// shopCart是那个购物车

        // 计算位移
        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
                ((MerchantActivity)mContext).refresh();
            }
        });

    }


    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

}

class MyViewHolder extends RecyclerView.ViewHolder {
    ImageButton add = (ImageButton)itemView.findViewById(R.id.merchant_lv_ib_add);
    ImageButton cut = (ImageButton)itemView.findViewById(R.id.merchant_lv_ib_cut);
    TextView num = (TextView)itemView.findViewById(R.id.merchant_lv_tv_num);
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

