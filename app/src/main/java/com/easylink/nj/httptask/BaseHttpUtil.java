package com.easylink.nj.httptask;

import android.os.Build;
import android.util.Log;

import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.util.AppInfoUtil;
import com.easylink.library.util.DeviceUtil;
import com.easylink.library.util.LogMgr;
import com.easylink.library.util.TextUtil;
import com.easylink.nj.BuildConfig;

import org.apache.http.NameValuePair;

import java.security.MessageDigest;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by KEVIN.DAI on 15/7/10.
 */
public class BaseHttpUtil implements HttpApi {

    /**
     * 获取基本的get请求参数对象，带坐标参数
     * @param url
     * @return
     */
    public static HttpTaskParams getBaseGetParams(String url) {

        return getBaseParams(HttpTaskParams.METHOD_GET, url);
    }

    /**
     * 获取基本的post请求参数对象，带坐标参数
     * @param url
     * @return
     */
    public static HttpTaskParams getBasePostParams(String url) {

        return getBaseParams(HttpTaskParams.METHOD_POST, url);
    }

    /**
     * 获取基本的upload请求参数对象，带坐标参数
     * @param url
     * @return
     */
    public static HttpTaskParams getBaseUploadParams(String url) {

        return getBaseParams(HttpTaskParams.METHOD_UPLOAD, url);
    }

    /**
     * 获取基本的请求参数对象
     * @param type get post upload
     * @param url
     * @return
     */
    public static HttpTaskParams getBaseParams(int type, String url) {

        HttpTaskParams params = null;
        switch (type) {
            case HttpTaskParams.METHOD_POST:
                params = HttpTaskParams.newPost(url);
                break;
            case HttpTaskParams.METHOD_UPLOAD:
                params = HttpTaskParams.newUpload(url);
                break;
            case HttpTaskParams.METHOD_GET:
            default:
                params = HttpTaskParams.newGet(url);
                break;
        }
        return params;
    }

    public static void setSignParam(HttpTaskParams param){

        param.addParam("appid", "100011");
        param.addParam("version", String.valueOf(AppInfoUtil.getVersionCode()));
        param.addParam("time", String.valueOf(System.currentTimeMillis()/ 1000));
        param.addParam("sign", getHttpTaskParamsSign(param));
    }

    private static String getHttpTaskParamsSign(HttpTaskParams param) {

        List<NameValuePair> pairs = param.getStringParams();
        if(pairs == null || pairs.size() == 0)
            return TextUtil.TEXT_EMPTY;

        Collections.sort(pairs, new Comparator<NameValuePair>() {
            @Override
            public int compare(NameValuePair lhs, NameValuePair rhs) {

                return lhs.getName().compareTo(rhs.getName());
            }
        });

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<pairs.size(); i++){

            sb.append(pairs.get(i).getValue());
        }
        sb.append("yyy1000111(*&j");

        if(LogMgr.isDebug())
            LogMgr.d("sign", "sign text = " + sb.toString());

        return md5(sb.toString());
    }

    private static String md5(String s) {

        try {

            char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] strTemp = s.getBytes();
            messageDigest.update(strTemp);
            byte[] md = messageDigest.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);

        } catch (Exception e) {

            e.printStackTrace();
            return s;
        }
    }

    protected static void addDefaultParams(Map<String, String> params) {

        params.put("client_id", "qyer_android");
        params.put("client_secret", "9fcaae8aefc4f9ac4915");
        params.put("v", "1");
        params.put("track_user_id", "");
        params.put("track_deviceid", DeviceUtil.getIMEI());
        params.put("track_app_version", BuildConfig.VERSION_NAME);
        params.put("track_app_channel", "");
        params.put("track_device_info", Build.DEVICE);
        params.put("track_os", "Android" + Build.VERSION.RELEASE);
        params.put("app_installtime", AppInfoUtil.getInstallAppTime() + "");
    }

    protected static String createGetUrl(String url, Map<String, String> params) {

        if (params == null || params.size() == 0)
            return url;

        StringBuilder sb = new StringBuilder(url).append('?');

        for (Map.Entry<String, String> entry : params.entrySet()) {

            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            sb.append('&');
        }
        sb.deleteCharAt(sb.length() - 1);

        String requestUrl = sb.toString();
        if (BuildConfig.DEBUG)
            Log.d("BaseHtpUtil", "~~" + requestUrl);

        return requestUrl;
    }
}
