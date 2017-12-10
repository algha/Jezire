package com.jezire.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import com.jezire.App;
import com.jezire.R;
import com.jezire.http.AsyncHttpClient;
import com.jezire.http.AsyncHttpResponseHandler;
import com.jezire.universalimageloader.DisplayImageOptions;
import com.jezire.widget.IconView;
import com.jezire.widget.TabButton;
import com.jezire.widget.UyghurTextView;
import com.jezire.xlistview.XListView;
import com.jezire.xlistview.XListView.IXListViewListener;

public class ActivityNews extends Activity {
	private IconView btnClose, btnTabControl;
	private LinearLayout tabNewsCategory;
	private HorizontalScrollView horizontalScrollView;
	private ViewPager viewPager;

	private ArrayList<View> pageList;
	private NewsPageAdapter pageAdapter;

	private LayoutInflater inflater;

	private HashMap<Integer, ArrayList<NewsEntry>> entryMap;

	private View imgLoading, layoutLoading, layoutFailure, layoutList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.animation_in_up, R.anim.animation_out_up);

		setContentView(R.layout.layout_activity_news);
		initialize();
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.animation_in_down, R.anim.animation_out_down);
	}

	private void initialize() {
		pageList = new ArrayList<View>();
		pageAdapter = new NewsPageAdapter();
		entryMap = new HashMap<Integer, ArrayList<NewsEntry>>();
		inflater = LayoutInflater.from(this);

		imgLoading = findViewById(R.id.imgLoading);
		layoutLoading = findViewById(R.id.layoutLoading);
		layoutFailure = findViewById(R.id.layoutFailure);
		layoutList = findViewById(R.id.layoutList);

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

		tabNewsCategory = (LinearLayout) findViewById(R.id.tabNewsCategory);
		horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
		viewPager = (ViewPager) findViewById(R.id.viewPager);

		UyghurTextView title = (UyghurTextView) findViewById(R.id.txtTitle);
		title.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		title.setText("خەۋەرلەر");

		btnClose = (IconView) findViewById(R.id.btnClose);
		btnTabControl = (IconView) findViewById(R.id.btnTabControl);

		btnClose.setText("\ue021");
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		btnTabControl.setText("\ue01d");
		btnTabControl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (btnTabControl.getText().equals("\ue01d")) {
					horizontalScrollView.smoothScrollTo(0, 0);
					btnTabControl.setText("\ue01f");
				} else {
					horizontalScrollView.smoothScrollTo(tabNewsCategory.getWidth(), 0);
					btnTabControl.setText("\ue01d");
				}
			}
		});

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				changeTab(position, true);
				if (!entryMap.containsKey(position)) {
					appendTopData(position, true);
				}
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
		});

		tabNewsCategory.addOnLayoutChangeListener(new OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
				horizontalScrollView.smoothScrollTo(right, 0);
			}
		});

		horizontalScrollView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (horizontalScrollView.getScrollX() == 0) {
					btnTabControl.setText("\ue01f");
				} else {
					btnTabControl.setText("\ue01d");
				}

				return false;
			}
		});

		viewPager.setAdapter(pageAdapter);

		loadData();
	}

	private void loadData() {
		showLayout(layoutLoading);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=news&f=getCat", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadXewerTurliri(responseBody);
				showLayout(layoutList);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				showLayout(layoutFailure);
			}
		});
	}

	private void loadXewerTurliri(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONArray array = new JSONArray(response);
				if (array != null && array.length() > 0) {
					ArrayList<Pair<String, String>> lst = new ArrayList<Pair<String, String>>();
					lst.add(0, new Pair<String, String>("-1", "باشبەت"));

					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);

						String id = obj.getString("id");
						String name = obj.getString("cat_name");

						if ((id != null && id.length() > 0) && (name != null && name.length() > 0)) {
							lst.add(0, new Pair<String, String>(id, name));
						}
					}
					makeTabAndPage(lst);
					viewPager.setCurrentItem(pageList.size() - 1);
				}
			}
		} catch (Exception ex) {
		}
	}

	private void makeTabAndPage(ArrayList<Pair<String, String>> lst) {
		for (int i = 0; i < lst.size(); i++) {
			Pair<String, String> p = lst.get(i);

			TabButton b = new TabButton();
			b.setTag(i);
			b.setText(p.second);

			b.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					changeTab(Integer.parseInt(v.getTag().toString()), false);
				}
			});

			tabNewsCategory.addView(b);

			View v = inflater.inflate(R.layout.layout_page_news, null);
			v.setTag(p.first);

			final int idx = i;
			final XListView listview = (XListView) v.findViewById(R.id.listView);
			listview.setPullRefreshEnable(true);
			listview.setXListViewListener(new IXListViewListener() {
				@Override
				public void onRefresh() {
					listview.setPullLoadEnable(false);
					entryMap.remove(idx);
					appendTopData(idx, false);
				}

				@Override
				public void onLoadMore() {
					appendData(idx, false);
				}
			});

			pageList.add(v);
		}

		pageAdapter.notifyDataSetChanged();
	}

	private void changeTab(int idx, boolean juststyle) {
		for (int i = 0; i < tabNewsCategory.getChildCount(); i++) {
			View c = tabNewsCategory.getChildAt(i);
			if (c.getClass().equals(TabButton.class)) {
				if (idx == Integer.parseInt(c.getTag().toString())) {
					((TabButton) c).setChecked(true);
					if (c.getLeft() < horizontalScrollView.getScrollX()) {
						horizontalScrollView.smoothScrollTo(c.getLeft(), 0);
					} else if (c.getRight() > horizontalScrollView.getScrollX() + horizontalScrollView.getWidth()) {
						int offset = c.getRight() - (horizontalScrollView.getScrollX() + horizontalScrollView.getWidth());
						horizontalScrollView.smoothScrollTo(horizontalScrollView.getScrollX() + offset, 0);
					}
				} else {
					((TabButton) c).setChecked(false);
				}
			}
		}

		if (!juststyle) {
			viewPager.setCurrentItem(idx);
		}
	}

	private void appendTopData(int idx, boolean loading) {
		final int index = idx;
		String id = pageList.get(index).getTag().toString();

		String url;
		if (id.equals("-1")) {
			url = App.SERVER_URL + "/?h=phone&t=news&f=getNadirByCat&catid=0";
		} else {
			url = App.SERVER_URL + "/?h=phone&t=news&f=getNadirByCat&catid=" + id;
		}

		if (loading) {
			showLayoutChild(index, R.id.layoutLoading);
		}
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				appendTopXewer(index, responseBody);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				View btnretry = pageList.get(index).findViewById(R.id.btnRetry);
				btnretry.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						appendTopData(index, true);
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
				showLayoutChild(index, R.id.layoutFailure);
			}
		});
	}

	private void appendTopXewer(int idx, byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONObject obj = new JSONObject(response);

				String id = obj.getString("id");
				String title = obj.getString("title");
				String image = obj.getString("orginal");

				if (id != null && id.length() > 0 && title != null && title.length() > 0 && image != null && image.length() > 0) {
					NewsEntry entry = new NewsEntry();
					entry.Id = id;
					entry.Title = title;
					entry.Image = image;

					entryMap.put(idx, new ArrayList<NewsEntry>());
					entryMap.get(idx).add(entry);
				}

				appendData(idx, false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void appendData(int idx, boolean loading) {
		final int index = idx;
		String id = pageList.get(index).getTag().toString();
		String page = entryMap.containsKey(index) ? String.valueOf(entryMap.get(index).size() / 10) : "0";

		String url;
		if (id.equals("-1")) {
			url = App.SERVER_URL + "/?h=phone&t=news&f=getLatest&p=" + page + "&pagesize=10";
		} else {
			url = App.SERVER_URL + "/?h=phone&t=news&f=getByCat&catid=" + id + "&p=" + page + "&pagesize=10";
		}

		if (loading) {
			showLayoutChild(index, R.id.layoutLoading);
		}
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				appendXewer(index, responseBody);
				showLayoutChild(index, R.id.listView);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				XListView list = (XListView) pageList.get(index).findViewById(R.id.listView);
				list.stopLoadMore();

				View btnretry = pageList.get(index).findViewById(R.id.btnRetry);
				btnretry.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						appendData(index, true);
					}
				});
				btnretry.setOnTouchListener(new OnTouchListener() {
					@SuppressLint("ClickableViewAccessibility") @Override
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
				showLayoutChild(index, R.id.layoutFailure);
			}
		});
	}

	private void appendXewer(int idx, byte[] responseBody) {
		XListView list = (XListView) pageList.get(idx).findViewById(R.id.listView);
		ListAdapter adapter = list.getAdapter();

		boolean flag = false;

		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONArray array = new JSONArray(response);
				if (array != null && array.length() > 0) {
					flag = true;
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);

						String id = obj.getString("id");
						String title = obj.getString("title");
						String time = obj.getString("created_at");
						String image = obj.getString("orginal");

						if (id != null && id.length() > 0 && title != null && title.length() > 0 && time != null && time.length() > 0 && image != null && image.length() > 0) {
							NewsEntry entry = new NewsEntry();
							entry.Id = id;
							entry.Title = title;
							entry.Time = time;
							entry.Image = image;

							entryMap.get(idx).add(entry);
						}
					}

					if (adapter == null) {
						list.setAdapter(new NewsListAdapter(idx));
					} else {
						((NewsListAdapter) adapter).notifyDataSetChanged();
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		list.setPullLoadEnable(flag);
		list.stopRefresh();
		list.stopLoadMore();
		// v.setRefreshTime("");
	}

	@SuppressWarnings("deprecation")
	private String getTimeText(String time) {
		long svr = Long.parseLong(time + "000");
		String ret = new SimpleDateFormat("M-ئاينىڭ d-كۈنى H دىن m ئۆتكەندە", Locale.getDefault()).format(new Date(svr));

		Date dts = new Date(svr);
		Date now = new Date(System.currentTimeMillis());
		if (now.getYear() == dts.getYear() && now.getMonth() == dts.getMonth() && now.getDay() == dts.getDay()) {
			if (now.getHours() - dts.getHours() > 0 && now.getHours() - dts.getHours() <= 12) {
				ret = String.valueOf(now.getHours() - dts.getHours()) + " سائەت بۇرۇن";
			}

			if (now.getHours() == dts.getHours()) {

				if (now.getMinutes() - dts.getMinutes() > 0 && now.getMinutes() - dts.getMinutes() <= 59) {
					ret = String.valueOf(now.getMinutes() - dts.getMinutes()) + " مىنۇت بۇرۇن";
				}

				if (now.getMinutes() == dts.getMinutes()) {
					ret = "ھازىر";
				}
			}
		}

		return ret;
	}

	private void showLayoutChild(int idx, int res) {
		View p = pageList.get(idx);

		View v = p.findViewById(res);
		View imgloading = p.findViewById(R.id.imgLoading);
		View layoutloading = p.findViewById(R.id.layoutLoading);
		View layoutfailure = p.findViewById(R.id.layoutFailure);
		View listview = p.findViewById(R.id.listView);

		imgloading.clearAnimation();
		layoutloading.setVisibility(View.GONE);
		layoutfailure.setVisibility(View.GONE);
		listview.setVisibility(View.GONE);

		if (res == R.id.layoutLoading) {
			App.startLoadingAnimation(imgloading);
		}

		v.setVisibility(View.VISIBLE);
	}

	private void showLayout(View v) {
		imgLoading.clearAnimation();
		layoutLoading.setVisibility(View.GONE);
		layoutFailure.setVisibility(View.GONE);
		layoutList.setVisibility(View.GONE);

		if (v.equals(layoutLoading)) {
			App.startLoadingAnimation(imgLoading);
		}

		v.setVisibility(View.VISIBLE);
	}

	private class NewsPageAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return pageList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(pageList.get(position));
			return pageList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(pageList.get(position));
		}
	}

	private class NewsListAdapter extends BaseAdapter {
		private int index;

		public NewsListAdapter(int idx) {
			index = idx;
		}

		@Override
		public int getCount() {
			if (entryMap.containsKey(index)) {
				return entryMap.get(index).size();
			}

			return 0;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final NewsEntry entry = entryMap.get(index).get(position);

			if (position == 0) {
				if (convertView == null || convertView.findViewById(R.id.imgImageTop) == null) {
					convertView = inflater.inflate(R.layout.layout_list_item_news_top, null);
				}

				UyghurTextView txtTitle = (UyghurTextView) convertView.findViewById(R.id.txtTitle);
				ImageView imgImage = (ImageView) convertView.findViewById(R.id.imgImageTop);

				txtTitle.setText(entry.Title);
				App.IMAGE_LOADER.displayImage(App.SERVER_URL + "/" + entry.Image, imgImage, new DisplayImageOptions.Builder().cacheOnDisc(true).build());
			} else {
				if (convertView == null || convertView.findViewById(R.id.imgImage) == null) {
					convertView = inflater.inflate(R.layout.layout_list_item_news, null);
				}

				UyghurTextView txtTitle = (UyghurTextView) convertView.findViewById(R.id.txtTitle);
				UyghurTextView txtTime = (UyghurTextView) convertView.findViewById(R.id.txtTime);
				ImageView imgImage = (ImageView) convertView.findViewById(R.id.imgImage);
				IconView iconTime = (IconView) convertView.findViewById(R.id.iconTime);

				txtTitle.setText(entry.Title);
				txtTime.setText(getTimeText(entry.Time));
				iconTime.setText("\ue007");
				App.IMAGE_LOADER.displayImage(App.SERVER_URL + "/" + entry.Image, imgImage, new DisplayImageOptions.Builder().cacheOnDisc(true).build());
			}

			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(ActivityNews.this, ActivityNewsContent.class);
					intent.putExtra("url", App.SERVER_URL + "/?h=phone&t=news&f=getone&id=" + entry.Id);
					intent.putExtra("title", entry.Title);
					startActivity(intent);
				}
			});

			return convertView;
		}
	}

	private class NewsEntry {
		public String Id;
		public String Title;
		public String Image;
		public String Time;
	}
}