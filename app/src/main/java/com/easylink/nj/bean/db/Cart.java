package com.easylink.nj.bean.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
@Table(name = "Cart")
public class Cart extends Model {

    // 产品ID
    @Column(name = "productId")
    public String productId;

    // 产品名称
    @Column(name = "name")
    public String name;

    // 产品头图URL
    @Column(name = "imgUrl")
    public String imgUrl;

    // 产品单价
    @Column(name = "price")
    public String price;

    // 产品数量
    @Column(name = "count")
    public int count;

    // 所属的订单
    @Column(name = "orderId")
    public String orderId;

//    @Column(name = "introTitle_0")
//    public String introTitle_0;
//    @Column(name = "intro_0")
//    public String intro_0;
}
