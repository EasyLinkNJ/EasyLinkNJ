package com.easylink.nj.bean;

import com.easylink.library.util.TextUtil;

import java.io.Serializable;

/**
 * Created by yihaibin on 15/9/7.
 */
public class Banner implements Serializable{

    public static final String MOD_NAME_NONE = "none";
    public static final String MOD_NAME_NONGJI = "nongji";
    public static final String MOD_NAME_NONGJI_PEIJIAN = "peijian";
    public static final String MOD_NAME_NONGYAO = "nongyao";
    public static final String MOD_NAME_HUAFEI = "huafei";
    public static final String MOD_NAME_ZHONGZI = "zhongzi";

    private String id = TextUtil.TEXT_EMPTY;
    private String modid = TextUtil.TEXT_EMPTY;
    private String modname = TextUtil.TEXT_EMPTY;
    private String picurl = TextUtil.TEXT_EMPTY;

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = TextUtil.filterNull(id);
    }

    public String getModid() {

        return modid;
    }

    public void setModid(String modid) {

        this.modid =TextUtil.filterNull(modid);
    }

    public String getModname() {

        return modname;
    }

    public void setModname(String modname) {

        this.modname = TextUtil.filterNull(modname);
    }

    public String getPicurl() {

        return picurl;
    }

    public void setPicurl(String picurl) {

        this.picurl = TextUtil.filterNull(picurl);
    }
}
