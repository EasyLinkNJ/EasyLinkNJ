package com.easylink.nj.bean.product;

import java.util.ArrayList;

/**
 * Created by KEVIN.DAI on 15/7/14.
 */
public class ProductList {

    private int sum;// 当前条件下的总数
    private int p;// 当前页码
    private int ps;// 当前返回的每页数量
    private ArrayList<Product> list;// 列表

    public int getSum() {

        return sum;
    }

    public int getP() {

        return p;
    }

    public int getPs() {

        return ps;
    }

    public ArrayList<Product> getList() {

        return list == null ? new ArrayList<Product>(0) : list;
    }

    public void setSum(int sum) {

        this.sum = sum;
    }

    public void setP(int p) {

        this.p = p;
    }

    public void setPs(int ps) {

        this.ps = ps;
    }

    public void setList(ArrayList<Product> list) {

        this.list = list == null ? new ArrayList<Product>(0) : list;
    }

    @Override
    public String toString() {

        return "ProductList{" +
                "sum=" + sum +
                ", p=" + p +
                ", ps=" + ps +
                ", list=" + list +
                '}';
    }
}
