package com.easylink.nj.activity.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.easylink.library.util.TextUtil;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjActivity;

/**
 * 新闻详情
 * @author yihaibin
 */
public class NewsDetailActivity extends NjActivity {

    private ProgressBar mPbLoading;
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_news_detail);
        webViewLoadUrl(getIntent().getStringExtra("url"));
//        loadDataFromServer(getIntent().getStringExtra("id"));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack(R.string.news_detail);
    }

    @Override
    protected void initContentView() {

        mPbLoading = (ProgressBar) findViewById(R.id.pbLoading);

        mWebView = (WebView) findViewById(R.id.wvContent);
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);//推荐使用的窗口
        settings.setLoadWithOverviewMode(true);//自适应?
        settings.setJavaScriptEnabled(true);

        settings.setBuiltInZoomControls(true);//构建缩放控制，组合调用setDisplayZoomControls setSupportZoom来实现
        //settings.setDisplayZoomControls(false);//是否显示缩放按钮
        //settings.setSupportZoom(false);//支持缩放
        mWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                super.onProgressChanged(view, newProgress);
                mPbLoading.setProgress(newProgress);
                if(newProgress >= 100){

                    ViewUtil.hideView(mPbLoading);
                }else{

                    ViewUtil.showView(mPbLoading);
                }
            }
        });
    }

//    private void loadDataFromServer(String newsId) {
//
//        executeHttpTaskByUiSwitch(0, NjHttpUtil.getNewsDetail(newsId), NewsDetail.class);
//    }

//    @Override
//    public void invalidateContent(int what, NewsDetail newsDetail) {
//
//        LogMgr.d("daisw", "detail: " + newsDetail.toString());
//        mWebView.loadData(newsDetail.getContent(), "text/html; charset=UTF-8", "utf-8");
//    }

    private void webViewLoadUrl(String url){

        mWebView.loadUrl(TextUtil.filterNull(url));
    }

    public static void startActivity(Activity activity, String id, String url){

        Intent intent = new Intent();
        intent.setClass(activity, NewsDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }
}
