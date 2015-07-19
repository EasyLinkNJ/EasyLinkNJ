package com.easylink.nj.httptask;

/**
 * 网络请求 api
 */
public interface HttpApi {

    String URL_BASE = "http://nongji.suanduoyi.com/api/";// base url

    String URL_NEWS = URL_BASE + "news/list";
    String URL_NEWS_DETAIL = URL_BASE + "news/detail";

    String URL_PRODUCT_LIST = URL_BASE + "product/list";
    String URL_PRODUCT_DETAIL = URL_BASE + "product/detail";

    String URL_POST_ORDER = URL_BASE + " askp/post";
    String URL_GET_ORDER = URL_BASE + "askp/list";
}
