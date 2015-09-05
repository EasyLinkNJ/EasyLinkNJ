package com.easylink.library.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public abstract class ExExpendAdapter<group,child> extends BaseExpandableListAdapter {
	
	private List<group> mData;
	private OnItemGroupViewClickListener mItemGroupViewClickLisn;
	private OnItemGroupViewLongClickListener mItemGroupViewLongClickLisn;
	private OnItemChildViewClickListener mItemChildViewClickLisn;
	private OnItemChildViewLongClickListener mItemChildViewLongClickLisn;
	
	public ExExpendAdapter(){}
	
	public ExExpendAdapter(List<group> data){
		
		mData = data;
	}
	
	public void setData(List<group> data){
		
		mData = data;
	}
	
	public void clear(){
		
		if(mData != null)
			mData.clear();
	}
	
	/**
	 * group item part
	 **/
	
	public boolean isGroupEmpty(){
		
		return getGroupCount() == 0;
	}
	
	@Override
	public int getGroupCount(){
		
		return mData == null ? 0 : mData.size();
	}
	
	@Override
	public long getGroupId(int groupPosition){
		
		return groupPosition;
	}
	
	@Override
	public group getGroup(int groupPosition) {
		
		if (mData == null)
			return null;

		group t = null;
		try {
			t = mData.get(groupPosition);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return t;
	}
	
	public boolean checkGroupPosition(int position){
		
		return position >= 0 && position < getGroupCount();
	}
	
	/**
	 * child item part
	 **/
	
	@Override
	public abstract child getChild(int groupPosition, int childPosition);
	
	public boolean isChildrenEmpty(int groupPos){
		
		return getChildrenCount(groupPos) == 0;
	}
	
	public boolean checkChildPosition(int groupPosition, int childPosition){
		
		return  childPosition >= 0 && childPosition < getChildrenCount(groupPosition);
	}
	
	/**
	 * item attr part
	 */
	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		
		return false;
	}
	
	@Override
	public boolean hasStableIds(){
		
		return false;
	}
	
	/**
	 * group view create and fresh
	 */
	
	@Override
	public View getGroupView(int groupPos, boolean isExpanded, View convertView, ViewGroup parent) {
		
		ExViewGroupHolder viewGroupHolder = null;
		if (convertView == null) {
			
			viewGroupHolder = getViewGroupHolder(groupPos, isExpanded);
			convertView = LayoutInflater.from(parent.getContext()).inflate(viewGroupHolder.getGroupViewRid(), null);
			viewGroupHolder.initGroupView(convertView, isExpanded);
			convertView.setTag(viewGroupHolder);
		}else{
			
			viewGroupHolder = (ExViewGroupHolder) convertView.getTag();
		}
		
		viewGroupHolder.invalidateGroupView(groupPos, isExpanded);
		return convertView;		
	}

	/**
	 * child view create and fresh
	 */
	
	@Override
	public View getChildView(int groupPos, int childPos, boolean isLastChild, View convertView, ViewGroup parent){
		
		ExViewChildHolder viewChildHolder = null;
		if (convertView == null) {
			
			viewChildHolder = getViewChildHolder(groupPos, childPos, isLastChild);
			convertView = LayoutInflater.from(parent.getContext()).inflate(viewChildHolder.getChildViewRid(), null);
			viewChildHolder.initChildView(convertView, isLastChild);
			convertView.setTag(viewChildHolder);
		}else{
			
			viewChildHolder = (ExViewChildHolder) convertView.getTag();
		}
		
		viewChildHolder.invalidateChildView(groupPos, childPos, isLastChild);
		return convertView;		
	}
	
	protected abstract ExViewGroupHolder getViewGroupHolder(int groupPos, boolean isExpand);
	protected abstract ExViewChildHolder getViewChildHolder(int groupPos, int childPos, boolean isLastChild);
	
	/*
	 * click listener part
	 */
	
	public void setOnItemGroupViewClickListener(OnItemGroupViewClickListener lisn){
		
		mItemGroupViewClickLisn = lisn;
	}
	
	public void setOnItemGroupViewLongClickListener(OnItemGroupViewLongClickListener lisn){
		
		mItemGroupViewLongClickLisn = lisn;
	}
	
	public void setOnItemChildViewClickListener(OnItemChildViewClickListener lisn){
		
		mItemChildViewClickLisn = lisn;
	}
	
	public void setOnItemChildViewLongClickListener(OnItemChildViewLongClickListener lisn){
		
		mItemChildViewLongClickLisn = lisn;
	}	
	
	protected void callbackItemGroupViewClick(int groupPos, View view){
		
		if(mItemGroupViewClickLisn != null)
			mItemGroupViewClickLisn.onGroupViewClick(groupPos, view);
	}
	
	protected void callbackItemGroupViewLongClick(int groupPos, View view){
		
		if(mItemGroupViewLongClickLisn != null)
			mItemGroupViewLongClickLisn.onGroupViewLongClick(groupPos, view);
	}	
	
	protected void callbackItemChildViewClick(int groupPos, int childPos, View view){
		
		if(mItemChildViewClickLisn != null)
			mItemChildViewClickLisn.onChildViewClick(groupPos, childPos, view);
	}
	
	protected void callbackItemChildViewLongClick(int groupPos, int childPos, View view){
		
		if(mItemChildViewLongClickLisn != null)
			mItemChildViewLongClickLisn.onChildViewLongClick(groupPos, childPos, view);
	}	
}
