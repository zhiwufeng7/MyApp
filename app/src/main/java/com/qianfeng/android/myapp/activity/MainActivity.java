package com.qianfeng.android.myapp.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.bean.Plots;
import com.qianfeng.android.myapp.dao.DaoMaster;
import com.qianfeng.android.myapp.dao.DaoSession;
import com.qianfeng.android.myapp.dao.ShoppingCart;
import com.qianfeng.android.myapp.dao.ShoppingCartDao;
import com.qianfeng.android.myapp.data.Url;
import com.qianfeng.android.myapp.fragment.AssortmentFragment;
import com.qianfeng.android.myapp.fragment.HomePageFragment;
import com.qianfeng.android.myapp.fragment.MyFragment;
import com.qianfeng.android.myapp.fragment.OrderFormFragment;
import com.qianfeng.android.myapp.fragment.ShoppingFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    public RadioGroup radioGroup;
    private List<Fragment> fragmentList;
    private Button search;
    private Button message;
    private Button address;
    private SharedPreferences sharedPreferences;
    private String village;
    private FragmentManager manager;
    private int curIndex;
    private HomePageFragment homePageFragment;
    private FrameLayout viewGroup;
    private TextView title;
    private TextView shoppingCartNo;
    private DaoMaster daoMaster;
    private RadioButton shoppingBtn;
    private LocationClient mLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(MainActivity.this, "liuxiao", null);
        //通过Handler类获得数据库对象
        SQLiteDatabase readableDatabase = mHelper.getReadableDatabase();
        //通过数据库对象生成DaoMaster对象
        daoMaster = new DaoMaster(readableDatabase);

        initLocation();

        initView();

        initFragment();

        initListener();

        initData();


    }

    private void initLocation() {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                String city = bdLocation.getCity();
                double latNum = bdLocation.getLatitude();
                double lotNum = bdLocation.getLongitude();
                String lat = String.valueOf(latNum);
                String lot = String.valueOf(lotNum);
                mLocationClient.stop();
                OkHttpUtils.get().url(Url.NEARBY_PLOT)
                        .addParams("lat", lat).addParams("lot", lot).addParams("v2", "v2")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                List<Plots.DataBean.CommunitiesBean> communitie = new ArrayList<>();
                                if (response.length() > 100) {
                                    Gson gson = new Gson();
                                    Plots plots = gson.fromJson(response, Plots.class);
                                    communitie = plots.getData().getCommunities();
                                    int cityID = plots.getData().getParent().getId();
                                    List<Plots.DataBean.BaiduCommunitiesBean> baiduCommunities =
                                            plots.getData().getBaiduCommunities();
                                    //将baiduCommunitiesBean转换成communitiesBean,并加入数据列表
                                    for (int i = 0; i < baiduCommunities.size(); i++) {
                                        Plots.DataBean.BaiduCommunitiesBean baiduCommunitiesBean = baiduCommunities.get(i);
                                        Plots.DataBean.CommunitiesBean communitiesBean = new Plots.DataBean.CommunitiesBean();
                                        communitiesBean.setAddr(baiduCommunitiesBean.getAddr());
                                        communitiesBean.setName(baiduCommunitiesBean.getName());
                                        communitiesBean.setLot(baiduCommunitiesBean.getX());
                                        communitiesBean.setLat(baiduCommunitiesBean.getY());
                                        communitiesBean.setId(cityID);
                                        communitie.add(communitiesBean);
                                    }
                                }
                                SharedPreferences location = getSharedPreferences("location", Context.MODE_PRIVATE);
                                String plot = location.getString("plot", "");
                                for (int i = 0; i < communitie.size(); i++) {
                                    if (plot.equals(communitie.get(i).getName())) {
                                        return;
                                    }
                                }

                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("当前小区不在您的位置附近 ,是否重新选择小区?")
                                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent();
                                                intent.setClass(MainActivity.this, MapActivity.class);
                                                startActivityForResult(intent, 3);
                                            }
                                        });
                                builder.show();
                            }
                        });
            }
        });
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(false);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }


    private void initData() {
        sharedPreferences = getSharedPreferences("location", Context.MODE_PRIVATE);
        village = sharedPreferences.getString("plot", "北京");
        address.setText(village);
    }


    private void initListener() {
        //底部的radioGroup设置监听  实现fragment的切换
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.main_bottom_home_rb:
                        //显示地址和搜索按钮
                        title.setText("");
                        address.setVisibility(View.VISIBLE);
                        search.setVisibility(View.VISIBLE);
                        //点击首页按钮切换第一个fragment
                        switchFragment(0);
                        break;
                    case R.id.main_bottom_assortment_rb:
                        //显示地址和搜索按钮
                        title.setText("");
                        address.setVisibility(View.VISIBLE);
                        search.setVisibility(View.VISIBLE);
                        //点击分类按钮切换第二个fragment
                        switchFragment(1);
                        break;
                    case R.id.main_bottom_shopping_rb:
                        //隐藏地址和搜索按钮 设置标题
                        address.setVisibility(View.INVISIBLE);
                        search.setVisibility(View.INVISIBLE);
                        title.setText("购物车");
                        //点击购物车按钮切换第三个fragment
                        switchFragment(2);
                        break;
                    case R.id.main_bottom_order_form_rb:
                        //隐藏地址和搜索按钮 设置标题
                        address.setVisibility(View.INVISIBLE);
                        search.setVisibility(View.INVISIBLE);
                        title.setText("我的订单");
                        //点击订单按钮切换第四个fragment
                        switchFragment(3);
                        break;
                    case R.id.main_bottom_my_rb:
                        //隐藏地址和搜索按钮 设置标题
                        address.setVisibility(View.INVISIBLE);
                        search.setVisibility(View.INVISIBLE);
                        title.setText("我的");
                        //点击我的按钮切换第五个fragment
                        switchFragment(4);
                        break;
                }
            }
        });


    }

    private void initFragment() {

        //初始化fragment
        manager = getSupportFragmentManager();
        FragmentTransaction transition = manager.beginTransaction();
        homePageFragment = HomePageFragment.newInstance();
        transition.add(R.id.main_content_fl, homePageFragment);
        transition.commit();


        /**
         * 在这里添加new fragment 并添加到 fragmentList
         */
        fragmentList = new ArrayList<>();
        fragmentList.add(homePageFragment);
        fragmentList.add(AssortmentFragment.newInstance(null));
        fragmentList.add(ShoppingFragment.newInstance());
        fragmentList.add(OrderFormFragment.newInstance());
        fragmentList.add(MyFragment.newInstance());

    }

    private void initView() {

        viewGroup = (FrameLayout) findViewById(R.id.main_content_fl);
        radioGroup = (RadioGroup) findViewById(R.id.main_bottom_rg);
        shoppingBtn = (RadioButton) findViewById(R.id.main_bottom_shopping_rb);
        search = (Button) findViewById(R.id.main_top_search_btn);
        message = (Button) findViewById(R.id.main_top_msg_btn);
        address = (Button) findViewById(R.id.main_top_address_btn);
        title = (TextView) findViewById(R.id.main_top_tv_show);

        shoppingCartNo = (TextView) findViewById(R.id.main_shopping_cart_tv);


    }

    /**
     * 首页头部的监听
     *
     * @param view
     */
    public void onClick(View view) {
        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.main_top_address_btn:
//               点击地址按钮，跳转mapActivity
                intent.setClass(this, MapActivity.class);
                startActivityForResult(intent, 3);
                break;
            case R.id.main_top_msg_btn:


                break;

            case R.id.main_top_search_btn:

                intent.setClass(this, SearchActivity.class);
                startActivity(intent);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3 && resultCode == 1) {
            initData();
            homePageFragment.initData();
            homePageFragment.index = 0;
            homePageFragment.loadFootData();
            if (fragmentList.get(1).isAdded()) {
                AssortmentFragment assortmentFragment = (AssortmentFragment) (fragmentList.get(1));
                assortmentFragment.refresh();
            }

        }
    }

    protected void switchFragment(int i) {
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = fragmentList.get(i);
        if (!fragment.isAdded()) {
            transaction.hide(fragmentList.get(curIndex)).add(R.id.main_content_fl, fragment);
        } else {
            transaction.hide(fragmentList.get(curIndex)).show(fragment);
        }
        transaction.commitAllowingStateLoss();

        curIndex = i;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }


    public void initShoppingCart() {

        //获取DaoSession对象
        DaoSession daoSession = daoMaster.newSession();
        //通过DaoSeesion对象获得CustomerDao对象
        ShoppingCartDao shoppingCartDao = daoSession.getShoppingCartDao();
        List<ShoppingCart> shoppingCarts = shoppingCartDao.loadAll();

        if (shoppingCarts == null || shoppingCarts.size() == 0) {
            shoppingCartNo.setVisibility(View.INVISIBLE);
            return;
        }
        shoppingCartNo.setVisibility(View.VISIBLE);
        int sum = 0;
        for (int i = 0; i < shoppingCarts.size(); i++) {
            sum += shoppingCarts.get(i).getBuyNum();
        }
        if (sum > 99) {
            shoppingCartNo.setText("99+");
        } else {
            shoppingCartNo.setText(sum + "");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initShoppingCart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (!TextUtils.isEmpty(intent.getStringExtra("re"))) {
            shoppingBtn.setChecked(true);
        }
        super.onNewIntent(intent);
    }
}
