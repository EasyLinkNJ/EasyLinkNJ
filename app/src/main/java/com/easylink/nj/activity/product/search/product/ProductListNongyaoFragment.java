package com.easylink.nj.activity.product.search.product;

import android.view.View;

import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.util.TextUtil;
import com.easylink.nj.activity.product.ProductDetailActivity;
import com.easylink.nj.activity.product.ProductListFragment;
import com.easylink.nj.bean.product.ProductHuafei;
import com.easylink.nj.bean.product.ProductHuafeiList;
import com.easylink.nj.bean.product.ProductItem;
import com.easylink.nj.bean.product.ProductNongyao;
import com.easylink.nj.bean.product.ProductNongyaoList;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.List;

/**
 * Created by yihaibin on 15/8/29.
 */
@Deprecated
public class ProductListNongyaoFragment  extends ProductListFragment<ProductNongyaoList> {

    @Override
    public HttpTaskParams getXlvHttpTaskParam(int page, int limit) {

        return NjHttpUtil.getProductNongyaoList(TextUtil.filterNull(getActivity().getIntent().getStringExtra("companyId")), page, limit);
    }

    @Override
    public Class<?> getXlvJsonClazz() {

        return ProductHuafeiList.class;
    }

    @Override
    protected List<?> getListOnInvalidateContent(ProductNongyaoList result) {

        return result == null ? null : result.getList();
    }

    @Override
    public void onItemViewClick(int position, View clickView) {

        ProductItem item = getProductItem(position);

        if (item != null) {

            ProductNongyao ny = (ProductNongyao) item;
            ProductDetailActivity.startActivityFromHF(getActivity(), ny.getId(), false);
        }
    }
}
