package com.qianfeng.android.myapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/15.
 */
public class HotSearch {

    /**
     * status : ok
     * data : ["按摩","足疗","推拿","SPA","家庭保洁","小时工","保姆","护工","手机维修","家电维修","电脑维修","家电清洗","洗衣","洗鞋","开荒","擦玻璃","搬家","上门养车","洗车","医护陪诊","家修","开锁","安装","管道疏通","化妆","美容","美甲","美睫","宠物","家教","鲜花","蛋糕"]
     */

    private String status;
    private List<String> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
