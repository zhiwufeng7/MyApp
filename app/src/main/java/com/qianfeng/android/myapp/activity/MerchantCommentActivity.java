package com.qianfeng.android.myapp.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.adapter.CommentListAdapter;
import com.qianfeng.android.myapp.bean.CommentList;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

public class MerchantCommentActivity extends AppCompatActivity {

    private PullToRefreshListView pull;
    private TabLayout tab;
    private List<CommentList.DataBean.CommentsBean> comments;
    private ListView listView;
    private String[] tabTypes = {"all", "good", "average", "bad"};
    private CommentList.DataBean data;
    private int num = 20;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_comment);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        initView();
        initData();
    }

    private void initData() {
        OkHttpUtils.get().url("http://api.daoway.cn/daoway/rest/service/"+id+"/comments?start=0&size=20&filter=all")
                .build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                CommentList commentList = gson.fromJson(response, CommentList.class);
                comments = commentList.getData().getComments();
                data = commentList.getData();
                setAdapter();
            }
        });
    }

    private void setAdapter() {
        tab.addTab(tab.newTab().setText("全部("+data.getTotalCount()+")"));
        tab.addTab(tab.newTab().setText("好评("+data.getGoodCount()+")"));
        tab.addTab(tab.newTab().setText("中评("+data.getAverageCount()+")"));
        tab.addTab(tab.newTab().setText("差评("+data.getBadCount()+")"));
        CommentListAdapter commentListAdapter = new CommentListAdapter(this,R.layout.comment_list_item,comments);
        listView.setAdapter(commentListAdapter);
    }

    private void initView() {
        tab = (TabLayout) findViewById(R.id.merchant_comment_tab);
        pull = (PullToRefreshListView) findViewById(R.id.merchant_comment_lv);
        listView = pull.getRefreshableView();
    }

    public void onBack(View v) {
        finish();
    }
}
