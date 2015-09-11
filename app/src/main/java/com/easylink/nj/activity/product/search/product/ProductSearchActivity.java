package com.easylink.nj.activity.product.search.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easylink.library.util.DensityUtil;
import com.easylink.library.util.SoftInputHandler;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjTabViewPagerFragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yihaibin on 15/7/23.
 */
public class ProductSearchActivity extends NjTabViewPagerFragmentActivity {

    private EditText mEt;
    private SoftInputHandler mInputHandler;
    private List<ProductSearchListFragment> mSearchFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentViewWithTab();
    }

    @Override
    protected void initData() {

        mInputHandler = new SoftInputHandler(this);
    }

    @Override
    protected void initTitleView() {

        addTitleLeftBackView(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mInputHandler.finishActivityBySoftInput(mEt);
            }
        });

        mEt = (EditText) getLayoutInflater().inflate(R.layout.view_search, null);
        mEt.setHint("请输入搜索关键词");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(36));
        lp.rightMargin = DensityUtil.dip2px(12);
        addTitleMiddleView(mEt, lp);
        mEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    onSearchClick(v.getText().toString());
                    return true;
                }

                return false;
            }
        });

        mEt.requestFocus();
    }

    @Override
    protected void initContentView() {

        mSearchFragments = getFragments();
        setTabAndFragment(getTabs(), mSearchFragments, 0, true);
        setViewPagerLimit(4);
        hideFrame();
    }

    private void onSearchClick(final String text) {

        mInputHandler.hideSoftInputPost(mEt, new Runnable() {
            @Override
            public void run() {

                if(mSearchFragments == null || mSearchFragments.isEmpty()){

                    //nothong
                }else{

                    showFrame();
                    for(int i=0; i<mSearchFragments.size(); i++){

                        mSearchFragments.get(i).loadDataFromServerBySearch(text);
                    }
                }
            }
        });
    }

    private List<String> getTabs(){

        ArrayList<String> tabs = new ArrayList<String>();
        tabs.add("农机");
        tabs.add("农药");
        tabs.add("种子");
        tabs.add("化肥");
        return tabs;
    }

    private List<ProductSearchListFragment> getFragments(){

        ArrayList<ProductSearchListFragment> fragments = new ArrayList<ProductSearchListFragment>();
        fragments.add(ProductSearchListNongjiFragment.newInstance(this));
        fragments.add(ProductSearchListNongyaoFragment.newInstance(this));
        fragments.add(ProductSearchListZhongziFragment.newInstance(this));
        fragments.add(ProductSearchListHuafeiFragment.newInstance(this));
        return fragments;
    }

    public static void startActivity(Activity act) {

        Intent intent = new Intent(act, ProductSearchActivity.class);
        act.startActivity(intent);
    }

}
