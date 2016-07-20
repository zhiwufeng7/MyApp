package com.qianfeng.android.myapp.bean;

/**
 * Created by admin on 2016/7/19.
 */
public class ServiceDetailsHeaderInfo {

    /**
     * status : ok
     * data : {"id":2373070,"serviceId":"2d546e36b4eb401294c55c39924d64a8","name":"韩式自然美睫（浓密款）","price":368,"priceUnit":"元/次","isMain":0,"picUrl":"http://img.daoway.cn/img/2016/02/02/9c752474-90bc-4fac-b2cf-270f2cea12a6.jpg","originalPicUrl":"http://img.daoway.cn/img/2016/02/02/9c752474-90bc-4fac-b2cf-270f2cea12a6.jpg","description":"时长：120分钟\n浓密度：浓密\n睫毛数量：120根\n美睫材料：蚕丝蛋白睫毛\n美睫胶水：医用级防敏睫毛胶水\n嫁接方法：五点定位法\n耗时：120min\n保持：15-30天\n效果：韩式清新自然风格，均匀美化整体睫毛、呈现出自然美睫弧度效果，浓密型适合自身睫毛量较少者。\u200b\n备注：不包含下睫毛、卸睫毛，七天内自然脱落免费修补一次。","seq":14,"minBuyNum":1,"inService":1,"jiesuanPrice":null,"catId":"3da4a31f059c4e0c9c5745e9ebc96a44","catName":"上门美睫","eventId":"","originalPrice":368,"stockNum":null,"isDiscount":null,"limitNum":0,"libid":null,"rootCategoryId":null,"rootCategoryName":null,"barCode":"","tags":null,"salesNum":1,"positiveCommentRate":"97.0%"}
     */

    private String status;
    /**
     * id : 2373070
     * serviceId : 2d546e36b4eb401294c55c39924d64a8
     * name : 韩式自然美睫（浓密款）
     * price : 368.0
     * priceUnit : 元/次
     * isMain : 0
     * picUrl : http://img.daoway.cn/img/2016/02/02/9c752474-90bc-4fac-b2cf-270f2cea12a6.jpg
     * originalPicUrl : http://img.daoway.cn/img/2016/02/02/9c752474-90bc-4fac-b2cf-270f2cea12a6.jpg
     * description : 时长：120分钟
     浓密度：浓密
     睫毛数量：120根
     美睫材料：蚕丝蛋白睫毛
     美睫胶水：医用级防敏睫毛胶水
     嫁接方法：五点定位法
     耗时：120min
     保持：15-30天
     效果：韩式清新自然风格，均匀美化整体睫毛、呈现出自然美睫弧度效果，浓密型适合自身睫毛量较少者。​
     备注：不包含下睫毛、卸睫毛，七天内自然脱落免费修补一次。
     * seq : 14
     * minBuyNum : 1
     * inService : 1
     * jiesuanPrice : null
     * catId : 3da4a31f059c4e0c9c5745e9ebc96a44
     * catName : 上门美睫
     * eventId :
     * originalPrice : 368.0
     * stockNum : null
     * isDiscount : null
     * limitNum : 0
     * libid : null
     * rootCategoryId : null
     * rootCategoryName : null
     * barCode :
     * tags : null
     * salesNum : 1
     * positiveCommentRate : 97.0%
     */

    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private String serviceId;
        private String name;
        private double price;
        private String priceUnit;
        private int isMain;
        private String picUrl;
        private String originalPicUrl;
        private String description;
        private int seq;
        private int minBuyNum;
        private int inService;
        private Object jiesuanPrice;
        private String catId;
        private String catName;
        private String eventId;
        private double originalPrice;
        private Object stockNum;
        private Object isDiscount;
        private int limitNum;
        private Object libid;
        private Object rootCategoryId;
        private Object rootCategoryName;
        private String barCode;
        private Object tags;
        private int salesNum;
        private String positiveCommentRate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getPriceUnit() {
            return priceUnit;
        }

        public void setPriceUnit(String priceUnit) {
            this.priceUnit = priceUnit;
        }

        public int getIsMain() {
            return isMain;
        }

        public void setIsMain(int isMain) {
            this.isMain = isMain;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getOriginalPicUrl() {
            return originalPicUrl;
        }

        public void setOriginalPicUrl(String originalPicUrl) {
            this.originalPicUrl = originalPicUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public int getMinBuyNum() {
            return minBuyNum;
        }

        public void setMinBuyNum(int minBuyNum) {
            this.minBuyNum = minBuyNum;
        }

        public int getInService() {
            return inService;
        }

        public void setInService(int inService) {
            this.inService = inService;
        }

        public Object getJiesuanPrice() {
            return jiesuanPrice;
        }

        public void setJiesuanPrice(Object jiesuanPrice) {
            this.jiesuanPrice = jiesuanPrice;
        }

        public String getCatId() {
            return catId;
        }

        public void setCatId(String catId) {
            this.catId = catId;
        }

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public double getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(double originalPrice) {
            this.originalPrice = originalPrice;
        }

        public Object getStockNum() {
            return stockNum;
        }

        public void setStockNum(Object stockNum) {
            this.stockNum = stockNum;
        }

        public Object getIsDiscount() {
            return isDiscount;
        }

        public void setIsDiscount(Object isDiscount) {
            this.isDiscount = isDiscount;
        }

        public int getLimitNum() {
            return limitNum;
        }

        public void setLimitNum(int limitNum) {
            this.limitNum = limitNum;
        }

        public Object getLibid() {
            return libid;
        }

        public void setLibid(Object libid) {
            this.libid = libid;
        }

        public Object getRootCategoryId() {
            return rootCategoryId;
        }

        public void setRootCategoryId(Object rootCategoryId) {
            this.rootCategoryId = rootCategoryId;
        }

        public Object getRootCategoryName() {
            return rootCategoryName;
        }

        public void setRootCategoryName(Object rootCategoryName) {
            this.rootCategoryName = rootCategoryName;
        }

        public String getBarCode() {
            return barCode;
        }

        public void setBarCode(String barCode) {
            this.barCode = barCode;
        }

        public Object getTags() {
            return tags;
        }

        public void setTags(Object tags) {
            this.tags = tags;
        }

        public int getSalesNum() {
            return salesNum;
        }

        public void setSalesNum(int salesNum) {
            this.salesNum = salesNum;
        }

        public String getPositiveCommentRate() {
            return positiveCommentRate;
        }

        public void setPositiveCommentRate(String positiveCommentRate) {
            this.positiveCommentRate = positiveCommentRate;
        }
    }
}
