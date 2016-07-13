package com.qianfeng.android.myapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView{

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

//        OkHttpUtils.postString()
//                .url("http://mobileif.maizuo.com/ver4/banner/list/v2?cityId=15&version=4.7&agentId=wandoujia&channelID=31&clientID=31&revision=4.7&agentID=wandoujia")
//                .content("{'cityId':'15','type':'2'}")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        Log.e("hehe",response);
//                    }
//                });
    }

    private void initView() {

//        refreshListView = (PullToRefreshExpandableListView) findViewById(R.id.main_content_ev);
//        radioGroup = (RadioGroup) findViewById(R.id.main_bottom_rg);

    }

    @Override
    public void initFragment() {

    }
}
