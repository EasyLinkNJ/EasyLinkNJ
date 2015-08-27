package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
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
public class ProductListNongyaoActivity extends ProductListActivity<ProductNongyaoList> implements OnItemViewClickListener{

    @Override
    public HttpTaskParams getXlvHttpTaskParam(int page, int limit) {

        return NjHttpUtil.getProductNongyaoList(TextUtil.filterNull(getIntent().getStringExtra("companyId")), page, limit);
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

        ProductNongyao ny = ((ProductNongyao) getAdapterItem(position));
        if(ny != null)
            ProductDetailActivity.startActivityFromNY(this, ny.getId(), false);
    }

    public static void startActivity(Activity activity, String companyId){

        Intent intent = new Intent();
        intent.setClass(activity, ProductListNongyaoActivity.class);
        intent.putExtra("companyId", companyId);
        activity.startActivity(intent);
    }
}
