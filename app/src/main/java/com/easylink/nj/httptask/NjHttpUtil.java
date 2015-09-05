package com.easylink.nj.httptask;

import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.util.DeviceUtil;
import com.easylink.library.util.TextUtil;
import com.easylink.nj.activity.product.ProductDetailActivity.ProductType;

/**
 * 网络接口工具类
 */
public class NjHttpUtil extends BaseHttpUtil {

    /**
     * 农机品牌列表
     * @return
     */
    public static HttpTaskParams getBrandNongjiList(){

        HttpTaskParams htp = HttpTaskParams.newPost(URL_BRAND_NONGJI_LIST);
        setSignParam(htp);
        return htp;
    }

    /**
     * 农药品牌列表
     * @param page
     * @param limitSize
     * @return
     */
    public static HttpTaskParams getBrandNongyaoList(int page, int limitSize){

        HttpTaskParams htp = HttpTaskParams.newPost(URL_BRAND_NONGYAO_LIST);
        setSignParam(htp);
        return htp;
    }

    /**
     * 种子品牌列表
     * @param page
     * @param limitSize
     * @return
     */
    public static HttpTaskParams getBrandZhongziList(int page, int limitSize){

        HttpTaskParams htp = HttpTaskParams.newPost(URL_BRAND_ZHONGZI_LIST);
        setSignParam(htp);
        return htp;
    }

    /**
     * 化肥品牌列表
     * @param page
     * @param limitSize
     * @return
     */
    public static HttpTaskParams getBrandHuafeiList(int page, int limitSize){

        HttpTaskParams htp = HttpTaskParams.newPost(URL_BRAND_HUAFEI_LIST);
        setSignParam(htp);
        return htp;
    }

    /**
     * 农机产品列表
     * @param brand
     * @param cateId
     * @param page
     * @param limitSize
     * @return
     */
    public static HttpTaskParams getProductNongjiList(String brand, String cateId, int page, int limitSize){

        HttpTaskParams htp = HttpTaskParams.newPost(URL_PRODUCT_NONGJI_LIST);
        htp.addParam("p", String.valueOf(page));
        htp.addParam("ps", String.valueOf(limitSize));
        if(!TextUtil.isEmpty(brand))
            htp.addParam("company_id", brand);

        if(!TextUtil.isEmpty(cateId))
            htp.addParam("cate_id", cateId);

        setSignParam(htp);
        return htp;
    }

    /**
     * 农药产品列表
     * @param page
     * @param limitSize
     * @return
     */
    public static HttpTaskParams getProductNongyaoList(String companyId, int page, int limitSize){

        HttpTaskParams htp = HttpTaskParams.newPost(URL_PRODUCT_NONGYAO_LIST);
        htp.addParam("p", String.valueOf(page));
        htp.addParam("ps", String.valueOf(limitSize));
        htp.addParam("company_id", companyId);
        setSignParam(htp);
        return htp;
    }

    /**
     * 种子产品列表
     * @param page
     * @param limitSize
     * @return
     */
    public static HttpTaskParams getProductZhongziList(String companyId, int page, int limitSize){

        HttpTaskParams htp = HttpTaskParams.newPost(URL_PRODUCT_ZHONGZI_LIST);
        htp.addParam("p", String.valueOf(page));
        htp.addParam("ps", String.valueOf(limitSize));
        htp.addParam("company_id", companyId);
        setSignParam(htp);
        return htp;
    }

    /**
     * 化肥产品列表
     * @param page
     * @param limitSize
     * @return
     */
    public static HttpTaskParams getProductHuafeiList(String companyId, int page, int limitSize){

        HttpTaskParams htp = HttpTaskParams.newPost(URL_PRODUCT_HUAFEI_LIST);
        htp.addParam("p", String.valueOf(page));
        htp.addParam("ps", String.valueOf(limitSize));
        htp.addParam("company_id", companyId);
        setSignParam(htp);
        return htp;
    }

