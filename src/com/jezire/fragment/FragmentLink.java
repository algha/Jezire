package com.jezire.fragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.jezire.App;
import com.jezire.R;
import com.jezire.activity.ActivityBrowser;
import com.jezire.http.AsyncHttpClient;
import com.jezire.http.AsyncHttpResponseHandler;
import com.jezire.universalimageloader.DisplayImageOptions;
import com.jezire.widget.IconView;
import com.jezire.widget.LinkButton;
import com.jezire.widget.UyghurTextView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FragmentLink extends FragmentActivity {
	private IconView btnMenu, btnUser;
	private LinearLayout linkRow1, linkRow2, linkRow3;
	private UyghurTextView txtTitle;
	private View imgLoading, layoutLoading, layoutFailure, scrollView;
	private ImageView imgAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize();
	}

	private void initialize() {
		View contentView = getLayoutInflater().inflate(R.layout.layout_fragment_link, null);
		initializeView(contentView);
		setContentView(contentView);

		loadData();
	}

	private void initializeView(View contentView) {
		imgLoading = contentView.findViewById(R.id.imgLoading);
		layoutLoading = contentView.findViewById(R.id.layoutLoading);
		layoutFailure = contentView.findViewById(R.id.layoutFailure);
		scrollView = contentView.findViewById(R.id.scrollView);

		imgAd = (ImageView) contentView.findViewById(R.id.imgAd);
		imgAd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getTag() != null) {
					String url = (String) v.getTag();
					Intent intent = new Intent(FragmentLink.this, ActivityBrowser.class);
					intent.putExtra("url", url);
					startActivity(intent);
				}
			}
		});

		View btnretry = contentView.findViewById(R.id.btnRetry);
		btnretry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadData();
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

		btnMenu = (IconView) contentView.findViewById(R.id.btnMenu);
		btnMenu.setText("\ue067");
		btnMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				App.SLIDE_MENU_MAIN.open(false, true);
			}
		});

		btnUser = (IconView) contentView.findViewById(R.id.btnUser);
		btnUser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				App.SLIDE_MENU_MAIN.open(true, true);
			}
		});
		btnUser.setText("\ue005");

		linkRow1 = (LinearLayout) contentView.findViewById(R.id.linkRow1);
		linkRow2 = (LinearLayout) contentView.findViewById(R.id.linkRow2);
		linkRow3 = (LinearLayout) contentView.findViewById(R.id.linkRow3);
		txtTitle = (UyghurTextView) contentView.findViewById(R.id.txtTitle);

		txtTitle.setText(this.getIntent().getExtras().getString("title"));
		txtTitle.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);

		App.FRAGMENT_LINK_LIST.set(Integer.parseInt(this.getIntent().getExtras().getString("index")), this);
	}

	private void loadData() {
		showLayout(layoutLoading);

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "?h=phone&t=links&f=getLinksByCat&catid=" + this.getIntent().getExtras().getString("category"), new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadLink(responseBody);
				loadDataAd();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				showLayout(layoutFailure);
			}
		});

	}

	private void loadDataAd() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=system&f=GetAndroidAds", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadAd(responseBody);
				showLayout(scrollView);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				showLayout(layoutFailure);
			}
		});
	}

	private void loadAd(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONObject obj = new JSONObject(response);
				JSONObject objad = obj.getJSONObject("IndexTop");
				if (objad != null) {
					String image = objad.getString("image");
					String url = objad.getString("url");

					if (image != null && image.length() > 0 && url != null && image.length() > 0) {
						imgAd.setTag(url);
						App.IMAGE_LOADER.displayImage(image, imgAd, new DisplayImageOptions.Builder().cacheOnDisc(true).build());
					}
				}
			}
		} catch (Exception e) {
		}
	}

	private void loadLink(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONArray array = new JSONArray(response);
				if (array != null && array.length() > 0) {

					int flag = 0;
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);

						String url = obj.getString("phone_linkadress");
						String name = obj.getString("linkname");
						String isTop = obj.getString("linkcolor");

						if ((url != null && url.length() > 0) && (name != null && name.length() > 0) && (isTop != null && isTop.length() > 0)) {
							flag += 1;

							LinkButton btn = new LinkButton();
							btn.setText(name);
							btn.setTag(url);

							if (Integer.parseInt(isTop) == 0)
								btn.setButtonStyle(false);
							else
								btn.setButtonStyle(true);

							btn.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									if (v.getTag() != null) {
										String url = (String) v.getTag();

										Intent intent = new Intent(FragmentLink.this, ActivityBrowser.class);
										intent.putExtra("url", url);

										startActivity(intent);
									}
								}
							});

							if (flag == 1) {
								linkRow1.addView(btn);
							} else if (flag == 2) {
								linkRow2.addView(btn);
							} else {
								btn.setLeftBorder(false);
								linkRow3.addView(btn);
							}

							if (flag == 3)
								flag = 0;
						}
					}
				}
			}
		} catch (Exception e) {

		}
	}

	private void showLayout(View v) {
		imgLoading.clearAnimation();
		layoutLoading.setVisibility(View.GONE);
		layoutFailure.setVisibility(View.GONE);
		scrollView.setVisibility(View.GONE);

		if (v.equals(layoutLoading)) {
			App.startLoadingAnimation(imgLoading);
		}

		v.setVisibility(View.VISIBLE);
	}
}
