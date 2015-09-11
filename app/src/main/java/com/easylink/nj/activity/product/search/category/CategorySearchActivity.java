package com.easylink.nj.activity.product.search.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.util.DensityUtil;
import com.easylink.library.util.SoftInputHandler;
import com.easylink.library.util.TextUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpXlvActivity;
import com.easylink.nj.adapter.product.CategoryAdapter;
import com.easylink.nj.bean.product.CategoryHuafei;
import com.easylink.nj.bean.product.CategoryItem;
import com.easylink.nj.bean.product.CategoryNongji;
import com.easylink.nj.bean.product.CategoryNongyao;
import com.easylink.nj.bean.product.CategoryZhongzi;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yihaibin on 15/9/9.
 */
public class CategorySearchActivity extends NjHttpXlvActivity<List<CategoryItem>> implements OnItemViewClickListener, TextWatcher{

    public static final String EXTRA_STRING_ID = "categoryId";
    public static final String EXTRA_STRING_NAME = "categoryName";

    private EditText mEt;
    private SoftInputHandler mInputHandler;
    private CategoryAdapter mCategoryAdapter;
    private List<CategoryItem> mAllData;
    private String mCurrentSearchKey;
    private boolean mDataPrepared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        loadDataFromServer();
    }

    @Override
    protected void initData() {

        super.initData();
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
        mEt.setHint("请输入类型关键词");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(36));
        lp.rightMargin = DensityUtil.dip2px(12);
        addTitleMiddleView(mEt, lp);
        mEt.addTextChangedListener(this);
        mEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

//                    onSearchClick(v.getText().toString());
                    return true;
                }

                return false;
            }
        });

        mEt.requestFocus();
    }

    @Override
    protected void initContentView() {

        super.initContentView();
        setLimitSize(1000);//无分页，设置分页数无限大
        setPullEnable(false);
        setLoadmoreEnable(false);
    }

    @Override
    public ExAdapter getAdapterOnInitData() {

        mCategoryAdapter = new CategoryAdapter();
        mCategoryAdapter.setOnItemViewClickListener(this);
        return mCategoryAdapter;
    }

    @Override
    public HttpTaskParams getXlvHttpTaskParam(int page, int limit) {

        int type = getIntent().getIntExtra("type", 1);

        switch(type){
            case 2:
                return NjHttpUtil.getCategoryNongyaoList();
            case 3:
                return NjHttpUtil.getCategoryZhongziList();
            case 4:
                return NjHttpUtil.getCategoryHuafeiList();
            case 1:
            default:
                return NjHttpUtil.getCategoryNongjiList();
        }
    }

    @Override
    public Class<?> getXlvJsonClazz() {

        int type = getIntent().getIntExtra("type", 1);

        switch(type){
            case 2:
                return CategoryNongyao.class;
            case 3:
                return CategoryZhongzi.class;
            case 4:
                return CategoryHuafei.class;
            case 1:
            default:
                return CategoryNongji.class;
        }
    }

    @Override
    public boolean invalidateContent(int what, List<CategoryItem> data) {

        boolean result = super.invalidateContent(what, data);
        if(result){

            mAllData = data;
            mDataPrepared = true;
            if(!TextUtil.isEmpty(mCurrentSearchKey)){

                invalidateDataBySearchKey(mCurrentSearchKey);
            }
        }
        return result;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        mCurrentSearchKey = s.toString();
        if(mDataPrepared)
            invalidateDataBySearchKey(mCurrentSearchKey);
    }

    private void invalidateDataBySearchKey(String searchKey){

        if(TextUtil.isEmpty(searchKey)){

            mCategoryAdapter.setData(mAllData);
            mCategoryAdapter.notifyDataSetChanged();
        }else{

            List<CategoryItem> items = getBrandsBySearchKey(searchKey);
            mCategoryAdapter.setData(items);
            mCategoryAdapter.notifyDataSetChanged();
        }
    }

    private List<CategoryItem> getBrandsBySearchKey(String searchKey){

        ArrayList<CategoryItem> items = new ArrayList<CategoryItem>();
        CategoryItem item = null;
        for(int i=0; i<mCategoryAdapter.getCount(); i++){

            item = mCategoryAdapter.getItem(i);
            if(item.getName().contains(searchKey)){

                items.add(item);
            }
        }
        return items;
    }

    @Override
    public void onItemViewClick(final int position, View clickView) {

        mInputHandler.hideSoftInputPost(mEt, new Runnable() {
            @Override
            public void run() {

                CategoryItem item = mCategoryAdapter.getItem(position);
                if(item != null){

                    finishForResult(item);
                }
            }
        });
    }

    private void finishForResult(CategoryItem item){

        Intent intent = new Intent();
        intent.putExtra(EXTRA_STRING_ID, item.getId());
        intent.putExtra(EXTRA_STRING_NAME, item.getName());
        setResult(RESULT_OK, intent);
        finish();
    }

    public static void startActivityNJ(Activity activity, int request){

        startActivity(activity, request, 1);
    }

    public static void startActivityNY(Activity activity, int request){

        startActivity(activity, request, 2);
    }

    public static void startActivityZZ(Activity activity, int request){

        startActivity(activity, request, 3);
    }

    public static void startActivityHF(Activity activity, int request){

        startActivity(activity, request, 4);
    }

    private static void startActivity(Activity activity, int request, int type){

        Intent intent = new Intent();
        intent.setClass(activity, CategorySearchActivity.class);
        intent.putExtra("type", type);
        activity.startActivityForResult(intent, request);
    }
}
