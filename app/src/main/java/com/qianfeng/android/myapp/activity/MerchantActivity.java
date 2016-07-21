package com.qianfeng.android.myapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.ecloud.pulltozoomview.TransparentToolBar;
import com.google.gson.Gson;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.adapter.MerchantRecyclerAdapter;
import com.qianfeng.android.myapp.bean.MerchantDetails;
import com.qianfeng.android.myapp.dao.DaoMaster;
import com.qianfeng.android.myapp.dao.DaoSession;
import com.qianfeng.android.myapp.dao.ShoppingCart;
import com.qianfeng.android.myapp.dao.ShoppingCartDao;
import com.qianfeng.android.myapp.data.Url;
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
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import okhttp3.Call;


public class MerchantActivity extends SwipeBackActivity {
    private SwipeBackLayout mSwipeBackLayout;
    private TransparentToolBar mTransparentToolBar;
    private ConvenientBanner mConvenientBanner;
    private PullToZoomScrollViewEx scrollView;
    private ConvenientBanner cb;
    private TextView tv_tool_name;
    private ImageButton ib_back;
    private ImageButton ib_menu;
    private int mScreenHeight;
    private int mScreenWidth;
    private List<MerchantDetails.DataBean.ImgsBean> cbItems;
    private MerchantDetails merchantDetails;
    private TextView tv_name;
    private TextView tv_service_time;
    private TextView tv_time;
    private TextView tv_succeed;
    private TextView tv_receiving;
    private TextView tv_reputation;
    private TabLayout tab;
    private List<MerchantDetails.DataBean.SubCategoriesBean> subCategories;
    private ArrayList<ImageView> stars;
    private TextView tv_commentCount;
    private TextView tv_comment;
    private TextView tv_createtime;
    private TextView tv_nick;
    private TextView tv_description;
    private RecyclerView rv;
    private ImageView iv_tag1;
    private ImageView iv_tag2;
    private MerchantDetails.DataBean data;
    private int num = 10;
    private List<MerchantDetails.DataBean.PricesBean> prices;
    private ArrayList<MerchantDetails.DataBean.PricesBean> rv_data = new ArrayList<>();
    private List<ArrayList<MerchantDetails.DataBean.PricesBean>> classifyPrices;
    private PullToZoomScrollViewEx.InternalScrollView internalScrollView;
    private MerchantRecyclerAdapter adapter;
    private RelativeLayout add_rl;
    private String id;
    private String title;
    private TextView tv_cat_num;
    private TextView tv_cat_sum;
    private DaoMaster daoMaster;
    private ImageButton ib_car;
    private SharePopupWindow popWnd;
    private LinearLayout ll_comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        initView();
        setToolBar();
        initData();
        refresh();

        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

    }


    private void setListener() {
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position > 0) {
                    adapter = new MerchantRecyclerAdapter(MerchantActivity.this, classifyPrices.get(position - 1), id, title);
                    rv.setAdapter(adapter);
                    add_rl.setVisibility(View.GONE);
                } else {
                    rv_data.clear();
                    add_rl.setVisibility(View.VISIBLE);
                    num = 10;
                    setAdapter();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ib_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MerchantActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        //创建一个开发环境的Helper类，如果是正式环境调用DaoMaster.OpenHelper
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "liuxiao", null);
        //通过Handler类获得数据库对象
        SQLiteDatabase readableDatabase = mHelper.getReadableDatabase();
        //通过数据库对象生成DaoMaster对象
        daoMaster = new DaoMaster(readableDatabase);
        SharedPreferences sharedPreferences = getSharedPreferences("location", Context.MODE_PRIVATE);
        String lot = sharedPreferences.getString("lot", "0");
        String lat = sharedPreferences.getString("lat", "0");
        OkHttpUtils.get().url(Url.getMerchantUrl(id,lot,lat))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                merchantDetails = gson.fromJson(response, MerchantDetails.class);
                cbItems = merchantDetails.getData().getImgs();
                setView();
                classifyData();
                setCBAdapter();
                setListener();
                setAdapter();
            }
        });
    }


    private void classifyData() {
        prices = data.getPrices();
        title = data.getTitle();
        classifyPrices = new ArrayList<>();
        for (int i = 0; i < subCategories.size(); i++) {
            MerchantDetails.DataBean.SubCategoriesBean subCategoriesBean = subCategories.get(i);
            ArrayList<MerchantDetails.DataBean.PricesBean> thisPrices = new ArrayList<>();
            for (int n = 0; n < prices.size(); n++) {
                MerchantDetails.DataBean.PricesBean pricesBean = prices.get(n);
                if (pricesBean.getSubName().equals(subCategoriesBean.getName())) {
                    thisPrices.add(pricesBean);
                }
            }
            classifyPrices.add(thisPrices);
        }
    }

    private void setView() {
        data = merchantDetails.getData();
        tv_tool_name.setText(data.getTitle());
        tv_name.setText(data.getTitle());
        tv_service_time.setText(" " + data.getStartTime() + "-" + data.getEndTime());
        tv_time.setText(getTime(data.getNextAppointTime()));
        tv_succeed.setText(data.getOrderTakingCount() + "");
        tv_receiving.setText(data.getOrderTakingRate());
        tv_reputation.setText(data.getPositiveCommentRate());
        tab.addTab(tab.newTab().setText("所有项目"));
        subCategories = data.getSubCategories();
        for (int i = 0; i < subCategories.size(); i++) {
            tab.addTab(tab.newTab().setText(subCategories.get(i).getName()));
        }
        MerchantDetails.DataBean.LastCommentBean lastComment = data.getLastComment();
        if (lastComment != null) {
            for (int i = 0; i < lastComment.getStar(); i++) {
                stars.get(i).setImageDrawable(getResources().getDrawable(R.drawable.img_star_3));
            }
            tv_commentCount.setText("(" + data.getCommentCount() + "条)");
            tv_comment.setText(lastComment.getComment());
            tv_createtime.setText(getDay(lastComment.getCreatetime()));
            tv_nick.setText(lastComment.getNick());
        } else {
            ll_comment.setVisibility(View.GONE);
        }
        tv_description.setText(data.getDescription());
        List<String> tagIcons = data.getTagIcons();
        if (tagIcons.size() == 2) {
            Glide.with(this).load(tagIcons.get(0)).into(iv_tag1);
            Glide.with(this).load(tagIcons.get(1)).into(iv_tag2);
        } else if (tagIcons.size() == 1) {
            Glide.with(this).load(tagIcons.get(0)).into(iv_tag1);
        }
    }

    public String getDay(long millis) {
        String day = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date currentTime = new Date(millis);
        return sdf.format(currentTime);
    }

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

    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        if (num < prices.size()) {
            for (int i = num - 10; i < num; i++) {
                rv_data.add(prices.get(i));
            }
            num += 10;

        } else {
            for (int i = num - 10; i < prices.size(); i++) {
                rv_data.add(prices.get(i));
            }
            add_rl.setVisibility(View.GONE);
        }
        adapter = new MerchantRecyclerAdapter(MerchantActivity.this, rv_data, id, title);
        rv.setAdapter(adapter);

    }

    private void setCBAdapter() {
        cb.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, cbItems)
                .setPageIndicator(new int[]{R.drawable.page, R.drawable.page_now})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

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
            tv_cat_num.setVisibility(View.GONE);
        } else {
            tv_cat_num.setVisibility(View.VISIBLE);
            tv_cat_num.setText(shoppingNum + "");
        }
        tv_cat_sum.setText(shoppingPrice + "");
    }


    class LocalImageHolderView implements Holder<MerchantDetails.DataBean.ImgsBean> {
        ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, MerchantDetails.DataBean.ImgsBean data) {
            Glide.with(context)
                    .load(data.getUrl())
                    .into(imageView);
        }
    }

    public View getCarView() {
        return ib_car;
    }

    private void setToolBar() {
        ScrollView pullRootView = scrollView.getPullRootView();
        internalScrollView = (PullToZoomScrollViewEx.InternalScrollView) pullRootView;
        mTransparentToolBar = (TransparentToolBar) findViewById(R.id.merchant_bar);
        mTransparentToolBar.setNameTV(tv_tool_name);
        mTransparentToolBar.setBackIB(ib_back);
        mTransparentToolBar.setMenuIB(ib_menu);
        mTransparentToolBar.setNameTVColor(Color.BLACK);
        //必须设置ToolBar颜色值，也就是0~1透明度变化的颜色值
        mTransparentToolBar.setBgColor(Color.WHITE);
        //必须设置ToolBar最大偏移量，这会影响到0~1透明度变化的范围
        mTransparentToolBar.setOffset(mScreenWidth - getResources().getDimension(R.dimen.title_height));
        internalScrollView.setTitleBar(mTransparentToolBar);
    }

    private void initView() {
        scrollView = (PullToZoomScrollViewEx) findViewById(R.id.merchant_scroll_view);
        View zoomView = LayoutInflater.from(this).inflate(R.layout.activity_merchant_zoom, null, false);
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_merchant_content, null, false);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
        cb = (ConvenientBanner) findViewById(R.id.merchant_cb);
        tv_tool_name = (TextView) findViewById(R.id.merchant_tv_tool_name);
        ib_back = (ImageButton) findViewById(R.id.merchant_ib_back);
        ib_menu = (ImageButton) findViewById(R.id.merchant_ib_menu);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        mScreenHeight = localDisplayMetrics.heightPixels;
        mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, mScreenWidth);
        scrollView.setHeaderLayoutParams(localObject);

        tv_name = (TextView) findViewById(R.id.merchant_tv_name);
        tv_service_time = (TextView) findViewById(R.id.merchant_tv_service_time);
        tv_time = (TextView) findViewById(R.id.merchant_tv_time);
        tv_succeed = (TextView) findViewById(R.id.merchant_tv_succeed);
        tv_receiving = (TextView) findViewById(R.id.merchant_tv_receiving);
        tv_reputation = (TextView) findViewById(R.id.merchant_tv_reputation);
        tab = (TabLayout) findViewById(R.id.merchant_tab);
        stars = new ArrayList<>();
        stars.add((ImageView) findViewById(R.id.merchant_star1));
        stars.add((ImageView) findViewById(R.id.merchant_star2));
        stars.add((ImageView) findViewById(R.id.merchant_star3));
        stars.add((ImageView) findViewById(R.id.merchant_star4));
        stars.add((ImageView) findViewById(R.id.merchant_star5));
        tv_commentCount = (TextView) findViewById(R.id.merchant_tv_commentCount);
        tv_comment = (TextView) findViewById(R.id.merchant_tv_comment);
        tv_createtime = (TextView) findViewById(R.id.merchant_tv_createtime);
        tv_nick = (TextView) findViewById(R.id.merchant_tv_nick);
        tv_description = (TextView) findViewById(R.id.merchant_tv_description);
        rv = (RecyclerView) findViewById(R.id.merchant_rv);
        iv_tag1 = (ImageView) findViewById(R.id.merchant_iv_tag1);
        iv_tag2 = (ImageView) findViewById(R.id.merchant_iv_tag2);
        add_rl = (RelativeLayout) findViewById(R.id.merchant_rv_foot_rl);

        tv_cat_num = (TextView) findViewById(R.id.merchant_tv_cat_num);
        tv_cat_sum = (TextView) findViewById(R.id.merchant_tv_sum);
        ib_car = (ImageButton) findViewById(R.id.merchant_ib_car);

        ll_comment = (LinearLayout) findViewById(R.id.merchant_ll_comment);
    }

    public void onBack(View view) {
        finish();
    }

    public void onAddList(View view) {
        if (num < prices.size()) {
            for (int i = num - 10; i < num; i++) {
                rv_data.add(prices.get(i));
            }
            num += 10;

        } else {
            for (int i = num - 10; i < prices.size(); i++) {
                rv_data.add(prices.get(i));
            }
            add_rl.setVisibility(View.GONE);
        }
        adapter = new MerchantRecyclerAdapter(MerchantActivity.this, rv_data, id, title);
        rv.setAdapter(adapter);
    }

    public void onMenu(View view) {
        OnClickLintener paramOnClickListener = new OnClickLintener();
        popWnd = new SharePopupWindow(this, paramOnClickListener, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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

    class OnClickLintener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.connection_seller_layout:
                    Toast.makeText(MerchantActivity.this, "联系商家", Toast.LENGTH_SHORT).show();
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
        ShareSDK.initSDK(this);
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
        oks.show(this);

    }

}
