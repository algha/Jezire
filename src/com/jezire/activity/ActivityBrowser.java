package com.jezire.activity;

import com.jezire.App;
import com.jezire.App.Preferences;
import com.jezire.R;
import com.jezire.algha.AToast;
import com.jezire.algha.XurjunPopup;
import com.jezire.algha.myAlertDialog;
import com.jezire.algha.XurjunPopup.OnLoadDataFinishListener;
import com.jezire.widget.FlatProgressBar;
import com.jezire.widget.IconView;
import com.jezire.widget.UyghurTextView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ActivityBrowser extends Activity {
	private IconView btnClose, btnRefreshOrStop, btnBackward, btnShare,
			btnForward;
	private EditText txtUrl;
	private UyghurTextView txtHint;

	private WebView webView;
	private String intentUrl;

	private FlatProgressBar progressBar;

	private InputMethodManager inputMethodManager;

	private LinearLayout browserControlLayout, shareParent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.animation_in_up,
				R.anim.animation_out_up);

		setContentView(R.layout.layout_activity_browser);
		initialize();
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.animation_in_down,
				R.anim.animation_out_down);
	}

	@SuppressLint({ "SetJavaScriptEnabled", "DefaultLocale" })
	private void initialize() {

		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		browserControlLayout = (LinearLayout) findViewById(R.id.browserControlLayout);
		shareParent = (LinearLayout) findViewById(R.id.shareParent);

		btnClose = (IconView) findViewById(R.id.btnClose);
		btnRefreshOrStop = (IconView) findViewById(R.id.btnRefreshOrStop);

		btnBackward = (IconView) findViewById(R.id.btnBackward);
		btnShare = (IconView) findViewById(R.id.btnShare);
		btnForward = (IconView) findViewById(R.id.btnForward);

		IconView xurjunSave = (IconView) findViewById(R.id.xurjunSave);
		xurjunSave.setText("\ue013");
		xurjunSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (Preferences.getUserName().length() == 0
						|| Preferences.getUserPassword().length() == 0) {
					final myAlertDialog ad = new myAlertDialog(
							ActivityBrowser.this);
					ad.setTitle("ئەسكەرتىش");
					ad.setMessage("سېستىمىغا كىرمەپسىز ، كىرگەندىن كىيىن ئاندىن خۇرجۇنغا سالالايسىز !");
					ad.setPositiveButton("بىلدىم", new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ad.dismiss();
						}
					});
				} else {
					XurjunPopup popup = new XurjunPopup(ActivityBrowser.this,
							btnShare);
					popup.InitView(0, null);
					popup.XurjunLink.setText(webView.getUrl());
					popup.XurjunName.setText(webView.getTitle());
					popup.showPopupWindow(btnShare);
					popup.setOnLoadDataFinishListener(new dbListener());
				}
			}
		});

		txtUrl = (EditText) findViewById(R.id.txtUrl);
		txtHint = (UyghurTextView) findViewById(R.id.txtHint);
		webView = (WebView) findViewById(R.id.webView);
		progressBar = (FlatProgressBar) findViewById(R.id.progressBar);

		progressBar.setPrimaryColor(Color.parseColor("#afc936"));
		progressBar.setSecondaryColor(Color.parseColor("#cfcfcf"));

		txtHint.setText("تور ئادرېسىنى كىرگۈزۈڭ");

		btnClose.setText("\ue021");
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.animation_in_down,
						R.anim.animation_out_down);
			}
		});

		btnRefreshOrStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (btnRefreshOrStop.getText().equals("\ue098")) {
					webView.reload();
				}
				if (btnRefreshOrStop.getText().equals("\ue003")) {
					webView.stopLoading();
				}
			}
		});

		btnBackward.setText("\ue006");
		btnBackward.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				webView.goBack();
			}
		});

		btnShare.setText("\ue011");
		btnShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupShare();
			}
		});

		btnForward.setText("\ue010");
		btnForward.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				webView.goForward();
			}
		});

		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				if (url.startsWith("file:///") == false)
					txtUrl.setText(url);

				btnRefreshOrStop.setText("\ue098");

				setButtonStyle();
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				if (url.startsWith("file:///") == false)
					txtUrl.setText(url);

				btnRefreshOrStop.setText("\ue003");

				setButtonStyle();
			}

			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				view.loadUrl("file:///android_asset/BrowserError.html");
				setButtonStyle();
			}

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				txtUrl.setText(url);
				btnRefreshOrStop.setText("\ue003");
				view.loadUrl(url);
				setButtonStyle();

				return true;
			}
		});

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				progressBar.setProgress(newProgress);
				if (newProgress == 100)
					progressBar.setProgress(0);
			}
		});

		txtUrl.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_GO
						|| actionId == EditorInfo.IME_NULL) {
					if (ActivityBrowser.this.getCurrentFocus() != null) {
						inputMethodManager.hideSoftInputFromWindow(
								getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
					}

					String url = txtUrl.getText().toString().trim();
					if (url.toLowerCase().startsWith("http://") == false)
						url = "http://" + url;

					txtUrl.setText(url);
					webView.loadUrl(url);

					return true;
				}
				return false;
			}
		});

		txtUrl.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (txtUrl.getText().length() > 0) {
					txtHint.setVisibility(View.INVISIBLE);
				} else {
					txtHint.setVisibility(View.VISIBLE);
					btnRefreshOrStop.setText("");
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
		});

		if (getIntent().getExtras() != null) {
			intentUrl = getIntent().getExtras().getString("url");
			if (intentUrl != null || intentUrl.length() != 0) {
				intentUrl = intentUrl.trim();
				txtUrl.setText(intentUrl);
				webView.loadUrl(intentUrl);
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void popupShare() {
		LinearLayout popupLayout = (LinearLayout) LayoutInflater.from(this)
				.inflate(R.layout.layout_widget_popup_share, null);
		IconView iconDelta = (IconView) popupLayout
				.findViewById(R.id.iconDelta);
		iconDelta.setText("\ue028");

		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iconDelta
				.getLayoutParams();
		layoutParams.leftMargin = shareParent.getLeft()
				- ((App.DISPLAY.getWidth() - App.getPixelFromDp(320)) / 2)
				+ shareParent.getWidth() / 2 - App.getPixelFromDp(15) / 2;
		iconDelta.setLayoutParams(layoutParams);

		IconView btnShareToWechat = (IconView) popupLayout
				.findViewById(R.id.btnShareToWechat);
		btnShareToWechat.setText("\ue029");
		btnShareToWechat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				App.Wechat.ShareToWechat(
						webView.getTitle(),
						"مەزكۇر ئادرېس جەزىرە تور بېكىتىنىڭ ئاندرويىد نۇسخىسىدىن ھەمبەھىرلەنگەن.",
						webView.getUrl(), false);
			}
		});

		IconView btnShareToTimeline = (IconView) popupLayout
				.findViewById(R.id.btnShareToTimeline);
		btnShareToTimeline.setText("\ue02a");
		btnShareToTimeline.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				App.Wechat.ShareToWechat(
						webView.getTitle(),
						"مەزكۇر ئادرېس جەزىرە تور بېكىتىنىڭ ئاندرويىد نۇسخىسىدىن ھەمبەھىرلەنگەن.",
						webView.getUrl(), true);
			}
		});

		PopupWindow popupWindow = new PopupWindow(popupLayout,
				App.getPixelFromDp(320), App.getPixelFromDp(95));
		popupWindow.setContentView(popupLayout);
		popupWindow.setOutsideTouchable(false);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

		popupWindow.showAtLocation(btnShare, Gravity.BOTTOM | Gravity.CENTER,
				0, browserControlLayout.getHeight() + App.getPixelFromDp(2));
	}

	private void setButtonStyle() {
		if (webView.canGoBack()) {
			btnBackward.setTextColor(Color.parseColor("#666666"));
		} else {
			btnBackward.setTextColor(Color.parseColor("#b9b9b9"));
		}

		if (webView.canGoForward()) {
			btnForward.setTextColor(Color.parseColor("#666666"));
		} else {
			btnForward.setTextColor(Color.parseColor("#b9b9b9"));
		}
	}
	
	public class dbListener implements OnLoadDataFinishListener {

		@Override
		public void onClickPost(int type) {
			// TODO Auto-generated method stub
			AToast toast = new AToast(ActivityBrowser.this);
			if (type == 0) {
				toast.show("يوللىنىۋاتىدۇ ، تەخىر قىلىڭ ...", 3000);
			} 
		}

		@Override
		public void onLoadDataFinish(String reString, int PopupType) {
			// TODO Auto-generated method stub
			if (PopupType == 0 && reString.equals("OK")) {
				AToast toast = new AToast(ActivityBrowser.this);
				toast.show("مۇۋاپىقىيەتلىك يوللاندى.", 3000);
			}
		
			if (reString.equals("POSTERROR") || reString.equals("LOGINERROR")) {

				AToast toast = new AToast(ActivityBrowser.this);
				toast.show("سېستىما ئالدىراش ، قايتا سىناڭ !", 3000);
			}
			if (reString.equals("NetWorkError")) {
				AToast toast = new AToast(ActivityBrowser.this);
				toast.show("ئۈسكۈنىڭىز تورغا ئۇلانمىغان ، قايتا سىناڭ !", 3000);
			}
		}
	}
}
