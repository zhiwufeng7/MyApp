package com.qianfeng.android.myapp.bean;

/**
 * Created by Administrator on 2016/7/14.
 */
public class City {
    private String name;
    private String lot;
    private String lat;

    public City(String name, String lot, String lat) {
        this.name = name;
        this.lot = lot;
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