    /**
     * 农机产品搜索列表
     * @param searchKey
     * @param limitSize
     * @return
     */
    public static HttpTaskParams getProductNongjiSearchList(String searchKey, int page, int limitSize){

        HttpTaskParams htp = HttpTaskParams.newPost(URL_PRODUCT_NONGJI_LIST);
        htp.addParam("p", String.valueOf(page));
        htp.addParam("ps", String.valueOf(limitSize));
        if(!TextUtil.isEmpty(searchKey))
            htp.addParam("keyword", searchKey);

        setSignParam(htp);
        return htp;
    }

    /**
     * 农药产品搜索列表
     * @param searchKey
     * @param limitSize
     * @return
     */
    public static HttpTaskParams getProductNongyaoSearchList(String searchKey, int page, int limitSize){

        HttpTaskParams htp = HttpTaskParams.newPost(URL_PRODUCT_NONGYAO_LIST);
        htp.addParam("p", String.valueOf(page));
        htp.addParam("ps", String.valueOf(limitSize));
        if(!TextUtil.isEmpty(searchKey))
            htp.addParam("keyword", searchKey);

        setSignParam(htp);
        return htp;
    }

    /**
     * 种子产品搜索列表
     * @param searchKey
     * @param limitSize
     * @return
     */
    public static HttpTaskParams getProductZhongziSearchList(String searchKey, int page, int limitSize){

        HttpTaskParams htp = HttpTaskParams.newPost(URL_PRODUCT_ZHONGZI_LIST);
        htp.addParam("p", String.valueOf(page));
        htp.addParam("ps", String.valueOf(limitSize));
        if(!TextUtil.isEmpty(searchKey))
            htp.addParam("keyword", searchKey);

        setSignParam(htp);
        return htp;
    }

    /**
     * 化肥产品搜索列表
     * @param searchKey
     * @param limitSize
     * @return
     */
    public static HttpTaskParams getProductHuafeiSearchList(String searchKey, int page, int limitSize){

        HttpTaskParams htp = HttpTaskParams.newPost(URL_PRODUCT_HUAFEI_LIST);
        htp.addParam("p", String.valueOf(page));
        htp.addParam("ps", String.valueOf(limitSize));
        if(!TextUtil.isEmpty(searchKey))
            htp.addParam("keyword", searchKey);

        setSignParam(htp);
        return htp;
    }

    /**
     * 农机分类列表
     * @return
     */
    public static HttpTaskParams getCategoryNongjiList(){

        HttpTaskParams htp = HttpTaskParams.newPost(URL_CATEGORY_NONGJI_LIST);
        setSignParam(htp);
        return htp;
    }


//--------------------------------------------------------------------------------------
    /**
     * 获取新闻列表
     *
     * @retrn
     */
    public static HttpTaskParams getNewsList(int page, int limitSize) {

        HttpTaskParams htp = HttpTaskParams.newPost(URL_NEWS);
        htp.addParam("p", String.valueOf(page));
        htp.addParam("ps", String.valueOf(limitSize));
        setSignParam(htp);
        return htp;
    }

    /**
     * 获取前5条新闻
     *
     * @return
     */
    public static HttpTaskParams getNewsTop5() {

        HttpTaskParams htp = HttpTaskParams.newPost(URL_NEWS);
        htp.addParam("p", "1");
        htp.addParam("ps", "5");
        setSignParam(htp);
        return htp;
    }

    /**
     * 新闻详情
     *
     * @param newsId
     * @return
     */
    public static HttpTaskParams getNewsDetail(String newsId) {

        HttpTaskParams htp = HttpTaskParams.newPost(URL_NEWS_DETAIL);
        htp.addParam("id", newsId);
        setSignParam(htp);
        return htp;
    }

