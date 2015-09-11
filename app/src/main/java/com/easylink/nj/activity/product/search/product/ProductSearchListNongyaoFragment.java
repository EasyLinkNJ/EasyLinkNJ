package com.easylink.nj.activity.product.search.product;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.util.TextUtil;
import com.easylink.nj.activity.product.ProductDetailActivity;
import com.easylink.nj.bean.product.ProductNongyao;
import com.easylink.nj.bean.product.ProductNongyaoList;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.List;

/**
 * Created by yihaibin on 15/8/29.
 */
public class ProductSearchListNongyaoFragment extends ProductSearchListFragment<ProductNongyaoList> implements OnItemViewClickListener {

    private String mSearchKey = TextUtil.TEXT_EMPTY;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public HttpTaskParams getXlvHttpTaskParam(int page, int limit) {

        return NjHttpUtil.getProductNongyaoSearchList(mSearchKey, page, limit);
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

        ProductNongyao ny = (ProductNongyao) getAdapterItem(position);
        if (ny != null)
            ProductDetailActivity.startActivityFromNY(getActivity(), ny.getId(), false);
    }

    @Override
    public void loadDataFromServerBySearch(String searchKey) {

        if(!isAdded())
            return;

        mSearchKey = searchKey;
        loadDataFromServer();
    }

    public static ProductSearchListNongyaoFragment newInstance(Context context) {

        return (ProductSearchListNongyaoFragment) instantiate(context, ProductSearchListNongyaoFragment.class.getName(), new Bundle());
    }
}
