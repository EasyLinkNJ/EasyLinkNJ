package com.easylink.library.view.pageindicator;

public interface IconPagerAdapter {
	/**
	 * Get icon representing the page at {@code index} in the adapter.
	 */
	int getIconResId(int index);

	// From PagerAdapter
	int getCount();
	
	/**
	 * 为无线循环添加的接口
	 */
	boolean isLoop();
	
	
	int getLoopCount();
}
