package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.util.TextUtil;
import com.easylink.nj.bean.product.ProductItem;
import com.easylink.nj.bean.product.ProductZhongzi;
import com.easylink.nj.bean.product.ProductZhongziList;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.List;

/**
 * Created by yihaibin on 15/8/25.
 */
public class ProductListZhongziActivity extends ProductListActivity<ProductZhongziList> implements OnItemViewClickListener{

    @Override
    public HttpTaskParams getXlvHttpTaskParam(int page, int limit) {

        return NjHttpUtil.getProductZhongziList(TextUtil.filterNull(getIntent().getStringExtra("companyId")), page, limit);
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

        ProductItem item = getProductItem(position);

        if (item != null) {

            ProductZhongzi zz = (ProductZhongzi) item;
            ProductDetailActivity.startActivityFromZZ(this, zz.getId(), false);
        }
    }

    public static void startActivity(Activity activity, String companyId){

        Intent intent = new Intent();
        intent.setClass(activity, ProductListZhongziActivity.class);
        intent.putExtra("companyId", companyId);
        activity.startActivity(intent);
    }
}
