package com.jezire.activity;

import java.util.ArrayList;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.jezire.App;
import com.jezire.App.Preferences;
import com.jezire.R;
import com.jezire.algha.AToast;
import com.jezire.algha.XurjunPopup;
import com.jezire.algha.XurjunPopup.OnLoadDataFinishListener;
import com.jezire.http.AsyncHttpClient;
import com.jezire.http.AsyncHttpResponseHandler;
import com.jezire.widget.IconView;
import com.jezire.widget.UyghurTextView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;

@SuppressLint("ResourceAsColor")
public class ActivityXurjun extends Activity {
	private IconView btnClose, btnAddXurjun, SelectedColor;
	private UyghurTextView txtTitle;
	private ArrayList<XurjunEntry> xurjunEntries;
	private ListView mylisListView;
	private Integer page;
	private XurjunAdapter adapter;
	private View Footer;
	private Boolean IsEnd = false, loadFinishBoolean = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		overridePendingTransition(R.anim.animation_in_up, R.anim.animation_out_up);
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_xurjun);

		initialize();
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.animation_in_down, R.anim.animation_out_down);
	}
	
	private void initialize() {
		btnClose = (IconView) findViewById(R.id.btnClose);
		btnClose.setText("\ue021");
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
				overridePendingTransition(R.anim.animation_in_down, R.anim.animation_out_down);
				finish();
			}
		});

		xurjunEntries = new ArrayList<XurjunEntry>();

		Footer = getLayoutInflater().inflate(R.layout.layout_xurjun_footer,null);

		txtTitle = (UyghurTextView) findViewById(R.id.txtTitle);
		txtTitle.setText("خۇرجۇن");

		btnAddXurjun = (IconView) findViewById(R.id.btnAddXurjun);
		btnAddXurjun.setText("\ue012");
		btnAddXurjun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				XurjunPopup popup = new XurjunPopup(ActivityXurjun.this,
						btnAddXurjun);
				popup.InitView(0, null);
				popup.showPopupWindow(btnAddXurjun);
				popup.setOnLoadDataFinishListener(new dbListener());
			}
		});

		mylisListView = (ListView) findViewById(R.id.XurjunList);
		mylisListView.setOnScrollListener(new MyOnScrollListener());
		mylisListView.setOnItemLongClickListener(new itemLongListener());

		mylisListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				XurjunEntry entry = xurjunEntries.get(position);
				Intent intent = new Intent(ActivityXurjun.this,
						ActivityBrowser.class);
				intent.putExtra("url", entry.urladdress);
				startActivity(intent);
			}
		});

		IconView brown = (IconView) findViewById(R.id.brown);
		brown.setText("\ue008");
		brown.setOnClickListener(new ColorClickListener());

		IconView red = (IconView) findViewById(R.id.red);
		red.setText("\ue008");
		red.setOnClickListener(new ColorClickListener());

		IconView yellow = (IconView) findViewById(R.id.yellow);
		yellow.setText("\ue008");
		yellow.setOnClickListener(new ColorClickListener());

		IconView blue = (IconView) findViewById(R.id.blue);
		blue.setText("\ue008");
		blue.setOnClickListener(new ColorClickListener());

		IconView green = (IconView) findViewById(R.id.green);
		green.setText("\ue008");
		green.setOnClickListener(new ColorClickListener());

		IconView cheyze = (IconView) findViewById(R.id.cheyze);
		cheyze.setText("\ue008");
		cheyze.setOnClickListener(new ColorClickListener());

		page = 0;

		adapter = new XurjunAdapter(this);
		mylisListView.addFooterView(Footer);
		mylisListView.setAdapter(adapter);
		mylisListView.removeFooterView(Footer);

		ShowLoading(true);

		LoadData("all");
	}

	public void ShowLoading(boolean isLoading) {
		View LoadingLayout = (View) findViewById(R.id.layoutLoading);
		ImageView imgLoading = (ImageView) findViewById(R.id.imgLoading);
		if (isLoading == true) {
			LoadingLayout.setVisibility(View.VISIBLE);
			App.startLoadingAnimation(imgLoading);
		} else {
			LoadingLayout.setVisibility(View.GONE);
			imgLoading.clearAnimation();
		}
	}

	public void ShowError(boolean error) {
		final View layoutFailure = (View) findViewById(R.id.layoutFailure);
		View btnRetry = (View) findViewById(R.id.btnRetry);
		if (error == true) {
			layoutFailure.setVisibility(View.VISIBLE);
			btnRetry.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					LoadData("all");
					layoutFailure.setVisibility(View.GONE);
					ShowLoading(true);
				}
			});
		} else {
			layoutFailure.setVisibility(View.GONE);
		}
	}

	public void PageLoading(boolean isLoading) {
		if (isLoading == true) {
			mylisListView.addFooterView(Footer);
			ImageView animImageView = (ImageView) Footer
					.findViewById(R.id.imgLoading);
			App.startLoadingAnimation(animImageView);
		} else if (isLoading == false) {
			ImageView animImageView = (ImageView) Footer
					.findViewById(R.id.imgLoading);
			animImageView.clearAnimation();
			mylisListView.removeFooterView(Footer);
		}
	}

	public class MyOnScrollListener implements OnScrollListener {
		@Override
		public void onScroll(AbsListView View, int VisibleItem,
				int VisibleItemCount, int TotalItemCount) {
			// TODO Auto-generated method stub
			final int totalLoad = TotalItemCount;
			int lastItemId = mylisListView.getLastVisiblePosition();
			if (xurjunEntries.size() >= 20 && (lastItemId + 1) == totalLoad
					&& loadFinishBoolean == true && IsEnd == false) {
				loadFinishBoolean = false;
				PageLoading(true);
				page++;
				String Color = "all";
				if (SelectedColor != null) {
					Color = SelectedColor.getTag().toString();
				}
				LoadData(Color);
			}
		}

		@Override
		public void onScrollStateChanged(AbsListView arg0, int arg1) {
			// TODO Auto-generated method stub

		}
	}

	public class ColorClickListener implements IconView.OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			IconView colorview = (IconView) v;

			if (colorview == SelectedColor) {
				ShowLoading(true);
				colorview.setText("\ue008");
				xurjunEntries.clear();
				adapter.notifyDataSetChanged();
				IsEnd = false;
				page = 0;
				LoadData("all");
				SelectedColor = null;
				return;
			}

			if (SelectedColor != null && SelectedColor != colorview) {
				SelectedColor.setText("\ue008");
			}

			String Color = colorview.getTag().toString();

			ShowLoading(true);

			xurjunEntries.clear();
			adapter.notifyDataSetChanged();
			IsEnd = false;
			page = 0;
			LoadData(Color);

			SelectedColor = colorview;
			SelectedColor.setText("\ue007");

			adapter.notifyDataSetChanged();
		}
	}

	public void LoadData(String color) {

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(GetUrl(new String[] { color }),
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub
						SetListViewData(responseBody);
						loadFinishBoolean = true;
						txtTitle.setText("خۇرجۇن");
						PageLoading(false);
						ShowLoading(false);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						// TODO Auto-generated method stub
						loadFinishBoolean = true;
						PageLoading(false);
						ShowLoading(false);
						if (page == 0) {
							ShowError(true);
						}
					}
				});
	}

	public void SetListViewData(byte[] responseBody) {
		try {
			String Response = new String(responseBody);

			if (Response != null && Response.length() > 0) {
				JSONArray Arr = new JSONArray(Response);
				if (Arr != null && Arr.length() > 0) {
					for (int i = 0; i < Arr.length(); i++) {
						JSONObject object = Arr.getJSONObject(i);
						XurjunEntry entry = new XurjunEntry();
						entry.id = object.getString("id");
						entry.urladdress = object.getString("urladdress");
						entry.urlname = object.getString("urlname");
						entry.urlsign = object.getString("urlsign");
						xurjunEntries.add(entry);
					}
					adapter.notifyDataSetChanged();
				} else {
					IsEnd = true;
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("xurjun get exaption : ", "" + e);
		}
	}

	public String GetUrl(String[] urlInfo) {
		String color = urlInfo[0];
		String pagesize = "20";

		String url;

		if (color.equals("all")) {
			url = "http://new.jezire.cn/?h=phone&t=xurjun&f=ListXurjun&nam="
					+ Preferences.getUserName() + "&im="
					+ Preferences.getUserPassword() + "&p=" + page
					+ "&pagesize=" + pagesize;
		} else {
			url = App.SERVER_URL + "?h=phone&t=xurjun&f=ListXurjun&sign="
					+ color + "&nam=" + Preferences.getUserName() + "&im="
					+ Preferences.getUserPassword() + "&p=" + page
					+ "&pagesize=" + pagesize;
		}

		return url;
	}

	public class XurjunAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private Context mContext = null;

		public XurjunAdapter(Context context) {
			mContext = context;
			mInflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return xurjunEntries.size();
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return xurjunEntries.get(arg0);
		}

		@Override
		public int getItemViewType(int position) {
			// TODO Auto-generated method stub
			return super.getItemViewType(position);
		}

		@Override
		public View getView(int arg0, View view, ViewGroup arg2) {
			// TODO Auto-generated method stub
			UyghurTextView XurjunTitle, XurjunUrl, XurjunNumber;
			View XurjunColor;

			if (view == null) {
				view = mInflater
						.inflate(R.layout.layout_xurjun_list_item, null);
			}

			XurjunTitle = (UyghurTextView) view.findViewById(R.id.XurjunTitle);
			XurjunUrl = (UyghurTextView) view.findViewById(R.id.XurjunUrl);
			XurjunColor = (View) view.findViewById(R.id.XurjunColor);
			XurjunNumber = (UyghurTextView) view
					.findViewById(R.id.XurjunNumber);

			XurjunEntry entry = xurjunEntries.get(arg0);
			XurjunTitle.setText(entry.urlname.toString());
			XurjunUrl.setText(entry.urladdress.toString());

			SetColor(entry.urlsign.toString(), XurjunColor);

			int xn = arg0 + 1;
			if (xn < 10) {
				XurjunNumber.setText("0" + xn);
			} else {
				XurjunNumber.setText("" + xn);
			}

			return view;
		}

		public void SetColor(String colorString, View iconView) {
			if (colorString.equals("borwn") == true) {
				iconView.setBackgroundColor(Color.parseColor("#707070"));
			} else if (colorString.equals("red") == true) {
				iconView.setBackgroundColor(Color.parseColor("#ed1c24"));
			} else if (colorString.equals("yellow")) {
				iconView.setBackgroundColor(Color.parseColor("#fbaf5d"));
			} else if (colorString.equals("green")) {
				iconView.setBackgroundColor(Color.parseColor("#7cc576"));
			} else if (colorString.equals("blue")) {
				iconView.setBackgroundColor(Color.parseColor("#00aeef"));
			} else if (colorString.equals("cheyze")) {
				iconView.setBackgroundColor(Color.parseColor("#8560a8"));
			} else {
				iconView.setBackgroundColor(Color.parseColor("#707070"));
			}
		}

		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return super.getViewTypeCount();
		}
	}

	public class itemLongListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub

			LayoutInflater infiLayoutInflater = LayoutInflater
					.from(ActivityXurjun.this);
			View xurjunLongClickView = (View) infiLayoutInflater.inflate(
					R.layout.xurjun_long_click, null);

			final PopupWindow popupWindow = new PopupWindow(
					xurjunLongClickView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			popupWindow.setFocusable(true);
			popupWindow.setBackgroundDrawable(new ColorDrawable(Color
					.parseColor("#80000000")));
			popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

			xurjunLongClickView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popupWindow.dismiss();
				}
			});

			final XurjunEntry entry = xurjunEntries.get(position);
			final int theposition = position;

			UyghurTextView deleTextView = (UyghurTextView) xurjunLongClickView
					.findViewById(R.id.delete);
			deleTextView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popupWindow.dismiss();
					
					AToast toast = new AToast(ActivityXurjun.this);
					toast.show("ئۆچۈرۈلىۋاتىدۇ ، تەخىر قىلىڭ ...", 3000);
					
					String DeleteUrl = App.SERVER_URL+"?h=phone&t=xurjun&f=delete&nam="+Preferences.getUserName()+"&im="+Preferences.getUserPassword()+"&id="+entry.id;
					
					AsyncHttpClient client = new AsyncHttpClient();
					client.get(DeleteUrl, new AsyncHttpResponseHandler() {
						
						@Override
						public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
							// TODO Auto-generated method stub
							
							String reString = new String(responseBody);
							
							Log.d("DEBUG", reString);
							
							if (reString.equals("OK")) {
								xurjunEntries.remove(theposition);
								adapter.notifyDataSetChanged();
								AToast toast = new AToast(ActivityXurjun.this);
								toast.show("مۇۋاپىقىيەتلىك ئۆچۈرۈلدى .", 3000);
							}
							
							if (reString.equals("LOGIN_ERROR") || reString.equals("NOT_FOUND_XURJUN")) {

								AToast toast = new AToast(ActivityXurjun.this);
								toast.show("سېستىما ئالدىراش ، قايتا سىناڭ !", 3000);
							}
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							// TODO Auto-generated method stub
							AToast toast = new AToast(ActivityXurjun.this);
							toast.show("ئۈسكۈنىڭىز تورغا ئۇلانمىغان ، قايتا سىناڭ !", 3000);
						}
					});
					
				}
			});

			UyghurTextView editTextView = (UyghurTextView) xurjunLongClickView
					.findViewById(R.id.edit);
			editTextView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					popupWindow.dismiss();
					// TODO Auto-generated method stub
					XurjunPopup popup = new XurjunPopup(ActivityXurjun.this,
							btnAddXurjun);
					popup.InitView(1, entry);
					popup.showPopupWindow(btnAddXurjun);
					popup.setOnLoadDataFinishListener(new dbListener());
				}
			});

			return true;
		}

	}

	public class dbListener implements OnLoadDataFinishListener {

		@Override
		public void onClickPost(int type) {
			// TODO Auto-generated method stub
			AToast toast = new AToast(ActivityXurjun.this);
			if (type == 0) {
				toast.show("قۇشۇلىۋاتىدۇ ، تەخىر قىلىڭ ...", 3000);
			} else {
				toast.show("ساقلىنىۋاتىدۇ ، تەخىر قىلىڭ ...", 3000);
			}
		}

		@Override
		public void onLoadDataFinish(String reString, int PopupType) {
			// TODO Auto-generated method stub
			if (PopupType == 0 && reString.equals("OK")) {
				txtTitle.setText("يىڭىلىنىۋاتىدۇ ...");

				if (SelectedColor != null) {
					SelectedColor.setText("\ue008");
					SelectedColor = null;
				}
				page = 0;

				xurjunEntries.clear();

				LoadData("all");

				AToast toast = new AToast(ActivityXurjun.this);
				toast.show("مۇۋاپىقىيەتلىك يوللاندى.", 3000);
			}
			if (PopupType == 1 && reString.equals("OK")) {
				txtTitle.setText("يىڭىلىنىۋاتىدۇ ...");

				if (SelectedColor != null) {
					SelectedColor.setText("\ue008");
					SelectedColor = null;
				}

				page = 0;

				xurjunEntries.clear();

				LoadData("all");

				AToast toast = new AToast(ActivityXurjun.this);
				toast.show("مۇۋاپىقىيەتلىك تەھرىرلەندى.", 3000);
			}
			if (reString.equals("POSTERROR") || reString.equals("LOGINERROR")) {

				AToast toast = new AToast(ActivityXurjun.this);
				toast.show("سېستىما ئالدىراش ، قايتا سىناڭ !", 3000);
			}
			if (reString.equals("NetWorkError")) {
				AToast toast = new AToast(ActivityXurjun.this);
				toast.show("ئۈسكۈنىڭىز تورغا ئۇلانمىغان ، قايتا سىناڭ !", 3000);
			}
		}
	}

	public class XurjunEntry {
		public String id;
		public String urladdress;
		public String urlname;
		public String urlsign;
	}
}
