package com.qianfeng.android.myapp.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qianfeng.android.myapp.R;
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
    private List<AssortmentRightLV.DataBean.ItemsBean> items = new ArrayList<>();
    private AssortmentRightAdapter rightAdapter;
    private Map<String, List<String>> buttonMap = new HashMap<>();
    private LinearLayout linearLayoutTitle;
    private List<String> buttonList = new ArrayList<>();
    private TextView addressTV;
    private RelativeLayout nullLinearLayout;
    private int startNum = 0;


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
        if (view == null) {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_assortment, null);
        }
        //初始化左右两边ListView及中间RadioGroup数据源
        initData();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化控件
        initView();

        //初始化适配器
        initAdapter();

        //绑定适配器
        bindAdapter();

        //设置监听
        initListener();

    }

    //设置监听
    private void initListener() {
        //设置左边的ListView监听事件，点击更改右边标题
        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RightTitleData(position);
                RightListViewData(position,startNum);
            }
        });
        rightRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        rightRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                startNum=0;
                RightListViewData(0,startNum);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                startNum+=10;
                RightListViewData(0,startNum);
            }
        });

    }

    //加载右边标题
    private void RightTitleData(final int position) {
        linearLayoutTitle.removeAllViews();
        buttonList = buttonMap.get(names.get(position));
        //如果已添加“全部”选项，则不重复添加
        if (!buttonList.get(0).equals("全部")) {
            buttonList.add(0, "全部");
        }
        int size = buttonList.size();
        final Button[] buttons = new Button[size];
        for (int i = 0; i < size; i++) {
            Button button = new Button(getActivity());
            button.setTag(i);
            buttons[i] = button;
            button.setTextSize(12);
            button.setBackgroundResource(R.drawable.assortment_right_title_text_style);
            button.setHeight(30);
            button.setText(buttonList.get(i));
            ColorStateList csl=getActivity().getResources().getColorStateList(R.color.assortment_right_title_text_color);
            button.setTextColor(csl);
            linearLayoutTitle.addView(button);
        }
        buttons[0].setEnabled(false);
        final int childCount = linearLayoutTitle.getChildCount();
        for (int i = 0; i < childCount; i++) {
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setEnabled(false);
                    int tag = (int) v.getTag();
                    if (tag == 0) {
                        RightListViewData(position,0);
                    } else {
                        RightListContentFL(buttonList.get(tag), position);
                    }
                    for (int i = 0; i < childCount; i++) {
                        if (tag != i) {
                            buttons[i].setEnabled(true);
                        }
                    }
                }
            });
        }

    }

    //加载分类子项数据

    private void RightListContentFL(String title, int position) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("location", Context.MODE_PRIVATE);
        String city = sharedPreferences.getString("cityName", "北京");
        String lot = sharedPreferences.getString("lot", "0");
        String lat = sharedPreferences.getString("lat", "0");
        String start = "0";
        String size = "20";
        String category = categoryIds.get(position);
        String titleTag = title;
        String tag = titleTag;
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

                        Gson gson = new Gson();
                        AssortmentRightLV assortmentRightLV = gson.fromJson(response, AssortmentRightLV.class);
                        items = assortmentRightLV.getData().getItems();
                        //rightAdapter.notifyDataSetChanged();

                        if (items.isEmpty()) {
                            nullLinearLayout.setVisibility(View.VISIBLE);
                            rightRefreshListView.setVisibility(View.GONE);
                        } else {
                            rightRefreshListView.setVisibility(View.VISIBLE);
                            nullLinearLayout.setVisibility(View.GONE);
                            rightAdapter = new AssortmentRightAdapter(getActivity(), items);
                            rightRefreshListView.setAdapter(rightAdapter);
                        }


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

    }

    //绑定适配器
    private void bindAdapter() {
        //绑定左边适配器
        leftListView.setAdapter(leftAdapter);
        //设置默认选中项为第一个
        leftListView.setItemChecked(0, true);

    }

    //初始化适配器
    private void initAdapter() {
        //初始化左边ListView适配器
        leftAdapter = new ArrayAdapter(getActivity(), R.layout.assortment_left_listview, names);
        //初始化右边RefreshListView适配器
        // rightAdapter = new AssortmentRightAdapter(getActivity(),items);

    }

    //初始化数据
    private void initData() {
        //初始化左边ListView数据
        LeftListViewData();


    }

    //加载右边listview数据
    private void RightListViewData(int position,int startNum) {
        //设置默认值
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("location", Context.MODE_PRIVATE);
        String city = sharedPreferences.getString("cityName", "北京");
        String lot = sharedPreferences.getString("lot", "0");
        String lat = sharedPreferences.getString("lat", "0");
        //从默认显示第一页
        String start = startNum+"";
        String size = "0";
        //根据左边项目名称获取相应内容
        String category = categoryIds.get(position);
        OkHttpUtils.get()
                //解析数据为空
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
                        Gson gson = new Gson();
                        AssortmentRightLV assortmentRightLV = gson.fromJson(response, AssortmentRightLV.class);
                        items = assortmentRightLV.getData().getItems();
                        if (items.isEmpty()) {
                            nullLinearLayout.setVisibility(View.VISIBLE);
                            rightRefreshListView.setVisibility(View.GONE);
                        } else {
                            rightRefreshListView.setVisibility(View.VISIBLE);
                            nullLinearLayout.setVisibility(View.GONE);
                            rightAdapter = new AssortmentRightAdapter(getActivity(), items);
                            rightRefreshListView.setAdapter(rightAdapter);
                        }

                    }
                });
    }

    //加载左边listview数据
    private void LeftListViewData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("location", Context.MODE_PRIVATE);
        String city = URLEncoder.encode(sharedPreferences.getString("cityName", "北京"));
        String lot = sharedPreferences.getString("lot", "0");
        String lat = sharedPreferences.getString("lat", "0");
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

                        leftAdapter.notifyDataSetChanged();
                        //初始化右边标题内容默认为左边第一项
                        RightTitleData(0);
                        //设置右边listview默认数据为左边列表全部详情
                        RightListViewData(0,0);
                    }
                });
    }

}
