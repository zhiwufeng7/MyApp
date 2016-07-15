package com.qianfeng.android.myapp.bean;

import java.util.List;

/**
 * Created by my on 2016/7/13.
 */
public class BannerInfo {

    /**
     * status : ok
     * data : [{"imgUrl":"http://img.daoway.cn/img/banners/2016/07/12/8b28b769-7063-4238-97d6-4d5c0569874c.jpg","target":"b4ae6806f5f148388e9f8c3101373269","type":"service","platform":"all","areaInclude":"1","city":"重庆 杭州 上海 海口 深圳 长沙 福州 成都  济南 西安 北京 天津 武汉 贵阳 南京 广州 合肥 南昌 南宁 苏州 昆明 郑州","sharePicUrl":"","shareTitile":"无","shareContent":"无","version":"","serviceId":"","isChaoshi":0,"h5Url":"","start":null,"end":null},{"imgUrl":"http://img.daoway.cn/img/banners/2016/07/08/80929945-55e9-4c8e-b677-e976de11b18b.jpg","target":"app://detail/openService/ab65512d16b745f1b420c915cbd399f2","type":"webpage","platform":"all","areaInclude":"1","city":"北京 上海 广州 郑州 济南 西安 成都 杭州 南京 苏州 长沙 合肥 福州 重庆 厦门 青岛 哈尔滨 南昌 石家庄 无锡 沈阳 徐州 温州 佛山 南通 深圳 武汉 宁波 天津","sharePicUrl":"","shareTitile":"无","shareContent":"无","version":"","serviceId":"","isChaoshi":0,"h5Url":"http://www.daoway.cn/h5/detailspage.html?id=ab65512d16b745f1b420c915cbd399f2","start":null,"end":null},{"imgUrl":"http://img.daoway.cn/img/banners/2016/07/08/2d92adce-d1a6-4954-8b1b-cfbe6c88e1ca.jpg","target":"85ed45ab90ad447d8362658c16b828fd","type":"service","platform":"all","areaInclude":"0","city":"北京,上海,广州,深圳,成都","sharePicUrl":"","shareTitile":"无","shareContent":"无","version":"","serviceId":"","isChaoshi":0,"h5Url":"","start":null,"end":null}]
     */

    private String status;
    /**
     * imgUrl : http://img.daoway.cn/img/banners/2016/07/12/8b28b769-7063-4238-97d6-4d5c0569874c.jpg
     * target : b4ae6806f5f148388e9f8c3101373269
     * type : service
     * platform : all
     * areaInclude : 1
     * city : 重庆 杭州 上海 海口 深圳 长沙 福州 成都  济南 西安 北京 天津 武汉 贵阳 南京 广州 合肥 南昌 南宁 苏州 昆明 郑州
     * sharePicUrl :
     * shareTitile : 无
     * shareContent : 无
     * version :
     * serviceId :
     * isChaoshi : 0
     * h5Url :
     * start : null
     * end : null
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
        private String imgUrl;
        private String target;
        private String type;
        private String platform;
        private String areaInclude;
        private String city;
        private String sharePicUrl;
        private String shareTitile;
        private String shareContent;
        private String version;
        private String serviceId;
        private int isChaoshi;
        private String h5Url;
        private Object start;
        private Object end;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getAreaInclude() {
            return areaInclude;
        }

        public void setAreaInclude(String areaInclude) {
            this.areaInclude = areaInclude;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getSharePicUrl() {
            return sharePicUrl;
        }

        public void setSharePicUrl(String sharePicUrl) {
            this.sharePicUrl = sharePicUrl;
        }

        public String getShareTitile() {
            return shareTitile;
        }

        public void setShareTitile(String shareTitile) {
            this.shareTitile = shareTitile;
        }

        public String getShareContent() {
            return shareContent;
        }

        public void setShareContent(String shareContent) {
            this.shareContent = shareContent;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public int getIsChaoshi() {
            return isChaoshi;
        }

        public void setIsChaoshi(int isChaoshi) {
            this.isChaoshi = isChaoshi;
        }

        public String getH5Url() {
            return h5Url;
        }

        public void setH5Url(String h5Url) {
            this.h5Url = h5Url;
        }

        public Object getStart() {
            return start;
        }

        public void setStart(Object start) {
            this.start = start;
        }

        public Object getEnd() {
            return end;
        }

        public void setEnd(Object end) {
            this.end = end;
        }
    }
}
