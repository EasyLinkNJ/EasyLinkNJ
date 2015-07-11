package com.easylink.library.view.listview;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.easylink.nj.R;

public class XListView extends ListView implements OnScrollListener {

	private float mLastY = -1; // save event y
	private Scroller mScroller; // used for scroll back
	private OnScrollListener mScrollListener; // user's scroll listener

	// the interface to trigger refresh and load more.
	private IXListViewListener mListViewListener;

	// -- header view
	private XListViewHeader mHeaderView;
	// header view content, use it to calculate the Header's height. And hide it
	// when disable pull refresh.
	private RelativeLayout mHeaderViewContent;
	//private TextView mHeaderTimeView;
	private int mHeaderViewHeight; // header view's height
	private boolean mEnablePullRefresh = true;
	private boolean mPullRefreshing = false; // is refreashing.

	public boolean isPullRefreshing() {
		return mPullRefreshing;
	}

	// -- footer view
	private XListViewFooter mFooterView;
	private boolean mEnablePullLoad = true;
	private boolean mPullLoading;

	public boolean isPullLoading() {
		return mPullLoading;
	}

	private boolean mIsFooterReady = false;

	// total list items, used to detect is at the bottom of listview.
	private int mTotalItemCount;

	// for mScroller, scroll back from header or footer.
	private int mScrollBack;
	private final static int SCROLLBACK_HEADER = 0;
	private final static int SCROLLBACK_FOOTER = 1;

	private final static int SCROLL_DURATION = 300; // scroll back duration
	private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
														// at bottom, trigger
														// load more.
	private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
													// feature.

	/**
	 * @param context
	 */
	public XListView(Context context) {
		super(context);
		initWithContext(context);
	}

