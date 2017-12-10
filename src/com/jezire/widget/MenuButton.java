package com.jezire.widget;

import com.jezire.App;
import com.jezire.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class MenuButton extends LinearLayout {
	public int index;

	private View lineView1, lineView2;
	private UyghurTextView txtText;
	private LinearLayout lineViewHolder;

	LinearLayout.LayoutParams layoutParams;

	public MenuButton() {
		super(App.CONTEXT);
		initialize(App.CONTEXT, false);
	}

	public MenuButton(boolean alignRight) {
		super(App.CONTEXT);
		initialize(App.CONTEXT, alignRight);
	}

	public MenuButton(Context context) {
		super(context);
		initialize(context, false);
	}

	public MenuButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context, false);
	}

	public MenuButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize(context, false);
	}

	private void initialize(Context context, boolean alignRight) {
		LayoutInflater.from(context).inflate(R.layout.layout_widget_menu_button, this, true);

		lineView1 = (View) findViewById(R.id.lineView1);
		lineView2 = (View) findViewById(R.id.lineView2);
		txtText = (UyghurTextView) findViewById(R.id.txtText);
		lineViewHolder = (LinearLayout) findViewById(R.id.lineViewHolder);

		txtText.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);

		setButtonClick(false);

		if (alignRight) {
			txtText.setGravity(Gravity.RIGHT | Gravity.CENTER);
			txtText.setPadding(App.getPixelFromDp(18), 0, App.getPixelFromDp(18), 0);

			layoutParams = (LinearLayout.LayoutParams) txtText.getLayoutParams();
			layoutParams.leftMargin = 0;
			txtText.setLayoutParams(layoutParams);
		}
	}

	public void setText(String text) {
		txtText.setText(text);
	}

	public String getText() {
		return (String) txtText.getText();
	}

	public void setButtonClick(boolean click) {
		if (click)
			buttonClick();
		else
			buttonUnClick();
	}

	public void setButtonClickFix() {
		buttonClickFix();
	}

	private void buttonClick() {
		txtText.setTextColor(Color.parseColor("#ffffff"));
		lineView2.setBackgroundColor(Color.parseColor("#272b2e"));
		txtText.setBackgroundColor(Color.parseColor("#272b2e"));
		lineViewHolder.setBackgroundColor(Color.parseColor("#272b2e"));
		this.setBackgroundColor(Color.parseColor("#afc936"));

		layoutParams = (LinearLayout.LayoutParams) lineView1.getLayoutParams();
		layoutParams.leftMargin = 0;
		layoutParams.rightMargin = 0;
		lineView1.setLayoutParams(layoutParams);

		layoutParams = (LinearLayout.LayoutParams) lineView2.getLayoutParams();
		layoutParams.leftMargin = 0;
		layoutParams.rightMargin = 0;
		lineView2.setLayoutParams(layoutParams);
	}

	private void buttonUnClick() {
		txtText.setTextColor(Color.parseColor("#767d82"));
		lineView2.setBackgroundColor(Color.parseColor("#3b4145"));
		txtText.setBackgroundColor(Color.parseColor("#2b3033"));
		lineViewHolder.setBackgroundColor(Color.parseColor("#2b3033"));
		this.setBackgroundColor(Color.parseColor("#2b3033"));

		layoutParams = (LinearLayout.LayoutParams) lineView1.getLayoutParams();
		layoutParams.leftMargin = App.getPixelFromDp(17);
		layoutParams.rightMargin = App.getPixelFromDp(17);
		lineView1.setLayoutParams(layoutParams);

		layoutParams = (LinearLayout.LayoutParams) lineView2.getLayoutParams();
		layoutParams.leftMargin = App.getPixelFromDp(17);
		layoutParams.rightMargin = App.getPixelFromDp(17);
		lineView2.setLayoutParams(layoutParams);
	}

	private void buttonClickFix() {
		layoutParams = (LinearLayout.LayoutParams) lineView1.getLayoutParams();
		layoutParams.leftMargin = 0;
		layoutParams.rightMargin = 0;
		lineView1.setLayoutParams(layoutParams);

		layoutParams = (LinearLayout.LayoutParams) lineView2.getLayoutParams();
		layoutParams.leftMargin = 0;
		layoutParams.rightMargin = 0;
		lineView2.setLayoutParams(layoutParams);
	}
}