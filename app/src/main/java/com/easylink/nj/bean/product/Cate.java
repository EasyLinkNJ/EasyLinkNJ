package com.easylink.nj.bean.product;

import com.easylink.library.util.TextUtil;

/**
 * Created by yihaibin on 15/7/22.
 */
public class Cate {

    public static final int TYPE_MAIN = 1;
    public static final int TYPE_SUB = 2;

    private int type;
    private String id = TextUtil.TEXT_EMPTY;
    private String name = TextUtil.TEXT_EMPTY;

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = String.valueOf(id);
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = String.valueOf(name);
    }

    public int getType() {

        return type;
    }

    public void setType(int type) {

        this.type = type;
    }
}
