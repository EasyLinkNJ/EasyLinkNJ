package com.easylink.library.adapter;

import android.view.View;

public interface ExViewChildHolder {

	public int getChildViewRid();
	
	public void initChildView(View childView, boolean isLastChild);
	
	public void invalidateChildView(int groupPos, int childPos, boolean isLastChild);
}
