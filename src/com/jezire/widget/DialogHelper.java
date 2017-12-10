package com.jezire.widget;

import com.jezire.App;
import com.jezire.R;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class DialogHelper {

	static PopupWindow popupWindowNetworkFailure;
	static PopupWindow popupWindowLoading;

	public static void showNetworkFailureDialog(View parent, final OnNetworkFailureListener l) {
		if (popupWindowNetworkFailure == null) {
			LayoutInflater inflater = (LayoutInflater) App.CONTEXT.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View popupview = inflater.inflate(R.layout.layout_dialog_networkfailure, null);

			popupWindowNetworkFailure = new PopupWindow(popupview, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			popupWindowNetworkFailure.setAnimationStyle(R.style.AnimationPopup);

			final LinearLayout btnretry = (LinearLayout) popupview.findViewById(R.id.btnRetry);
			btnretry.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (l != null) {
						l.OnNetworkFailure();
						popupWindowNetworkFailure.dismiss();
					}
				}
			});

			btnretry.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					int action = event.getAction();

					if (action == MotionEvent.ACTION_DOWN) {
						btnretry.setBackgroundColor(Color.parseColor("#ed1c24"));
					}

					if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
						btnretry.setBackgroundColor(Color.parseColor("#f26c4f"));
					}

					return false;
				}
			});
		} else {
			if (popupWindowNetworkFailure.isShowing()) {
				popupWindowNetworkFailure.dismiss();
			}
		}

		popupWindowNetworkFailure.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
	}

	public static void showLoadingDialog(View parent) {
		if (popupWindowLoading == null) {
			LayoutInflater inflater = (LayoutInflater) App.CONTEXT.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View popupview = inflater.inflate(R.layout.layout_dialog_loading, null);

			popupWindowLoading = new PopupWindow(popupview, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			popupWindowLoading.setAnimationStyle(R.style.AnimationPopup);
		} else {
			if (popupWindowLoading.isShowing()) {
				popupWindowLoading.dismiss();
			}
		}

		popupWindowLoading.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
	}

	public static void dismissLoadingDialog() {
		if (popupWindowLoading != null && popupWindowLoading.isShowing()) {
			popupWindowLoading.dismiss();
		}
	}

	public interface OnNetworkFailureListener {
		public abstract void OnNetworkFailure();
	}
}