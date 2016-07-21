package com.qianfeng.android.myapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.activity.MerchantActivity;
import com.qianfeng.android.myapp.activity.ProjectListActivity;
import com.qianfeng.android.myapp.activity.RecommendActivity;
import com.qianfeng.android.myapp.adapter.HomeFootGridViewAdapter;
import com.qianfeng.android.myapp.adapter.HomeHeaderServiceAdapter;
import com.qianfeng.android.myapp.adapter.HomePullToRefreshExpandListViewAdapter;
import com.qianfeng.android.myapp.adapter.HomeRecommendAdapter;
import com.qianfeng.android.myapp.bean.BannerInfo;
import com.qianfeng.android.myapp.bean.FootInfo;
import com.qianfeng.android.myapp.bean.HomePageEVInfo;
import com.qianfeng.android.myapp.bean.HomeRecommendInfo;
import com.qianfeng.android.myapp.bean.HomeServiceInfo;
import com.qianfeng.android.myapp.common.UrlConfig;
import com.qianfeng.android.myapp.widget.MyGridView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


public class HomePageFragment extends Fragment {
    private HomePullToRefreshExpandListViewAdapter expandAdapter;

    private PullToRefreshExpandableListView refreshExpandableListView;
    private ExpandableListView expandListView;
    private ConvenientBanner banner;
    private GridView headerGridView;
    private MyGridView myGridView;
    private Context mContext;
    private BannerInfo bannerInfo;
    private List<BannerInfo.DataBean> bannerList = new ArrayList<>();
    private Gson gson;
    private HomeHeaderServiceAdapter serviceAdapter;
    private HomeRecommendAdapter recommendAdapter;
    private MyGridView footGrid;
    private HomeFootGridViewAdapter footGridViewAdapter;
    private SharedPreferences sharedPreferences;
    private String city;
    private String lot;
    private String lat;
    public int index = 0;
    private String id;
    private Button service;
    private HomeServiceInfo homeServiceInfo;
    private HomeRecommendInfo homeRecommendInfo;
    private List<FootInfo.DataBean> footList;


    public HomePageFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomePageFragment newInstance() {
        HomePageFragment fragment = new HomePageFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getActivity();
        index = 0;


        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        View headerView = inflater.inflate(R.layout.home_header_view, null);
        View footView = inflater.inflate(R.layout.home_foot_view, null);

        initView(view);
        initHeardView(headerView);
        initFootView(footView);


        //添加头部视图
        expandListView.addHeaderView(headerView);
        //添加底部视图
        expandListView.addFooterView(footView);

        initData();

        initAdapter();

        initListener();

        return view;
    }

    private void initFootView(View footView) {

        footGrid = (MyGridView) footView.findViewById(R.id.home_page_foot_my_grid);

    }

    private void initAdapter() {

        expandAdapter = new HomePullToRefreshExpandListViewAdapter(mContext);
        expandListView.setAdapter(expandAdapter);

        serviceAdapter = new HomeHeaderServiceAdapter(mContext);
        myGridView.setAdapter(serviceAdapter);

        recommendAdapter = new HomeRecommendAdapter(mContext);
        headerGridView.setAdapter(recommendAdapter);

        footGridViewAdapter = new HomeFootGridViewAdapter(mContext);
        footGrid.setAdapter(footGridViewAdapter);

    }

