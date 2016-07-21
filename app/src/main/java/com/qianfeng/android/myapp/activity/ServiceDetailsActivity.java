package com.qianfeng.android.myapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.ecloud.pulltozoomview.TransparentToolBar;
import com.google.gson.Gson;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.adapter.MaybeYuoLikeAdapter;
import com.qianfeng.android.myapp.bean.MaybeYouLikeInfo;
import com.qianfeng.android.myapp.bean.ServiceDetailsHeaderInfo;
import com.qianfeng.android.myapp.bean.ShopInfo;
import com.qianfeng.android.myapp.dao.DaoMaster;
import com.qianfeng.android.myapp.dao.DaoSession;
import com.qianfeng.android.myapp.dao.ShoppingCart;
import com.qianfeng.android.myapp.dao.ShoppingCartDao;
import com.qianfeng.android.myapp.data.AssortmentURL;
import com.qianfeng.android.myapp.widget.MyGridView;
import com.qianfeng.android.myapp.widget.SharePopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;

public class ServiceDetailsActivity extends AppCompatActivity {

    private PullToZoomScrollViewEx scrollView;
    private TransparentToolBar toolBar;
    private SharedPreferences sharedPreferences;
    private String lot;
    private String lat;
    private String manualCity;
    private String mId;
    List<MaybeYouLikeInfo.DataBean> data = new ArrayList<>();
    private MyGridView myGridView;
    private TextView tv_name;
    private ImageButton ib_back;
    private ImageButton ib_menu;
    private TransparentToolBar mTransparentToolBar;
    private View contentView;
    private ImageView bannerIV;
    private String pic_url;
    private String mName;
    private String mSalesNum;
    private String mPrice;
    private String mOriginalPrice;
    private String mPrice_unit;
    private String mDescription;
    private String mTime;
    private String mShopName;
    private String mPositiveCommentRate;
    private String mShopDescription;
    private String mGuarantee_tv1;
    private String mGuarantee_tv2;
    private String mGuarantee_tv3;
    private String mIconUrl1;
    private String mIiconUrl2;
    private String mIconUrl3;

    private TextView name;
    private TextView salesNum;
    private TextView price;
    private TextView originalprice;
    private TextView description;
    private String serviceId;
    private TextView time;
    private TextView shopName;
    private TextView positiveCommentRate;
    private TextView shopDescription;
    private TextView guarantee_tv1;
    private TextView guarantee_tv2;
    private TextView guarantee_tv3;
    private ImageView iconUrl1;
    private ImageView iconUrl2;
    private ImageView iconUrl3;
    private RelativeLayout guaranteeRL;
    private ImageView introduce_more;
    private ImageView jumpToShop;
    private TextView introduce;
    private TextView bespeaktime;

    private static final int VIDEO_CONTENT_DESC_MAX_LINE = 5;// 默认展示最大行数3行
    private static final int SHRINK_UP_STATE = 1;// 收起状态
    private static final int SPREAD_STATE = 2;// 展开状态
    private static int mState = SHRINK_UP_STATE;//默认收起状态

    private String guaranteeURL;
    private String shopId;
    private RecyclerView recyclerview;
    private SharePopupWindow popWnd;
    private TextView catNum;
    private DaoMaster daoMaster;
    private TextView sum;
    private ImageView up_add;
    private ImageView reduce;
    private TextView buyNum;
    private int minBuyNum;
    private int id1;
    private Button goBuy;
    private ImageButton shoppingCar;
    private ImageView ball;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        //获取当前地址信息
        getAddress();
        //获取店铺信息
        getServiceId();
        //初始化视图
        initView();
        //加载顶部商品详情数据
        initHeanderdata();
        //加载中间店铺简介数据及加载底部recyclerview数据
        initShopSummary();
        //设置监听
        initListener();
        //刷新购物车
        refresh();
    }

    private void initListener() {
        //店铺介绍点击展开和收缩
        introduce_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mState == SPREAD_STATE) {
                    introduce.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                    introduce.requestLayout();
                    mState = SHRINK_UP_STATE;
                } else if (mState == SHRINK_UP_STATE) {
                    introduce.setMaxLines(Integer.MAX_VALUE);
                    introduce.requestLayout();
                    mState = SPREAD_STATE;
                }
            }
        });

        //跳转到店铺详情视图
        jumpToShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceDetailsActivity.this, MerchantActivity.class);
                intent.putExtra("id", shopId);
                startActivity(intent);

            }
        });
        //返回
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //分享及联系商家
        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });

        //购买数量递增
        up_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取DaoSession对象
                DaoSession daoSession = daoMaster.newSession();
                //通过DaoSeesion对象获得CustomerDao对象
                ShoppingCartDao shoppingCartDao = daoSession.getShoppingCartDao();
                List<ShoppingCart> shoppingCarts = shoppingCartDao.loadAll();
