package com.qianfeng.android.myapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/13.
 */
public class Plots {

    /**
     * communities : [{"id":237857,"city":"武汉","name":"华顶机电工业园","addr":"汉南区兴业大道 （西区）","lot":114.3162,"lat":30.581084,"serviceId":null,"area":"其他"},{"id":244516,"city":"武汉","name":"光谷新界","addr":"东湖高新高新大道888号（高新大道与光谷七路交汇处）","lot":114.3162,"lat":30.581084,"serviceId":null,"area":"东湖高新"},{"id":244519,"city":"武汉","name":"凯德知音的湖","addr":"蔡甸汉阳大道与知音湖大道交界处（知音的湖公交站旁边）","lot":114.3162,"lat":30.581084,"serviceId":null,"area":"蔡甸"},{"id":242217,"city":"武汉","name":"新纪元艺墅庄园","addr":"黄陂武湖五七路北（农耕年华隔壁）","lot":114.3162,"lat":30.581084,"serviceId":null,"area":"黄陂"},{"id":237973,"city":"武汉","name":"国际企业中心3期","addr":"洪山关山二路特一号","lot":114.3162,"lat":30.581084,"serviceId":null,"area":"洪山"},{"id":237977,"city":"武汉","name":"CMD新华光华中国际空港高新区","addr":"黄陂武汉临空经济区川龙大道","lot":114.3162,"lat":30.581084,"serviceId":null,"area":"黄陂"},{"id":240815,"city":"武汉","name":"八一花园（还建房）","addr":"洪山雄楚大道与民族大道洪山高中旁熊家咀还建房","lot":114.3162,"lat":30.581084,"serviceId":null,"area":"洪山"},{"id":239569,"city":"武汉","name":"泛海国际香海园","addr":"江汉中央商务区淮海路中段","lot":114.3162,"lat":30.581084,"serviceId":null,"area":"江汉"},{"id":296972,"city":"武汉","name":"武汉名车世界汽车销售有限公司","addr":"解放大道696武汉国际会展中心附近","lot":114.3161926269531,"lat":30.58107948303223,"serviceId":null,"area":"武昌区"},{"id":273530,"city":"武汉","name":"武汉伟诚专业贴膜","addr":"临江大道江滩211","lot":114.32018274016,"lat":30.581591680508,"serviceId":null,"area":"武昌区"},{"id":279969,"city":"武汉","name":"新河社区船院小区","addr":"临江大道387号附近","lot":114.32044324876,"lat":30.580713370154,"serviceId":null,"area":"武昌区"},{"id":274782,"city":"武汉","name":"江景壹号","addr":"临江大道333号","lot":114.32060494375,"lat":30.581467318468,"serviceId":null,"area":"武昌区"}]
     * baiduCommunities : [{"name":"新劲邦6A汽车服务部","addr":"临江大道江滩200-207","city":"武汉市","province":"湖北省","district":"武昌区","street":"武汉长江隧道","x":114.31875443439894,"y":30.578964498174223},{"name":"武昌江滩电厂闸口","addr":"武汉市武昌区","city":"武汉市","province":"湖北省","district":"武昌区","street":"武汉长江隧道","x":114.32221291061236,"y":30.583177282387766},{"name":"武汉禾丽联合医疗投资有限公司","addr":"临江大道168号","city":"武汉市","province":"湖北省","district":"武昌区","street":"武汉长江隧道","x":114.31788307805427,"y":30.576376109173335},{"name":"华新里小区(下新河后街)","addr":"临江大道297","city":"武汉市","province":"湖北省","district":"武昌区","street":"武汉长江隧道","x":114.32197935117976,"y":30.578832359885602},{"name":"明盛汽车维修服务部","addr":"武昌区临江大道江滩214","city":"武汉市","province":"湖北省","district":"武昌区","street":"武汉长江隧道","x":114.31778426444816,"y":30.575451112395168},{"name":"宝宝乐园","addr":"临江大道291附6号","city":"武汉市","province":"湖北省","district":"武昌区","street":"武汉长江隧道","x":114.32211409700626,"y":30.578031753423982},{"name":"众鑫堂风湿中心","addr":"和平大道融侨华府5栋10号","city":"武汉市","province":"湖北省","district":"武昌区","street":"武汉长江隧道","x":114.32363223331812,"y":30.577316642978182},{"name":"中商平价","addr":"和平大道和平大道666号（三层楼站融侨华府小区）","city":"武汉市","province":"湖北省","district":"武昌区","street":"武汉长江隧道","x":114.32348850443653,"y":30.577021269717864}]
     * parent : {"id":289406,"city":"武汉","name":"武昌区","addr":"武昌区","lot":114.35362228468497,"lat":30.56486029278519,"serviceId":null,"area":"武昌区"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 289406
         * city : 武汉
         * name : 武昌区
         * addr : 武昌区
         * lot : 114.35362228468497
         * lat : 30.56486029278519
         * serviceId : null
         * area : 武昌区
         */

        private ParentBean parent;
        /**
         * id : 237857
         * city : 武汉
         * name : 华顶机电工业园
         * addr : 汉南区兴业大道 （西区）
         * lot : 114.3162
         * lat : 30.581084
         * serviceId : null
         * area : 其他
         */

        private List<CommunitiesBean> communities;
        /**
         * name : 新劲邦6A汽车服务部
         * addr : 临江大道江滩200-207
         * city : 武汉市
         * province : 湖北省
         * district : 武昌区
         * street : 武汉长江隧道
         * x : 114.31875443439894
         * y : 30.578964498174223
         */

        private List<BaiduCommunitiesBean> baiduCommunities;

        public ParentBean getParent() {
            return parent;
        }

        public void setParent(ParentBean parent) {
            this.parent = parent;
        }

        public List<CommunitiesBean> getCommunities() {
            return communities;
        }

        public void setCommunities(List<CommunitiesBean> communities) {
            this.communities = communities;
        }

        public List<BaiduCommunitiesBean> getBaiduCommunities() {
            return baiduCommunities;
        }

        public void setBaiduCommunities(List<BaiduCommunitiesBean> baiduCommunities) {
            this.baiduCommunities = baiduCommunities;
        }

        public static class ParentBean {
            private int id;
            private String city;
            private String name;
            private String addr;
            private double lot;
            private double lat;
            private Object serviceId;
            private String area;

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

            public Object getServiceId() {
                return serviceId;
            }

            public void setServiceId(Object serviceId) {
                this.serviceId = serviceId;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }
        }

        public static class CommunitiesBean {
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

        public static class BaiduCommunitiesBean {
            private String name;
            private String addr;
            private String street;
            private double x;
            private double y;

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

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public double getX() {
                return x;
            }

            public void setX(double x) {
                this.x = x;
            }

            public double getY() {
                return y;
            }

            public void setY(double y) {
                this.y = y;
            }
        }
    }
}
