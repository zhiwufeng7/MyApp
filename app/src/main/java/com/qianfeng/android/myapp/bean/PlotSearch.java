package com.qianfeng.android.myapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/14.
 */
public class PlotSearch {

    /**
     * id : 241875
     * city : 武汉
     * name : 逸境HOUSE
     * addr : 江夏江夏大道36号（邬树村创业农庄）
     * lot : 114.3644790649414
     * lat : 30.41534996032715
     * serviceId : null
     * area :
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private String city;
        private String name;
        private String addr;
        private double lot;
        private double lat;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public double getLot() {
            return lot;
        }

        public void setLot(double lot) {
            this.lot = lot;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }
}