    private void initHeardView(View heardView) {
        service = (Button) heardView.findViewById(R.id.home_page_header_recommend_btn);
        banner = (ConvenientBanner) heardView.findViewById(R.id.home_page_header_view_cb);
        myGridView = (MyGridView) heardView.findViewById(R.id.home_page_header_my_grid);
        headerGridView = (GridView) heardView.findViewById(R.id.home_page_header_recommend_grid);

        //设置顶部banner
        banner.setPages(new CBViewHolderCreator<HeaderBannerViewHolder>() {
            @Override
            public HeaderBannerViewHolder createHolder() {
                return new HeaderBannerViewHolder();
            }
        }, bannerList)
                .setPageIndicator(new int[]{R.drawable.page, R.drawable.page_now})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);

    }


    class HeaderBannerViewHolder implements Holder<BannerInfo.DataBean> {
        ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, BannerInfo.DataBean data) {

            Glide.with(mContext).load(data.getImgUrl()).into(imageView);
        }
    }


    private void initView(View view) {
        refreshExpandableListView = (PullToRefreshExpandableListView) view.findViewById(R.id.home_page_fragment_ev);
        expandListView = refreshExpandableListView.getRefreshableView();
        refreshExpandableListView.setMode(PullToRefreshBase.Mode.BOTH);

    }

    private void initListener(){

        //今日推荐跳转
        headerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String url= homeRecommendInfo.getData().get(position).getUrl();
                int length=url.length();
                url=url.substring(length-32);
                Intent intent = new Intent(mContext, MerchantActivity.class);
                intent.putExtra("id", url);
                startActivity(intent);
            }
        });


        //推荐服务商
        footGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url=footList.get(position).getServiceId();
                Intent intent = new Intent(mContext, MerchantActivity.class);
                intent.putExtra("id", url);
                startActivity(intent);
            }
        });



        //banner跳转

        banner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String id = bannerList.get(position).getServiceId();
                Intent intent = new Intent(mContext, MerchantActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        expandListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });


        refreshExpandableListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                index += 10;
                loadFootData();
            }
        });

        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecommendActivity.class);
                intent.putExtra("city", city);
                intent.putExtra("lot", lot);
                intent.putExtra("lat", lat);
                startActivity(intent);
            }
        });

        //头部10个服务分类的监听
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                /**
                 * 在这里写需要跳转的activity
                 */
                intent.setClass(mContext, ProjectListActivity.class);
                intent.putStringArrayListExtra("tags", (ArrayList<String>) homeServiceInfo.getData().get(position).getTags());
                intent.putExtra("id", homeServiceInfo.getData().get(position).getId());
                startActivity(intent);

            }
        });
    }

    public void loadFootData() {
        //底部footView
        if (index == 0) {
            footGridViewAdapter = new HomeFootGridViewAdapter(mContext);
            footGrid.setAdapter(footGridViewAdapter);
            return;
        }
        OkHttpUtils.get()
                .url(UrlConfig.getRecommendServiceUrl(city, lot, lat, index))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {

                     FootInfo footListInfo = gson.fromJson(response, FootInfo.class);
                    footList = footListInfo.getData();

                        if (footList != null) {
                            footGridViewAdapter.setData(footList);
                            footGridViewAdapter.notifyDataSetChanged();
                        }

                        refreshExpandableListView.onRefreshComplete();
                    }
                });
    }

    private void getAdress() {
        //得到位置信息
        sharedPreferences = getActivity().getSharedPreferences("location", Context.MODE_PRIVATE);
        city = sharedPreferences.getString("cityName", "北京");
        lot = sharedPreferences.getString("lot", "0");
        lat = sharedPreferences.getString("lat", "0");
        id = sharedPreferences.getString("id", "0");
    }

    public void initData() {
        if (gson == null) {
            gson = new Gson();
        }
        getAdress();

        //今日推荐数据
        OkHttpUtils.get()
                .url(UrlConfig.getRecommendUrl(city))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        homeRecommendInfo = gson.fromJson(response, HomeRecommendInfo.class);
                        recommendAdapter.setData(homeRecommendInfo);
                        recommendAdapter.notifyDataSetChanged();

                    }
                });


        //头部视图10个分类数据
        OkHttpUtils.get()
                .url(UrlConfig.getHomeSevice(city))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        homeServiceInfo = gson.fromJson(response, HomeServiceInfo.class);
                        serviceAdapter.setData(homeServiceInfo);
                        serviceAdapter.notifyDataSetChanged();
                        refreshExpandableListView.onRefreshComplete();
                    }
                });


        //顶部banner数据
        OkHttpUtils.get()
                .url(UrlConfig.getBannerBase(city, id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        bannerInfo = gson.fromJson(response, BannerInfo.class);
                        bannerList.clear();
                        bannerList.addAll(bannerInfo.getData());
                        banner.getViewPager().getAdapter().notifyDataSetChanged();
                        banner.setPageIndicator(new int[]{R.drawable.page, R.drawable.page_now});
                    }
                });


        //中部的expandableListView数据
        OkHttpUtils.get()
                .url(UrlConfig.getHomeEV(city))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                    @Override
                    public void onResponse(String response, int id) {

                        HomePageEVInfo data = gson.fromJson(response, HomePageEVInfo.class);
                        expandAdapter.setData(data);
                        expandListView.setAdapter(expandAdapter);
                        //默认所有的Group全部展开
                        for (int i = 0; i < data.getData().size(); i++) {
                            expandListView.expandGroup(i);
                        }
                    }
                });


    }

    @Override
    public void onStart() {
        super.onStart();
        //开始自动滚动
        banner.startTurning(2000);
    }

    @Override
    public void onStop() {
        super.onStop();
        //停止滚动
        banner.stopTurning();
    }


}
