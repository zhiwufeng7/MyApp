package com.qianfeng.android.myapp.bean;

import java.util.List;

/**
 * Created by my on 2016/7/13.
 */
public class HomeRecommendInfo {

    /**
     * status : ok
     * data : [{"id":"42e2d3d68e5341529f8b95e21306793c","url":"app://detail/openService/05219ff82a41477e8a7c4539bad74a17","h5Url":"","img":"http://img.daoway.cn/img/banners/2016/07/01/a1579348-b361-4e35-bbaf-7c1dc5a81996.png","subject":"急速上门修手机","title":"逸修哥","city":"武汉","priority":0,"status":1,"createtime":1467355537000,"isMarket":0,"type":1},{"id":"fb5101e1005146c484b240577b275229","url":"app://detail/openService/224dd9bfb63c4d75b77a6dc6f174fbf9","h5Url":"","img":"http://img.daoway.cn/img/banners/2016/07/12/48b5d9b9-d977-4812-8b86-0f6aca35f13d.png","subject":"贴心的家政服务","title":"美宅宝","city":"武汉,成都,重庆","priority":0,"status":1,"createtime":1468292651000,"isMarket":0,"type":1},{"id":"b5051650e45a45399dc09f4e46b8d669","url":"app://detail/openService/cb255185c2834d6ea419ca0fa7e19d3a","h5Url":"","img":"http://img.daoway.cn/img/banners/2016/05/31/b1e1f56a-d1fe-4a5d-a9b4-06d8fc9b6179.png","subject":"上门修手机","title":"极客修","city":"北京,上海,广州,深圳,杭州,重庆,成都,天津,南京,武汉,长沙,济南,青岛,沈阳,西安,郑州,石家庄,合肥,太原,昆明,贵阳,福州,厦门,南宁,苏州","priority":11,"status":1,"createtime":1464678121000,"isMarket":0,"type":1},{"id":"07dd5dfc29ed4740867450eefe0231ee","url":"app://detail/openService/2d546e36b4eb401294c55c39924d64a8","h5Url":"","img":"http://img.daoway.cn/img/banners/2016/05/31/f42e0925-c364-4a7e-a393-d1c4d3a0266b.png","subject":"上门化妆美睫","title":"俏猫","city":"广州,深圳,中山,汕头,佛山,珠海,湛江,东莞,韶关,茂名,泉州,福州,厦门,长沙,北京,武汉,无锡,苏州,上海,贵阳,重庆,成都,昆明,南宁,杭州,南京,合肥,天津,大连,沈阳,长春,哈尔滨,西安,郑州,石家庄,济南,青岛,江门,桂林,兰州,柳州,温州,银川,太原,大同,秦皇岛,绵阳,西宁,常州,徐州,海口,鄂尔多斯,南昌,洛阳,宁波,丽江,遵义,衡阳,常德,上饶,金华,邯郸,嘉兴,湖州,广安,广元,淮南,娄底,廊坊,荆州,龙岩,惠州","priority":20,"status":1,"createtime":1464681135000,"isMarket":0,"type":1}]
     */

    private String status;
    /**
     * id : 42e2d3d68e5341529f8b95e21306793c
     * url : app://detail/openService/05219ff82a41477e8a7c4539bad74a17
     * h5Url :
     * img : http://img.daoway.cn/img/banners/2016/07/01/a1579348-b361-4e35-bbaf-7c1dc5a81996.png
     * subject : 急速上门修手机
     * title : 逸修哥
     * city : 武汉
     * priority : 0
     * status : 1
     * createtime : 1467355537000
     * isMarket : 0
     * type : 1
     */

    private List<DataBean> data;

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
        private String url;
        private String h5Url;
        private String img;
        private String subject;
        private String title;
        private String city;
        private int priority;
        private int status;
        private long createtime;
        private int isMarket;
        private int type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getH5Url() {
            return h5Url;
        }

        public void setH5Url(String h5Url) {
            this.h5Url = h5Url;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public int getIsMarket() {
            return isMarket;
        }

        public void setIsMarket(int isMarket) {
            this.isMarket = isMarket;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
