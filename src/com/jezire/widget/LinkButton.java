package com.jezire.widget;

import com.jezire.App;
import com.jezire.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LinkButton extends LinearLayout {

	private View leftBorder;
	private ImageView imgIcon;
	private UyghurTextView txtText;

	public LinkButton() {
		super(App.CONTEXT);
		initialize(App.CONTEXT);
	}

	public LinkButton(Context context) {
		super(context);
		initialize(context);
	}

	public LinkButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context);
	}

	public LinkButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize(context);
	}

	private void initialize(Context context) {
		LayoutInflater.from(context).inflate(R.layout.layout_widget_link_button, this, true);

		txtText = (UyghurTextView) findViewById(R.id.txtText);
		leftBorder = (View) findViewById(R.id.leftBorder);
		imgIcon = (ImageView) findViewById(R.id.imgIcon);

		setButtonStyle(false);
		setLeftBorder(true);
	}

	public void setText(String text) {
		txtText.setText(text);
	}

	public void setButtonStyle(boolean isTop) {
		if (isTop) {
			imgIcon.setImageResource(R.drawable.link_icon_top);
			txtText.setTextColor(Color.parseColor("#ed1c24"));
		} else {
			imgIcon.setImageResource(R.drawable.link_icon_normal);
			txtText.setTextColor(Color.parseColor("#666666"));
		}
	}

	public void setLeftBorder(boolean show) {
		if (show) {
			leftBorder.setVisibility(View.VISIBLE);
		} else {
			leftBorder.setVisibility(View.INVISIBLE);
		}
	}
}