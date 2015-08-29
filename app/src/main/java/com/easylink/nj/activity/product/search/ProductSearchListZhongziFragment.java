package com.easylink.nj.activity.product.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.util.TextUtil;
import com.easylink.nj.activity.product.ProductDetailActivity;
import com.easylink.nj.bean.product.ProductNongji;
import com.easylink.nj.bean.product.ProductNongjiList;
import com.easylink.nj.bean.product.ProductZhongzi;
import com.easylink.nj.bean.product.ProductZhongziList;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.List;

/**
 * Created by yihaibin on 15/8/29.
 */
public class ProductSearchListZhongziFragment extends ProductSearchListFragment<ProductZhongziList> implements OnItemViewClickListener {

    private String mSearchKey = TextUtil.TEXT_EMPTY;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public HttpTaskParams getXlvHttpTaskParam(int page, int limit) {

        return NjHttpUtil.getProductZhongziSearchList(mSearchKey, page, limit);
    }

    @Override
    public Class<?> getXlvJsonClazz() {

        return ProductZhongziList.class;
    }

    @Override
    protected List<?> getListOnInvalidateContent(ProductZhongziList result) {

        return result == null ? null : result.getList();
    }

    @Override
    public void onItemViewClick(int position, View clickView) {

        ProductZhongzi nj = (ProductZhongzi) getAdapterItem(position);
        if (nj != null)
            ProductDetailActivity.startActivityFromZZ(getActivity(), nj.getId(), false);
    }

    @Override
    public void loadDataFromServerBySearch(String searchKey) {

        if(!isAdded())
            return;

        mSearchKey = searchKey;
        loadDataFromServer();
    }

    public static ProductSearchListZhongziFragment newInstance(Context context) {

        return (ProductSearchListZhongziFragment) Fragment.instantiate(context, ProductSearchListZhongziFragment.class.getName(), new Bundle());
    }
}
