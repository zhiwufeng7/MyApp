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
import android.view.View;
import android.widget.RadioButton;
import android.widget.TableLayout;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.adapter.ProjectListAdapter;
import com.qianfeng.android.myapp.adapter.ProjectTabLayoutAdapter;
import com.qianfeng.android.myapp.bean.AssortmentRightLV;
import com.qianfeng.android.myapp.data.AssortmentURL;
import com.qianfeng.android.myapp.fragment.AssortmentFragment;
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

    private String lot = null;
    private String lat = null;
    private String category = null;
    private String manualCity = null;

    List<Fragment> fragments;
    private ProjectTabLayoutAdapter tabLayoutAdapter;
    List<String> titles;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        //从跳转页面获取一下信息：城市名，lot，lat，category(子项目id)，tags(List<Srting>子项目名)
        Intent intent = getIntent();
        SharedPreferences sharedPreferences = ProjectListActivity.this.getSharedPreferences("location", Context.MODE_PRIVATE);
        //先设置一个默认值
        lot = "0";
        lat = "0";
        category = "20";
        manualCity = "北京";

        //初始化视图
        initView();
        //初始化数据源
        initData();
        //初始化适配器
        initAdapter();

        bindAdapter();
    }

    private void bindAdapter() {
        viewPager.setAdapter(tabLayoutAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initAdapter() {
        tabLayoutAdapter = new ProjectTabLayoutAdapter(getSupportFragmentManager(),fragments,titles);
    }


    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.project_vp);

        tabLayout = (TabLayout) findViewById(R.id.project_tableLayout);
        leftRB = (RadioButton) findViewById(R.id.project_title_left_rb);
        rightRB = (RadioButton) findViewById(R.id.project_title_right_rb);
        tabLayout.setTabTextColors(Color.GRAY,Color.rgb(230,48,32));//标题字体颜色

    }

    private void initData() {
        initTitles();
        fragments = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putString("lot",lot);
        bundle.putString("lat",lat);
        bundle.putString("category",category);
        bundle.putString("manualCity",manualCity);
        fragments.add(ProjactViewPagerFragment.newInstance(bundle));
        fragments.add(ProjactViewPagerFragment.newInstance(bundle));
        fragments.add(ProjactViewPagerFragment.newInstance(bundle));
        fragments.add(ProjactViewPagerFragment.newInstance(bundle));
        fragments.add(ProjactViewPagerFragment.newInstance(bundle));
        fragments.add(ProjactViewPagerFragment.newInstance(bundle));
        fragments.add(ProjactViewPagerFragment.newInstance(bundle));

    }

    private void initTitles() {
        titles = new ArrayList<>();
        titles.add("标题一");
        titles.add("标题二");
        titles.add("标题二");
        titles.add("标题二");
        titles.add("标题二");
        titles.add("标题二");
        titles.add("标题二");


    }



}
