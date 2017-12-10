package com.jezire.widget;

import com.jezire.App;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class IconView extends TextView {

	public IconView(Context context) {
		super(context);
		initialize();
	}

	public IconView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	public IconView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize();
	}

	private void initialize() {
		super.setTypeface(App.TYPE_FACE_SIMPLE_LINE_ICONS);
	}
}