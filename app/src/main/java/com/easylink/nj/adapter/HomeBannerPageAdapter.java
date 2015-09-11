package com.easylink.nj.adapter;

import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView.ScaleType;

import com.easylink.library.adapter.ExPagerAdapter;
import com.easylink.library.view.pageindicator.IconPagerAdapter;
import com.easylink.nj.R;
import com.easylink.nj.bean.Banner;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 运营banner 适配器
 *
 * @author pzwwei
 * @version 1.0
 * @since 2014-12-16
 */
public class HomeBannerPageAdapter extends ExPagerAdapter<Banner> implements IconPagerAdapter {

	//	private OnItemClick onItemClick;
//	private int mImageMaxPix;

	public HomeBannerPageAdapter(){

//		mImageMaxPix = imageMaxPix;
	}

//	public OnItemClick getOnItemClick() {
//
//		return onItemClick;
//	}
//
//	public void setOnItemClick(OnItemClick onItemClick) {
//
//		this.onItemClick = onItemClick;
//	}

	public interface OnItemClick{

		public void onItemClick(int position);
	}

	@Override
	public int getCount() {

		if(super.getCount() < 2)
			return super.getCount();

		return Integer.MAX_VALUE;
	}

	@Override
	protected View createItem(ViewGroup container, final int position) {

		final int modPos = position % super.getCount();
		Banner banner = getItem(modPos);
		if(banner == null)
			banner = new Banner();

		//add advert cover
		SimpleDraweeView aivCover = new SimpleDraweeView(container.getContext());
		aivCover.getHierarchy().setPlaceholderImage(R.color.list_image_def_color);
		aivCover.setScaleType(ScaleType.CENTER_CROP);
		aivCover.setImageURI(Uri.parse(banner.getPicurl()));

		FrameLayout convertView = new FrameLayout(container.getContext());
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				callbackItemViewClick(modPos, v);
			}
		});

		convertView.addView(aivCover, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT));
		return convertView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

		container.removeView((View) object);
	}


	@Override
	public int getIconResId(int index) {

		return R.drawable.selector_indicator_round;
	}

	@Override
	public int getLoopCount() {

		return super.getCount();
	}

	@Override
	public boolean isLoop() {

		return true;
	}

}
