package com.qianfeng.android.myapp.activity;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.adapter.CollectionAdapter;
import com.qianfeng.android.myapp.dao.CollectionInfo;
import com.qianfeng.android.myapp.dao.CollectionInfoDao;
import com.qianfeng.android.myapp.dao.DaoMaster;
import com.qianfeng.android.myapp.dao.DaoSession;

import java.util.List;

public class CollectionActivity extends AppCompatActivity {

    private GridView gridView;
    private ImageView back;
    private CollectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        initView();

        initAdapter();

        initData();

        initListener();
    }

    private void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void initView() {
        gridView = (GridView) findViewById(R.id.collection_gv);
        back = (ImageView) findViewById(R.id.collection_service_back_iv);

    }

    private void initAdapter() {
        adapter = new CollectionAdapter(this);
        gridView.setAdapter(adapter);
    }

    private void initData() {
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "liuxiao", null);
        //通过Handler类获得数据库对象
        SQLiteDatabase readableDatabase = mHelper.getReadableDatabase();
        //通过数据库对象生成DaoMaster对象
        DaoMaster daoMaster = new DaoMaster(readableDatabase);
        DaoSession daoSession = daoMaster.newSession();
        //通过DaoSeesion对象获得CustomerDao对象
        CollectionInfoDao collectionInfoDao = daoSession.getCollectionInfoDao();

        List<CollectionInfo> collectionInfos = collectionInfoDao.loadAll();
        adapter.setData(collectionInfos);
    }
}
