package com.easylink.nj.bean.news;

import com.easylink.library.util.TextUtil;

import java.util.List;

/**
 */
public class NewsList {

    private String sum = TextUtil.TEXT_EMPTY;
    private List<News> list;


    public String getSum() {

        return sum;
    }

    public void setSum(String sum) {

        this.sum = TextUtil.filterNull(sum);
    }

    public List<News> getList() {

        return list;
    }

    public void setList(List<News> list) {

        this.list = list;
    }
}
