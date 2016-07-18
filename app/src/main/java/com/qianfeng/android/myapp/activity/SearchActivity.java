package com.qianfeng.android.myapp.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import com.google.gson.Gson;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.adapter.HistoryAdapter;
import com.qianfeng.android.myapp.adapter.HotSearchAdapter;
import com.qianfeng.android.myapp.bean.HotSearch;
import com.qianfeng.android.myapp.dao.DaoMaster;
import com.qianfeng.android.myapp.dao.DaoSession;
import com.qianfeng.android.myapp.dao.SearchHistory;
import com.qianfeng.android.myapp.dao.SearchHistoryDao;
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
public class SearchActivity extends AppCompatActivity{
    private EditText et;
    private MyGridView gv;
    private MyListView lv;
    private List<String> historyList = new ArrayList<>();
    private List<String> hotList ;
    private HistoryAdapter historyAdapter;
    private SearchHistoryDao searchHistoryDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initGreenDao();
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et.setText(hotList.get(position));
                onSearch(null);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et.setText(historyList.get(position));
                onSearch(null);
            }
        });
    }

    private void initGreenDao() {
        //创建一个开发环境的Helper类，如果是正式环境调用DaoMaster.OpenHelper
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this,"liuxiao",null);
        //通过Handler类获得数据库对象
        SQLiteDatabase readableDatabase = mHelper.getReadableDatabase();
        //通过数据库对象生成DaoMaster对象
        DaoMaster daoMaster = new DaoMaster(readableDatabase);
        //获取DaoSession对象
        DaoSession daoSession = daoMaster.newSession();
        //通过DaoSeesion对象获得CustomerDao对象
        searchHistoryDao = daoSession.getSearchHistoryDao();
        List<SearchHistory> searchHistories = searchHistoryDao.loadAll();
        for (int i = 0 ;i<searchHistories.size();i++){
            historyList.add(searchHistories.get(i).getContent());
        }

    }

    private void initData() {
        OkHttpUtils.get().url(Url.HOT_SEARCH)
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
        gv.setAdapter(new HotSearchAdapter(SearchActivity.this,R.layout.hot_city_item,hotList));
        historyAdapter = new HistoryAdapter(SearchActivity.this, R.layout.history_item, historyList);
        lv.setAdapter(historyAdapter);
    }

    private void initView() {
        et = (EditText) findViewById(R.id.search_et);
        gv = (MyGridView) findViewById(R.id.search_gv);
        lv = (MyListView) findViewById(R.id.search_lv);

    }

    public void onClear(View view){
        historyList.clear();
        historyAdapter.notifyDataSetChanged();
    }

    public void onSearch(View view){
        String text = et.getText().toString();
        if (!TextUtils.isEmpty(text)){
            historyListAdd(text);
            historyAdapter.notifyDataSetChanged();
            Intent intent = new Intent(this,SearchContentActivity.class);
            intent.putExtra("content",text);
            startActivity(intent);
        }

    }

    public void historyListAdd(String text){
        for (int i = 0 ;i<historyList.size();i++){
            String content = historyList.get(i);
            if (content.equals(text)){
                historyList.remove(i);
            }
        }
        historyList.add(0,text);
    }


    public void onBack(View view){
       // finish();
        Intent intent = new Intent(this,MerchantActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        searchHistoryDao.deleteAll();
        for (int i = 0 ;i<historyList.size();i++){
            SearchHistory searchHistory = new SearchHistory();
            searchHistory.setContent(historyList.get(i));
            searchHistoryDao.insert(searchHistory);
        }
        super.onDestroy();
    }
}
