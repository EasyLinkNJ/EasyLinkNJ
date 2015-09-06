package com.easylink.nj.bean.product;

import com.easylink.library.util.TextUtil;

/**
 * Created by yihaibin on 15/8/28.
 */
public class CategoryNongyao {

    private String id = TextUtil.TEXT_EMPTY;
    private String name = TextUtil.TEXT_EMPTY;


    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = TextUtil.filterNull(id);
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = TextUtil.filterNull(name);
    }
}
