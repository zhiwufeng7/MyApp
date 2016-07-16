package com.qianfeng.android.myapp.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.activity.ProjectListActivity;
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
    private String category;
    private String manualCity;
    private String tag;
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
        if(view==null){
            view =LayoutInflater.from(getActivity()).inflate(R.layout.fragment_projact_view_pager, null);
        }
        lot = getArguments().getString("lot");
        lat = getArguments().getString("lat");
        category = getArguments().getString("category");
        manualCity = getArguments().getString("manualCity");
        listViewData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            view =inflater.inflate(R.layout.fragment_projact_view_pager, container,false);
        initView();
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAdapter();
        bindAdapter();
    }
    private void initView() {
        ptrListView = (PullToRefreshListView) view.findViewById(R.id.project_ptr_lv);
    }

    private void bindAdapter() {
        ptrListView.setAdapter(adapter);
    }

    private void initAdapter() {
        adapter = new ProjectListAdapter(getActivity(), mItems);
    }

    private void listViewData() {
        sharedPreferences = getActivity().getSharedPreferences("location", Context.MODE_PRIVATE);
        //先设置一个默认值
        lot = sharedPreferences.getString("lot", "0");
        lat = sharedPreferences.getString("lat", "0");
        manualCity = sharedPreferences.getString("cityName", "北京");
       // tags = new ArrayList<>();//各类服务的子服务名称集合，需要从跳转页面传过来
        category = "36";//各类服务名称
        tag = "小时工";//从Bundle中获取
        String size = sizeNum + "";
        String strat = stratNum + "";
        String url = AssortmentURL.ASSORTMENT_SUBITEM;
        OkHttpUtils.get()
                .url(url).addParams("start", strat).addParams("size", size)
                .addParams("lot", lot).addParams("lat", lat)
                .addParams("category", category).addParams("manualCity", manualCity)
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
                    }
                });
    }

}
