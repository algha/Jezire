package com.jezire.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class ScrollableWebView extends WebView {
	private OnScrollChangeListener onScrollChangeListener;

	public ScrollableWebView(Context context) {
		super(context);
	}

	public ScrollableWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollableWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@SuppressWarnings("deprecation")
	public ScrollableWebView(Context context, AttributeSet attrs, int defStyle, boolean privateBrowsing) {
		super(context, attrs, defStyle, privateBrowsing);
	}

	@Override
	protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (onScrollChangeListener != null)
			onScrollChangeListener.OnScrollChange(l, t, oldl, oldt);
	}

	public void setOnScrollChangeListener(OnScrollChangeListener l) {
		onScrollChangeListener = l;
	}

	public interface OnScrollChangeListener {
		public void OnScrollChange(int l, int t, int oldl, int oldt);
	}
}