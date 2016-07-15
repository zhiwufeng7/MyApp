package com.qianfeng.android.myapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.google.gson.Gson;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.adapter.HistoryAdapter;
import com.qianfeng.android.myapp.adapter.HotSearchAdapter;
import com.qianfeng.android.myapp.bean.HotSearch;
import com.qianfeng.android.myapp.data.Url;
import com.qianfeng.android.myapp.widget.MyGridView;
import com.qianfeng.android.myapp.widget.MyListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/7/15.
 */
public class SearchAcitvity extends AppCompatActivity{
    private EditText et;
    private MyGridView gv;
    private MyListView lv;
    private List<String> historyList = new ArrayList<>();
    private List<String> hotList ;
    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();

    }

    private void initData() {
        OkHttpUtils.get().url(Url.SEARCH)
                .build()
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                HotSearch hotSearch = gson.fromJson(response, HotSearch.class);
                hotList = hotSearch.getData();
                setAdapter();


            }
        });
    }

    private void setAdapter() {
        gv.setAdapter(new HotSearchAdapter(SearchAcitvity.this,R.layout.hot_city_item,hotList));
        historyAdapter = new HistoryAdapter(SearchAcitvity.this, R.layout.history_item, historyList);
        lv.setAdapter(historyAdapter);
    }

    private void initView() {
        et = (EditText) findViewById(R.id.search_et);
        gv = (MyGridView) findViewById(R.id.search_gv);
        lv = (MyListView) findViewById(R.id.search_lv);
    }

    public void onClear(View view){

    }

    public void onSearch(View view){

    }

    public void onBack(View view){
        finish();
    }
}
