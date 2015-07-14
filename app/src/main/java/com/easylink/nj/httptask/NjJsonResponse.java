package com.easylink.nj.httptask;

import com.easylink.library.util.TextUtil;

/**
 * http json 响应数据对象
 * @author yihaibin
 */
public class NjJsonResponse<T> {

    private String result = TextUtil.TEXT_EMPTY;
    private T data;
    private boolean parseSuccess = true;


    public T getData() {

        return data;
    }

    public void setData(T data) {

        this.data = data;
    }

    public String getResult() {

        return result;
    }

    public void setResult(String result) {

        this.result = TextUtil.filterNull(result);
    }

    public boolean isSuccess(){

        return "succ".equals(result);
    }

    public void setParseBrokenStatus(){

        parseSuccess = false;
    }

    public boolean isParseBroken(){

        return !parseSuccess;
    }
}
