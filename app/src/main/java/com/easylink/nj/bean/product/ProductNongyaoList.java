package com.easylink.nj.bean.product;

import com.easylink.library.util.TextUtil;

import java.util.List;

/**
 * Created by yihaibin on 15/8/27.
 */
public class ProductNongyaoList {

   // {"id":"1","model":"aaa","name":"vvvaa氮肥","cate_id":"32","price":"333","listorder":"0","cate_name":"氮肥","mainpic":"http:\/\/nongji.suanduoyi.com\/upload\/huafei\/201508\/1.jpg","brand_id":"6","brand_name":"芭农","title":"芭农aaavvvaa氮肥","url":"http:\/\/nongji.suanduoyi.com\/product\/huafei\/dan\/1.html"}
   private String sum = TextUtil.TEXT_EMPTY;
    private List<ProductNongyao> list;


    public String getSum() {

        return sum;
    }

    public void setSum(String sum) {

        this.sum = TextUtil.filterNull(sum);
    }

    public List<ProductNongyao> getList() {

        return list;
    }

    public void setList(List<ProductNongyao> list) {

        this.list = list;
    }
}
