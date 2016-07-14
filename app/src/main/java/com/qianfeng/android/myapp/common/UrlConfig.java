package com.qianfeng.android.myapp.common;

/**
 * Created by my on 2016/7/14.
 */
public class UrlConfig {
    //切换城市（各城市id，经纬度）
    public static final String CITY_URL = "http://api.daoway.cn/daoway/rest/community/city_list";


    //选择我的小区界面（需要传入城市经度和纬度）
    public static final String DETAILED_ADDRESS_BASE =
            "http://api.daoway.cn/daoway/rest/community/autoPositionContainsBaidu?v2=v2";

    //首页--banner(城市名称及community_id传入)
    public static String getBannerBase(String city, String id) {
        return "http://api.daoway.cn/daoway/rest/config/banners?platform=android&city="
                + city + "&community_id=" + id;
    }


    //首页--今日推荐（传入城市名称）
    public static String getRecommendUrl(String city) {
        return "http://api.daoway.cn/daoway/rest/indexEvent/all?market=false&version=v2&serviceMinLimit=4&marketMinLimit=2&city="
                + city;
    }

    //首页--广告下面的十项服务（传入城市名称）
    public static final String HOME_SEVICE =
            "http://api.daoway.cn/daoway/rest/category/for_filter?manual";

    public static String getHomeSevice(String city) {
        return "http://api.daoway.cn/daoway/rest/category/for_filter?weidian=false&recommendOnly=true&includeChaoshi=true&hasChaoshi=false&manualCity="
                + city;
    }

    //首页--expandListView（传入城市名称）
    public static final String HOME_EV =
            "http://api.daoway.cn/daoway/rest/service_items/recommend_top?start=0&size=10";

    //首页--推荐服务商（传入城市 和经纬度）
    public static String getRecommendServiceUrl(String city, String lot, String lat,int index) {

        return "http://api.daoway.cn/daoway/rest/services?start="+(index-10)+"&size="+index+"&manualCity="
                + city + "&lot=" + lot + "&lat=" + lat + "&includeNotInScope=true";
    }
}
