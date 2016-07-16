package com.qianfeng.android.myapp.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.activity.ProjectListActivity;
import com.qianfeng.android.myapp.adapter.AssortmentRightAdapter;
import com.qianfeng.android.myapp.bean.AssortmentLeftLV;
import com.qianfeng.android.myapp.bean.AssortmentRightLV;
import com.qianfeng.android.myapp.data.AssortmentURL;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * fragment：分类页面
 * create by chengxiao
 */

public class AssortmentFragment extends Fragment {
    private View view;
    private List<String> names = new ArrayList<>();
    private List<String> categoryIds = new ArrayList<>();
    private ArrayAdapter leftAdapter;
    private ListView leftListView;
    private PullToRefreshListView rightRefreshListView;
    private List<AssortmentRightLV.DataBean.ItemsBean> mItems = new ArrayList<>();
    private AssortmentRightAdapter rightAdapter;
    private Map<String, List<String>> buttonMap = new HashMap<>();
    private LinearLayout linearLayoutTitle;
    private List<String> buttonList = new ArrayList<>();
    private RelativeLayout nullLinearLayout;
    private int startNum = 0;
    private int sizeNum = 20;
    private SharedPreferences sharedPreferences;
    private String city;
    private String lot;
    private String lat;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AssortmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AssortmentFragment newInstance(Bundle args) {
        AssortmentFragment fragment = new AssortmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建视图


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assortment, null);
        //初始化左右两边ListView及中间标题数据源
        initData();
        //初始化控件
        initView();
        //初始化适配器
        initAdapter();
        //绑定适配器
        bindAdapter();
        //设置监听
        initListener();
        //默认页面全部选项的刷新。
        initAllBtnRefresh();

