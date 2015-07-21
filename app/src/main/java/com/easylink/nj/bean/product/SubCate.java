package com.easylink.nj.bean.product;

import com.easylink.library.util.TextUtil;

/**
 * 子类别
 * Created by yihaibin on 15/7/22.
 */
public class SubCate {

    private String id = TextUtil.TEXT_EMPTY;
    private String name = TextUtil.TEXT_EMPTY;

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
}
