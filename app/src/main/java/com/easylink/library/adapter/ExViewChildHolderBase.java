package com.easylink.library.adapter;

public abstract class ExViewChildHolderBase implements ExViewChildHolder{

	protected int mGroupPos, mChildPos;
	
	@Override
	public void invalidateChildView(int groupPos, int childPos, boolean isLastChild){
		
		mGroupPos = groupPos;
		mChildPos = childPos;
		invalidateChildView(isLastChild);
	}
	
	public abstract void invalidateChildView(boolean isLastChild);
}
