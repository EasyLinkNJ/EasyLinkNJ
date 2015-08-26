package com.easylink.nj.bean.product;

import com.easylink.library.util.TextUtil;

/**
 * Created by yihaibin on 15/8/27.
 */
public class BrandHuafei implements BrandItem {

//    {"id":"6","cn_brand":"芭农","title":"上海芭农化工有限公司","logo":""}

    private String id = TextUtil.TEXT_EMPTY;
    private String cn_brand = TextUtil.TEXT_EMPTY;
    private String title = TextUtil.TEXT_EMPTY;
    private String logo = TextUtil.TEXT_EMPTY;

    @Override
    public String getLogoUrl() {

        return logo;
    }

    @Override
    public String getSimpleName() {

        return cn_brand;
    }

    @Override
    public String getDesc() {

        return title;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = TextUtil.filterNull(id);
    }

    public String getCn_brand() {

        return cn_brand;
    }

    public void setCn_brand(String brand) {

        this.cn_brand = TextUtil.filterNull(brand);
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = TextUtil.filterNull(title);
    }

    public String getLogo() {

        return logo;
    }

    public void setLogo(String logo) {

        this.logo = TextUtil.filterNull(logo);
    }
}
