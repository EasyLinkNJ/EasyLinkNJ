package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.util.TextUtil;
import com.easylink.nj.bean.product.ProductHuafei;
import com.easylink.nj.bean.product.ProductHuafeiList;
import com.easylink.nj.bean.product.ProductItem;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.List;

/**
 * Created by yihaibin on 15/8/25.
 */
public class ZProductListHuafei1Activity extends ProductListActivity<ProductHuafeiList> implements OnItemViewClickListener{

    @Override
    public HttpTaskParams getXlvHttpTaskParam(int page, int limit) {

        return NjHttpUtil.getProductHuafeiList(TextUtil.filterNull(getIntent().getStringExtra("companyId")), page, limit);
    }

    @Override
    public Class<?> getXlvJsonClazz() {

        return ProductHuafeiList.class;
    }

    @Override
    protected List<?> getListOnInvalidateContent(ProductHuafeiList result) {

        return result == null ? null : result.getList();
    }

    @Override
    public void onItemViewClick(int position, View clickView) {

        ProductItem item = getProductItem(position);

        if (item != null) {

            ProductHuafei hf = (ProductHuafei) item;
            ProductDetailActivity.startActivityFromHF(this, hf.getId(), false);
        }
    }

    public static void startActivity(Activity activity, String companyId){

        Intent intent = new Intent();
        intent.setClass(activity, ZProductListHuafei1Activity.class);
        intent.putExtra("companyId", companyId);
        activity.startActivity(intent);
    }
}
