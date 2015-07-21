package com.easylink.nj.activity.product;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.easylink.library.util.ViewUtil;
import com.easylink.nj.R;
import com.easylink.nj.activity.CartActivity;
import com.easylink.nj.activity.common.NjHttpActivity;
import com.easylink.nj.bean.db.Cart;
import com.easylink.nj.bean.product.ProductDetail;
import com.easylink.nj.httptask.NjHttpUtil;
import com.easylink.nj.utils.DBManager;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 产品详情
 * Created by KEVIN.DAI on 15/7/14.
 */
public class ProductDetailActivity extends NjHttpActivity<ProductDetail> {

    private ProductDetail mDetail;
    private TextView mTvCartCount;
    private int mCartCount;
    private boolean mOnlyGlance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_product_detail);
        loadDataFromServer();
    }

    @Override
    protected void onResume() {

        super.onResume();
        updateTitleBarCartCount();
    }

    private void updateTitleBarCartCount() {

        if (mTvCartCount != null) {

            mCartCount = DBManager.getInstance().getCartCount();
            mTvCartCount.setText(String.valueOf(mCartCount));
            mTvCartCount.setVisibility(mCartCount > 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    protected void initData() {

        mOnlyGlance = getIntent().getBooleanExtra("onlyGlance", false);
    }

    @Override
    protected void initTitleView() {

        // left view
        addTitleMiddleTextViewWithBack("产品详情");

        // right view
        if (!mOnlyGlance) {

            View vCart = ViewUtil.inflateLayout(R.layout.view_cart_titlebar);

            mTvCartCount = (TextView) vCart.findViewById(R.id.tvCount);
            updateTitleBarCartCount();

            LayoutParams lp = new LayoutParams(getTitleViewLayoutParams().height, LayoutParams.MATCH_PARENT);
            addTitleRightView(vCart, lp);
            vCart.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    CartActivity.startActivity(ProductDetailActivity.this);
                }
            });
        }
    }

    @Override
    protected void initContentView() {

        if (mOnlyGlance) {

            ViewUtil.goneView(findViewById(R.id.llBtnDiv));
        } else {

            findViewById(R.id.tvBuy).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            });
            findViewById(R.id.tvAdd).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    mCartCount++;
                    mTvCartCount.setText(String.valueOf(mCartCount));
                    ViewUtil.showView(mTvCartCount);

                    Cart cart = DBManager.getInstance().getCart(mDetail.getId());
                    if (cart == null) {//该产品在DB中不存在

                        cart = new Cart();
                        cart.productId = mDetail.getId();
                        cart.name = mDetail.getTitle();
                        cart.imgUrl = mDetail.getMainpic();
                        cart.price = mDetail.getPrice();
                        cart.count = mCartCount;
//                        cart.introTitle_0 = mDetail.getCt_0();
//                        cart.intro_0 = mDetail.getContent_0();
                        cart.save();
                    } else {//存在，更新时间和数量

                        cart.count = cart.count + 1;
                        cart.save();
                    }
                    showToast("已添加");
                }
            });
        }
    }

    @Override
    protected void onTipViewClick() {

        loadDataFromServer();
    }

    private void loadDataFromServer() {

        executeHttpTaskByUiSwitch(0, NjHttpUtil.getProductDetail(getIntent().getStringExtra("productId")), ProductDetail.class);
    }

    @Override
    public void invalidateContent(int what, ProductDetail data) {

        mDetail = data;

        SimpleDraweeView sdvCover = (SimpleDraweeView) findViewById(R.id.sdvCover);
        sdvCover.setImageURI(Uri.parse(data.getMainpic()));

        ((TextView) findViewById(R.id.tvTitle)).setText(data.getTitle());
        ((TextView) findViewById(R.id.tvPrice)).setText(data.getPrice());

        ((TextView) findViewById(R.id.tvIntroTitle)).setText(data.getCt_0());
        TextView tvIntro = (TextView) findViewById(R.id.tvIntro);
        tvIntro.setText(Html.fromHtml(data.getContent_0()));
    }

    public static void startActivity(Activity act, String productId, boolean onlyGlance) {

        if (act == null)
            return;
        Intent intent = new Intent(act, ProductDetailActivity.class);
        intent.putExtra("productId", productId);
        intent.putExtra("onlyGlance", onlyGlance);
        act.startActivity(intent);
    }
}
