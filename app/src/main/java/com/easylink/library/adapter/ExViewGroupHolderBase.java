package com.easylink.library.adapter;

public abstract class ExViewGroupHolderBase implements ExViewGroupHolder{

	protected int mGroupPos;
	
	@Override
	public void invalidateGroupView(int groupPos, boolean isExpand){
		
		mGroupPos = groupPos;
		invalidateGroupView(isExpand);
	}
	
	public abstract void invalidateGroupView(boolean isExpand);
}
