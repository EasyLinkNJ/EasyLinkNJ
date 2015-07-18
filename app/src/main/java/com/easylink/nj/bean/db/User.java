package com.easylink.nj.bean.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

/**
 * Created by KEVIN.DAI on 15/7/18.
 */
public class User extends Model {

    @Column(name = "name")
    public String name;

    @Column(name = "tel")
    public String tel;

    @Column(name = "address")
    public String address;
}
