package com.easylink.nj.activity.product.search.product;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.util.TextUtil;
import com.easylink.nj.activity.product.ProductDetailActivity;
import com.easylink.nj.bean.product.ProductNongji;
import com.easylink.nj.bean.product.ProductNongjiList;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.List;

/**
 * Created by yihaibin on 15/8/29.
 */
public class ProductSearchListNongjiFragment extends ProductSearchListFragment<ProductNongjiList> implements OnItemViewClickListener {

    private String mSearchKey = TextUtil.TEXT_EMPTY;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public HttpTaskParams getXlvHttpTaskParam(int page, int limit) {

        return NjHttpUtil.getProductNongjiSearchList(mSearchKey, page, limit);
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
        if (nj != null)
            ProductDetailActivity.startActivityFromNJ(getActivity(), nj.getId(), false);
    }

    @Override
    public void loadDataFromServerBySearch(String searchKey) {

        if(!isAdded())
            return;

        mSearchKey = searchKey;
        loadDataFromServer();
    }

    public static ProductSearchListNongjiFragment newInstance(Context context) {

        return (ProductSearchListNongjiFragment) instantiate(context, ProductSearchListNongjiFragment.class.getName(), new Bundle());
    }
}
