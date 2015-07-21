package com.easylink.nj.bean.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
@Table(name = "Address")
public class Address extends Model {

    // 收货人
    @Column(name = "name")
    public String name;

    // 联系方式
    @Column(name = "phone")
    public String phone;

    // 收货地址
    @Column(name = "address")
    public String address;

    // 是否为默认地址
    @Column(name = "isDefault")
    public boolean isDefault;
}
