package com.easylink.nj.activity.product;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.util.TextUtil;
import com.easylink.nj.bean.product.ProductNongji;
import com.easylink.nj.bean.product.ProductNongjiList;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.List;

/**
 * Created by yihaibin on 15/8/25.
 */
public class ProductListNongjiFragment extends ProductListFragment<ProductNongjiList> implements OnItemViewClickListener{

    private String mBrand = TextUtil.TEXT_EMPTY;//品牌
    private String mCategory = TextUtil.TEXT_EMPTY;//名称

    @Override
    public HttpTaskParams getXlvHttpTaskParam(int page, int limit) {

        return NjHttpUtil.getProductNongjiList(mBrand, mCategory, page, limit);
    }

    @Override
    public Class<?> getXlvJsonClazz() {

        return ProductNongjiList.class;
    }

    @Override
    protected List<?> getListOnInvalidateContent(ProductNongjiList result) {

        return result == null ? null : result.getList();
    }

    @Override
    public void onItemViewClick(int position, View clickView) {

        ProductNongji nj = (ProductNongji) getAdapterItem(position);
        ProductDetailActivity.startActivityFromNJ(getActivity(), nj.getId(), false);
    }

    public void updateListByBrand(String brand){

        if(isAdded()){

            mBrand = TextUtil.filterNull(brand);
            abortAllHttpTask();
            loadDataFromServer();
        }
    }

    public void updateListByCategory(String category){

        if(isAdded()){

            mCategory = TextUtil.filterNull(category);
            abortAllHttpTask();
            loadDataFromServer();
        }
    }

    public static ProductListNongjiFragment newInstance(Context context){

        return (ProductListNongjiFragment) Fragment.instantiate(context, ProductListNongjiFragment.class.getName(), new Bundle());
    }
}
