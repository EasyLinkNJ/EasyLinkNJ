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

    String URL_BRAND_HUAFEI_LIST = URL_BASE + "huafei/brands";
    String URL_BRAND_NONGYAO_LIST = URL_BASE + "nongyao/brands";
    String URL_BRAND_ZHONGZI_LIST = URL_BASE + "zhongzi/brands";

    String URL_PRODUCT_HUAFEI_LIST = URL_BASE + "huafei/list";
    String URL_PRODUCT_NONGYAO_LIST = URL_BASE + "nongyao/list";
    String URL_PRODUCT_ZHONGZI_LIST = URL_BASE + "zhongzi/list";

    String URL_DETAIL_NONGJI_LIST = URL_BASE + "nongji/detail";
    String URL_DETAIL_NONGYAO_LIST = URL_BASE + "nongyao/detail";
    String URL_DETAIL_HUAFEI_LIST = URL_BASE + "huafei/detail";
    String URL_DETAIL_ZHONGZI_LIST = URL_BASE + "zhongzi/detail";

    // login
    String URL_POST_LOGIN = URL_BASE + "account/loginster";
}
