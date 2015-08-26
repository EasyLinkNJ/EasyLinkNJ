package com.easylink.nj.bean.news;

import com.easylink.library.util.TextUtil;
import com.easylink.nj.bean.product.BrandItem;
import com.easylink.nj.bean.product.ProductItem;

/**
 * 新闻实体类
 * @author yihaibin
 */
public class News implements BrandItem, ProductItem {

    //{"result":"succ","data":{"sum":"40","p":1,"ps":30,"list":[{"id":"6","title":"中国科协陈章良副主席为中国农技协海南蜂业技术交流中心成立揭牌","url":"http:\/\/nongji.suanduoyi.com\/news\/enterprise\/201506_6.html","created":"1433656279","cate_id":"9","hits":"6","keywords":"","description":"3月28日，中国农村专业技术协会海南蜂业技术交流中心揭牌仪式在热科院环植所举行，中国科协陈章良副主席与热科院王庆煌院长为该中心揭牌。该中心依托热科院环植所建设，将充分发挥国家蜂产业技术体系和海南省蜂业学会等专家资源优势，联系、服务、指导海南各市县基层蜂业农村专业技术协会，进行海南蜂业技术交流合作项目的普及推广。","postdate":"2015-06-07 13:49:27","changed":"1433656279","good_1":"1","good_2":"0","listorder":"0","durl":"","source":"","mainpic":"http:\/\/nongji.suanduoyi.com\/upload\/upfs\/201506\/07\/f_1433656270543005.jpg","cate_name":"企业新闻"},{"id":"47","title":"湖北省十项农机指标跻身全国前十","url":"http:\/\/nongji.suanduoyi.com\/news\/industry\/201506_5.html","created":"1433649406","cate_id":"8","hits":"0","keywords":"","description":"昨从省农机局获悉，农业部农机化司最新统计的13项农机化发展指标中，我省有10项指标排进全国前十位。2014年，我省农机总动力达到4293万千瓦，8种主要农作物耕种收综合机械化水平达65%，高出全国平均水平3.5个百分点，为现代农业发展提供了有力装备支撑。","postdate":"2015-06-07 12:53:50","changed":"1433649406","good_1":"1","good_2":"0","listorder":"0","durl":"","source":"","mainpic":"http:\/\/nongji.suanduoyi.com\/upload\/upfs\/201506\/07\/f_1433649385024666.jpg","cate_name":"行业动态"},{"id":"4","title":"“农业服务员”的服务组织也面临着谁来服务自己的困局","url":"http:\/\/nongji.suanduoyi.com\/news\/industry\/201506_4.html","created":"1433649323","cate_id":"7","hits":"11","keywords":"","description":"近年来，通过“点菜”或“套餐”从农业社会化服务组织那里购买各个环节的服务，已经成为有一定生产规模的农户完成农业生产的主要方式。这种新型生产方式的流行和农业服务组织的普及，不仅有效缓解了“谁来种地”的压力，也使得我国农业朝着规模化现代化的方向加速前进。","postdate":"2015-06-07 11:54:30","changed":"1433649323","good_1":"0","good_2":"0","listorder":"0","durl":"","source":"","mainpic":"http:\/\/nongji.suanduoyi.com\/upload\/upfs\/201506\/07\/f_1433649281728322.jpg","cate_name":"政策法规"}
    private String id = TextUtil.TEXT_EMPTY;
    private String title = TextUtil.TEXT_EMPTY;
    private String url = TextUtil.TEXT_EMPTY;
    private String mainpic = TextUtil.TEXT_EMPTY;//icon
    private String postdate = TextUtil.TEXT_EMPTY;//发布日期的格式化文本

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = TextUtil.filterNull(id);
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {

        this.url = TextUtil.filterNull(url);
    }

    public String getMainpic() {

        return mainpic;
    }

    public void setMainpic(String mainpic) {

        this.mainpic = TextUtil.filterNull(mainpic);
    }

    public String getPostdate() {

        return postdate;
    }

    public void setPostdate(String postdate) {

        this.postdate = TextUtil.filterNull(postdate);
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = TextUtil.filterNull(title);
    }

    @Override
    public String getLogoUrl() {

        return mainpic;
    }

    @Override
    public String getSimpleName() {

        return title;
    }

    @Override
    public String getPrice() {

        return null;
    }

    @Override
    public String getDesc() {

        return title + postdate;
    }
}
