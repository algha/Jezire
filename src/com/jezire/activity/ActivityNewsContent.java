package com.jezire.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jezire.App;
import com.jezire.R;
import com.jezire.widget.IconView;
import com.jezire.widget.UyghurTextView;

public class ActivityNewsContent extends Activity {
	private IconView btnClose;
	private WebView webView;
	private String url;
	private String title;

	private IconView btnTheme, btnShare, btnFontSmall, btnFontBig;
	private LinearLayout newsContentControlLayout, shareParent;
	private View titleBar, titleLine, controlLine;

	private View imgLoading, layoutLoading, layoutFailure;
	private boolean error = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.animation_in_up, R.anim.animation_out_up);

		setContentView(R.layout.layout_activity_news_content);
		initialize();
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.animation_in_down, R.anim.animation_out_down);
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initialize() {
		if (getIntent().getExtras() == null) {
			onBackPressed();
		}

		url = getIntent().getExtras().getString("url");
		title = getIntent().getExtras().getString("title");

		imgLoading = findViewById(R.id.imgLoading);
		layoutLoading = findViewById(R.id.layoutLoading);
		layoutFailure = findViewById(R.id.layoutFailure);

		titleBar = findViewById(R.id.titleBar);
		titleLine = findViewById(R.id.titleLine);
		controlLine = findViewById(R.id.controlLine);

		btnTheme = (IconView) findViewById(R.id.btnTheme);
		btnShare = (IconView) findViewById(R.id.btnShare);
		btnFontSmall = (IconView) findViewById(R.id.btnFontSmall);
		btnFontBig = (IconView) findViewById(R.id.btnFontBig);
		newsContentControlLayout = (LinearLayout) findViewById(R.id.newsContentControlLayout);
		shareParent = (LinearLayout) findViewById(R.id.shareParent);

		UyghurTextView title = (UyghurTextView) findViewById(R.id.txtTitle);
		title.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		title.setText("خەۋەرلەر");

		View btnretry = findViewById(R.id.btnRetry);
		btnretry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadUrl();
			}
		});
		btnretry.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();

				if (action == MotionEvent.ACTION_DOWN) {
					v.setBackgroundColor(Color.parseColor("#ed1c24"));
				}

				if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
					v.setBackgroundColor(Color.parseColor("#f26c4f"));
				}

				return false;
			}
		});

		if (App.Preferences.getThemeDay()) {
			btnTheme.setText("\ue020");
		} else {
			btnTheme.setText("\ue016");
		}
		btnTheme.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeTheme(!App.Preferences.getThemeDay());
				if (App.Preferences.getThemeDay()) {
					btnTheme.setText("\ue020");
				} else {
					btnTheme.setText("\ue016");
				}
			}
		});

		btnShare.setText("\ue011");
		btnShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupShare();
			}
		});

		btnFontSmall.setText("\ue015");
		btnFontSmall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (App.Preferences.getFontSize() > 10) {
					changeFontSize(App.Preferences.getFontSize() - 2);
				}
			}
		});

		btnFontBig.setText("\ue014");
		btnFontBig.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (App.Preferences.getFontSize() < 20) {
					changeFontSize(App.Preferences.getFontSize() + 2);
				}
			}
		});

		btnClose = (IconView) findViewById(R.id.btnClose);
		btnClose.setText("\ue021");
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);

		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				error = false;
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				error = false;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				if (error) {
					showLayout(layoutFailure);
				} else {
					changeTheme(App.Preferences.getThemeDay());
					changeFontSize(App.Preferences.getFontSize());
					showLayout(webView);
				}
			}

			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				error = true;
			}
		});

		loadUrl();
	}

	private void loadUrl() {
		showLayout(layoutLoading);
		error = false;
		webView.loadUrl(url);
	}

	@SuppressWarnings("deprecation")
	private void popupShare() {
		LinearLayout popupLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_widget_popup_share, null);
		IconView iconDelta = (IconView) popupLayout.findViewById(R.id.iconDelta);
		iconDelta.setText("\ue028");

		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iconDelta.getLayoutParams();
		layoutParams.leftMargin = shareParent.getLeft() - ((App.DISPLAY.getWidth() - App.getPixelFromDp(320)) / 2) + shareParent.getWidth() / 2 - App.getPixelFromDp(15) / 2;
		iconDelta.setLayoutParams(layoutParams);

		IconView btnShareToWechat = (IconView) popupLayout.findViewById(R.id.btnShareToWechat);
		btnShareToWechat.setText("\ue029");
		btnShareToWechat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				App.Wechat.ShareToWechat(title, title, url, false);
			}
		});

		IconView btnShareToTimeline = (IconView) popupLayout.findViewById(R.id.btnShareToTimeline);
		btnShareToTimeline.setText("\ue02a");
		btnShareToTimeline.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				App.Wechat.ShareToWechat(title, title, url, true);
			}
		});

		PopupWindow popupWindow = new PopupWindow(popupLayout, App.getPixelFromDp(320), App.getPixelFromDp(95));
		popupWindow.setContentView(popupLayout);
		popupWindow.setOutsideTouchable(false);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

		popupWindow.showAtLocation(btnShare, Gravity.BOTTOM | Gravity.CENTER, 0, newsContentControlLayout.getHeight() + App.getPixelFromDp(2));
	}

	private void showLayout(View v) {
		imgLoading.clearAnimation();
		layoutLoading.setVisibility(View.GONE);
		layoutFailure.setVisibility(View.GONE);
		webView.setVisibility(View.INVISIBLE);

		if (v.equals(layoutLoading)) {
			App.startLoadingAnimation(imgLoading);
		}

		v.setVisibility(View.VISIBLE);
	}

	private void changeFontSize(int size) {
		App.Preferences.setFontSize(size);
		webView.loadUrl("javascript:$('.content').find('p,span,font,div').css({'font-size':'" + String.valueOf(size) + "px'})");
	}

	private void changeTheme(boolean day) {
		App.Preferences.setThemeDay(day);
		if (day) {
			webView.loadUrl("javascript:document.getElementById('style').href='/public/css/day.css';");
			titleBar.setBackgroundColor(Color.parseColor("#0891f2"));
			titleLine.setBackgroundColor(Color.parseColor("#0784dd"));
			controlLine.setBackgroundColor(Color.parseColor("#cfcfcf"));
			newsContentControlLayout.setBackgroundColor(Color.parseColor("#fefefe"));
		} else {
			webView.loadUrl("javascript:document.getElementById('style').href='/public/css/night.css';");
			titleBar.setBackgroundColor(Color.parseColor("#272727"));
			titleLine.setBackgroundColor(Color.parseColor("#1d1d1d"));
			controlLine.setBackgroundColor(Color.parseColor("#1d1d1d"));
			newsContentControlLayout.setBackgroundColor(Color.parseColor("#272727"));
		}
	}
}