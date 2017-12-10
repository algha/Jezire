package com.jezire.widget;

import com.jezire.App;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

public class LinedEditText extends EditText {
	private int line1X, line2X;
	private Paint paint;
	private boolean horizontalLineEnabled = true;

	public LinedEditText(Context context) {
		super(context);
		initialize();
	}

	public LinedEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	public LinedEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize();
	}

	private void initialize() {
		if (isInEditMode()) {
			return;
		}

		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.parseColor("#dff0f7"));
		paint.setStrokeWidth(App.getPixelFromDp(1));
		paint.setStyle(Paint.Style.FILL_AND_STROKE);

		super.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);

		line1X = App.getPixelFromDp(14);
		line2X = App.getPixelFromDp(18);
	}

	public void setHorizontalLineEnabled(boolean enabled) {
		horizontalLineEnabled = enabled;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (isInEditMode()) {
			return;
		}

		int w = getWidth();
		int lh = getLineHeight();

		int count = getHeight() / lh;
		int baseline = getLineBounds(0, null);

		if (getLineCount() > count)
			count = getLineCount();

		for (int i = 0; i < count; i++) {
			if (horizontalLineEnabled) {
				canvas.drawLine(0, baseline + 1, w, baseline + 1, paint);
			}
			baseline += lh;
		}

		canvas.drawLine(w - line1X, 0, w - line1X, baseline + lh, paint);
		canvas.drawLine(w - line2X, 0, w - line2X, baseline + lh, paint);

		super.onDraw(canvas);
	}
}
