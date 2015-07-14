package com.easylink.nj.httptask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KEVIN.DAI on 15/7/10.
 */
public class TestHttpUtil extends BaseHttpUtil {

    public static String getTestUrl() {

        Map<String, String> params = new HashMap<>();
        addDefaultParams(params);
        params.put("count", "200");
        return createGetUrl(URL_GET_SEARCH_HOT_CITY_TEST, params);
    }
}
