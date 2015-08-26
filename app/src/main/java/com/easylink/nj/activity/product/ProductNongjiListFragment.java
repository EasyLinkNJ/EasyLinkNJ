package com.easylink.nj.activity.product;

import android.view.View;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.nj.bean.product.ProductList;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.List;

/**
 * Created by yihaibin on 15/8/25.
 */
public class ProductNongjiListFragment extends ProductListFragment<ProductList> implements OnItemViewClickListener{

    @Override
    public HttpTaskParams getXlvHttpTaskParam(int page, int limit) {

        return NjHttpUtil.getProductListByCateId(page, limit, "13");
    }

    @Override
    public Class<?> getXlvJsonClazz() {

        return ProductList.class;
    }

    @Override
    protected List<?> getListOnInvalidateContent(ProductList result) {

        return result == null ? null : result.getList();
    }

    @Override
    public void onItemViewClick(int position, View clickView) {


    }
}
