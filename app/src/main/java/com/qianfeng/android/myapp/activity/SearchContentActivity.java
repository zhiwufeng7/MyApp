package com.qianfeng.android.myapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.adapter.PlotSearchAdapter;
import com.qianfeng.android.myapp.adapter.SearchListAdapter;
import com.qianfeng.android.myapp.bean.PlotSearch;
import com.qianfeng.android.myapp.bean.SearchList;
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
public class SearchContentActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et;
    private RadioGroup rg;
    private ListView lv;
    private RadioButton synthesize;
    private RadioButton sales;
    private RadioButton price;
    private RadioButton good;
    private String sort_by = "auto";
    private String sort = "desc";
    private String[] sorts = {"auto", "sale", "price", "comment"};
    boolean order = false;
    private ArrayList<RadioButton> radioButtons;
    private SharedPreferences sharedPreferences;
    private PullToRefreshListView pull;
    private String cityName;
    private String lot;
    private String lat;
    private int start;
    private String content;
    private List<SearchList.DataBean.ItemsBean> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_content);
        Intent intent = getIntent();
        content = intent.getStringExtra("content");
        initView();
        initData();
        setListener();
        et.setText(content);
        onSearch(null);
    }

    private void initData() {
        sharedPreferences = getSharedPreferences("location", Context.MODE_PRIVATE);
        cityName = sharedPreferences.getString("cityName", "北京");
        lot = sharedPreferences.getString("lot", "0");
        lat = sharedPreferences.getString("lat", "0");
    }

    private void setListener() {
        for (int i = 0; i < radioButtons.size(); i++) {
            RadioButton radioButton = radioButtons.get(i);
            radioButton.setTag(i);
            radioButton.setOnClickListener(this);
        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                order = true;
                sort = "desc";
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchContentActivity.this, ServiceDetailsActivity.class);
                String mId = items.get(position-1).getId();
                String serviceId = items.get(position-1).getServiceId();
                intent.putExtra("id",mId);
                intent.putExtra("serviceId",serviceId);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        et = (EditText) findViewById(R.id.search_content_et);
        rg = (RadioGroup) findViewById(R.id.search_content_rg);
        synthesize = (RadioButton) findViewById(R.id.search_content_rb_synthesize);
        sales = (RadioButton) findViewById(R.id.search_content_rb_sales);
        price = (RadioButton) findViewById(R.id.search_content_rb_price);
        good = (RadioButton) findViewById(R.id.search_content_rb_good);
        pull = (PullToRefreshListView) findViewById(R.id.search_content_lv);
        pull.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view, null));
        pull.setMode(PullToRefreshBase.Mode.BOTH);
        lv = pull.getRefreshableView();
        radioButtons = new ArrayList<>();
        radioButtons.add(synthesize);
        radioButtons.add(sales);
        radioButtons.add(price);
        radioButtons.add(good);
    }

    public void onSearch(View view) {
        content = et.getText().toString();
        OkHttpUtils.get().url(Url.SEARCH)
                .addParams("start", String.valueOf(start)).addParams("size", String.valueOf(start + 20))
                .addParams("text", content).addParams("manualCity", cityName)
                .addParams("sort_by", sort_by).addParams("sort", sort)
                .addParams("lot", lot).addParams("lat", lat)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                SearchList searchList = gson.fromJson(response, SearchList.class);
                items = searchList.getData().getItems();
                setAdapter();
            }
        });
    }

    private void setAdapter() {
        lv.setAdapter(new SearchListAdapter(this,R.layout.assortment_right_listview_content,items));
    }

    public void onBack(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        sort_by = sorts[tag];
        Drawable drawable = null;
        for (int i = 0; i < radioButtons.size(); i++) {
            RadioButton radioButton = radioButtons.get(i);
            if (i != tag) {
                drawable = getResources().getDrawable(R.drawable.img_search_1_down);
            } else {
                if (order) {
                    drawable = getResources().getDrawable(R.drawable.img_search_2_down);
                    sort = "desc";
                    order = false;
                } else {
                    drawable = getResources().getDrawable(R.drawable.img_search_2_up);
                    sort = "asc";
                    order = true;
                }
            }
            drawable.setBounds(0, 0, 20, 20);
            radioButton.setCompoundDrawables(null, null, drawable, null);
        }
        onSearch(null);
    }
}
