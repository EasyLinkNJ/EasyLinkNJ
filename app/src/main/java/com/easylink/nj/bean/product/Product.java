package com.easylink.nj.bean.product;

import com.easylink.library.util.TextUtil;

/**
 * Created by KEVIN.DAI on 15/7/14.
 */
public class Product {

    private String id = TextUtil.TEXT_EMPTY;// 编号
    private String title = TextUtil.TEXT_EMPTY;// 设备全称
    private String name = TextUtil.TEXT_EMPTY;// 设备名称（不带品牌、型号）
    private String model = TextUtil.TEXT_EMPTY;// 设备型号
    private String brand_id = TextUtil.TEXT_EMPTY;// 品牌ID
    private String cate_id = TextUtil.TEXT_EMPTY;// 分类ID
    private String price = TextUtil.TEXT_EMPTY;// 设备单价
    private int stock;// 库存数量
    private int listorder;// 排序号
    private String mainpic;// 主图url
    private String smainpic;// 主图url，缩略参数传入时
    private String brand_name;// 品牌名称
    private String cate_name;// 分类名称
    private String url;// 线上地址

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

        return "0".equals(price)?"价格面议":price;
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

        this.price = TextUtil.filterEmpty(price,"0");
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

    @Override
    public String toString() {

        return "Product{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", brand_id='" + brand_id + '\'' +
                ", cate_id='" + cate_id + '\'' +
                ", price='" + price + '\'' +
                ", stock=" + stock +
                ", listorder=" + listorder +
                ", mainpic='" + mainpic + '\'' +
                ", smainpic='" + smainpic + '\'' +
                ", brand_name='" + brand_name + '\'' +
                ", cate_name='" + cate_name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
