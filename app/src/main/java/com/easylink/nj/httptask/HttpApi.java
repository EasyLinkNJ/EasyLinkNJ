package com.easylink.nj.httptask;

/**
 * 网络请求 api
 */
public interface HttpApi {

    String URL_BASE = "http://nongji.suanduoyi.com/api/";// base url
    String URL_NEWS = URL_BASE+"news/list";

    String URL_BASE_TEST = "http://open.qyer.com";// base url
    String URL_LOGIN_TEST = URL_BASE + "/qyer/user/login";// 登录
    String URL_GET_SEARCH_HOT_CITY_TEST = URL_BASE + "/qyer/hotel/hot_city_list";// 200热门城市
    String URL_GET_ONWAY_CITY_DETAIL_TEST = URL_BASE + "/qyer/onroad/city_detail";// 根据坐标获取对应的城市信息（包含聊天室）
}
