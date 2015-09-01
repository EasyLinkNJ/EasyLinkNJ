package com.easylink.nj.bean;

import com.easylink.library.util.TextUtil;

import java.io.Serializable;

/**
 * Created by KEVIN.DAI on 15/8/31.
 */
public class OrderData implements Serializable {

    private static final long serialVersionUID = 1L;

    public String orderId = TextUtil.TEXT_EMPTY;
    public long time;
    public String money = TextUtil.TEXT_EMPTY;
    public int itemNum;

    public String productId = TextUtil.TEXT_EMPTY;
    public String title = TextUtil.TEXT_EMPTY;
    public String imgUrl = TextUtil.TEXT_EMPTY;
    public String price = TextUtil.TEXT_EMPTY;
    public int num;
    public String type = TextUtil.TEXT_EMPTY;

    public String name = TextUtil.TEXT_EMPTY;
    public String phone = TextUtil.TEXT_EMPTY;
    public String address = TextUtil.TEXT_EMPTY;

    @Override
    public String toString() {
        return "OrderData{" +
                "orderId='" + orderId + '\'' +
                ", time=" + time +
                ", money='" + money + '\'' +
                ", productId='" + productId + '\'' +
                ", title='" + title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", price='" + price + '\'' +
                ", num=" + num +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
