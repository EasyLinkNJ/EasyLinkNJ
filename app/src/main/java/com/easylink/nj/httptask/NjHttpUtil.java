package com.easylink.nj.httptask;

import com.easylink.library.http.params.HttpTaskParams;

/**
 * 网络接口工具类
 */
public class NjHttpUtil extends BaseHttpUtil {

    /**
     * 获取新闻列表
     *
     * @retrn
     */
    public static HttpTaskParams getNews() {

        HttpTaskParams htp = HttpTaskParams.newPost(URL_NEWS);
        setSignParam(htp);
//        htp.addParam("id", "1001");
//        htp.addParam("cate_id", "123");
//        htp.addParam("menu_id", "456");
        return htp;
    }

    public static HttpTaskParams getNewsDetail(String newsId) {

        HttpTaskParams htp = HttpTaskParams.newPost(URL_NEWS_DETAIL);
        htp.addParam("id", newsId);
        setSignParam(htp);

        return htp;
    }
}