//                buyNum.setVisibility(View.VISIBLE);
//                reduce.setVisibility(View.VISIBLE);
                String text = buyNum.getText().toString().trim();
                int itemNum = Integer.parseInt(text);
                if (itemNum == 0) {
                    itemNum = minBuyNum;
                } else {
                    itemNum += 1;
                }
                String mItemNum = itemNum + "";
                buyNum.setText(mItemNum);
                shoppingCartDao.insertOrReplace(new ShoppingCart(Long.valueOf(id1),
                        mShopName, mName, mPrice + "", pic_url,
                        itemNum, minBuyNum
                ));


                int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                buyNum.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                ball = new ImageView(ServiceDetailsActivity.this);// buyImg是动画的图片，我的是一个小球（R.drawable.sign）
                ball.setImageResource(R.drawable.unread_count_bg);// 设置buyImg的图片
                setAnim(ball, startLocation);// 开始执行动画
                buyNum.startAnimation(AnimationUtils.loadAnimation(ServiceDetailsActivity.this, R.anim.text_plus));

            }
        });

        //购买数量递减
        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取DaoSession对象
                DaoSession daoSession = daoMaster.newSession();
                //通过DaoSeesion对象获得CustomerDao对象
                ShoppingCartDao shoppingCartDao = daoSession.getShoppingCartDao();
                List<ShoppingCart> shoppingCarts = shoppingCartDao.loadAll();
                String text = buyNum.getText().toString().trim();
                int itemNum = Integer.parseInt(text);
                if (itemNum > minBuyNum) {
                    itemNum -= 1;
                    shoppingCartDao.insertOrReplace(new ShoppingCart(Long.valueOf(id1),
                            mShopName, mName, mPrice + "", pic_url,
                            itemNum, minBuyNum
                    ));
                } else {
//                    buyNum.setVisibility(View.GONE);
//                    reduce.setVisibility(View.GONE);
                    itemNum = 0;
                    shoppingCartDao.deleteByKey(Long.valueOf(id1));
                }
                buyNum.setText(itemNum + "");
                refresh();
            }
        });

        //调转致购物车页面
        shoppingCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void showPopupWindow() {
        OnClickLintener paramOnClickListener = new OnClickLintener();
        popWnd = new SharePopupWindow(ServiceDetailsActivity.this, paramOnClickListener, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popWnd.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    popWnd.dismiss();
                }
            }
        });

        //设置默认获取焦点
        popWnd.setFocusable(true);
        //以某个控件的x和y的偏移量位置开始显示窗口
        popWnd.showAsDropDown(ib_menu);
        //如果窗口存在，则更新
        popWnd.update();

    }

    //加载中间店铺简介数据
    private void initShopSummary() {
        String url = AssortmentURL.SHOP_SERVICES + serviceId + "?" + lot + "&" + lat;
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        ShopInfo shopInfo = gson.fromJson(response, ShopInfo.class);
                        String startTime = shopInfo.getData().getStartTime();
                        String endTime = shopInfo.getData().getEndTime();
                        mShopName = shopInfo.getData().getTitle();
                        mPositiveCommentRate = shopInfo.getData().getPositiveCommentRate();
                        mShopDescription = shopInfo.getData().getDescription();
                        shopId = shopInfo.getData().getId();

                        ShopInfo.DataBean.GuaranteeBean guarantee = shopInfo.getData().getGuarantee();
                        if (guarantee != null) {
                            guaranteeRL.setVisibility(View.VISIBLE);
                            guaranteeURL = guarantee.getUrl();
                            List<ShopInfo.DataBean.GuaranteeBean.ItemsBean> items = guarantee.getItems();
                            String[] iconUrls = new String[items.size()];
                            String[] labels = new String[items.size()];
                            for (int i = 0; i < items.size(); i++) {
                                iconUrls[i] = items.get(i).getIconUrl();
                                labels[i] = items.get(i).getLabel();
                            }
                            guarantee_tv1.setText(labels[0]);
                            guarantee_tv2.setText(labels[1]);
                            guarantee_tv3.setText(labels[2]);

                            Glide.with(ServiceDetailsActivity.this).load(iconUrls[0]).into(iconUrl1);
                            Glide.with(ServiceDetailsActivity.this).load(iconUrls[1]).into(iconUrl2);
                            Glide.with(ServiceDetailsActivity.this).load(iconUrls[2]).into(iconUrl3);


                        } else {
                            guaranteeRL.setVisibility(View.GONE);
                            guarantee_tv1.setText("");
                            guarantee_tv1.setText("");
                            guarantee_tv1.setText("");
                            iconUrl1.setImageDrawable(null);
                            iconUrl2.setImageDrawable(null);
                            iconUrl3.setImageDrawable(null);
                        }

                        long nextAppointTime = shopInfo.getData().getNextAppointTime();
                        String time = getTime(nextAppointTime);
                        bespeaktime.setText(time);

                        mTime = " " + startTime + " - " + endTime;
                        ServiceDetailsActivity.this.time.setText(mTime);
                        shopName.setText(mShopName);
                        positiveCommentRate.setText(mPositiveCommentRate);
                        shopDescription.setText(mShopDescription);
                        //加载底部recyclerview数据
                        initdata();

                    }
                });
    }

    //获取跳转时得到的ServiceId
    private void getServiceId() {
        //创建一个开发环境的Helper类，如果是正式环境调用DaoMaster.OpenHelper
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "liuxiao", null);
        //通过Handler类获得数据库对象
        SQLiteDatabase readableDatabase = mHelper.getReadableDatabase();
        //通过数据库对象生成DaoMaster对象
        daoMaster = new DaoMaster(readableDatabase);

        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        serviceId = intent.getStringExtra("serviceId");
    }


    //初始化视图
    private void initView() {
        //初始化scrollView和toolBar
        scrollView = (PullToZoomScrollViewEx) findViewById(R.id.service_details_scrollview);
        toolBar = (TransparentToolBar) findViewById(R.id.service_details_toolbar);
        tv_name = (TextView) findViewById(R.id.service_details_tv_name);
        ib_back = (ImageButton) findViewById(R.id.service_details_ib_back);
        ib_menu = (ImageButton) findViewById(R.id.service_details_ib_menu);
        catNum = (TextView) findViewById(R.id.service_details_tv_cat_num);
        sum = (TextView) findViewById(R.id.service_details_tv_sum);
        goBuy = (Button) findViewById(R.id.service_details_btn_gobuy);
        shoppingCar = (ImageButton) findViewById(R.id.service_details_ib_cat);

        //初始化头部视图
        View zoomView = LayoutInflater.from(this).inflate(R.layout.service_details_banner, null, false);
        View headerView = LayoutInflater.from(this).inflate(R.layout.service_details_header, null, false);
        bannerIV = (ImageView) zoomView.findViewById(R.id.service_details_banner_iv);
        name = (TextView) headerView.findViewById(R.id.service_details_banner_name);
        salesNum = (TextView) headerView.findViewById(R.id.service_details_banner_salesNum);
        scrollView.setZoomView(zoomView);
        scrollView.setHeaderView(headerView);
        scrollView.setParallax(false);


        //初始化scrollView中的内容视图
        contentView = LayoutInflater.from(this).inflate(R.layout.service_details_layout, null, false);
        scrollView.setScrollContentView(contentView);
        price = (TextView) contentView.findViewById(R.id.service_details_price_tv);
        originalprice = (TextView) contentView.findViewById(R.id.service_details_originalprice_tv);
        description = (TextView) contentView.findViewById(R.id.service_details_tv);

        time = (TextView) contentView.findViewById(R.id.service_time_tv);
        shopName = (TextView) contentView.findViewById(R.id.service_details_shopname_tv);
        positiveCommentRate = (TextView) contentView.findViewById(R.id.service_details_positivecommentrate_tv);
        shopDescription = (TextView) contentView.findViewById(R.id.service_details_shop_introduce_tv);
        guarantee_tv1 = (TextView) contentView.findViewById(R.id.service_details_guarantee_tv1);
        guarantee_tv2 = (TextView) contentView.findViewById(R.id.service_details_guarantee_tv2);
        guarantee_tv3 = (TextView) contentView.findViewById(R.id.service_details_guarantee_tv3);
        iconUrl1 = (ImageView) contentView.findViewById(R.id.service_details_guarantee_iconUrl1);
        iconUrl2 = (ImageView) contentView.findViewById(R.id.service_details_guarantee_iconUrl2);
        iconUrl3 = (ImageView) contentView.findViewById(R.id.service_details_guarantee_iconUrl3);
        guaranteeRL = (RelativeLayout) contentView.findViewById(R.id.service_details_guarantee_rl);
        introduce_more = (ImageView) contentView.findViewById(R.id.service_details_shop_introduce_more_tv);
        jumpToShop = (ImageView) contentView.findViewById(R.id.service_details_iv);
        introduce = (TextView) contentView.findViewById(R.id.service_details_shop_introduce_tv);
        bespeaktime = (TextView) contentView.findViewById(R.id.service_bespeaktime_tv);
        up_add = (ImageView) contentView.findViewById(R.id.service_details_up_add_tv);
        reduce = (ImageView) contentView.findViewById(R.id.service_details_reduce_iv);
        buyNum = (TextView) contentView.findViewById(R.id.service_details_buy_tv);

        recyclerview = (RecyclerView) contentView.findViewById(R.id.service_details_recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerview.setLayoutManager(gridLayoutManager);

        //获取屏幕尺寸
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;

        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, mScreenWidth);
        scrollView.setHeaderLayoutParams(localObject);
        ScrollView pullRootView = scrollView.getPullRootView();
        PullToZoomScrollViewEx.InternalScrollView internalScrollView = (PullToZoomScrollViewEx.InternalScrollView) pullRootView;
        mTransparentToolBar = (TransparentToolBar) findViewById(R.id.service_details_toolbar);
        mTransparentToolBar.setNameTV(tv_name);
        mTransparentToolBar.setBackIB(ib_back);
        mTransparentToolBar.setMenuIB(ib_menu);
        mTransparentToolBar.setNameTVColor(Color.BLACK);
        //必须设置ToolBar颜色值，也就是0~1透明度变化的颜色值
        mTransparentToolBar.setBgColor(Color.WHITE);

        //必须设置ToolBar最大偏移量，这会影响到0~1透明度变化的范围
        mTransparentToolBar.setOffset(mScreenWidth - getResources().getDimension(R.dimen.title_height));
        internalScrollView.setTitleBar(mTransparentToolBar);



    }


    //加载顶部商品详情及图片数据
    private void initHeanderdata() {
        String url = AssortmentURL.PROJECT_SERVICEPRICE + mId;
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        ServiceDetailsHeaderInfo serviceDetailsHeaderInfo = gson.fromJson(response, ServiceDetailsHeaderInfo.class);
                        mPrice_unit = serviceDetailsHeaderInfo.getData().getPriceUnit();
                        mOriginalPrice = serviceDetailsHeaderInfo.getData().getOriginalPrice() + "";
                        mPrice = serviceDetailsHeaderInfo.getData().getPrice() + "";
                        mSalesNum = "已售 " + serviceDetailsHeaderInfo.getData().getSalesNum() + "";
                        mName = serviceDetailsHeaderInfo.getData().getName();
                        pic_url = serviceDetailsHeaderInfo.getData().getPicUrl();
                        mDescription = serviceDetailsHeaderInfo.getData().getDescription();
                        minBuyNum = serviceDetailsHeaderInfo.getData().getMinBuyNum();
                        id1 = serviceDetailsHeaderInfo.getData().getId();

                        ServiceDetailsActivity.this.name.setText(mName);
                        salesNum.setText(mSalesNum);
                        price.setText(mPrice + " " + mPrice_unit);
                        description.setText(mDescription);
                        if (!mOriginalPrice.equals(mPrice)) {
                            originalprice.setText("原价" + mOriginalPrice + " " + mPrice_unit);
                        } else {
                            originalprice.setText("");
                        }
                        Glide.with(ServiceDetailsActivity.this).load(pic_url).into(bannerIV);


                        //获取DaoSession对象
                        DaoSession daoSession = daoMaster.newSession();
                        //通过DaoSeesion对象获得CustomerDao对象
                        ShoppingCartDao shoppingCartDao = daoSession.getShoppingCartDao();

                        ShoppingCart shoppingCart = shoppingCartDao.loadByRowId(Long.valueOf(id1));
                        if (shoppingCart!=null){
                            buyNum.setText(shoppingCart.getBuyNum()+"");
                        }else {
                            buyNum.setText("0");
                        }
                    }
                });

    }

    //加载底部recyclerview数据
    private void initdata() {
        String url = AssortmentURL.PROJECT_SERVICEPRICE + mId + AssortmentURL.PROJECT_SERVICEPRICEA;
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        MaybeYouLikeInfo maybeYuoLikeInfo = gson.fromJson(response, MaybeYouLikeInfo.class);
                        data = maybeYuoLikeInfo.getData();

                        MaybeYuoLikeAdapter maybeYuoLikeAdapter = new MaybeYuoLikeAdapter(data, ServiceDetailsActivity.this, catNum, mShopName);
                        recyclerview.setAdapter(maybeYuoLikeAdapter);
                        maybeYuoLikeAdapter.setOnItemClickListener(new MaybeYuoLikeAdapter.OnRecyclerViewItemClickListener() {

                            @Override
                            public void onItemClick(View view, MaybeYouLikeInfo.DataBean dataBean) {
                                mId = dataBean.getId() + "";
                                serviceId = dataBean.getServiceId();
                                data.clear();
                                //加载顶部商品详情数据
                                initHeanderdata();
                                //加载中间店铺简介数据
                                initShopSummary();
                                //加载底部recyclerview数据
                                initdata();
                            }
                        });

                    }
                });

    }

    //获取当前位置信息
    public void getAddress() {
        sharedPreferences = this.getSharedPreferences("location", Context.MODE_PRIVATE);
        lot = sharedPreferences.getString("lot", "0");
        lat = sharedPreferences.getString("lat", "0");
        manualCity = sharedPreferences.getString("cityName", "北京");

    }

    //获取预约时间
    public String getTime(long millis) {
        int time = 0;
        int thisTime = 0;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        Date currentTime = new Date();
        long thisTimeNum = currentTime.getTime();

        calendar.setTimeInMillis(thisTimeNum);
        thisTime = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTimeInMillis(millis);
        time = calendar.get(Calendar.DAY_OF_YEAR);

        currentTime = new Date(millis);
        if (thisTime == time) {
            return "今天  " + sdf.format(currentTime);
        }
        if (time - thisTime == 1) {
            return "明天  " + sdf.format(currentTime);
        }
        StringBuilder sb = new StringBuilder();
        sdf = new SimpleDateFormat("MM-dd");
        sb.append(sdf.format(currentTime));
        String week = "";
        int cweek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (cweek) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        sb.append("(" + week + ")  ");
        sdf = new SimpleDateFormat("HH:mm");
        sb.append(sdf.format(currentTime));
        return sb.toString();
    }


    class OnClickLintener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.connection_seller_layout:
                    Toast.makeText(ServiceDetailsActivity.this, "联系商家", Toast.LENGTH_SHORT).show();
                    popWnd.dismiss();
                    break;
                case R.id.share_layout:
                    share();
                    popWnd.dismiss();
                    break;

                default:
                    break;
            }

        }

    }

    //分享
    private void share() {
        ShareSDK.initSDK(ServiceDetailsActivity.this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("分享");

        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(ServiceDetailsActivity.this);

    }

    public void refresh() {
        //获取DaoSession对象
        DaoSession daoSession = daoMaster.newSession();
        //通过DaoSeesion对象获得CustomerDao对象
        ShoppingCartDao shoppingCartDao = daoSession.getShoppingCartDao();
        List<ShoppingCart> shoppingCarts = shoppingCartDao.loadAll();
        int shoppingNum = 0;
        Double shoppingPrice = 0.00;
        for (int i = 0; i < shoppingCarts.size(); i++) {
            ShoppingCart shoppingCart = shoppingCarts.get(i);
            int buyNum = shoppingCart.getBuyNum();
            shoppingNum += buyNum;
            shoppingPrice += (Double.valueOf(shoppingCart.getOriginalPrice()) * buyNum);
        }
        if (shoppingNum == 0) {
            catNum.setVisibility(View.GONE);
        } else {
            catNum.setVisibility(View.VISIBLE);
            catNum.setText(shoppingNum + "");
        }
        sum.setText(shoppingPrice + "");
    }

    public View getCarView() {
        return shoppingCar;
    }

    /**
     * @param
     * @return void
     * @throws
     * @Description: 创建动画层
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
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
        getCarView().getLocationInWindow(endLocation);// shopCart是那个购物车

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
                refresh();
            }
        });

    }

}