	public XListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWithContext(context);
		setFadingEdgeLength(0);
	}

	public XListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWithContext(context);
	}

	private void initWithContext(Context context) {
		mScroller = new Scroller(context, new DecelerateInterpolator());
		// XListView need the scroll event, and it will dispatch the event to
		// user's listener (as a proxy).
		super.setOnScrollListener(this);

		// init header view
		mHeaderView = new XListViewHeader(context);
		mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlistview_header_content);
		//mHeaderTimeView = (TextView) mHeaderView.findViewById(R.id.xlistview_header_time);
		addHeaderView(mHeaderView);

		// init footer view
		mFooterView = new XListViewFooter(context);
		mFooterView.setRetryLoadClickListener(new XListViewFooter.RetryLoadClickListener() {
			@Override
			public boolean onRetryLoadClickListener() {
				
				if(mListViewListener != null)
					return mListViewListener.onManualLoadMore();
				
				return false;
			}
		});
		// init header height
		/*
		 * mHeaderViewContent.getViewTreeObserver().addOnGlobalLayoutListener(
		 * new OnGlobalLayoutListener() {
		 * 
		 * @Override public void onGlobalLayout() {
		 * 
		 * mHeaderViewContent.getViewTreeObserver()
		 * .removeGlobalOnLayoutListener(this); mHeaderViewHeight =
		 * mHeaderViewContent.getHeight(); } });
		 */
		int w = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		int h = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		mHeaderViewContent.measure(w, h);
		mHeaderViewHeight = mHeaderViewContent.getMeasuredHeight();

	}
	
	public void setPullRefreshText(String text){
		mHeaderView.setPullRefreshingText(text);
	}
	
	public void addHeaderViewFirst(View v) {

		if(v != null)
			mHeaderView.addView(v, 0);
	}
	
	public void addFooterViewFirst(View v){
		
		if(v != null)
			mFooterView.addView(v, 0);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		// make sure XListViewFooter is the last footer view, and only add once.
		if (mIsFooterReady == false) {
			mIsFooterReady = true;
			addFooterView(mFooterView);
		}
		super.setAdapter(adapter);
	}

	/**
	 * enable or disable pull down refresh feature.
	 * 
	 * @param enable
	 */
	public void setPullRefreshEnable(boolean enable) {
		
		if(mEnablePullRefresh == enable)
			return;
		
		mEnablePullRefresh = enable;
		if (!mEnablePullRefresh) { // disable, hide the content
			mHeaderViewContent.setVisibility(View.INVISIBLE);
		} else {
			mHeaderViewContent.setVisibility(View.VISIBLE);
		}
	}
	
	public boolean isPullRefreshEnable(){
		
		return mEnablePullRefresh;
	}
	
	public void setPullLoadEnableByLimit(int limit) {
		
		if(getAdapter() == null || getAdapter().getCount() < limit){
			setPullLoadEnable(false);
		}else{
			setPullLoadEnable(true);
		}
	}
	
	/**
	 * enable or disable pull up load more feature.
	 * 
	 * @param enable
	 */
	public void setPullLoadEnable(boolean enable) {
		
		if(mEnablePullLoad == enable)
			return;
		
		mEnablePullLoad = enable;
		if (!mEnablePullLoad) {
			mFooterView.hide();
			mFooterView.setOnClickListener(null);
		} else {
			mPullLoading = false;
			mFooterView.show();
			mFooterView.setState(XListViewFooter.STATE_NORMAL);
			// both "pull up" and "click" will invoke load more.
			mFooterView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startLoadMore();
				}
			});
		}
	}
	
	public boolean isPullLoadEnable(){
		
		return mEnablePullLoad;
	}

	/**
	 * stop refresh, reset header view.
	 */
	public void stopRefresh() {
		if (mPullRefreshing == true) {
			mPullRefreshing = false;
			resetHeaderHeight();
		}
	}

	/**
	 * stop load more, reset footer view.
	 */
	public void stopLoadMore() {
		if (mPullLoading == true) {
			mPullLoading = false;
			mFooterView.setState(XListViewFooter.STATE_NORMAL);
		}
	}
	
	public void stopLoadMoreFailed(){
		
		if (mPullLoading == true) {
			mPullLoading = false;
			mFooterView.setState(XListViewFooter.STATE_FAILED);
		}		
	}

	private String lastTime;

	/**
	 * set last refresh time
	 * 
	 * @param time
	 */
	public void setLastTime(String time) {
		lastTime = time;
	}

	private void invokeOnScrolling() {
		if (mScrollListener instanceof OnXScrollListener) {
			OnXScrollListener l = (OnXScrollListener) mScrollListener;
			l.onXScrolling(this);
		}
	}

	private void updateHeaderHeight(float delta) {
		mHeaderView.setVisiableHeight((int) delta
				+ mHeaderView.getVisiableHeight());
		if (mEnablePullRefresh && !mPullRefreshing) {
			if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
				mHeaderView.setState(XListViewHeader.STATE_READY);
			} else {
				mHeaderView.setState(XListViewHeader.STATE_NORMAL);
			}
		}
		setSelection(0); // scroll to top each time
	}

	/**
	 * reset header view's height.
	 */
	private void resetHeaderHeight() {
		int height = mHeaderView.getVisiableHeight();
		if (height == 0) // not visible.
			return;
		// refreshing and header isn't shown fully. do nothing.
		if (mPullRefreshing && height <= mHeaderViewHeight) {
			return;
		}
		int finalHeight = 0; // default: scroll back to dismiss header.
		// is refreshing, just scroll back to show all the header.
		if (mPullRefreshing && height > mHeaderViewHeight) {
			finalHeight = mHeaderViewHeight;
		}
		mScrollBack = SCROLLBACK_HEADER;
		mScroller.startScroll(0, height, 0, finalHeight - height,
				SCROLL_DURATION);
		// trigger computeScroll
		invalidate();
	}

	private void updateFooterHeight(float delta) {
		int height = mFooterView.getBottomMargin() + (int) delta;
		if (mEnablePullLoad && !mPullLoading) {
			if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
													// more.
				mFooterView.setState(XListViewFooter.STATE_READY);
			} else {
				mFooterView.setState(XListViewFooter.STATE_NORMAL);
			}
		}
		mFooterView.setBottomMargin(height);

		// setSelection(mTotalItemCount - 1); // scroll to bottom
	}

	private void resetFooterHeight() {
		int bottomMargin = mFooterView.getBottomMargin();
		if (bottomMargin > 0) {
			mScrollBack = SCROLLBACK_FOOTER;
			mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
					SCROLL_DURATION);
			invalidate();
		}
	}

	private void startLoadMore() {
		
		if (!mPullLoading && mListViewListener != null) {
			
			boolean result = mListViewListener.onAutoLoadMore();
			if(result){
				mPullLoading = true;
				mFooterView.setState(XListViewFooter.STATE_LOADING);
			}else{
				mFooterView.setState(XListViewFooter.STATE_FAILED);
			}
		}

	}

	private void startRefresh(boolean force) {
		if (!mPullRefreshing && mListViewListener != null) {
			mPullRefreshing = true;
			mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
			mListViewListener.onRefresh(force);
		}

	}

	public void ForceRefresh() {

		startRefresh(true);

		mScrollBack = SCROLLBACK_HEADER;
		mScroller.startScroll(0, 0, 0, mHeaderViewHeight, SCROLL_DURATION);
		// trigger computeScroll
		invalidate();
	}

	private String computeTime() {
		String second = " 秒前";
		String minute = " 分钟前";
		String hour = " 小时前";
		String timeDesc = "";

		if (lastTime == null)
			return "0" + second;
		long diffTime = (Long.valueOf(System.currentTimeMillis()) - Long
				.valueOf(lastTime)) / 1000;
		if (diffTime < 0)
			return "0";
		if (diffTime < 60)
			timeDesc = diffTime + second;
		else if (diffTime < 3600)
			timeDesc = diffTime / 60 + minute;
		else if (diffTime < 3600 * 24)
			timeDesc = diffTime / 3600 + hour;
		else {
			Date date = new Date(Long.valueOf(lastTime));
			timeDesc = new SimpleDateFormat("yyyy-MM-dd").format(date);
		}
		return timeDesc;
	}


	private boolean scrollAble = true;
	public void setScrollable(boolean scrollAble){
		this.scrollAble = scrollAble;
	}
	
	public boolean isScrollAble(){
		
		return scrollAble;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		if(scrollAble){
//			return super.onInterceptTouchEvent(ev);
//		}else{
//			return false;
//		}
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
//		if(!scrollAble)return false;
		if(!isPullRefreshEnable())
			return super.onTouchEvent(ev);
		
		if (mLastY == -1) {
			mLastY = ev.getRawY();
		}

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastY = ev.getRawY();
			//mHeaderTimeView.setText(computeTime());
			break;
		case MotionEvent.ACTION_MOVE:
			final float deltaY = ev.getRawY() - mLastY;
			mLastY = ev.getRawY();

			// the first item is showing, header has shown or pull down.
			if (getFirstVisiblePosition() == 0
					&& (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
				// the first item is showing, header has shown or pull down.
				updateHeaderHeight(deltaY / OFFSET_RADIO);
				invokeOnScrolling();
			} /*
			 * else if (getLastVisiblePosition() == mTotalItemCount - 1 &&
			 * (mFooterView.getBottomMargin() > 0 || deltaY < 0)) { // last
			 * item, already pulled up or want to pull up.
			 * updateFooterHeight(-deltaY / OFFSET_RADIO); }
			 */
			break;
		default:
			mLastY = -1; // reset
			if (getFirstVisiblePosition() == 0) {
				// invoke refresh
				if (mEnablePullRefresh
						&& mHeaderView.getVisiableHeight() > mHeaderViewHeight) {

					startRefresh(false);
				}
				resetHeaderHeight();
			} /*
			 * else if (getLastVisiblePosition() == mTotalItemCount - 1) { //
			 * invoke load more. if (mEnablePullLoad &&
			 * mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
			 * startLoadMore(); } resetFooterHeight(); }
			 */
			break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			if (mScrollBack == SCROLLBACK_HEADER) {
				mHeaderView.setVisiableHeight(mScroller.getCurrY());
			} else {
				mFooterView.setBottomMargin(mScroller.getCurrY());
			}
			postInvalidate();
			invokeOnScrolling();
		}
		super.computeScroll();
	}

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mScrollListener = l;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (mScrollListener != null) {
			mScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// send to user's listener
		
		// callback OnScrllBottomLisnter
		if (mListViewListener != null && !mPullLoading /*&& !mPullRefreshing*/
				&& mEnablePullLoad /*&& !mFooterView.isFailedState()*/) {
			if (visibleItemCount + firstVisibleItem == totalItemCount) {
				startLoadMore();
			}
		}

		mTotalItemCount = totalItemCount;
		if (mScrollListener != null) {
			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
					totalItemCount);
		}
	}

	public void setXListViewListener(IXListViewListener l) {
		mListViewListener = l;
	}

	/**
	 * you can listen ListView.OnScrollListener or this one. it will invoke
	 * onXScrolling when header/footer scroll back.
	 */
	public interface OnXScrollListener extends OnScrollListener {
		public void onXScrolling(View view);
	}

	/**
	 * implements this interface to get refresh/load more event.
	 */
	public interface IXListViewListener {
		public void onRefresh(boolean force);

		public boolean onAutoLoadMore();
		
		public boolean onManualLoadMore();
	}
	
	public void setLoadMoreFailedView(){
		mPullLoading = false;
		mFooterView.setState(XListViewFooter.STATE_FAILED);
	}
	
	public void setLoadMoreLoadingView(){
		mFooterView.setState(XListViewFooter.STATE_LOADING);
	}
	
	public void resetFootViewHeight(int height){
		mFooterView.resetHeight(height);
	}
}
