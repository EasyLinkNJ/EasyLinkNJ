package com.easylink.nj.bean.product;

import com.easylink.library.util.TextUtil;

import java.util.List;

/**
 * 主类别
 * Created by yihaibin on 15/7/22.
 */
public class MainCate {

    private String id = TextUtil.TEXT_EMPTY;
    private String name = TextUtil.TEXT_EMPTY;
    private List<SubCate> mSubCateList;

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = String.valueOf(id);;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = String.valueOf(name);
    }

    public void setSubCateList(List<SubCate> subCateList){

        mSubCateList = subCateList;
    }

    public List<SubCate> getSubCateList(){

        return mSubCateList;
    }
}