    public static HttpTaskParams getProductList(int page, int limitSize) {

        HttpTaskParams htp = HttpTaskParams.newPost(URL_PRODUCT_NONGJI_LIST);
        htp.addParam("p", String.valueOf(page));
        htp.addParam("ps", String.valueOf(limitSize));
        setSignParam(htp);
        return htp;
    }

    public static HttpTaskParams getProductListByCateId(int page, int limitSize, String cateId){

        HttpTaskParams htp = HttpTaskParams.newPost(URL_PRODUCT_NONGJI_LIST);
        htp.addParam("p", String.valueOf(page));
        htp.addParam("ps", String.valueOf(limitSize));
        htp.addParam("cate_id", cateId);
        setSignParam(htp);
        return htp;
    }

    public static HttpTaskParams getProductListByKey(int page, int limitSize, String key){

        HttpTaskParams htp = HttpTaskParams.newPost(URL_PRODUCT_NONGJI_LIST);
        htp.addParam("p", String.valueOf(page));
        htp.addParam("ps", String.valueOf(limitSize));
        htp.addParam("keyword", TextUtil.filterNull(key));
        setSignParam(htp);
        return htp;
    }

    public static HttpTaskParams getProductList() {

        HttpTaskParams htp = HttpTaskParams.newPost(URL_PRODUCT_NONGJI_LIST);
        setSignParam(htp);

        return htp;
    }

    public static HttpTaskParams getDetail(ProductType type, String id) {

        String url = "";
        switch (type) {
            case NJ:
            case NJPARTS:
                url = URL_DETAIL_NONGJI;
                break;
            case NY:
                url = URL_DETAIL_NONGYAO_LIST;
                break;
            case HF:
                url = URL_DETAIL_HUAFEI_LIST;
                break;
            case ZZ:
                url = URL_DETAIL_ZHONGZI_LIST;
                break;
        }

        HttpTaskParams htp = HttpTaskParams.newPost(url);
        htp.addParam("id", id);
        setSignParam(htp);

        return htp;
    }

    /**
     * 获取产品配件列表，目前只有农机在用
     * @param productId
     * @return
     */
    public static HttpTaskParams getProductPps(String productId){

        HttpTaskParams htp = HttpTaskParams.newPost(URL_PPS_NONGJI);
        htp.addParam("id", productId);
        setSignParam(htp);
        return htp;
    }

    public static HttpTaskParams getPostOrder(String onlinekey, String name, String phone, String address, String orderjs) {

        HttpTaskParams htp = HttpTaskParams.newPost(URL_POST_ORDER);
        htp.addParam("onlinekey", onlinekey);
        htp.addParam("name", name);
        htp.addParam("phone", phone);
        htp.addParam("address", address);
        htp.addParam("orderjs", orderjs);

        setSignParam(htp);
        return htp;
    }

    public static HttpTaskParams getMyOrder(String onlinekey, int page, int limitSize) {

        HttpTaskParams htp = HttpTaskParams.newPost(URL_GET_ORDER);
        htp.addParam("onlinekey", onlinekey);
        htp.addParam("p", String.valueOf(page));
        htp.addParam("ps", String.valueOf(limitSize));

        setSignParam(htp);
        return htp;
    }

    public static HttpTaskParams getOrderDel(String orderId, String onlinekey) {

        HttpTaskParams htp = HttpTaskParams.newPost(URL_DEL_ORDER);
        htp.addParam("onlinekey", onlinekey);
        htp.addParam("id", orderId);

        setSignParam(htp);
        return htp;
    }

    public static HttpTaskParams getLoginParams() {

        HttpTaskParams htp = HttpTaskParams.newPost(URL_POST_LOGIN);
        String deviceId = DeviceUtil.getIMEI();
        htp.addParam("uname", deviceId);
        htp.addParam("passwd", deviceId);
        htp.addParam("name", deviceId);
        htp.addParam("ua", deviceId);

        setSignParam(htp);
        return htp;
    }
}
