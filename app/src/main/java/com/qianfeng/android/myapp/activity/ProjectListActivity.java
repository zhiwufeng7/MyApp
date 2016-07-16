package com.qianfeng.android.myapp.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.adapter.HomeFootGridViewAdapter;
import com.qianfeng.android.myapp.adapter.ProjectListAdapter;
import com.qianfeng.android.myapp.adapter.ProjectTabLayoutAdapter;
import com.qianfeng.android.myapp.bean.AssortmentRightLV;
import com.qianfeng.android.myapp.bean.FootInfo;
import com.qianfeng.android.myapp.bean.ProjectServicesInfo;
import com.qianfeng.android.myapp.data.AssortmentURL;
import com.qianfeng.android.myapp.fragment.AssortmentFragment;
import com.qianfeng.android.myapp.fragment.ProjactViewPagerFragment;
import com.qianfeng.android.myapp.widget.MyGridView;
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

    private String lot;
    private String lat;
    private String category ="20";
    private String manualCity;

    List<Fragment> fragments;
    private ProjectTabLayoutAdapter tabLayoutAdapter;
    List<String> titles;
    private ViewPager viewPager;
    private SharedPreferences sharedPreferences;
    private List<String> tags = new ArrayList<>();
    private RelativeLayout project_rl;
    private PopupWindow popWnd;
    private MyGridView myGridView;
    private int stratNum = 0;
    private int sizeNum = 20;
    private List<FootInfo.DataBean> datas = new ArrayList<>();
    private HomeFootGridViewAdapter mYGridViewAdapter;
    private String id;


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

        bindAdapter();

        initListener();

    }

    private void initListener() {
        leftRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWnd.dismiss();
            }
        });
        rightRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //商家列表
                showPopupWindow();
            }
        });
    }

    //显示商家列表
    private void showPopupWindow() {
        View contentView = LayoutInflater.from(ProjectListActivity.this).inflate(R.layout.project_popuplayout, null);
        myGridView = (MyGridView) contentView.findViewById(R.id.projact_my_gv);

        popWnd = new PopupWindow();
        popWnd.setContentView(contentView);
        popWnd.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popWnd.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popWnd.setTouchable(true);
        popWnd.setBackgroundDrawable(getResources().getDrawable(R.drawable.project_popup_backgroud));
        popWnd.showAsDropDown(project_rl);

        //初始化GridView数据

        initMYGridViewData();

        initMYGridViewAdapter();

        bindMYGridViewAdapter();
    }

    private void bindMYGridViewAdapter() {
        myGridView.setAdapter(mYGridViewAdapter);
    }

    private void initMYGridViewAdapter() {
        mYGridViewAdapter = new HomeFootGridViewAdapter(ProjectListActivity.this);
    }

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
                        List<FootInfo.DataBean> data = footInfo.getData();
                        datas.addAll(data);
                        mYGridViewAdapter.upData(datas);
                        mYGridViewAdapter.notifyDataSetChanged();
                    }

                });
    }

    private void bindAdapter() {
        viewPager.setAdapter(tabLayoutAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initAdapter() {
        tabLayoutAdapter = new ProjectTabLayoutAdapter(getSupportFragmentManager(), fragments, titles);
    }

    private void initView() {
        project_rl = (RelativeLayout) findViewById(R.id.project_rl);
        viewPager = (ViewPager) findViewById(R.id.project_vp);
        tabLayout = (TabLayout) findViewById(R.id.project_tableLayout);
        leftRB = (RadioButton) findViewById(R.id.project_title_left_rb);
        rightRB = (RadioButton) findViewById(R.id.project_title_right_rb);
        tabLayout.setTabTextColors(Color.GRAY, Color.rgb(230, 48, 32));//标题字体颜色

    }

    private void initData() {
        initTitles();
        fragments = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putString("category", id);
        for (int i = 0; i <tags.size() ; i++) {
            fragments.add(ProjactViewPagerFragment.newInstance(bundle));
        }

    }

    private void initTitles() {
        titles = new ArrayList<>();

        for (int i = 0; i <tags.size() ; i++) {
            titles.add(tags.get(i));
        }
    }

    public void getAddress() {
        sharedPreferences = this.getSharedPreferences("location", Context.MODE_PRIVATE);
        //先设置一个默认值
        lot = sharedPreferences.getString("lot", "0");
        lat = sharedPreferences.getString("lat", "0");
        manualCity = sharedPreferences.getString("cityName", "北京");
    }
}
