package com.jezire.activity;

import com.jezire.R;
import com.jezire.widget.UyghurTextView;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;

public class ActivitySplash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.layout_activity_splash);
		initialize();
	}

	void initialize() {
		UyghurTextView utv = (UyghurTextView) findViewById(R.id.txtTip);
		utv.setText("يان تېلېفوندا ئانا تىلدىكى توربەتلەرنىڭ ھۇزۇرىنى سۈرۈڭ");

		new Handler().postDelayed(new Runnable() {
			public void run() {
				startActivity(new Intent(ActivitySplash.this, ActivityMain.class));
				finish();
				overridePendingTransition(R.anim.animation_in_fade, R.anim.animation_out_fade);
			}
		}, 1500);
	}

	@Override
	public void onBackPressed() {
		return;
	}
}