        return view;
    }

    //默认页面全部选项的刷新。
    private void initAllBtnRefresh() {
        startNum = 0;
        sizeNum = 20;
        rightRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        rightRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mItems.clear();
                startNum = 0;
                sizeNum = 20;
                rightListViewData(0, startNum, sizeNum);

            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                startNum += 20;
                sizeNum += 20;
                rightListViewData(0, startNum, sizeNum);
            }
        });
    }


    //设置监听
    private void initListener() {
        //设置左边的ListView监听事件，点击更改右边标题
        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //加载标题
                rightTitleData(position);
                mItems.clear();//清空列表内容
                rightListViewData(position, 0, 20);//设置默认内容，显示各子项的全部信息
                refreshRightContent(0, position);//刷新
            }
        });

    }

    //加载右边标题
    private void rightTitleData(final int position) {
        linearLayoutTitle.removeAllViews();
        buttonList = buttonMap.get(names.get(position));
        //如果已添加“全部”选项，则不重复添加
        if (!buttonList.get(0).equals("全部")) {
            buttonList.add(0, "全部");
        }
        final int size = buttonList.size();
        for (int i = 0; i < size; i++) {
            Button button = new Button(getActivity());
            button.setTag(i);
            button.setTextSize(12);
            button.setBackgroundResource(R.drawable.assortment_right_title_text_style);
            button.setGravity(Gravity.CENTER);
            button.setText(buttonList.get(i));
            //设置标题文字颜色
            ColorStateList csl = getActivity().getResources().getColorStateList(R.color.assortment_right_title_text_color);
            button.setTextColor(csl);
            linearLayoutTitle.addView(button);
        }
        linearLayoutTitle.getChildAt(0).setEnabled(false);
        //根据标题的位置刷新列表内容
        rightTitleListener(position);

    }

    //设置点击右边标题监听
    private void rightTitleListener(final int position) {
        final int childCount = linearLayoutTitle.getChildCount();
        for (int i = 0; i < childCount; i++) {
            linearLayoutTitle.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setEnabled(false);
                    final int tag = (int) v.getTag();
                    mItems.clear();
                    if (tag == 0) {
                        rightListViewData(position, 0, 20);
                    } else {
                        rightListContentFL(buttonList.get(tag), position, 0, 20);
                    }
                    for (int i = 0; i < childCount; i++) {
                        if (tag != i) {
                            linearLayoutTitle.getChildAt(i).setEnabled(true);
                        }
                    }
                    //根据标题位置设置刷新
                    refreshRightContent(tag, position);
                }
            });
        }
    }

    //右边listview刷新
    private void refreshRightContent(final int tag, final int position) {
        startNum = 0;
        sizeNum = 20;
        rightRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mItems.clear();
                startNum = 0;
                sizeNum = 20;
                if (tag == 0) {
                    rightListViewData(position, startNum, sizeNum);
                } else {
                    rightListContentFL(buttonList.get(tag), position, startNum, sizeNum);
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                startNum += 20;
                sizeNum += 20;
                //如果标题为全部，加载“全部”对应详情，否则加载子类详情
                if (tag == 0) {
                    rightListViewData(position, startNum, sizeNum);
                } else {
                    rightListContentFL(buttonList.get(tag), position, startNum, sizeNum);
                }

            }
        });
    }


    //通过获取到的地址更新数据
    public void refresh() {
        initData();
    }


    //加载分类子项数据
    private void rightListContentFL(String title, int position, int startNum, int sizeNum) {
        String start = startNum + "";
        String size = sizeNum + "";
        String category = categoryIds.get(position);
        String tag = title;
        OkHttpUtils.get()
                .url(AssortmentURL.ASSORTMENT_SUBITEM).addParams("start", start).addParams("size", size)
                .addParams("category", category).addParams("tag", tag)
                .addParams("manualCity", city).addParams("lot", lot).addParams("lat", lat)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        List<AssortmentRightLV.DataBean.ItemsBean> items = new ArrayList<>();
                        Gson gson = new Gson();
                        AssortmentRightLV assortmentRightLV = gson.fromJson(response, AssortmentRightLV.class);
                        items = assortmentRightLV.getData().getItems();
                        mItems.addAll(items);
                        if (mItems.isEmpty()) {
                            nullLinearLayout.setVisibility(View.VISIBLE);
                            rightRefreshListView.setVisibility(View.GONE);
                        } else {
                            rightRefreshListView.setVisibility(View.VISIBLE);
                            nullLinearLayout.setVisibility(View.GONE);
                        }
                        rightAdapter.notifyDataSetChanged();
                        rightRefreshListView.onRefreshComplete();
                    }
                });
    }


    //初始化视图控件
    private void initView() {
        //初始化视图：
        nullLinearLayout = (RelativeLayout) view.findViewById(R.id.assortment_right_content_null_ll);
        //左右两边listView
        leftListView = (ListView) view.findViewById(R.id.assortment_left_lv);
        rightRefreshListView = (PullToRefreshListView) view.findViewById(R.id.assortment_right_refreshlv);
        //右边标题
        linearLayoutTitle = (LinearLayout) view.findViewById(R.id.assortment_right_linearLayout);

        //程序调试，请删掉！
        TextView text = (TextView) view.findViewById(R.id.assortment_left_header);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProjectListActivity.class);
                startActivity(intent);
            }
        });

    }


    //绑定适配器
    private void bindAdapter() {
        //绑定左边适配器
        leftListView.setAdapter(leftAdapter);
        rightRefreshListView.setAdapter(rightAdapter);
        //设置默认选中项为第一个
        leftListView.setItemChecked(0, true);

    }


    //初始化适配器
    private void initAdapter() {
        //初始化左边ListView适配器
        leftAdapter = new ArrayAdapter(getActivity(), R.layout.assortment_left_listview, names);
        //初始化右边RefreshListView适配器
        // rightAdapter = new AssortmentRightAdapter(getActivity(),items);
        rightAdapter = new AssortmentRightAdapter(getActivity(), mItems);

    }


    //初始化数据
    private void initData() {
        mItems.clear();
        categoryIds.clear();
        names.clear();
        buttonMap.clear();
        buttonList.clear();
        sharedPreferences = getActivity().getSharedPreferences("location", Context.MODE_PRIVATE);
        city = sharedPreferences.getString("cityName", "北京");
        lot = sharedPreferences.getString("lot", "0");
        lat = sharedPreferences.getString("lat", "0");
        //初始化左边ListView数据
        leftListViewData();


    }


    //加载右边listview数据
    private void rightListViewData(int position, int startNum, int sizeNum) {
        String start = startNum + "";
        String size = sizeNum + "";
        //根据左边项目名称获取相应内容
        String category = categoryIds.get(position);
        OkHttpUtils.get()
                .url(AssortmentURL.ASSORTMENT_SUBITEM)
                .addParams("lot", lot).addParams("lat", lat).addParams("manualCity", city)
                .addParams("start", start).addParams("category", category).addParams("size", size)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        List<AssortmentRightLV.DataBean.ItemsBean> items = new ArrayList<>();
                        Gson gson = new Gson();
                        AssortmentRightLV assortmentRightLV = gson.fromJson(response, AssortmentRightLV.class);
                        items = assortmentRightLV.getData().getItems();
                        mItems.addAll(items);
                        if (mItems.isEmpty()) {
                            nullLinearLayout.setVisibility(View.VISIBLE);
                            rightRefreshListView.setVisibility(View.GONE);
                        } else {
                            rightRefreshListView.setVisibility(View.VISIBLE);
                            nullLinearLayout.setVisibility(View.GONE);
                        }
                        rightAdapter.notifyDataSetChanged();
                        rightRefreshListView.onRefreshComplete();
                    }
                });
    }

    //加载左边listview数据
    private void leftListViewData() {
        OkHttpUtils.get()
                .url(AssortmentURL.ASSORTMENT_CATEGORY)
                .addParams("manualCity", city).addParams("lot", lot).addParams("lat", lat)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        AssortmentLeftLV leftLV = gson.fromJson(response, AssortmentLeftLV.class);
                        List<AssortmentLeftLV.DataBean> data = leftLV.getData();

                        int dataSize = data.size();
                        for (int i = 0; i < dataSize; i++) {
                            String name = data.get(i).getName();
                            String categoryId = data.get(i).getId();
                            List<String> rbList = data.get(i).getTags();
                            categoryIds.add(categoryId);
                            names.add(name);
                            buttonMap.put(name, rbList);
                        }
                        leftListView.setItemChecked(0, true);
                        leftAdapter.notifyDataSetChanged();
                        //初始化右边标题内容默认为左边第一项
                        rightTitleData(0);
                        //设置右边listview默认数据为左边列表全部详情
                        rightListViewData(0, 0, 20);
                    }
                });
    }

}
