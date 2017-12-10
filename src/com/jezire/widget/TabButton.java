package com.jezire.widget;

import com.jezire.App;
import com.jezire.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class TabButton extends LinearLayout {
	private UyghurTextView txtText;
	private View lineView;

	public TabButton() {
		super(App.CONTEXT);
		initialize(App.CONTEXT);
	}

	public TabButton(Context context) {
		super(context);
		initialize(context);
	}

	public TabButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context);
	}

	public TabButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize(context);
	}

	private void initialize(Context context) {
		LayoutInflater.from(context).inflate(R.layout.layout_widget_tab_button, this, true);
		txtText = (UyghurTextView) findViewById(R.id.txtText);
		lineView = findViewById(R.id.lineView);

		txtText.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
	}

	public void setText(String text) {
		txtText.setText(text);
	}

	public void setChecked(boolean check) {
		if (check) {
			lineView.setBackgroundColor(Color.parseColor("#0891f2"));
		} else {
			lineView.setBackgroundColor(Color.parseColor("#bebebe"));
		}
	}
}
