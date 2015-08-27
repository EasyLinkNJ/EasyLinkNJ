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
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjFragmentActivity;
import com.easylink.nj.bean.product.BrandNongji;
import com.easylink.nj.bean.product.CategoryNongji;
import com.easylink.nj.httptask.NjHttpUtil;
import com.easylink.nj.httptask.NjJsonListener;

import java.util.List;

/**
 * Created by yihaibin on 15/8/25.
 */
public class ProductListNongjiActivity extends NjFragmentActivity implements OnItemViewClickListener, View.OnClickListener{

    private LinearLayout mLlToolbar;
    private View mVToolbarLine;
    private TextView mTvBrand, mTvCategory;
    private PopupWindow mPwLoading;
    private ProductListNongjiPop mPwList;
    private View mVLoadingShadow;
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
        mVLoadingShadow = findViewById(R.id.vLoadingShadow);
        mTvBrand = (TextView) findViewById(R.id.tvBrand);
        mTvBrand.setOnClickListener(this);

        mTvCategory = (TextView) findViewById(R.id.tvCategory);
        mTvCategory.setOnClickListener(this);
    }

    @Override
    public void onItemViewClick(int position, View clickView) {

        ProductDetailActivity.startActivityFromNJ(this, "1"/*product.getId()*/, false);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tvBrand:
                showBrandList();
                break;
            case R.id.tvCategory:
                showCategoryList();
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

    private void showBrandList(){

        if(mBrandList == null || mBrandList.isEmpty()){

            executeBrandsHttpTask();
        }else{

            initPopList();
            mPwList.updateBrand(mBrandList);
            mPwList.showAsDropDown(mVToolbarLine);
            ViewUtil.showView(mVLoadingShadow);
        }
    }

    private void showCategoryList(){

        if(mCategoryList == null || mCategoryList.isEmpty()){

            executeCategoryHttpTask();
        }else{

            initPopList();
            mPwList.updateCategory(mCategoryList);
            mPwList.showAsDropDown(mVToolbarLine);
            ViewUtil.showView(mVLoadingShadow);
        }
    }

    private void executeBrandsHttpTask(){

        executeHttpTask(1, NjHttpUtil.getBrandNongjiList(), new NjJsonListener<List<BrandNongji>>(BrandNongji.class) {

            @Override
            public void onTaskPre() {

                showLoadingPopup(mLlToolbar);
                ViewUtil.showView(mVLoadingShadow);
            }

            @Override
            public void onTaskResult(List<BrandNongji> result) {

                dismissLoadingPop();
                mBrandList = result;
                showBrandList();
            }

            @Override
            public void onTaskFailed(int failedCode, String msg) {

                dismissLoadingPop();
            }
        });
    }

    private void executeCategoryHttpTask(){

        executeHttpTask(1, NjHttpUtil.getCategoryNongjiList(), new NjJsonListener<List<CategoryNongji>>(CategoryNongji.class) {

            @Override
            public void onTaskPre() {

                showLoadingPopup(mLlToolbar);
                ViewUtil.showView(mVLoadingShadow);
            }

            @Override
            public void onTaskResult(List<CategoryNongji> result) {

                dismissLoadingPop();
                mCategoryList = result;
                showCategoryList();
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
        showView(mVLoadingShadow);
    }

    private void initLoadingPopup() {

        View view = getLayoutInflater().inflate((R.layout.pop_product_nongji_list_loading), null);
        mPwLoading = new PopupWindow(view, -1, 100) {
            @Override
            public void dismiss() {
                super.dismiss();
//                abortHttpTask(HTTPTASK_WHAT_LOAD_CATEGORY_LIST);
                hideView(mVLoadingShadow);
//                mDealTitleSelectBar.clearTitleTvColor();
            }

        };
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

                    if(mPwList.getType() == ProductListNongjiPop.TYPE_BRAND)
                        onBrandListItemClick(position);
                    else if(mPwList.getType() == ProductListNongjiPop.TYPE_CATEGORY)
                        onCategoryListItemClick(position);
                }
            });
            mPwList.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                    ViewUtil.hideView(mVLoadingShadow);
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
