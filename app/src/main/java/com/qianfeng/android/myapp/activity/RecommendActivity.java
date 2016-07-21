package com.qianfeng.android.myapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.adapter.HomeFootGridViewAdapter;
import com.qianfeng.android.myapp.bean.FootInfo;
import com.qianfeng.android.myapp.common.UrlConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

public class RecommendActivity extends AppCompatActivity {

    private HomeFootGridViewAdapter adapter;
    private GridView gridView;
    private ImageView back;
    private int index;
    private PullToRefreshGridView refreshGridView;
    private String lat;
    private String city;
    private String lot;
    private FootInfo footListInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        lot = intent.getStringExtra("lot");
        lat = intent.getStringExtra("lat");
        index = 10;


        initView();

        initAdapter();

        initData();

        initListener();
    }

    private void initListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = footListInfo.getData().get(position).getServiceId();
                Intent intent = new Intent(RecommendActivity.this, MerchantActivity.class);
                intent.putExtra("id", url);
                startActivity(intent);
            }
        });

        refreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                index = 10;
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                index += 10;
                initData();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {

        OkHttpUtils.get()
                .url(UrlConfig.getRecommendServiceUrl(city, lot, lat, index))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        footListInfo = gson.fromJson(response, FootInfo.class);
                        List<FootInfo.DataBean> list = footListInfo.getData();

                        if (list != null) {
                            if (index == 10) {

                                initAdapter();
                            }
                            adapter.setData(footListInfo.getData());
                            adapter.notifyDataSetChanged();
                            refreshGridView.onRefreshComplete();
                        }
                    }
                });


    }

    private void initAdapter() {
        adapter = new HomeFootGridViewAdapter(this);
        gridView.setAdapter(adapter);
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.recommend_service_back_iv);
        refreshGridView = (PullToRefreshGridView) findViewById(R.id.recommend_service_gv);
        refreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
        gridView = refreshGridView.getRefreshableView();
    }


}
