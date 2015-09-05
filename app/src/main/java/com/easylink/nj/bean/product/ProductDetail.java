package com.easylink.nj.bean.product;

import com.easylink.library.util.LogMgr;
import com.easylink.library.util.TextUtil;
import com.easylink.nj.activity.product.ProductDetailActivity.ProductType;

import java.io.Serializable;

/**
 * Created by KEVIN.DAI on 15/7/14.
 */
public class ProductDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id = TextUtil.TEXT_EMPTY;// 编号
    private String title = TextUtil.TEXT_EMPTY;// 设备全称
    private String name = TextUtil.TEXT_EMPTY;// 设备名称（不带品牌、型号）
    private String model = TextUtil.TEXT_EMPTY;// 设备型号
    private String brand_id = TextUtil.TEXT_EMPTY;// 品牌ID
    private String cate_id = TextUtil.TEXT_EMPTY;// 分类ID
    private String price = TextUtil.TEXT_EMPTY;// 设备单价
    private int stock;// 库存数量
    private int listorder;// 排序号
    private String mainpic = TextUtil.TEXT_EMPTY;// 主图url
    private String smainpic = TextUtil.TEXT_EMPTY;// 主图url，缩略参数传入时
    private String brand_name = TextUtil.TEXT_EMPTY;// 品牌名称
    private String cate_name = TextUtil.TEXT_EMPTY;// 分类名称
    private String url = TextUtil.TEXT_EMPTY;// 线上地址

    private String ct_0 = TextUtil.TEXT_EMPTY;// 参数详情名称0
    private String ct_1 = TextUtil.TEXT_EMPTY;// 参数详情名称1，如 基本参数
    private String ct_2 = TextUtil.TEXT_EMPTY;// 参数详情名称2，如 性能特点
    private String ct_3 = TextUtil.TEXT_EMPTY;// 参数详情名称3，如 结构特点
    private String ct_4 = TextUtil.TEXT_EMPTY;// 参数详情名称4，如 施工案例
    private String content_0 = TextUtil.TEXT_EMPTY;// 参数详情0
    private String content_1 = TextUtil.TEXT_EMPTY;// 参数详情1，如 基本参数
    private String content_2 = TextUtil.TEXT_EMPTY;// 参数详情2，如 性能特点
    private String content_3 = TextUtil.TEXT_EMPTY;// 参数详情3，如 结构特点
    private String content_4 = TextUtil.TEXT_EMPTY;// 参数详情4，如 施工案例

    private ProductType type;
    private String pps;

    public ProductType getType() {

        return type;
    }

    public void setType(ProductType type) {

        this.type = type;
    }

    public String getId() {

        return id;
    }

    public String getTitle() {

        return title;
    }

    public String getName() {

        return name;
    }

    public String getModel() {

        return model;
    }

    public String getBrand_id() {

        return brand_id;
    }

    public String getCate_id() {

        return cate_id;
    }

    public String getPrice() {

        return "0".equals(price) ? "价格面议" : price;
    }

    public int getStock() {

        return stock;
    }

    public int getListorder() {

        return listorder;
    }

    public String getMainpic() {

        return mainpic;
    }

    public String getSmainpic() {

        return smainpic;
    }

    public String getBrand_name() {

        return brand_name;
    }

    public String getCate_name() {

        return cate_name;
    }

    public String getUrl() {

        return url;
    }

    public void setId(String id) {

        this.id = TextUtil.filterNull(id);
    }

    public void setTitle(String title) {

        this.title = TextUtil.filterNull(title);
    }

    public void setName(String name) {

        this.name = TextUtil.filterNull(name);
    }

    public void setModel(String model) {

        this.model = TextUtil.filterNull(model);
    }

    public void setBrand_id(String brand_id) {

        this.brand_id = TextUtil.filterNull(brand_id);
    }

    public void setCate_id(String cate_id) {

        this.cate_id = TextUtil.filterNull(cate_id);
    }

    public void setPrice(String price) {

        this.price = TextUtil.filterEmpty(price, "0");
    }

    public void setStock(int stock) {

        this.stock = stock;
    }

    public void setListorder(int listorder) {

        this.listorder = listorder;
    }

    public void setMainpic(String mainpic) {

        this.mainpic = TextUtil.filterNull(mainpic);
    }

    public void setSmainpic(String smainpic) {

        this.smainpic = TextUtil.filterNull(smainpic);
    }

    public void setBrand_name(String brand_name) {

        this.brand_name = TextUtil.filterNull(brand_name);
    }

    public void setCate_name(String cate_name) {

        this.cate_name = TextUtil.filterNull(cate_name);
    }

    public void setUrl(String url) {

        this.url = TextUtil.filterNull(url);
    }

    public String getCt_1() {

        return ct_1;
    }

    public String getCt_2() {

        return ct_2;
    }

    public String getCt_3() {

        return ct_3;
    }

    public String getCt_4() {

        return ct_4;
    }

    public String getContent_1() {

        return content_1;
    }

    public String getContent_2() {

        return content_2;
    }

    public String getContent_3() {

        return content_3;
    }

    public String getContent_4() {

        return content_4;
    }

    public void setCt_1(String ct_1) {

        this.ct_1 = TextUtil.filterNull(ct_1);
    }

    public void setCt_2(String ct_2) {

        this.ct_2 = TextUtil.filterNull(ct_2);
    }

    public void setCt_3(String ct_3) {

        this.ct_3 = TextUtil.filterNull(ct_3);
    }

    public void setCt_4(String ct_4) {

        this.ct_4 = TextUtil.filterNull(ct_4);
    }

    public void setContent_1(String content_1) {

        this.content_1 = TextUtil.filterNull(content_1);
    }

    public void setContent_2(String content_2) {

        this.content_2 = TextUtil.filterNull(content_2);
    }

    public void setContent_3(String content_3) {

        this.content_3 = TextUtil.filterNull(content_3);
    }

    public void setContent_4(String content_4) {

        this.content_4 = TextUtil.filterNull(content_4);
    }

    public String getCt_0() {

        return ct_0;
    }

    public String getContent_0() {

        return content_0;
    }

    public void setCt_0(String ct_0) {

        this.ct_0 = TextUtil.filterNull(ct_0);
    }

    public void setContent_0(String content_0) {

        this.content_0 = TextUtil.filterNull(content_0);
    }

    public String getPps() {
        return pps;
    }

    public void setPps(String pps) {

        LogMgr.d("bean", "productDetail set pps = "+pps);
        this.pps = pps;
    }
}
