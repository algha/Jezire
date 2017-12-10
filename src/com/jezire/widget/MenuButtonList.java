package com.jezire.widget;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class MenuButtonList extends LinearLayout {
	private ArrayList<MenuButton> lstMenuButton = new ArrayList<MenuButton>();
	private OnMenuButtonClickListener onMenuButtonClickListener;

	public MenuButtonList(Context context) {
		super(context);

	}

	public MenuButtonList(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public MenuButtonList(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public void clearMenuButton() {
		lstMenuButton.clear();
		this.removeAllViews();
	}

	public void addMenuButton(MenuButton menuButton) {
		menuButton.index = lstMenuButton.size();
		menuButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				buttonClick(Integer.parseInt(String.valueOf(((MenuButton) v).index)));
			}
		});

		lstMenuButton.add(menuButton);
		this.addView(menuButton);
	}

	public MenuButton getMenuButton(int idx) {
		return lstMenuButton.get(idx);
	}

	private void buttonClick(int idx) {
		for (int i = 0; i < lstMenuButton.size(); i++) {
			if (i != idx)
				lstMenuButton.get(i).setButtonClick(false);
		}

		lstMenuButton.get(idx).setButtonClick(true);

		if (idx < lstMenuButton.size() - 1)
			lstMenuButton.get(idx + 1).setButtonClickFix();

		if (onMenuButtonClickListener != null)
			onMenuButtonClickListener.onMenuButtonClick(idx);
	}

	public void setOnMenuButtonClickListener(OnMenuButtonClickListener listener) {
		onMenuButtonClickListener = listener;
	}

	public interface OnMenuButtonClickListener {
		public abstract void onMenuButtonClick(int idx);
	}
}