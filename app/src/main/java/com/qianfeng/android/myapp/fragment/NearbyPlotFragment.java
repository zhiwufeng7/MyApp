package com.qianfeng.android.myapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.gson.Gson;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.adapter.PlotListAdapter;
import com.qianfeng.android.myapp.bean.Plots;
import com.qianfeng.android.myapp.data.Url;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/7/13.
 */
public class NearbyPlotFragment extends Fragment {

    private String lat;
    private String lot;
    private View view;
    private ListView lv;
    private String cityName;
    private List<Plots.DataBean.CommunitiesBean> communitie = new ArrayList<>();
    private PlotListAdapter mAdapter;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    public static NearbyPlotFragment newInstance() {
        NearbyPlotFragment fragment = new NearbyPlotFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nearby_plot,null);
        sharedPreferences = getActivity().getSharedPreferences("location", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        initView();
        setListener();
        return view;
    }

    private void setListener() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Plots.DataBean.CommunitiesBean communitiesBean = communitie.get(position);
                editor.putString("plot", communitiesBean.getName());
                editor.putString("cityName", cityName);
                editor.putString("lot", String.valueOf(communitiesBean.getLot()));
                editor.putString("lat",String.valueOf(communitiesBean.getLat()));
                editor.putString("id", String.valueOf(communitiesBean.getId()));
                editor.commit();
                FragmentActivity activity = NearbyPlotFragment.this.getActivity();
                activity.setResult(1);
                activity.finish();

            }
        });
    }

    private void initView() {
       lv = (ListView) view.findViewById(R.id.nearby_plot_lv);
    }
    public void initData(String lat, String lot) {
        this.lat = lat;
        this.lot = lot;
        OkHttpUtils.get().url(Url.NEARBY_PLOT)
                .addParams("lat",lat).addParams("lot",lot).addParams("v2","v2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.length()>100){
                            Gson gson = new Gson();
                            Plots plots = gson.fromJson(response,Plots.class);
                            cityName = plots.getData().getParent().getCity();
                            communitie = plots.getData().getCommunities();
                            int cityID = plots.getData().getParent().getId();
                            List<Plots.DataBean.BaiduCommunitiesBean> baiduCommunities =
                                    plots.getData().getBaiduCommunities();
                            //将baiduCommunitiesBean转换成communitiesBean,并加入数据列表
                            for (int i = 0; i<baiduCommunities.size();i++){
                                Plots.DataBean.BaiduCommunitiesBean baiduCommunitiesBean = baiduCommunities.get(i);
                                Plots.DataBean.CommunitiesBean communitiesBean = new Plots.DataBean.CommunitiesBean();
                                communitiesBean.setAddr(baiduCommunitiesBean.getAddr());
                                communitiesBean.setName(baiduCommunitiesBean.getName());
                                communitiesBean.setLot(baiduCommunitiesBean.getX());
                                communitiesBean.setLat(baiduCommunitiesBean.getY());
                                communitiesBean.setId(cityID);
                                communitie.add(communitiesBean) ;
                            }
                        }else {
                            communitie.clear();
                        }
                        setAdapter();
                    }
                });
    }

    private void setAdapter() {
        mAdapter = new PlotListAdapter(getContext(),R.layout.nearby_plot_lv_item,communitie);
        lv.setAdapter(mAdapter);
    }

}
