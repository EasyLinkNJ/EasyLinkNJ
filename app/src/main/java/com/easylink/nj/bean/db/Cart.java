package com.easylink.nj.bean.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
@Table(name = "Cart")
public class Cart extends Model {

    @Column(name = "productId")
    public String productId;

    @Column(name = "name")
    public String name;

    @Column(name = "imgUrl")
    public String imgUrl;

    @Column(name = "price")
    public String price;

    @Column(name = "introTitle_0")
    public String introTitle_0;

    @Column(name = "intro_0")
    public String intro_0;

    @Column(name = "count")
    public int count;

    @Column(name = "time")
    public long time;
}
