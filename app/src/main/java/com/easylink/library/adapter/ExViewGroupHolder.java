package com.easylink.library.adapter;

import android.view.View;

public interface ExViewGroupHolder {

	public int getGroupViewRid();
	
	public void initGroupView(View groupView, boolean isExpand);
	
	public void invalidateGroupView(int groupPos, boolean isExpand);
}
