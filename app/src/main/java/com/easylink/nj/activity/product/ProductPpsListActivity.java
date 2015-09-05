package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.easylink.library.adapter.OnItemChildViewClickListener;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.adapter.product.ProductPpsAdapter;
import com.easylink.nj.bean.product.Pps;
import com.easylink.nj.bean.product.ProductPps;
import com.easylink.nj.httptask.NjHttpUtil;

import java.util.List;

/**
 * 产品配件分类列表，目前只有农机在使用
 * Created by yihaibin on 15/9/5.
 */
public class ProductPpsListActivity extends NjHttpActivity<List<ProductPps>>{

    private ExpandableListView mElv;
    private ProductPpsAdapter ppa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(ViewUtil.getCleanExpandListView(this, R.id.expandlv));
        loadDataFromServer();
    }

    @Override
    protected void initData() {

        ppa = new ProductPpsAdapter();
        ppa.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {

            @Override
            public void onChildViewClick(int groupPos, int childPos, View view) {

                Pps pps = ppa.getChild(groupPos, childPos);
                if (pps == null)
                    return;

                ProductDetailActivity.startActivityFromNJParts(ProductPpsListActivity.this, pps.getId(), false);
            }
        });
    }

    @Override
    protected void initTitleView() {

        addTitleMiddleTextViewWithBack("配件列表");
    }

    @Override
    protected void initContentView() {

        mElv = (ExpandableListView) findViewById(R.id.expandlv);
        mElv.setDivider(new ColorDrawable(getResources().getColor(R.color.list_split)));
        mElv.setChildDivider(new ColorDrawable(getResources().getColor(R.color.list_split)));
        mElv.setDividerHeight(2);//2px
        mElv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return true;
            }
        });
        mElv.setAdapter(ppa);
    }

    private void loadDataFromServer() {

        executeHttpTaskByUiSwitch(0, NjHttpUtil.getProductPps(getIntent().getStringExtra("productId")), ProductPps.class);
    }

    @Override
    public boolean invalidateContent(int what, List<ProductPps> result) {

        ppa.setData(result);
        ppa.notifyDataSetChanged();
        expandListViewIfNotEmpty();
        return !ppa.isEmpty();
    }

    private void expandListViewIfNotEmpty() {

        if(ppa.isEmpty())
            return;

        for(int i=0; i<ppa.getGroupCount(); i++){

            mElv.expandGroup(i);
        }
    }

    public static void startActivity(Activity activity, String productId){

        Intent intent = new Intent();
        intent.setClass(activity, ProductPpsListActivity.class);
        intent.putExtra("productId", productId);
        activity.startActivity(intent);
    }
}
