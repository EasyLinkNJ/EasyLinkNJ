package com.easylink.nj.bean.product;

import com.easylink.library.util.TextUtil;
import com.easylink.nj.bean.news.News;

import java.util.List;

/**
 * Created by yihaibin on 15/8/27.
 */
public class ProductHuafei implements ProductItem{

    // {"id":"1","model":"aaa","name":"vvvaa氮肥","cate_id":"32","price":"333","listorder":"0","cate_name":"氮肥","mainpic":"http:\/\/nongji.suanduoyi.com\/upload\/huafei\/201508\/1.jpg","brand_id":"6","brand_name":"芭农","title":"芭农aaavvvaa氮肥","url":"http:\/\/nongji.suanduoyi.com\/product\/huafei\/dan\/1.html"}
    private String model = TextUtil.TEXT_EMPTY;
    private String name =TextUtil.TEXT_EMPTY;
    private String price = TextUtil.TEXT_EMPTY;
    private String mainpic = TextUtil.TEXT_EMPTY;
    private String title = TextUtil.TEXT_EMPTY;
    private String url = TextUtil.TEXT_EMPTY;

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

        return price;
    }

    public String getModel() {

        return model;
    }

    public void setModel(String model) {

        this.model = TextUtil.filterNull(model);
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = TextUtil.filterNull(name);
    }

    public void setPrice(String price) {

        this.price = TextUtil.filterNull(price);
    }

    public String getMainpic() {

        return mainpic;
    }

    public void setMainpic(String mainpic) {

        this.mainpic = TextUtil.filterNull(mainpic);
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = TextUtil.filterNull(title);
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {

        this.url = TextUtil.filterNull(url);
    }
}
