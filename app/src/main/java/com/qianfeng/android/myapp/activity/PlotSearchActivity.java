package com.qianfeng.android.myapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.adapter.PlotSearchAdapter;
import com.qianfeng.android.myapp.bean.PlotSearch;
import com.qianfeng.android.myapp.bean.Plots;
import com.qianfeng.android.myapp.data.Url;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class PlotSearchActivity extends AppCompatActivity {

    private EditText et;
    private ListView lv;
    private String cityName;
    private List<PlotSearch.DataBean> data = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_search);
        Intent intent = getIntent();
        cityName = intent.getStringExtra("cityName");
        sharedPreferences = getSharedPreferences("location", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        initView();
        setListener();
    }

    private void setListener() {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                initData( et.getText().toString());
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlotSearch.DataBean dataBean = data.get(position);
                editor.putString("piot", dataBean.getName());
                editor.putString("cityName", cityName);
                editor.putString("lot", String.valueOf(dataBean.getLot()));
                editor.putString("lat",String.valueOf(dataBean.getLat()));
                editor.putString("id", String.valueOf(dataBean.getId()));
                editor.commit();
                setResult(1);
                finish();
            }
        });
    }

    private void initData(String content) {
        OkHttpUtils.get().url(Url.PLOT_SEARCH)
                .addParams("manualCity",cityName)
                .addParams("search",content)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.length() > 100) {
                            Gson gson = new Gson();
                            PlotSearch plots = gson.fromJson(response, PlotSearch.class);
                            data = plots.getData();
                        } else {
                            data.clear();
                        }
                        setAdapter();
                    }
                });
    }

    private void setAdapter() {
        lv.setAdapter(new PlotSearchAdapter(this,R.layout.nearby_plot_lv_item,data));
    }

    private void initView() {
        et = (EditText)findViewById(R.id.plot_search_et);
        lv = (ListView) findViewById(R.id.plot_search_lv);
    }

    public void onBack(View view) {
        finish();
    }
}
