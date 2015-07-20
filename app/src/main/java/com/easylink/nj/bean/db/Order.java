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

    @Column(name = "orderId")
    public String orderId;

    @Column(name = "User")
    public User user;

    @Column(name = "time")
    public long time;

    public List<Cart> carts;
}
