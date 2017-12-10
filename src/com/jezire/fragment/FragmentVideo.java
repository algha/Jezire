package com.jezire.fragment;

import com.jezire.App;
import com.jezire.R;
import com.jezire.activity.ActivityVideoInfo;
import com.jezire.widget.IconView;
import com.jezire.widget.UyghurTextView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FragmentVideo extends FragmentActivity {
	private IconView btnClose, btnMenu;
	private WebView webView;
	private View imgLoading, layoutLoading, layoutFailure;
	private boolean error = false;

	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize();
	}

	private void initialize() {
		View contentView = getLayoutInflater().inflate(R.layout.layout_fragment_video, null);
		setContentView(contentView);
		initializeView(contentView);

		App.FRAGMENT_VIDEO = this;
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initializeView(View contentView) {
		imgLoading = contentView.findViewById(R.id.imgLoading);
		layoutLoading = contentView.findViewById(R.id.layoutLoading);
		layoutFailure = contentView.findViewById(R.id.layoutFailure);

		UyghurTextView title = (UyghurTextView) contentView.findViewById(R.id.txtTitle);
		title.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		title.setText("فىلىملەر");

		btnClose = (IconView) contentView.findViewById(R.id.btnClose);
		btnMenu = (IconView) contentView.findViewById(R.id.btnMenu);

		btnClose.setText("\ue021");
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentVideo.this.getParent().onBackPressed();
			}
		});

		btnMenu.setText("\ue01c");
		btnMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				App.SLIDE_MENU_VIDEO.open(true, true);
			}
		});

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

		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);

		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				error = false;
				if (url.contains("&id=")) {
					Intent intent = new Intent(FragmentVideo.this, ActivityVideoInfo.class);
					intent.putExtra("id", url.substring(url.lastIndexOf("&id=") + 4));
					startActivity(intent);
				} else {
					view.loadUrl(url);
				}

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
					showLayout(webView);
				}
			}

			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				error = true;
			}
		});
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
			}
		});

		url = App.SERVER_URL + "/?h=phone&t=video&f=gethome";

		loadUrl();
	}

	private void loadUrl() {
		showLayout(layoutLoading);
		error = false;
		webView.loadUrl(url);
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

	public void setUrl(String u) {
		url = u;
		loadUrl();
	}
}