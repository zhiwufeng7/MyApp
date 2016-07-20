package com.qianfeng.android.myapp.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.dao.DaoMaster;
import com.qianfeng.android.myapp.dao.DaoSession;
import com.qianfeng.android.myapp.dao.ShoppingCart;
import com.qianfeng.android.myapp.dao.ShoppingCartDao;
import com.qianfeng.android.myapp.fragment.AssortmentFragment;
import com.qianfeng.android.myapp.fragment.HomePageFragment;
import com.qianfeng.android.myapp.fragment.MyFragment;
import com.qianfeng.android.myapp.fragment.OrderFormFragment;
import com.qianfeng.android.myapp.fragment.ShoppingFragment;

import java.util.ArrayList;
import java.util.List;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initFragment();

        initListener();

        initData();

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
        transaction.commit();

        curIndex = i;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    public void initShoppingCart() {

        //创建一个开发环境的Helper类，如果是正式环境调用DaoMaster.OpenHelper
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "liuzhao", null);
        //通过Handler类获得数据库对象
        SQLiteDatabase readableDatabase = mHelper.getReadableDatabase();
        //通过数据库对象生成DaoMaster对象
        DaoMaster daoMaster = new DaoMaster(readableDatabase);
        //获取DaoSession对象
        DaoSession daoSession = daoMaster.newSession();
        //通过DaoSeesion对象获得CustomerDao对象
        ShoppingCartDao shoppingCartDao = daoSession.getShoppingCartDao();
        List<ShoppingCart> shoppingCarts = shoppingCartDao.loadAll();
        if (shoppingCarts==null || shoppingCarts.size()==0){
            shoppingCartNo.setVisibility(View.INVISIBLE);
            return;
        }
        int sum = 0;
        for (int i = 0; i < shoppingCarts.size(); i++) {
           sum+= shoppingCarts.get(i).getBuyNum();
        }
        if (sum>99){
            shoppingCartNo.setText("99+");
        }else {
            shoppingCartNo.setText(sum+"");
        }

    }


}
