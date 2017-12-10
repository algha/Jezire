package com.jezire.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class FlatProgressBar extends View {
	private int progress = 0;

	private int primaryColor = Color.BLACK, secondaryColor = Color.WHITE;

	private Paint paint = new Paint();

	public FlatProgressBar(Context context) {
		super(context);
		initialize();
	}

	public FlatProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	public FlatProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize();
	}

	private void initialize() {
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);

	}

	public void setPrimaryColor(int color) {
		primaryColor = color;
	}

	public void setSecondaryColor(int color) {
		secondaryColor = color;
	}

	public void setProgress(int value) {
		progress = value;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (progress > 100)
			progress = 100;

		paint.setColor(secondaryColor);
		canvas.drawRect(0, 0, getRight(), getBottom(), paint);

		paint.setColor(primaryColor);
		canvas.drawRect(0, 0, getWidth() * (progress / 100F), getBottom(), paint);
	}
}
