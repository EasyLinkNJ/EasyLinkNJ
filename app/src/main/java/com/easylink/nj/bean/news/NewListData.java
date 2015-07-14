package com.easylink.nj.bean.news;

import com.easylink.library.util.TextUtil;

import java.util.List;

/**
 */
public class NewListData {

    private String sum = TextUtil.TEXT_EMPTY;
    private List<New> list;


    public String getSum() {

        return sum;
    }

    public void setSum(String sum) {

        this.sum = TextUtil.filterNull(sum);
    }

    public List<New> getList() {

        return list;
    }

    public void setList(List<New> list) {

        this.list = list;
    }
}
