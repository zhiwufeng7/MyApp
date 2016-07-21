package com.qianfeng.android.myapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.adapter.ProjectGridViewAdapter;
import com.qianfeng.android.myapp.adapter.ProjectTabLayoutAdapter;
import com.qianfeng.android.myapp.bean.FootInfo;
import com.qianfeng.android.myapp.data.AssortmentURL;
import com.qianfeng.android.myapp.fragment.ProjactViewPagerFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * creat by chengxiao
 */
public class ProjectListActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private RadioButton leftRB;
    private RadioButton rightRB;
    private String id;
    private String lot;
    private String lat;

    private String manualCity;

    List<Fragment> fragments;

    List<String> titles;
    private ViewPager viewPager;
    private SharedPreferences sharedPreferences;
    private List<String> tags = new ArrayList<>();
    private RelativeLayout project_rl;
    private PopupWindow popWnd = new PopupWindow();
    private PullToRefreshGridView myGridView;
    private int stratNum = 0;
    private int sizeNum = 20;
    private List<FootInfo.DataBean> datas = new ArrayList<>();
    private ProjectGridViewAdapter mYGridViewAdapter;

    private ImageView backIV;
    private ImageView slideIV;
    private ImageView searchIV;
    private ProjectTabLayoutAdapter tabLayoutAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        //从跳转页面获取一下信息：category(子项目id)，tags(List<Srting>子项目名)
        Intent intent = getIntent();
        tags = intent.getStringArrayListExtra("tags");
        id = intent.getStringExtra("id");
        //获取地址
        getAddress();

        //初始化视图
        initView();
        //初始化数据源
        initData();
        //初始化适配器
        initAdapter();
        //绑定适配器
        bindAdapter();
        //设置监听
        initListener();

    }

    //设置监听
    private void initListener() {
        //返回项目页面
        leftRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWnd.dismiss();
                leftRB.setSelected(true);
            }
        });

        //点击显示商家列列表PopupWindow
        rightRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.clear();
                //商家列表
                showPopupWindow();
            }
        });

        //点击返回
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popWnd.isShowing()){
                    popWnd.dismiss();
                }
               onBackPressed();
            }
        });


        slideIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabLayout.setScrollPosition((titles.size() - 1), 0, false);
            }
        });

        //点击搜索跳转到搜索页面
        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectListActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });


    }

    //显示商家列表
    private void showPopupWindow() {
        //初始化GridView数据
        initMYGridViewData();
    }

    private void InitMyGridViewListener() {

        myGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                pullToRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                pullDownRefresh();

            }
        });

    }

    //上拉加载
    private void pullDownRefresh() {
        stratNum += 20;
        sizeNum += 20;
        String size = sizeNum + "";
        String strat = stratNum + "";
        String url = AssortmentURL.PROJECT_SERVICES;
        OkHttpUtils.get()
                .url(url).addParams("start", strat).addParams("size", size)
                .addParams("lot", lot).addParams("lat", lat)
                .addParams("category", id).addParams("manualCity", manualCity)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        FootInfo footInfo = gson.fromJson(response, FootInfo.class);
                        datas.addAll(footInfo.getData());
                        mYGridViewAdapter.notifyDataSetChanged();
                        myGridView.onRefreshComplete();
                    }

                });
    }


    //下拉刷新
    private void pullToRefresh() {
        datas.clear();
        stratNum = 0;
        sizeNum = 20;
        String size = sizeNum + "";
        String strat = stratNum + "";
        String url = AssortmentURL.PROJECT_SERVICES;
        OkHttpUtils.get()
                .url(url).addParams("start", strat).addParams("size", size)
                .addParams("lot", lot).addParams("lat", lat)
                .addParams("category", id).addParams("manualCity", manualCity)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        FootInfo footInfo = gson.fromJson(response, FootInfo.class);
                        datas.addAll(footInfo.getData());
                        //初始化商家列表PopupWindow中GridView的适配器
                        initMYGridViewAdapter();
                        //绑定商家列表PopupWindow中GridView的适配器
                        bindMYGridViewAdapter();
                        myGridView.onRefreshComplete();
                    }

                });
    }


    //绑定商家列表PopupWindow中GridView的适配器
    private void bindMYGridViewAdapter() {
        myGridView.setAdapter(mYGridViewAdapter);
    }


    //初始化商家列表PopupWindow中GridView的适配器
    private void initMYGridViewAdapter() {
        mYGridViewAdapter = new ProjectGridViewAdapter(ProjectListActivity.this, datas);

    }


    //初始化商家列表数据
    private void initMYGridViewData() {

        String size = sizeNum + "";
        String strat = stratNum + "";
        String url = AssortmentURL.PROJECT_SERVICES;
        OkHttpUtils.get()
                .url(url).addParams("start", strat).addParams("size", size)
                .addParams("lot", lot).addParams("lat", lat)
                .addParams("category", id).addParams("manualCity", manualCity)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        FootInfo footInfo = gson.fromJson(response, FootInfo.class);
                        datas.addAll(footInfo.getData());
                        myGridView = new PullToRefreshGridView(ProjectListActivity.this);
                        myGridView.setMode(PullToRefreshBase.Mode.BOTH);
                        GridView refreshableView = myGridView.getRefreshableView();
                        refreshableView.setNumColumns(2);
                        //初始化商家列表PopupWindow中GridView的适配器
                        initMYGridViewAdapter();

                        //绑定商家列表PopupWindow中GridView的适配器
                        bindMYGridViewAdapter();

                        //MyGridView上拉加载下拉刷新
                        InitMyGridViewListener();

                        popWnd.setContentView(myGridView);
                        if (datas.isEmpty()) {
                            popWnd.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                            popWnd.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                            View inflate = LayoutInflater.from(ProjectListActivity.this).inflate(R.layout.project_noservice_shop_layout, null);
                            myGridView.setEmptyView(inflate);
                        } else {
                            popWnd.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                            popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

                        }
                        popWnd.setFocusable(true);
                        popWnd.setBackgroundDrawable(getResources().getDrawable(R.drawable.project_popup_backgroud));
                        popWnd.setOutsideTouchable(true);
                        popWnd.update();
                        popWnd.showAsDropDown(project_rl);
                        myGridView.onRefreshComplete();
                        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String serviceId = datas.get(position).getServiceId();
                                Intent intent = new Intent(ProjectListActivity.this,MerchantActivity.class);
                                intent.putExtra("id",serviceId);
                                startActivity(intent);
                            }
                        });

                    }

                });
    }


    //绑定适配器viewPager和tabLayout
    private void bindAdapter() {
        viewPager.setAdapter(tabLayoutAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    //初始化标题栏适配器
    private void initAdapter() {
        tabLayoutAdapter = new ProjectTabLayoutAdapter(getSupportFragmentManager(), fragments, titles);
    }

    //初始化视图
    private void initView() {

        backIV = (ImageView) findViewById(R.id.project_back_iv);
        slideIV = (ImageView) findViewById(R.id.project_tableLayout_slide_iv);
        searchIV = (ImageView) findViewById(R.id.project_search_iv);
        project_rl = (RelativeLayout) findViewById(R.id.project_rl);
        viewPager = (ViewPager) findViewById(R.id.project_vp);
        tabLayout = (TabLayout) findViewById(R.id.project_tableLayout);
        leftRB = (RadioButton) findViewById(R.id.project_title_left_rb);
        rightRB = (RadioButton) findViewById(R.id.project_title_right_rb);
        tabLayout.setTabTextColors(Color.GRAY, Color.rgb(230, 48, 32));//标题字体颜色

    }

    //初始化项目列表
    private void initData() {
        initTitles();
        Bundle[] bundles = new Bundle[tags.size() + 1];
        fragments = new ArrayList<>();
        //第一项为全部项目内容,tag为空
        String tag = "";
        bundles[0] = new Bundle();
        bundles[0].putString("tag", tag);
        bundles[0].putString("category", id);
        fragments.add(ProjactViewPagerFragment.newInstance(bundles[0]));
        //根据标题传入相应的tag到对应的fragment
        for (int i = 0; i < tags.size(); i++) {
            tag = tags.get(i);
            bundles[i + 1] = new Bundle();
            bundles[i + 1].putString("category", id);
            bundles[i + 1].putString("tag", tag);
            fragments.add(ProjactViewPagerFragment.newInstance(bundles[i + 1]));
        }
    }

    //初始化项目标题
    private void initTitles() {
        titles = new ArrayList<>();
        titles.add("全部");
        for (int i = 0; i < tags.size(); i++) {
            titles.add(tags.get(i));
        }
    }


    //获取当前位置信息
    public void getAddress() {
        sharedPreferences = this.getSharedPreferences("location", Context.MODE_PRIVATE);
        lot = sharedPreferences.getString("lot", "0");
        lat = sharedPreferences.getString("lat", "0");
        manualCity = sharedPreferences.getString("cityName", "北京");

    }

    @Override
    protected void onPause() {
        super.onPause();
        popWnd.dismiss();
    }
}
