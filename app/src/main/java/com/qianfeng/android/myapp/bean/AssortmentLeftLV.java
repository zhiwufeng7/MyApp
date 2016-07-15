package com.qianfeng.android.myapp.bean;

import java.util.List;

/**
 * Created by admin on 2016/7/13.
 */
public class AssortmentLeftLV {

    /**
     * status : ok
     * data : [{"id":"20","name":"家政","count":0,"iconUrl":"http://img.daoway.cn/img/icon/jiazheng3.png","iconUrl2":"http://img.daoway.cn/img/icon/new/jiazhen.png","order":20,"tags":["小时工","深度保洁","擦玻璃","家电清洗","家居养护","上门做饭","杀虫/除甲醛","保姆/护工","月嫂/育儿嫂"]},{"id":"22","name":"上门按摩","count":0,"iconUrl":"http://img.daoway.cn/img/icon/anmo.png","iconUrl2":"http://img.daoway.cn/img/icon/new/anmo.png","order":21,"tags":["中医推拿","油压/SPA","足疗套餐","养生护理","小儿推拿","开奶催乳"]},{"id":"36","name":"维修","count":0,"iconUrl":"http://img.daoway.cn/img/icon/weixiu3.png","iconUrl2":"http://img.daoway.cn/img/icon/new/weixiu.png","order":22,"tags":["手机维修","家电维修","电脑维修","家庭维修","管道疏通","开锁换锁"]},{"id":"21","name":"家电清洗","count":0,"iconUrl":"http://img.daoway.cn/img/icon/jiadian.png","iconUrl2":"http://img.daoway.cn/img/icon/new/jiadian.png","order":23,"tags":["空调清洗","油烟机清洗","燃气灶清洗","冰箱清洗","洗衣机清洗","热水器清洗","微波炉清洗","饮水机清洗"]},{"id":"110","name":"美容美体","count":0,"iconUrl":"http://img.daoway.cn/img/icon/liren.png","iconUrl2":"http://img.daoway.cn/img/icon/new/meirong.png","order":53,"tags":["美体塑形","养生护理","美容","化妆","美甲","美眉/美睫"]},{"id":"26","name":"洗衣洗鞋","count":0,"iconUrl":"http://img.daoway.cn/img/icon/yixi3.png","iconUrl2":"http://img.daoway.cn/img/icon/new/xiyi.png","order":55,"tags":["洗衣","洗鞋","家居家纺","洗包","奢品养护"]},{"id":"23","name":"搬家","count":0,"iconUrl":"http://img.daoway.cn/img/icon/paotui3.png","iconUrl2":"http://img.daoway.cn/img/icon/new/peisong.png","order":75,"tags":["搬家套餐","家具拆装","大件搬运","仓储收纳"]},{"id":"60","name":"上门养车","count":0,"iconUrl":"http://img.daoway.cn/img/icon/yangche.png","iconUrl2":"http://img.daoway.cn/img/icon/yangche.png","order":80,"tags":["保养套餐","维修检测","清洁养护","上门洗车","保养工时"]},{"id":"25","name":"外卖","count":0,"iconUrl":"http://img.daoway.cn/img/icon/waimai3.png","iconUrl2":"http://img.daoway.cn/img/icon/new/waimai.png","order":81,"tags":["热送美食","火锅上门","半成净菜","送水","水果生鲜","蛋糕"]},{"id":"34","name":"更多","count":0,"iconUrl":"http://img.daoway.cn/img/icon/qita4.png","iconUrl2":"http://img.daoway.cn/img/icon/new/qita4.png","order":97,"tags":["鲜花速递","上门美宠","家教培训","医护陪诊","装修"]}]
     */

    private String status;
    /**
     * id : 20
     * name : 家政
     * count : 0
     * iconUrl : http://img.daoway.cn/img/icon/jiazheng3.png
     * iconUrl2 : http://img.daoway.cn/img/icon/new/jiazhen.png
     * order : 20
     * tags : ["小时工","深度保洁","擦玻璃","家电清洗","家居养护","上门做饭","杀虫/除甲醛","保姆/护工","月嫂/育儿嫂"]
     */

    private List<DataBean> data;

    @Override
    public String toString() {
        return "AssortmentLeftLV{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String name;
        private String count;
        private String iconUrl;
        private String iconUrl2;
        private String order;
        private List<String> tags;

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", count='" + count + '\'' +
                    ", iconUrl='" + iconUrl + '\'' +
                    ", iconUrl2='" + iconUrl2 + '\'' +
                    ", order='" + order + '\'' +
                    ", tags=" + tags +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getIconUrl2() {
            return iconUrl2;
        }

        public void setIconUrl2(String iconUrl2) {
            this.iconUrl2 = iconUrl2;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }
}
