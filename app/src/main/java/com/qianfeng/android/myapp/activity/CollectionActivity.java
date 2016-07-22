package com.qianfeng.android.myapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.adapter.CollectionAdapter;

public class CollectionActivity extends AppCompatActivity {

    private PullToRefreshGridView pull;
    private ImageView back;
    private CollectionAdapter adapter;
    private GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        initView();

        initAdapter();


        initListener();
    }

    private void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pull.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {

            @Override
            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
                initAdapter();
                pull.onRefreshComplete();
            }
        });

    }

    private void initView() {
        pull = (PullToRefreshGridView) findViewById(R.id.collection_gv);
        gv = pull.getRefreshableView();
        back = (ImageView) findViewById(R.id.collection_service_back_iv);

    }

    private void initAdapter() {
        adapter = new CollectionAdapter(this);
        gv.setAdapter(adapter);
    }


}
