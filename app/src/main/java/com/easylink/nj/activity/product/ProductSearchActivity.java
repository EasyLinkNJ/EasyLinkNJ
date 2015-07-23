package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.easylink.library.adapter.ExAdapter;
import com.easylink.library.adapter.OnItemViewClickListener;
import com.easylink.library.http.params.HttpTaskParams;
import com.easylink.library.util.DensityUtil;
import com.easylink.library.util.SoftInputHandler;
import com.easylink.library.util.TextUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpXlvActivity;
import com.easylink.nj.adapter.ProductListAdapter;
import com.easylink.nj.bean.product.Product;
import com.easylink.nj.bean.product.ProductList;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.List;

/**
 * Created by yihaibin on 15/7/23.
 */
public class ProductSearchActivity extends NjHttpXlvActivity<ProductList>{

    private EditText mEt;
    private ProductListAdapter adapter;
    private SoftInputHandler mInputHandler;

    @Override
    protected void initData() {

        super.initData();
        mInputHandler = new SoftInputHandler(this);
    }

    @Override
    public ExAdapter getAdapter() {

        adapter = new ProductListAdapter();
        adapter.setOnItemViewClickListener(new OnItemViewClickListener() {

            @Override
            public void onItemViewClick(int position, View clickView) {

                Product product = adapter.getItem(position);
                if (product != null)
                    ProductDetailActivity.startActivity(ProductSearchActivity.this, product.getId(), false);
            }
        });

        return adapter;
    }

    @Override
    protected void initTitleView() {

        addTitleLeftBackView(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mInputHandler.finishActivityBySoftInput(mEt);
            }
        });

        mEt = (EditText) getLayoutInflater().inflate(R.layout.view_search, null);
        mEt.setHint("请输入设备名称");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(36));
        lp.rightMargin = DensityUtil.dip2px(12);
        addTitleMiddleView(mEt, lp);

        mEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String content = s.toString();
                abortAllHttpTask();
                if(TextUtil.isEmptyTrim(content)){

                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    switchInvisible();
                }else{

                    loadDataFromServer();
                }
            }
        });
    }

    @Override
    public HttpTaskParams getXlvHttpTaskParam(int page, int limit) {

        return NjHttpUtil.getProductListByKey(page, limit, mEt.getText().toString());
    }

    @Override
    public Class<?> getXlvJsonClazz() {

        return ProductList.class;
    }

    @Override
    protected List<?> getListOnInvalidateContent(ProductList result) {

        return result == null ? null : result.getList();
    }

    public static void startActivity(Activity act) {

        Intent intent = new Intent(act, ProductSearchActivity.class);
        act.startActivity(intent);
    }
}
