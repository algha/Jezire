package com.jezire.algha;

import com.jezire.R;
import com.jezire.widget.UyghurTextView;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.ContentResolver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ScreenBrightness extends PopupWindow {
	private Activity contextActivity;
	private View screenBrigtnessView;

	public ScreenBrightness(Activity context, View parent) {
		super(context);

		contextActivity = context;

		LayoutInflater inflater = LayoutInflater.from(contextActivity);
		screenBrigtnessView = (View) inflater.inflate(
				R.layout.screen_brigtness_view, null);
		this.setContentView(screenBrigtnessView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.MATCH_PARENT);
		this.setFocusable(true);
		this.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#80000000")));

		showPopupWindow(parent);
		
		
		final SeekBar seekBar = (SeekBar)screenBrigtnessView.findViewById(R.id.seekBar);
		seekBar.setProgress(50);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				setBrightness(contextActivity, progress);
			}
		});
		
		UyghurTextView muqumlash = (UyghurTextView)screenBrigtnessView.findViewById(R.id.muqumlash);
		muqumlash.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPopupWindow(null);
				saveBrightness(seekBar.getProgress());
			}
		});
		
		UyghurTextView qaldurush = (UyghurTextView)screenBrigtnessView.findViewById(R.id.qaldurush);
		qaldurush.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPopupWindow(null);
				
			}
		});
	}

	public void showPopupWindow(View parent) {
		if (!this.isShowing()) {
			// 以下拉方式显示popupwindow
			this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		} else {
			this.dismiss();
		}
	}

	public static int getScreenBrightness(Activity activity) {
		int nowBrightnessValue = 0;
		ContentResolver resolver = activity.getContentResolver();
		try {
			nowBrightnessValue = android.provider.Settings.System.getInt(
					resolver, Settings.System.SCREEN_BRIGHTNESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nowBrightnessValue;
	}

	public  void setBrightness(Activity activity, int brightness) {
		
		 if(brightness<=1)
         {
                  brightness=1;
         }
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.screenBrightness = Float.valueOf(brightness/255f); 
		activity.getWindow().setAttributes(lp);

	}

	public  void saveBrightness(int brightness) {
		Uri uri = android.provider.Settings.System
				.getUriFor("screen_brightness");
		android.provider.Settings.System.putInt(contextActivity.getContentResolver(), "screen_brightness",
				brightness/255);
		contextActivity.getContentResolver().notifyChange(uri, null);
	}

}
