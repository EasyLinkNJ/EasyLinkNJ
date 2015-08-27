package com.easylink.nj.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.easylink.library.activity.ExFragmentActivity;
import com.easylink.library.plugin.DelayBackHandler;
import com.easylink.library.util.DensityUtil;
import com.easylink.library.util.ViewUtil;
import com.easylink.nj.EasyApplication;
import com.easylink.nj.R;
import com.easylink.nj.activity.product.ProductSearchActivity;
import com.easylink.nj.httptask.NjHttpUtil;
import com.easylink.nj.httptask.NjJsonListener;
import com.easylink.nj.httptask.NjJsonResponse;
import com.easylink.nj.utils.DBManager;

import org.json.JSONObject;

/**
 * Created by KEVIN.DAI on 15/7/8.
 */
public class MainActivity extends ExFragmentActivity implements View.OnClickListener, DelayBackHandler.OnDelayBackListener {

    private DelayBackHandler mBackKeyHandler;
    private View mTvCurrentSelected;
    private TextView mTvCartCount;
    private ImageView mIvSearch;
    private Fragment mHomeFragment, mCartFragment, mMineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        if (!EasyApplication.getCommonPrefs().hasUserToken())
            executeLogin();
    }

    @Override
    protected void onResume() {

        super.onResume();
        updateActivityCart();
    }

    public void updateActivityCart() {

        int count = DBManager.getInstance().getCartCount();
        if (mTvCartCount != null) {

            mTvCartCount.setText(String.valueOf(count));
            mTvCartCount.setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    protected void initData() {

        mBackKeyHandler = new DelayBackHandler();
        mBackKeyHandler.setOnDelayBackListener(this);
    }

    @Override
    protected void initTitleView() {

        ImageView iv = new ImageView(this);
        iv.setImageResource(R.mipmap.ic_logo);
        LayoutParams lp1 = new LayoutParams(DensityUtil.dip2px(53), DensityUtil.dip2px(25));
        lp1.leftMargin = DensityUtil.dip2px(16);
        addTitleLeftView(iv, lp1);

        mIvSearch = addTitleRightImageView(R.mipmap.ic_search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductSearchActivity.startActivity(MainActivity.this);
            }
        });
        ViewUtil.hideImageView(mIvSearch);
    }

    @Override
    protected void initContentView() {

        findViewById(R.id.rlCart).setOnClickListener(this);

        mTvCartCount = (TextView) findViewById(R.id.tvCount);
        updateActivityCart();

        findViewById(R.id.tvMine).setOnClickListener(this);

        TextView tv = (TextView) findViewById(R.id.tvHome);
        tv.setOnClickListener(this);
        tv.performClick();
    }

    private void executeLogin() {

        executeHttpTask(0, NjHttpUtil.getLoginParams(), new NjJsonListener<Object>(Object.class) {

            @Override
            public NjJsonResponse<Object> onTaskResponse(String jsonText) {

                NjJsonResponse<Object> resp = new NjJsonResponse<>();
                if (TextUtils.isEmpty(jsonText))
                    return resp;

                try {
                    JSONObject jsonObject = new JSONObject(jsonText);
                    jsonObject = jsonObject.getJSONObject("data");
                    String onlinekey = jsonObject.getString("onlinekey");// AVD: be4786ff2a20d583a5bb49da7ae7e918
                    EasyApplication.getCommonPrefs().setUserToken(onlinekey);

                } catch (Exception e) {

                    e.printStackTrace();
                }
                return resp;
            }

            @Override
            public void onTaskResult(Object result) {
            }

            @Override
            public void onTaskFailed(int failedCode, String msg) {
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (mTvCurrentSelected == v)
            return;

        switch (v.getId()) {
            case R.id.tvHome:
                if (mHomeFragment == null) {

                    mHomeFragment = HomeFragment.newInstance(this);
                    addFragment(R.id.flContent, mHomeFragment);
                }
                switchFragment(mHomeFragment);
                ViewUtil.showImageView(mIvSearch, R.mipmap.ic_search);
                break;
            case R.id.rlCart:
                if (mCartFragment == null) {

                    mCartFragment = CartListFragment.newInstance(this);
                    addFragment(R.id.flContent, mCartFragment);
                }
                switchFragment(mCartFragment);
                ViewUtil.hideImageView(mIvSearch);
                break;
            case R.id.tvMine:
                if (mMineFragment == null) {

                    mMineFragment = MainMineFragment.newInstance(this);
                    addFragment(R.id.flContent, mMineFragment);
                }
                switchFragment(mMineFragment);
                ViewUtil.hideImageView(mIvSearch);
                break;
        }

        if (mTvCurrentSelected != null)
            mTvCurrentSelected.setSelected(false);

        mTvCurrentSelected = v;
        mTvCurrentSelected.setSelected(true);
    }

    @Override
    public void onBackPressed() {

        mBackKeyHandler.triggerPreBack();
    }

    @Override
    public void onDelayBack(boolean preBack) {

        if (preBack) {

            showToast(R.string.toast_exit_tip);
        } else {

            super.finish();
        }
    }

    public static void startActivity(Activity activity) {

        Intent intent = new Intent();
        intent.setClass(activity, MainActivity.class);
        activity.startActivity(intent);
    }
}
