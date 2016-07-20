package com.qianfeng.android.myapp.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.activity.ProjectListActivity;
import com.qianfeng.android.myapp.activity.ServiceDetailsActivity;
import com.qianfeng.android.myapp.adapter.ProjectListAdapter;
import com.qianfeng.android.myapp.bean.AssortmentRightLV;
import com.qianfeng.android.myapp.data.AssortmentURL;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjactViewPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjactViewPagerFragment extends Fragment {
    private View view;
    private int stratNum = 0;
    private int sizeNum = 20;
    private String lot;
    private String lat;
    private String tag;
    private String manualCity;
    private String category;
    private List<AssortmentRightLV.DataBean.ItemsBean> mItems = new ArrayList<>();
    private ProjectListAdapter adapter;
    private PullToRefreshListView ptrListView;
    private SharedPreferences sharedPreferences;

    // TODO: Rename and change types and number of parameters
    public static ProjactViewPagerFragment newInstance(Bundle args) {
        ProjactViewPagerFragment fragment = new ProjactViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_projact_view_pager, container,false);

        //从当前Activity（ProjectListActivity）中获取Bundle中的值
        Bundle arguments = getArguments();
        category = (String) arguments.get("category");
        tag = (String) arguments.get("tag");

        //初始化视图控件
        initView();
        //初始化数据
        listViewData();
        //设置监听
        initListener();
        //初始化适配器
        initAdapter();
        //绑定适配器
        bindAdapter();

        return view;
    }

    private void initListener() {
        stratNum = 0;
        sizeNum = 20;

        //设置上拉及下拉刷新
        ptrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mItems.clear();
                stratNum = 0;
                sizeNum = 20;
                listViewData();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                stratNum +=20;
                sizeNum +=20;
                listViewData();

            }
        });

        ptrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ServiceDetailsActivity.class);
                String id1 = mItems.get(position-1).getId();
                String serviceId = mItems.get(position-1).getServiceId();
                intent.putExtra("id",id1);
                intent.putExtra("serviceId",serviceId);
                startActivity(intent);
            }
        });
    }

    //初始化数据
    private void listViewData() {
        sharedPreferences = getActivity().getSharedPreferences("location", Context.MODE_PRIVATE);
        lot = sharedPreferences.getString("lot", "0");
        lat = sharedPreferences.getString("lat", "0");
        manualCity = sharedPreferences.getString("cityName", "北京");
        String size = sizeNum + "";
        String strat = stratNum + "";
        String url = AssortmentURL.ASSORTMENT_SUBITEM;
        OkHttpUtils.get()
                .url(url).addParams("start", strat).addParams("size", size)
                .addParams("lot", lot).addParams("lat", lat)
                .addParams("manualCity", manualCity)
                .addParams("tag",tag).addParams("category",category)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        AssortmentRightLV assortmentRightLV = gson.fromJson(response, AssortmentRightLV.class);
                        mItems.addAll(assortmentRightLV.getData().getItems()) ;
                        adapter.notifyDataSetChanged();
                        ptrListView.onRefreshComplete();
                    }
                });
    }

    //初始化视图
    private void initView() {
        ptrListView = (PullToRefreshListView) view.findViewById(R.id.project_ptr_lv);
        ptrListView.setMode(PullToRefreshBase.Mode.BOTH);
        View noservice = LayoutInflater.from(getActivity()).inflate(R.layout.project_noservice_layout, null);
        ptrListView.setEmptyView(noservice);
    }

    //初始化适配器
    private void initAdapter() {
        adapter = new ProjectListAdapter(getActivity(), mItems);
    }

    //绑定适配器
    private void bindAdapter() {
        ptrListView.setAdapter(adapter);
    }



}
