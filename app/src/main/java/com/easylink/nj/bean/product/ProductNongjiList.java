package com.easylink.nj.bean.product;

import com.easylink.library.util.TextUtil;

import java.util.ArrayList;

/**
 * Created by KEVIN.DAI on 15/7/14.
 */
public class ProductNongjiList {

    private String sum;// 当前条件下的总数
    private int p;// 当前页码
    private int ps;// 当前返回的每页数量
    private ArrayList<ProductNongji> list;// 列表

    public String getSum() {

        return sum;
    }

    public int getP() {

        return p;
    }

    public int getPs() {

        return ps;
    }

    public ArrayList<ProductNongji> getList() {

        return list == null ? new ArrayList<ProductNongji>(0) : list;
    }

    public void setSum(String sum) {

        this.sum = TextUtil.filterNull(sum);
    }

    public void setP(int p) {

        this.p = p;
    }

    public void setPs(int ps) {

        this.ps = ps;
    }

    public void setList(ArrayList<ProductNongji> list) {

        this.list = list == null ? new ArrayList<ProductNongji>(0) : list;
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
