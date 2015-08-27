package com.easylink.nj.bean.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/7/20.
 */
@Table(name = "Order_")
public class Order extends Model {

    // 订单ID
    @Column(name = "orderId")
    public String orderId;

    // 订单创建时间
    @Column(name = "time")
    public long time;

    // 关联用户表
    @Column(name = "Address")
    public Address address;

    // 关联多个产品
    public List<Cart> carts;
}
