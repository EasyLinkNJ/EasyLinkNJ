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
    public static HttpTaskParams getNewsAll() {

        HttpTaskParams htp = HttpTaskParams.newPost(URL_NEWS);
//        htp.addParam("id", "1001");
//        htp.addParam("cate_id", "123");
//        htp.addParam("menu_id", "456");
        setSignParam(htp);
        return htp;
    }

    /**
     * 获取前5条新闻
     * @return
     */
    public static HttpTaskParams getNewsTop5(){

        HttpTaskParams htp = HttpTaskParams.newPost(URL_NEWS);
        htp.addParam("p", "1");
        htp.addParam("ps", "5");
        setSignParam(htp);
        return htp;
    }

    /**
     * 新闻详情
     * @param newsId
     * @return
     */
    public static HttpTaskParams getNewsDetail(String newsId) {

        HttpTaskParams htp = HttpTaskParams.newPost(URL_NEWS_DETAIL);
        htp.addParam("id", newsId);
        setSignParam(htp);

        return htp;
    }

    public static HttpTaskParams getProductList() {

        HttpTaskParams htp = HttpTaskParams.newPost(URL_PRODUCT_LIST);

        setSignParam(htp);

        return htp;
    }

    public static HttpTaskParams getProductDetail(String productId) {

        HttpTaskParams htp = HttpTaskParams.newPost(URL_PRODUCT_DETAIL);
        htp.addParam("id", productId);
        setSignParam(htp);

        return htp;
    }
}
