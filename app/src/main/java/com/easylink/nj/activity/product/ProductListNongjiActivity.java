package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.util.DeviceUtil;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjFragmentActivity;
import com.easylink.nj.bean.product.BrandNongji;
import com.easylink.nj.bean.product.CategoryNongji;
import com.easylink.nj.httptask.NjHttpUtil;
import com.easylink.nj.httptask.NjJsonListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yihaibin on 15/8/25.
 */
public class ProductListNongjiActivity extends NjFragmentActivity implements View.OnClickListener{

    private final int HTTP_TASK_BRAND = 1;
    private final int HTTP_TASK_CATEGORY = 2;

    private LinearLayout mLlToolbar;
    private View mVToolbarLine;
    private TextView mTvBrand, mTvCategory;
    private PopupWindow mPwLoading;
    private ProductListNongjiPop mPwList;
//    private View mVLoadingShadow;
    private List<BrandNongji> mBrandList;
    private List<CategoryNongji> mCategoryList;
    private ProductListNongjiFragment mNongjiListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_product_nongji_list);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("农业机械");
    }

    @Override
    protected void initContentView() {

        initTabViews();
        mNongjiListFragment = ProductListNongjiFragment.newInstance(this);
        addFragment(R.id.flContent, mNongjiListFragment);
    }

    private void initTabViews() {

        mLlToolbar = (LinearLayout) findViewById(R.id.llToolbar);
        mVToolbarLine = findViewById(R.id.vToolbarLine);
//        mVLoadingShadow = findViewById(R.id.vLoadingShadow);
        mTvBrand = (TextView) findViewById(R.id.tvBrand);
        mTvBrand.setOnClickListener(this);

        mTvCategory = (TextView) findViewById(R.id.tvCategory);
        mTvCategory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tvBrand:
                showBrandList(true);
                break;
            case R.id.tvCategory:
                showCategoryList(true);
                break;
        }
    }

    private void onBrandListItemClick(int position) {

        BrandNongji brand = (BrandNongji) mPwList.getItemObject(position);
        dismissPopList();
        mNongjiListFragment.updateListByBrand(brand.getId());
        mTvBrand.setText(brand.getCn_brand());
        mPwList.onItemClick(position);
    }

    private void onCategoryListItemClick(int position) {

        CategoryNongji category = (CategoryNongji) mPwList.getItemObject(position);
        dismissPopList();
        mNongjiListFragment.updateListByCategory(category.getId());
        mTvCategory.setText(category.getName());
        mPwList.onItemClick(position);
    }

    private void showBrandList(boolean fromClick){

        if(mBrandList == null || mBrandList.isEmpty()){

            if(DeviceUtil.isNetworkEnable()){

                if(fromClick)
                    executeBrandsHttpTask();
            }else{

                showToast(R.string.toast_network_none);
            }
        }else{

            initPopList();
            mPwList.updateBrand(mBrandList);
            mPwList.showAsDropDown(mVToolbarLine);
        }
    }

    private void showCategoryList(boolean fromClick){

        if(mCategoryList == null || mCategoryList.isEmpty()){

            if(DeviceUtil.isNetworkEnable()){

                if(fromClick)
                    executeCategoryHttpTask();
            }else{

                showToast(R.string.toast_network_none);
            }
        }else{

            initPopList();
            mPwList.updateCategory(mCategoryList);
            mPwList.showAsDropDown(mVToolbarLine);
        }
    }

    private void executeBrandsHttpTask(){

        abortHttpTask(HTTP_TASK_BRAND);
        executeHttpTask(HTTP_TASK_BRAND, NjHttpUtil.getBrandNongjiList(), new NjJsonListener<List<BrandNongji>>(BrandNongji.class) {

            @Override
            public void onTaskPre() {

                showLoadingPopup(mLlToolbar);
//                ViewUtil.showView(mVLoadingShadow);
            }

            @Override
            public void onTaskResult(List<BrandNongji> result) {

                dismissLoadingPop();
                mBrandList = result;
                if(mBrandList == null)
                    mBrandList = new ArrayList<BrandNongji>();

                BrandNongji bnj = new BrandNongji();
                bnj.setId("");
                bnj.setCn_brand("全部");
                mBrandList.add(0, bnj);

                showBrandList(false);
            }

            @Override
            public void onTaskFailed(int failedCode, String msg) {

                dismissLoadingPop();
            }
        });
    }

    private void executeCategoryHttpTask(){

        abortHttpTask(HTTP_TASK_CATEGORY);
        executeHttpTask(HTTP_TASK_CATEGORY, NjHttpUtil.getCategoryNongjiList(), new NjJsonListener<List<CategoryNongji>>(CategoryNongji.class) {

            @Override
            public void onTaskPre() {

                showLoadingPopup(mLlToolbar);
//                ViewUtil.showView(mVLoadingShadow);
            }

            @Override
            public void onTaskResult(List<CategoryNongji> result) {

                dismissLoadingPop();
                mCategoryList = result;
                if (mCategoryList == null)
                    mCategoryList = new ArrayList<CategoryNongji>();

                CategoryNongji cnj = new CategoryNongji();
                cnj.setId("");
                cnj.setName("全部");
                mCategoryList.add(0, cnj);

                showCategoryList(false);
            }

            @Override
            public void onTaskFailed(int failedCode, String msg) {

                dismissLoadingPop();
            }
        });
    }

    private void showLoadingPopup(View v) {

        if (mPwLoading == null)
            initLoadingPopup();

        mPwLoading.showAsDropDown(v);
//        showView(mVLoadingShadow);
    }

    private void initLoadingPopup() {

        View view = getLayoutInflater().inflate((R.layout.pop_product_nongji_list_loading), null);
        mPwLoading = new PopupWindow(view, -1, 100) {
            @Override
            public void dismiss() {

                super.dismiss();
                abortHttpTask(HTTP_TASK_BRAND);
                abortHttpTask(HTTP_TASK_CATEGORY);
//                hideView(mVLoadingShadow);
//                mDealTitleSelectBar.clearTitleTvColor();
            }

        };

        mPwLoading.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        mPwLoading.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        mPwLoading.setFocusable(true);
        mPwLoading.setOutsideTouchable(true);
        mPwLoading.update();
    }

    private void dismissLoadingPop(){

        if(mPwLoading != null && mPwLoading.isShowing())
            mPwLoading.dismiss();
    }

    private void initPopList(){

        if(mPwList == null){

            mPwList = new ProductListNongjiPop(this);
            mPwList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if(mPwList.getType() == ProductListNongjiPop.TYPE_BRAND){

                        onBrandListItemClick(position);
                    }else if(mPwList.getType() == ProductListNongjiPop.TYPE_CATEGORY){

                        onCategoryListItemClick(position);
                    }
                }
            });
            mPwList.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                }
            });
        }
    }

    private void dismissPopList(){

        if(mPwList != null && mPwList.isShowing())
            mPwList.dismiss();
    }

    public static void startActivity(Activity activity){

        Intent intent = new Intent();
        intent.setClass(activity, ProductListNongjiActivity.class);
        activity.startActivity(intent);
    }
}
