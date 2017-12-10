package com.jezire.activity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.jezire.App;
import com.jezire.R;
import com.jezire.http.AsyncHttpClient;
import com.jezire.http.AsyncHttpResponseHandler;
import com.jezire.universalimageloader.DisplayImageOptions;
import com.jezire.widget.IconView;
import com.jezire.widget.UyghurTextView;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class ActivityVideoInfo extends Activity {
	private String id;
	private View imgLoading, layoutLoading, layoutFailure, scrollView, layoutControl;
	private UyghurTextView btnInfo, btnQisim;
	private View tabInfo;
	private LinearLayout tabQisim;
	private UyghurTextView txtName, txtRegion, txtLang, txtCategory, txtTime, txtInfo;
	private ImageView imgImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.animation_in_up, R.anim.animation_out_up);

		setContentView(R.layout.layout_activity_video_info);
		initialize();
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.animation_in_down, R.anim.animation_out_down);
	}

	private void initialize() {
		if (getIntent().getExtras() == null) {
			onBackPressed();
		}

		id = getIntent().getExtras().getString("id");

		imgLoading = findViewById(R.id.imgLoading);
		layoutLoading = findViewById(R.id.layoutLoading);
		layoutFailure = findViewById(R.id.layoutFailure);
		scrollView = findViewById(R.id.scrollView);
		layoutControl = findViewById(R.id.layoutControl);

		btnInfo = (UyghurTextView) findViewById(R.id.btnInfo);
		btnQisim = (UyghurTextView) findViewById(R.id.btnQisim);
		tabInfo = findViewById(R.id.tabInfo);
		tabQisim = (LinearLayout) findViewById(R.id.tabQisim);

		imgImage = (ImageView) findViewById(R.id.imgImage);
		txtName = (UyghurTextView) findViewById(R.id.txtName);
		txtRegion = (UyghurTextView) findViewById(R.id.txtRegion);
		txtLang = (UyghurTextView) findViewById(R.id.txtLang);
		txtCategory = (UyghurTextView) findViewById(R.id.txtCategory);
		txtTime = (UyghurTextView) findViewById(R.id.txtTime);
		txtInfo = (UyghurTextView) findViewById(R.id.txtInfo);

		UyghurTextView title = (UyghurTextView) findViewById(R.id.txtTitle);
		title.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		title.setText("فىلىم تەپسىلاتى");

		View btnretry = findViewById(R.id.btnRetry);
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

		IconView btnClose = (IconView) findViewById(R.id.btnClose);
		btnClose.setText("\ue021");
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		IconView btnShare = (IconView) findViewById(R.id.btnShare);
		btnShare.setText("\ue011");
		btnShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupShare();
			}
		});

		btnInfo.setText("فىلىم ھەققىدە");
		btnInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeTab(btnInfo);
			}
		});

		btnQisim.setText("فىلىم قىسىملىرى");
		btnQisim.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeTab(btnQisim);
			}
		});

		changeTab(btnQisim);

		loadData();
	}

	@SuppressWarnings("deprecation")
	private void popupShare() {
		LinearLayout popupLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_widget_popup_share, null);
		IconView iconDelta = (IconView) popupLayout.findViewById(R.id.iconDelta);
		iconDelta.setText("\ue028");

		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iconDelta.getLayoutParams();
		layoutParams.leftMargin = layoutControl.getLeft() - ((App.DISPLAY.getWidth() - App.getPixelFromDp(320)) / 2) + layoutControl.getWidth() / 2 - App.getPixelFromDp(15) / 2;
		iconDelta.setLayoutParams(layoutParams);

		IconView btnShareToWechat = (IconView) popupLayout.findViewById(R.id.btnShareToWechat);
		btnShareToWechat.setText("\ue029");
		btnShareToWechat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				App.Wechat.ShareToWechat(txtName.getText().toString(), "فىلىم ھەققىدە: " + txtInfo.getText().toString().trim(), App.SERVER_URL + "?h=phone&t=video&f=OnBrowser&id=" + id, false);
			}
		});

		IconView btnShareToTimeline = (IconView) popupLayout.findViewById(R.id.btnShareToTimeline);
		btnShareToTimeline.setText("\ue02a");
		btnShareToTimeline.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				App.Wechat.ShareToWechat(txtName.getText().toString(), "فىلىم ھەققىدە: " + txtInfo.getText().toString().trim(), App.SERVER_URL + "?h=phone&t=video&f=OnBrowser&id=" + id, true);
			}
		});

		PopupWindow popupWindow = new PopupWindow(popupLayout, App.getPixelFromDp(320), App.getPixelFromDp(95));
		popupWindow.setContentView(popupLayout);
		popupWindow.setOutsideTouchable(false);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

		popupWindow.showAtLocation(layoutControl, Gravity.BOTTOM | Gravity.CENTER, 0, layoutControl.getHeight() + App.getPixelFromDp(2));
	}

	private void changeTab(View btn) {
		tabInfo.setVisibility(View.GONE);
		tabQisim.setVisibility(View.GONE);
		btnInfo.setTextColor(Color.parseColor("#979797"));
		btnQisim.setTextColor(Color.parseColor("#979797"));
		btnInfo.setBackgroundColor(Color.parseColor("#ebebeb"));
		btnQisim.setBackgroundColor(Color.parseColor("#ebebeb"));

		if (btn.equals(btnInfo)) {
			btnInfo.setTextColor(Color.parseColor("#605ca8"));
			btnInfo.setBackgroundColor(Color.parseColor("#ffffff"));
			tabInfo.setVisibility(View.VISIBLE);
		}

		if (btn.equals(btnQisim)) {
			btnQisim.setTextColor(Color.parseColor("#605ca8"));
			btnQisim.setBackgroundColor(Color.parseColor("#ffffff"));
			tabQisim.setVisibility(View.VISIBLE);
		}
	}

	private void showLayout(View v) {
		imgLoading.clearAnimation();
		layoutLoading.setVisibility(View.GONE);
		layoutFailure.setVisibility(View.GONE);
		scrollView.setVisibility(View.GONE);
		layoutControl.setVisibility(View.GONE);

		if (v.equals(scrollView)) {
			layoutControl.setVisibility(View.VISIBLE);
		}

		if (v.equals(layoutLoading)) {
			App.startLoadingAnimation(imgLoading);
		}

		v.setVisibility(View.VISIBLE);
	}

	private void loadData() {
		showLayout(layoutLoading);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "?h=phone&t=video&f=getOne&id=" + id, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				showInfo(responseBody);
				loadOtherInfo();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				showLayout(layoutFailure);
			}
		});
	}

	private void showInfo(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONObject obj = new JSONObject(response);
				String name = obj.getString("name");
				String img = obj.getString("orginal");

				if ((name != null && name.length() > 0) && (img != null && img.length() > 0)) {
					txtName.setText(name);
					App.IMAGE_LOADER.displayImage(App.SERVER_URL + "/" + img, imgImage, new DisplayImageOptions.Builder().cacheOnDisc(true).build());
				}
			}
		} catch (Exception e) {
		}
	}

	private void loadOtherInfo() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "?h=phone&t=video&f=otherInfo&id=" + id, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				showOtherInfo(responseBody);
				showLayout(scrollView);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				showLayout(layoutFailure);
			}
		});
	}

	private void showOtherInfo(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONObject obj = new JSONObject(response);
				String region = obj.getString("region");
				String time = obj.getString("time");
				String lang = obj.getString("lang");
				String category = obj.getString("category");
				String description = obj.getString("description");
				JSONArray qisim = obj.getJSONArray("qisim");

				if ((region != null && region.length() > 0) && (time != null && time.length() > 0) && (lang != null && lang.length() > 0) && (category != null && category.length() > 0) && (description != null && description.length() > 0)) {
					txtRegion.setText("رايون: " + region);
					txtTime.setText("ۋاقتى: " + time);
					txtLang.setText("تىلى: " + lang);
					txtCategory.setText("تۈرى: " + category);
					txtInfo.setText("    " + description);
				}

				if (qisim != null && qisim.length() > 0) {
					int count = qisim.length();
					int row = (int) Math.floor(count / 3);
					if (count % 3 != 0) {
						row += 1;
					}

					int index = 0;
					for (int i = 0; i < row; i++) {
						LinearLayout rowView = new LinearLayout(getBaseContext());
						rowView.setOrientation(LinearLayout.HORIZONTAL);
						rowView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, App.getPixelFromDp(50)));

						for (int j = index; j < index + 3; j++) {
							LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
							p.setMargins(App.getPixelFromDp(5), App.getPixelFromDp(5), App.getPixelFromDp(5), App.getPixelFromDp(5));

							if (j < count) {
								UyghurTextView utv = new UyghurTextView(getBaseContext());
								utv.setLayoutParams(p);
								utv.setText(String.valueOf(j + 1) + "-قىسىم");
								utv.setTextColor(Color.parseColor("#ffffff"));
								utv.setBackgroundColor(Color.parseColor("#fbaf5d"));
								utv.setGravity(Gravity.CENTER | Gravity.CENTER);

								String url = qisim.get(j).toString();

								if (url != null && url.length() > 0) {
									final String qisimidx = String.valueOf(j);
									utv.setTag(url);
									utv.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											showPlayer(qisimidx);
										}
									});
								}

								rowView.addView(utv, 0);
							} else {
								View v = new View(getBaseContext());
								v.setBackgroundColor(Color.TRANSPARENT);
								v.setLayoutParams(p);
								rowView.addView(v, 0);
							}
						}

						index += 3;
						tabQisim.addView(rowView);
					}
				}
			}
		} catch (Exception e) {
		}
	}

	private void showPlayer(String qisim) {
		showLayout(layoutLoading);
		
		int nqisim = Integer.parseInt(qisim);
		
		AsyncHttpClient client = new AsyncHttpClient();
		Log.d("DEBUG", ""+App.SERVER_URL + "?h=phone&t=video&f=geturl&id=" + id + "&qisim=" + nqisim+1 + "&os=android");
		client.get(App.SERVER_URL + "?h=phone&t=video&f=geturl&id=" + id + "&qisim=" + nqisim+1 + "&os=android", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				try {
					String response = new String(responseBody);
					if (response != null && response.length() > 0) {
						JSONObject obj = new JSONObject(response);
						String normal = obj.getString("normal");
						if (normal != null && normal.length() > 0) {
							showLayout(scrollView);

							Intent intent = new Intent(ActivityVideoInfo.this, ActivityVideoPlayer.class);
							intent.putExtra("url", normal);
							startActivity(intent);
						} else {
							showLayout(scrollView);
						}
					}
				} catch (Exception e) {
					showLayout(scrollView);
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				showLayout(layoutFailure);
			}
		});
	}
}
