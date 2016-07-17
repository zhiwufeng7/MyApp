package com.qianfeng.android.myapp.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bigkoo.quicksidebar.QuickSideBarTipsView;
import com.bigkoo.quicksidebar.QuickSideBarView;
import com.bigkoo.quicksidebar.listener.OnQuickSideBarTouchListener;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.adapter.CutCityExpadnListAdapter;
import com.qianfeng.android.myapp.adapter.HotCityAdapter;
import com.qianfeng.android.myapp.bean.City;
import com.qianfeng.android.myapp.data.Url;
import com.qianfeng.android.myapp.widget.MyGridView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;

public class CutCityActivity extends AppCompatActivity {

    private LocationClient mLocationClient;
    private String cityName;
    private ExpandableListView elv;
    private ArrayList<City> hotCitys;
    private HashMap<String,  ArrayList<City>> cityMap;
    private ArrayList<String> a_z;
    private ArrayList<String> groupName;
    private View headView;
    private MyGridView gv;
    private TextView my_city;
    private ImageButton location_btn;
    private String lat;
    private String lot;
    private QuickSideBarView quickSideBarView;
    private QuickSideBarTipsView quickSideBarTipsView;
    private View headView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut_city);
        initView();
        initData();
        setLocationClient();
        setListener();
        mLocationClient.start();
        my_city.setText("正在定位中...");
    }


    private void setListener() {
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                City city = cityMap.get(groupName.get(groupPosition)).get(childPosition);
                Intent intent = new Intent();
                intent.putExtra("lot",city.getLot());
                intent.putExtra("lat",city.getLat());
                intent.putExtra("cityName",city.getName());
                CutCityActivity.this.setResult(1,intent);
                CutCityActivity.this.finish();
                return true;
            }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = hotCitys.get(position);
                Intent intent = new Intent();
                intent.putExtra("lot",city.getLot());
                intent.putExtra("lat",city.getLat());
                intent.putExtra("cityName",city.getName());
                CutCityActivity.this.setResult(1,intent);
                CutCityActivity.this.finish();
            }
        });

        my_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("lot",lot);
                intent.putExtra("lat",lat);
                intent.putExtra("cityName",cityName);
                CutCityActivity.this.setResult(1,intent);
                CutCityActivity.this.finish();
            }
        });

        quickSideBarView.setOnQuickSideBarTouchListener(new OnQuickSideBarTouchListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onLetterChanged(String letter, int position, float y) {
                quickSideBarTipsView.setText(letter, position, y);
                switch (position){
                    case 0:
                        elv.setSelection(0);
                        break;
                    case 1:
                        elv.setSelection(1);
                        break;
                    default:
                        elv.setSelectedGroup(position-2);
                        break;
                }

            }

            @Override
            public void onLetterTouching(boolean touching) {
                quickSideBarTipsView.setVisibility(touching? View.VISIBLE:View.INVISIBLE);
            }
        });
    }

    private void initData() {
        OkHttpUtils.get().url(Url.CITY_LIST)
                .build()
                .execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        a_z = new ArrayList<>();
                        groupName = new ArrayList<>();
                        for (int i = 0; i < 26; i++) {
                            char num = (char) (97 + i);
                            a_z.add(String.valueOf(num));
                        }
                        hotCitys = new ArrayList<>();
                        cityMap = new HashMap<>();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONArray hot = data.getJSONArray("hot");
                            for (int i = 0; i < hot.length(); i++) {
                                JSONObject object = hot.getJSONObject(i);
                                String name = object.getString("name");
                                String lot = object.getString("lot");
                                String lat = object.getString("lat");
                                City item = new City(name,lot,lat);
                                hotCitys.add(item);
                            }
                            for (int i = 0; i < a_z.size(); i++){
                                JSONArray cityArray = data.getJSONArray(a_z.get(i));
                                ArrayList<City> citys = new ArrayList<>();
                                for (int n = 0; n < cityArray.length(); n++) {
                                    JSONObject object = cityArray.getJSONObject(n);
                                    String name = object.getString("name");
                                    String lot = object.getString("lot");
                                    String lat = object.getString("lat");
                                    City item = new City(name,lot,lat);
                                    citys.add(item);
                                }
                                if (citys.size()>0){
                                    groupName.add(a_z.get(i));
                                    cityMap.put(a_z.get(i),citys);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setAdapter();
                    }
                });
    }

    private void setAdapter() {
        ArrayList<String> letters = new ArrayList<>();
        letters.add("*");
        letters.add("#");
        letters.addAll(groupName);
        quickSideBarView.setLetters(letters);
        elv.setAdapter(new CutCityExpadnListAdapter(this,groupName,cityMap));
        //设置ExpandableListView点击不收缩
        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        //设置ExpandableListView无箭头
        elv.setGroupIndicator(null);
        //默认所有的Group全部展开
        for (int i = 0; i < groupName.size(); i++) {
            elv.expandGroup(i);
        }
        gv.setAdapter(new HotCityAdapter(this,R.layout.hot_city_item,hotCitys));
    }

    private void setLocationClient() {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                String city = bdLocation.getCity();
                cityName = city.substring(0, city.length() - 1);
                my_city.setText(cityName);
                double latNum = bdLocation.getLatitude();
                double lotNum = bdLocation.getLongitude();
                lat=String.valueOf(latNum);
                lot=String.valueOf(lotNum);
                mLocationClient.stop();
            }
        });
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(false);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    private void initView() {
        elv = (ExpandableListView) findViewById(R.id.cut_city_elv);
        headView = LayoutInflater.from(this).inflate(R.layout.cut_city_head, null);
        headView2 = LayoutInflater.from(this).inflate(R.layout.cut_city_head2, null);
        elv.addHeaderView(headView);
        elv.addHeaderView(headView2);
        gv = (MyGridView) headView2.findViewById(R.id.cut_city_head_gv);
        my_city = (TextView)headView.findViewById(R.id.cut_city_head_my_city_tv);
        location_btn = (ImageButton)headView.findViewById(R.id.cut_city_head_location_btn);
        quickSideBarView = (QuickSideBarView) findViewById(R.id.quickSideBarView);
        quickSideBarTipsView = (QuickSideBarTipsView) findViewById(R.id.quickSideBarTipsView);
    }

    public void onBack(View view) {
        finish();
    }


    public void onRefresh(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.refresh_loction_btn));
        mLocationClient.start();
        my_city.setText("正在定位中...");
    }
}
