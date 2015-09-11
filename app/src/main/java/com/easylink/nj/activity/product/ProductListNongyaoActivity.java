package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.easylink.library.util.DeviceUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjFragmentActivity;
import com.easylink.nj.activity.product.search.brand.BrandSearchActivity;
import com.easylink.nj.activity.product.search.category.CategorySearchActivity;
import com.easylink.nj.bean.product.BrandNongyao;
import com.easylink.nj.bean.product.CategoryNongyao;
import com.easylink.nj.httptask.NjHttpUtil;
import com.easylink.nj.httptask.NjJsonListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yihaibin on 15/8/25.
 */
public class ProductListNongyaoActivity extends NjFragmentActivity implements View.OnClickListener{

    private final int HTTP_TASK_BRAND = 1;
    private final int HTTP_TASK_CATEGORY = 2;

    private final int REQ_CODE_BRAND = 1;
    private final int REQ_CODE_CATEGORY = 2;

    private LinearLayout mLlToolbar;
    private View mVToolbarLine;
    private TextView mTvBrand, mTvCategory;
    private PopupWindow mPwLoading;
    private ProductListNongyaoPop mPwList;
    //    private View mVLoadingShadow;
    private List<BrandNongyao> mBrandList;
    private List<CategoryNongyao> mCategoryList;
    private ProductListNongyaoFragment mHuafeiListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_product_nongji_list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK)
            return;

        if(requestCode == REQ_CODE_BRAND){

            handleBrandReturn(data);
        }else if(requestCode == REQ_CODE_CATEGORY){

            handleCategoryReturn(data);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("农药列表");
    }

    @Override
    protected void initContentView() {

        initTabViews();
        mHuafeiListFragment = ProductListNongyaoFragment.newInstance(this);
        addFragment(R.id.flContent, mHuafeiListFragment);
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
//                showBrandList(true);
                BrandSearchActivity.startActivityNY(this, REQ_CODE_BRAND);
                break;
            case R.id.tvCategory:
//                showCategoryList(true);
                CategorySearchActivity.startActivityNY(this, REQ_CODE_CATEGORY);
                break;
        }
    }

    private void handleBrandReturn(Intent data) {

        if(data == null)
            return;

        String brandId = data.getStringExtra(BrandSearchActivity.EXTRA_STRING_ID);
        String brandName = data.getStringExtra(BrandSearchActivity.EXTRA_STRING_NAME);
        mHuafeiListFragment.updateListByBrand(brandId);
        mTvBrand.setText(brandName);
    }

    private void handleCategoryReturn(Intent data) {

        if(data == null)
            return;

        String categoryId = data.getStringExtra(CategorySearchActivity.EXTRA_STRING_ID);
        String categoryName = data.getStringExtra(CategorySearchActivity.EXTRA_STRING_NAME);
        mHuafeiListFragment.updateListByCategory(categoryId);
        mTvCategory.setText(categoryName);
    }

    private void onBrandListItemClick(int position) {

        BrandNongyao brand = (BrandNongyao) mPwList.getItemObject(position);
        dismissPopList();
        mHuafeiListFragment.updateListByBrand(brand.getId());
        mTvBrand.setText(brand.getCn_brand());
        mPwList.onItemClick(position);
    }

    private void onCategoryListItemClick(int position) {

        CategoryNongyao category = (CategoryNongyao) mPwList.getItemObject(position);
        dismissPopList();
        mHuafeiListFragment.updateListByCategory(category.getId());
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
        executeHttpTask(HTTP_TASK_BRAND, NjHttpUtil.getBrandNongyaoList(), new NjJsonListener<List<BrandNongyao>>(BrandNongyao.class) {

            @Override
            public void onTaskPre() {

                showLoadingPopup(mLlToolbar);
//                ViewUtil.showView(mVLoadingShadow);
            }

            @Override
            public void onTaskResult(List<BrandNongyao> result) {

                dismissLoadingPop();
                mBrandList = result;
                if(mBrandList == null)
                    mBrandList = new ArrayList<BrandNongyao>();

                BrandNongyao bnj = new BrandNongyao();
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
        executeHttpTask(HTTP_TASK_CATEGORY, NjHttpUtil.getCategoryNongyaoList(), new NjJsonListener<List<CategoryNongyao>>(CategoryNongyao.class) {

            @Override
            public void onTaskPre() {

                showLoadingPopup(mLlToolbar);
//                ViewUtil.showView(mVLoadingShadow);
            }

            @Override
            public void onTaskResult(List<CategoryNongyao> result) {

                dismissLoadingPop();
                mCategoryList = result;
                if (mCategoryList == null)
                    mCategoryList = new ArrayList<CategoryNongyao>();

                CategoryNongyao cnj = new CategoryNongyao();
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

            mPwList = new ProductListNongyaoPop(this);
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
        intent.setClass(activity, ProductListNongyaoActivity.class);
        activity.startActivity(intent);
    }
}
