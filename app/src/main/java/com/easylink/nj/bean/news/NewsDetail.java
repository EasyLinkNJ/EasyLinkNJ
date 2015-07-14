package com.easylink.nj.bean.news;

import com.easylink.library.util.TextUtil;

/**
 * Created by KEVIN.DAI on 15/7/14.
 */
public class NewsDetail {

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
