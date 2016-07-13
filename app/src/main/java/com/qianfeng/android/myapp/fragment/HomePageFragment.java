package com.qianfeng.android.myapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.adapter.HomePullToRefreshExpandListViewAdapter;
import com.qianfeng.android.myapp.bean.HomePageEV;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;


public class HomePageFragment extends Fragment {
    private HomePullToRefreshExpandListViewAdapter adapter;

    private PullToRefreshExpandableListView refreshExpandableListView;
    private ExpandableListView listView;


    public HomePageFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomePageFragment newInstance() {
        HomePageFragment fragment = new HomePageFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        refreshExpandableListView = (PullToRefreshExpandableListView) view.findViewById(R.id.home_page_fragment_ev);
        listView = refreshExpandableListView.getRefreshableView();
        adapter = new HomePullToRefreshExpandListViewAdapter(getContext());
        listView.setAdapter(adapter);

        initData();

        initListener();


        return view;
    }

    private void initListener() {

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });




    }

    private void initData() {

        String url="http://api.daoway.cn/daoway/rest/service_items/recommend_top?start=0&size=10&lot=118.778074&lat=32.057236&manualCity=%E5%8D%97%E4%BA%AC&imei=133524632646575&includeNotInScope=true";
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson=new Gson();
                        HomePageEV data=gson.fromJson(response,HomePageEV.class);
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                        //默认所有的Group全部展开
                        for (int i = 0; i < data.getData().size(); i++) {
                            listView.expandGroup(i);
                        }
                    }
                });

    }


}
