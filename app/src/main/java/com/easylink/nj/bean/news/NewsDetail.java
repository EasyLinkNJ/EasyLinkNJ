package com.easylink.nj.bean.news;

import com.easylink.library.util.TextUtil;

/**
 * Created by KEVIN.DAI on 15/7/14.
 */
public class NewsDetail {

    //NewsDetail{id='6', title='中国科协陈章良副主席为中国农技协海南蜂业技术交流中心成立揭牌', content='<p style="text-align: right;"><img src="/upload/upfs/201506/07/f_1433656270543005.jpg" alt="中国科协陈章良副主席为中国农技协海南蜂业技术交流中心成立揭牌"/></p><p>3月28日，中国农村专业技术协会海南蜂业技术交流中心揭牌仪式在热科院环植所举行，中国科协陈章良副主席与热科院王庆煌院长为该中心揭牌。</p><p>该中心依托热科院环植所建设，将充分发挥国家蜂产业技术体系和海南省蜂业学会等专家资源优势，联系、服务、指导海南各市县基层蜂业农村专业技术协会，进行海南蜂业技术交流合作项目的普及推广，加速科研成果转化，促进热带农业发展和农民增收致富。</p><p>揭牌仪式上，陈章良副主席强调了热作产业的重要性，充分肯定了热科院在蜜蜂产业发展方面做出的贡献，希望热科院在蜜蜂育种、蜜蜂授粉技术推广、大宗农作物授粉等方面加强研究，为南繁育种和热作产业发展做出更大贡献。王庆煌院长表示，热科院高度重视热带农业的科学普及、技术推广工作，将以中心成立为契机，整合相关科技资源，加强与中国科协、中国农技协和海南省等热区科协部门的对接服务，在科普工作中发挥热带农业科技国家队的作用。</p><p>近年来，热科院环植所蜜蜂与传粉昆虫研究团队主动与地方农技推广部门合作，为家庭农场、种养大户、合作社等新型经营主体服务，成效显著，已在海南6个市县建立蜜蜂优质高效饲养技术示范基地，举办了32场次养蜂技术培训班，累计培训蜂农3636人次、蜂业管理技术骨干52人。通过推广蜜蜂授粉等新技术，充分利用昆虫授粉代替人工授粉，减少农业劳动力投入，降低生产成本，在荔枝、龙眼、梨树、黑皮冬瓜、设施哈密瓜、苦瓜、黄瓜等作物上进行蜜蜂授粉服务并取得成功。</p><p><br/></p>', created='1433656279', postdate='2015-06-07 13:49:27', cate_id='9', cate_name='企业新闻', mainpic='http://nongji.suanduoyi.com/upload//upload/upfs/201506/07/f_1433656270543005.jpg'}
    private String id = TextUtil.TEXT_EMPTY;
    private String title = TextUtil.TEXT_EMPTY;
    private String content = TextUtil.TEXT_EMPTY;
    private String created = TextUtil.TEXT_EMPTY;
    private String postdate = TextUtil.TEXT_EMPTY;
    private String cate_id = TextUtil.TEXT_EMPTY;
    private String cate_name = TextUtil.TEXT_EMPTY;
    private String mainpic = TextUtil.TEXT_EMPTY;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreated() {
        return created;
    }

    public String getPostdate() {
        return postdate;
    }

    public String getCate_id() {
        return cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public String getMainpic() {
        return mainpic;
    }

    public void setId(String id) {

        this.id = TextUtil.filterNull(id);
    }

    public void setTitle(String title) {

        this.title = TextUtil.filterNull(title);
    }

    public void setContent(String content) {

        this.content = TextUtil.filterNull(content);
    }

    public void setCreated(String created) {

        this.created = TextUtil.filterNull(created);
    }

    public void setPostdate(String postdate) {

        this.postdate = TextUtil.filterNull(postdate);
    }

    public void setCate_id(String cate_id) {

        this.cate_id = TextUtil.filterNull(cate_id);
    }

    public void setCate_name(String cate_name) {

        this.cate_name = TextUtil.filterNull(cate_name);
    }

    public void setMainpic(String mainpic) {

        this.mainpic = TextUtil.filterNull(mainpic);
    }

    @Override
    public String toString() {
        return "NewsDetail{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", created='" + created + '\'' +
                ", postdate='" + postdate + '\'' +
                ", cate_id='" + cate_id + '\'' +
                ", cate_name='" + cate_name + '\'' +
                ", mainpic='" + mainpic + '\'' +
                '}';
    }
}
