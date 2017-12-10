package com.jezire.algha;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class XurjunGirdView extends GridView {

	public XurjunGirdView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public XurjunGirdView(Context context) {
		super(context);
	}

	public XurjunGirdView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
