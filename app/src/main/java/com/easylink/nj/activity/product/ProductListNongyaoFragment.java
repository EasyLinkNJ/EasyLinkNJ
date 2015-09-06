package com.easylink.nj.activity.product;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.util.TextUtil;
import com.easylink.nj.bean.product.ProductNongyao;
import com.easylink.nj.bean.product.ProductNongyaoList;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.List;

/**
 * Created by yihaibin on 15/8/25.
 */
public class ProductListNongyaoFragment extends ProductListFragment<ProductNongyaoList> implements OnItemViewClickListener{

    private String mBrand = TextUtil.TEXT_EMPTY;//品牌
    private String mCategory = TextUtil.TEXT_EMPTY;//名称

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        loadDataFromServer();
    }

    @Override
    public HttpTaskParams getXlvHttpTaskParam(int page, int limit) {

        return NjHttpUtil.getProductNongyaoList(mBrand, mCategory, page, limit);
    }

    @Override
    public Class<?> getXlvJsonClazz() {

        return ProductNongyaoList.class;
    }

    @Override
    protected List<?> getListOnInvalidateContent(ProductNongyaoList result) {

        return result == null ? null : result.getList();
    }

    @Override
    public void onItemViewClick(int position, View clickView) {

        ProductNongyao pn = (ProductNongyao) getAdapterItem(position);
        if(pn != null)
            ProductDetailActivity.startActivityFromNY(getActivity(), pn.getId(), false);
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

    public static ProductListNongyaoFragment newInstance(Context context){

        return (ProductListNongyaoFragment) Fragment.instantiate(context, ProductListNongyaoFragment.class.getName(), new Bundle());
    }
}
