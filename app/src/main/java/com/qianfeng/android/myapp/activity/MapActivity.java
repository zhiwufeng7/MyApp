package com.qianfeng.android.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.qianfeng.android.myapp.R;
import com.qianfeng.android.myapp.adapter.MyViewPagerAdapter;
import com.qianfeng.android.myapp.fragment.CommonLocationFragment;
import com.qianfeng.android.myapp.fragment.NearbyPlotFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/13.
 */
public class MapActivity extends AppCompatActivity {
    private TextureMapView map;
    private BaiduMap mBaiduMap;
    private String lot;
    private String lat;
    private Marker marker;
    private TabLayout tab;
    private ViewPager vp;
    private ArrayList<Fragment> fragmentList;
    private Double latNum;
    private Double lotNum;
    private TextView cancel;
    private TextView city;
    private TextView search;
    private LocationClient mLocationClient;
    private String cityName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initView();
        initLocation();
        setAdapter();
        setlistener();
        setLocationClient();
        mLocationClient.start();
    }

    private void setLocationClient() {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                latNum = bdLocation.getLatitude();
                lotNum = bdLocation.getLongitude();
                String city = bdLocation.getCity();
                cityName = city.substring(0,city.length()-1);
                MapActivity.this.city.setText(cityName);
                LatLng point = new LatLng(latNum, lotNum);
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(point, 16);
                mBaiduMap.setMapStatus(msu);
                lat=String.valueOf(latNum);
                lot=String.valueOf(lotNum);
                ((NearbyPlotFragment)fragmentList.get(0)).initData(lat,lot);
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

    private void setAdapter() {
        List<String> stringList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        stringList.add("附近小区");
        stringList.add("常用地址");
        fragmentList.add(NearbyPlotFragment.newInstance());
        fragmentList.add(CommonLocationFragment.newInstance());
        vp.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),fragmentList,stringList));
        tab.setupWithViewPager(vp);
    }

    private void setlistener() {
        setMapListener();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapActivity.this.finish();
            }
        });
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this,CutCityActivity.class);
                startActivityForResult(intent,1);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this,PlotSearchActivity.class);
                intent.putExtra("cityName",cityName);
                startActivityForResult(intent,2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode==1){
                    Bundle extras = data.getExtras();
                    cityName=extras.getString("cityName");
                    lot=extras.getString("lot");
                    lat=extras.getString("lat");
                    MapActivity.this.city.setText(cityName);
                    lotNum = Double.valueOf(lot);
                    latNum = Double.valueOf(lat);
                    LatLng point = new LatLng(latNum, lotNum);
                    MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(point, 16);
                    mBaiduMap.setMapStatus(msu);
                    ((NearbyPlotFragment)fragmentList.get(0)).initData(lat,lot);
                }

                break;
            case 2:
                if (resultCode==1){
                    setResult(1);
                    finish();
                }
                break;

        }
    }

    private void setMapListener() {
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                LatLng target = mapStatus.target;
                latNum=target.latitude;
                lotNum=target.longitude;
                marker.setPosition(target);
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                LatLng target = mapStatus.target;
                lat=String.valueOf(target.latitude);
                lot=String.valueOf(target.longitude);
                ((NearbyPlotFragment)fragmentList.get(0)).initData(lat,lot);
            }
        });
    }

    private void initLocation() {
        LatLng point = new LatLng(0, 0);
        mBaiduMap = map.getMap();
        //取消缩放按钮
        map.showZoomControls(false);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.map_state_edit_icon);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        marker = (Marker)(mBaiduMap.addOverlay(option));
    }

    private void initView() {
        map = (TextureMapView) findViewById(R.id.map_view);
        tab = (TabLayout)findViewById(R.id.map_tab);
        vp = (ViewPager)findViewById(R.id.map_vp);
        cancel = (TextView)findViewById(R.id.map_tv_cancel);
        city = (TextView)findViewById(R.id.map_tv_city);
        search = (TextView)findViewById(R.id.map_tv_search);
    }

    public void setLocation(View view){
        mLocationClient.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        map.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        map.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }
}

