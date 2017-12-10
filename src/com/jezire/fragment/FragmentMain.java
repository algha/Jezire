package com.jezire.fragment;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.jezire.App;
import com.jezire.App.Preferences;
import com.jezire.R;
import com.jezire.activity.ActivityBrowser;
import com.jezire.activity.ActivityLogin;
import com.jezire.activity.ActivityNews;
import com.jezire.activity.ActivityNewsContent;
import com.jezire.activity.ActivityVideo;
import com.jezire.activity.ActivityVideoInfo;
import com.jezire.activity.ActivityXurjun;
import com.jezire.activity.ActivityXurjun.XurjunEntry;
import com.jezire.algha.AToast;
import com.jezire.algha.XurjunGirdView;
import com.jezire.http.AsyncHttpClient;
import com.jezire.http.AsyncHttpResponseHandler;
import com.jezire.universalimageloader.DisplayImageOptions;
import com.jezire.widget.IconView;
import com.jezire.widget.UyghurTextView;

import android.R.bool;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentMain extends FragmentActivity {
	private ImageView imgAd;
	private TextView txtFooter2;
	private IconView btnMenu, btnUser;
	private UyghurTextView btnTeximuKopXewerler, btnTeximuKopFilimler, txtFooter1;
	private LinearLayout tab2Page1, tab2Page2, tab2Page3, tab3Page1, tab3Page2, tab3Page3, tab4Page1, tab4Page2, tab5Page1, tab5Page2, tab5Page3;

	private ArrayList<UyghurTextView> lstDangliqBeketlerName = new ArrayList<UyghurTextView>();
	private UyghurTextView tab1Page1Row1Link1, tab1Page1Row1Link2, tab1Page1Row1Link3, tab1Page1Row2Link1, tab1Page1Row2Link2, tab1Page1Row2Link3, tab1Page1Row3Link1, tab1Page1Row3Link2, tab1Page1Row3Link3;

	private ArrayList<ImageView> lstDangliqBeketlerIcon = new ArrayList<ImageView>();
	private ImageView tab1Page1Row1Icon1, tab1Page1Row1Icon2, tab1Page1Row1Icon3, tab1Page1Row2Icon1, tab1Page1Row2Icon2, tab1Page1Row2Icon3, tab1Page1Row3Icon1, tab1Page1Row3Icon2, tab1Page1Row3Icon3;

	private ImageView tab2YengiXewerlerImage;
	private UyghurTextView tab2YengiXewerlerTitle;

	private ImageView tab2XeliqaraXewerlerImage;
	private UyghurTextView tab2XeliqaraXewerlerTitle;

	private ImageView tab2MemliketXewerlerImage;
	private UyghurTextView tab2MemliketXewerlerTitle;

	private ArrayList<UyghurTextView> lstYengiXewerler = new ArrayList<UyghurTextView>();
	private UyghurTextView tab2Page1Link1, tab2Page1Link2, tab2Page1Link3, tab2Page1Link4, tab2Page1Link5;

	private ArrayList<UyghurTextView> lstXeliqaraXewerler = new ArrayList<UyghurTextView>();
	private UyghurTextView tab2Page2Link1, tab2Page2Link2, tab2Page2Link3, tab2Page2Link4, tab2Page2Link5;

	private ArrayList<UyghurTextView> lstMemliketXewerler = new ArrayList<UyghurTextView>();
	private UyghurTextView tab2Page3Link1, tab2Page3Link2, tab2Page3Link3, tab2Page3Link4, tab2Page3Link5;

	private ArrayList<ImageView> lstYengiFilimlerImage = new ArrayList<ImageView>();
	private ImageView tab3Page1Image1, tab3Page1Image2, tab3Page1Image3, tab3Page1Image4, tab3Page1Image5, tab3Page1Image6;
	private ArrayList<UyghurTextView> lstYengiFilimlerName = new ArrayList<UyghurTextView>();
	private UyghurTextView tab3Page1Name1, tab3Page1Name2, tab3Page1Name3, tab3Page1Name4, tab3Page1Name5, tab3Page1Name6;

	private ArrayList<ImageView> lstMTVlarImage = new ArrayList<ImageView>();
	private ImageView tab3Page2Image1, tab3Page2Image2, tab3Page2Image3, tab3Page2Image4, tab3Page2Image5, tab3Page2Image6;
	private ArrayList<UyghurTextView> lstMTVlarName = new ArrayList<UyghurTextView>();
	private UyghurTextView tab3Page2Name1, tab3Page2Name2, tab3Page2Name3, tab3Page2Name4, tab3Page2Name5, tab3Page2Name6;

	private ArrayList<ImageView> lstQiziqarliqImage = new ArrayList<ImageView>();
	private ImageView tab3Page3Image1, tab3Page3Image2, tab3Page3Image3, tab3Page3Image4, tab3Page3Image5, tab3Page3Image6;
	private ArrayList<UyghurTextView> lstQiziqarliqName = new ArrayList<UyghurTextView>();
	private UyghurTextView tab3Page3Name1, tab3Page3Name2, tab3Page3Name3, tab3Page3Name4, tab3Page3Name5, tab3Page3Name6;

	private ArrayList<UyghurTextView> lstYengiYazmilar = new ArrayList<UyghurTextView>();
	private UyghurTextView tab4Page1Link1, tab4Page1Link2, tab4Page1Link3, tab4Page1Link4, tab4Page1Link5;

	private ArrayList<UyghurTextView> lstJewherYazmilar = new ArrayList<UyghurTextView>();
	private UyghurTextView tab4Page2Link1, tab4Page2Link2, tab4Page2Link3, tab4Page2Link4, tab4Page2Link5;

	private ArrayList<ImageView> lstUyghurcheEplerImage = new ArrayList<ImageView>();
	private ImageView tab5Page1Image1, tab5Page1Image2, tab5Page1Image3, tab5Page1Image4, tab5Page1Image5, tab5Page1Image6, tab5Page1Image7, tab5Page1Image8;
	private ArrayList<UyghurTextView> lstUyghurcheEplerName = new ArrayList<UyghurTextView>();
	private UyghurTextView tab5Page1Name1, tab5Page1Name2, tab5Page1Name3, tab5Page1Name4, tab5Page1Name5, tab5Page1Name6, tab5Page1Name7, tab5Page1Name8;
	private ArrayList<TextView> lstUyghurcheEplerSize = new ArrayList<TextView>();
	private TextView tab5Page1Size1, tab5Page1Size2, tab5Page1Size3, tab5Page1Size4, tab5Page1Size5, tab5Page1Size6, tab5Page1Size7, tab5Page1Size8;

	private ArrayList<ImageView> lstOyunlarImage = new ArrayList<ImageView>();
	private ImageView tab5Page2Image1, tab5Page2Image2, tab5Page2Image3, tab5Page2Image4, tab5Page2Image5, tab5Page2Image6, tab5Page2Image7, tab5Page2Image8;
	private ArrayList<UyghurTextView> lstOyunlarName = new ArrayList<UyghurTextView>();
	private UyghurTextView tab5Page2Name1, tab5Page2Name2, tab5Page2Name3, tab5Page2Name4, tab5Page2Name5, tab5Page2Name6, tab5Page2Name7, tab5Page2Name8;
	private ArrayList<TextView> lstOyunlarSize = new ArrayList<TextView>();
	private TextView tab5Page2Size1, tab5Page2Size2, tab5Page2Size3, tab5Page2Size4, tab5Page2Size5, tab5Page2Size6, tab5Page2Size7, tab5Page2Size8;

	private ArrayList<ImageView> lstQollinishchanEplerImage = new ArrayList<ImageView>();
	private ImageView tab5Page3Image1, tab5Page3Image2, tab5Page3Image3, tab5Page3Image4, tab5Page3Image5, tab5Page3Image6, tab5Page3Image7, tab5Page3Image8;
	private ArrayList<UyghurTextView> lstQollinishchanEplerName = new ArrayList<UyghurTextView>();
	private UyghurTextView tab5Page3Name1, tab5Page3Name2, tab5Page3Name3, tab5Page3Name4, tab5Page3Name5, tab5Page3Name6, tab5Page3Name7, tab5Page3Name8,XurjunEmpty;
	private ArrayList<TextView> lstQollinishchanEplerSize = new ArrayList<TextView>();
	private TextView tab5Page3Size1, tab5Page3Size2, tab5Page3Size3, tab5Page3Size4, tab5Page3Size5, tab5Page3Size6, tab5Page3Size7, tab5Page3Size8;

	private View imgLoading, layoutLoading, layoutFailure, scrollView;
	private XurjunGirdView gridView;
	
	public ArrayList<XurjunEntry> xurjunEntries;
	public GridViewAdapter gridViewAdapter;
	private View list_xurjun,not_login; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize();
	}

	private void initialize() {
		View contentView = getLayoutInflater().inflate(R.layout.layout_fragment_main, null);
		initializeView(contentView);
		setContentView(contentView);

		App.FRAGMENT_MAIN = this;
		
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		if (Preferences.getUserName().length() > 0 && Preferences.getUserPassword().length() > 0) {
			list_xurjun.setVisibility(View.VISIBLE);
			not_login.setVisibility(View.GONE);
			LoadXurjun();
		}else{
			list_xurjun.setVisibility(View.GONE);
			not_login.setVisibility(View.VISIBLE);
		}
		super.onRestart();
	}
	protected BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {  
        @Override  
        public void onReceive(Context context, Intent intent) { 
        	list_xurjun.setVisibility(View.GONE);
			not_login.setVisibility(View.VISIBLE);
        	AToast toast = new AToast(FragmentMain.this);
			toast.show("مۇۋاپىقىيەتلىك چىكىندىڭىز .", 3000);
        }  
    }; 
	   @Override  
	public void onResume() {  
	        super.onResume();  
	        // 在当前的activity中注册广播  
	        IntentFilter filter = new IntentFilter();  
	        filter.addAction("LogOut");  
	        this.registerReceiver(this.broadcastReceiver, filter);  
	    }  
	      
	    @Override  
	protected void onDestroy() {  
	       // TODO Auto-generated method stub  
	     super.onDestroy();  
	     this.unregisterReceiver(this.broadcastReceiver);    
	} 
	
	public void LoadXurjun(){
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL+"?h=phone&t=xurjun&f=ListXurjun&nam="+Preferences.getUserName()+"&im="+Preferences.getUserPassword()+"&p=0&pagesize=8", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				// TODO Auto-generated method stub
				xurjunEntries.clear();
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
									xurjunEntries.add(entry);
								}
								XurjunEntry nEntry = new XurjunEntry();
								nEntry.urlname = "تېخىمۇ كۆپ...";
								nEntry.more = true;
								xurjunEntries.add(nEntry);
								gridViewAdapter.notifyDataSetChanged();
							} else {
								XurjunEmpty.setVisibility(View.VISIBLE);
								gridView.setVisibility(View.GONE);
							}

						}
					} catch (Exception e) {
						// TODO: handle exception
						Log.d("xurjun get exaption : ", "" + e);
					}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void initializeView(View contentView) {
		UyghurTextView title = (UyghurTextView) contentView.findViewById(R.id.txtTitle);
		title.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		title.setText("باشبەت");

		imgLoading = contentView.findViewById(R.id.imgLoading);
		layoutLoading = contentView.findViewById(R.id.layoutLoading);
		layoutFailure = contentView.findViewById(R.id.layoutFailure);
		scrollView = contentView.findViewById(R.id.scrollView);
		
		showLayout(layoutLoading);
		loadData();

		imgAd = (ImageView) contentView.findViewById(R.id.imgAd);
		imgAd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getTag() != null) {
					String url = (String) v.getTag();
					Intent intent = new Intent(FragmentMain.this, ActivityBrowser.class);
					intent.putExtra("url", url);
					startActivity(intent);
				}
			}
		});

		final View btnretry = contentView.findViewById(R.id.btnRetry);
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
					btnretry.setBackgroundColor(Color.parseColor("#ed1c24"));
				}

				if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
					btnretry.setBackgroundColor(Color.parseColor("#f26c4f"));
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
		btnUser.setText("\ue005");
		btnUser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				App.SLIDE_MENU_MAIN.open(true, true);
			}
		});

		final UyghurTextView tab1Btn1 = (UyghurTextView) contentView.findViewById(R.id.tab1Btn1);
		final UyghurTextView tab1Btn2 = (UyghurTextView) contentView.findViewById(R.id.tab1Btn2);
		final UyghurTextView tab2Btn1 = (UyghurTextView) contentView.findViewById(R.id.tab2Btn1);
		final UyghurTextView tab2Btn2 = (UyghurTextView) contentView.findViewById(R.id.tab2Btn2);
		final UyghurTextView tab2Btn3 = (UyghurTextView) contentView.findViewById(R.id.tab2Btn3);
		final UyghurTextView tab3Btn1 = (UyghurTextView) contentView.findViewById(R.id.tab3Btn1);
		final UyghurTextView tab3Btn2 = (UyghurTextView) contentView.findViewById(R.id.tab3Btn2);
		final UyghurTextView tab3Btn3 = (UyghurTextView) contentView.findViewById(R.id.tab3Btn3);
		final UyghurTextView tab4Btn1 = (UyghurTextView) contentView.findViewById(R.id.tab4Btn1);
		final UyghurTextView tab4Btn2 = (UyghurTextView) contentView.findViewById(R.id.tab4Btn2);
		final UyghurTextView tab5Btn1 = (UyghurTextView) contentView.findViewById(R.id.tab5Btn1);
		final UyghurTextView tab5Btn2 = (UyghurTextView) contentView.findViewById(R.id.tab5Btn2);
		final UyghurTextView tab5Btn3 = (UyghurTextView) contentView.findViewById(R.id.tab5Btn3);
		
		final View xurjun = (View)contentView.findViewById(R.id.topxurjun);
		final View top_links = (View)contentView.findViewById(R.id.top_links);
		
		

		tab1Btn1.setText("داڭلىق بېكەتلەر");
		tab1Btn1.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		tab1Btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				top_links.setVisibility(View.VISIBLE);
				xurjun.setVisibility(View.GONE);
				
				tab1Btn1.setTextColor(Color.parseColor("#afc936"));
				tab1Btn1.setBackgroundColor(Color.parseColor("#ffffff"));
				
				tab1Btn2.setTextColor(Color.parseColor("#979797"));
				tab1Btn2.setBackgroundColor(Color.parseColor("#ebebeb"));
				
			}
		});
		
		list_xurjun = (View)contentView.findViewById(R.id.list_xurjun);
		not_login = (View)contentView.findViewById(R.id.not_login);
		
		xurjunEntries = new ArrayList<FragmentMain.XurjunEntry>();
		
		gridViewAdapter = new GridViewAdapter(FragmentMain.this);
		gridView = (XurjunGirdView)contentView.findViewById(R.id.GirdView);
		XurjunEmpty = (UyghurTextView)contentView.findViewById(R.id.XurjunEmpty);
		gridView.setAdapter(gridViewAdapter);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position == xurjunEntries.size()-1) {
					Intent intent = new Intent(FragmentMain.this, ActivityXurjun.class);
					startActivity(intent);
				}else{
					XurjunEntry entry = xurjunEntries.get(position);
					Intent intent = new Intent(FragmentMain.this, ActivityBrowser.class);
					intent.putExtra("url", entry.urladdress);
					startActivity(intent);
				}
			}
		});
		
		if (Preferences.getUserName().length() > 0 && Preferences.getUserPassword().length() > 0) {
			list_xurjun.setVisibility(View.VISIBLE);
			not_login.setVisibility(View.GONE);
			LoadXurjun();
		}else{
			list_xurjun.setVisibility(View.GONE);
			not_login.setVisibility(View.VISIBLE);
		}
		
		tab1Btn2.setText("خۇرجۇنۇم");
		tab1Btn2.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		tab1Btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				top_links.setVisibility(View.GONE);
				xurjun.setVisibility(View.VISIBLE);
				
				tab1Btn2.setTextColor(Color.parseColor("#afc936"));
				tab1Btn2.setBackgroundColor(Color.parseColor("#ffffff"));
				
				tab1Btn1.setTextColor(Color.parseColor("#979797"));
				tab1Btn1.setBackgroundColor(Color.parseColor("#ebebeb"));
			}
		});
		
		UyghurTextView ILogin = (UyghurTextView)contentView.findViewById(R.id.ILogin);
		ILogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(FragmentMain.this, ActivityLogin.class));
			}
		});
		
		UyghurTextView IRegister = (UyghurTextView)contentView.findViewById(R.id.IRegister);
		IRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(FragmentMain.this, ActivityLogin.class));
			}
		});
		

		tab2Btn1.setText("ئەڭ يېڭى");
		tab2Btn1.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		tab2Btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeTab(tab2Btn1, new UyghurTextView[] { tab2Btn2, tab2Btn3 }, tab2Page1, new LinearLayout[] { tab2Page2, tab2Page3 });
			}
		});

		tab2Btn2.setText("خەلقئارا");
		tab2Btn2.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		tab2Btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeTab(tab2Btn2, new UyghurTextView[] { tab2Btn1, tab2Btn3 }, tab2Page2, new LinearLayout[] { tab2Page1, tab2Page3 });
			}
		});

		tab2Btn3.setText("مەملىكەت");
		tab2Btn3.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		tab2Btn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeTab(tab2Btn3, new UyghurTextView[] { tab2Btn1, tab2Btn2 }, tab2Page3, new LinearLayout[] { tab2Page1, tab2Page2 });
			}
		});

		tab3Btn1.setText("يېڭى فىلىملەر");
		tab3Btn1.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		tab3Btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeTab(tab3Btn1, new UyghurTextView[] { tab3Btn2, tab3Btn3 }, tab3Page1, new LinearLayout[] { tab3Page2, tab3Page3 });
			}
		});

		tab3Btn2.setText("كىنولار");
		tab3Btn2.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		tab3Btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeTab(tab3Btn2, new UyghurTextView[] { tab3Btn1, tab3Btn3 }, tab3Page2, new LinearLayout[] { tab3Page1, tab3Page3 });
			}
		});

		tab3Btn3.setText("ئېتوتلار");
		tab3Btn3.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		tab3Btn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeTab(tab3Btn3, new UyghurTextView[] { tab3Btn1, tab3Btn2 }, tab3Page3, new LinearLayout[] { tab3Page1, tab3Page2 });
			}
		});

		tab4Btn1.setText("ئەڭ يېڭى يازمىلار");
		tab4Btn1.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		tab4Btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeTab(tab4Btn1, new UyghurTextView[] { tab4Btn2 }, tab4Page1, new LinearLayout[] { tab4Page2 });
			}
		});

		tab4Btn2.setText("جەۋھەر يازمىلار");
		tab4Btn2.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		tab4Btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeTab(tab4Btn2, new UyghurTextView[] { tab4Btn1 }, tab4Page2, new LinearLayout[] { tab4Page1 });
			}
		});

		tab5Btn1.setText("ئۇيغۇرچە ئەپلەر");
		tab5Btn1.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		tab5Btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeTab(tab5Btn1, new UyghurTextView[] { tab5Btn2, tab5Btn3 }, tab5Page1, new LinearLayout[] { tab5Page2, tab5Page3 });
			}
		});

		tab5Btn2.setText("ئويۇنلار");
		tab5Btn2.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		tab5Btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeTab(tab5Btn2, new UyghurTextView[] { tab5Btn1, tab5Btn3 }, tab5Page2, new LinearLayout[] { tab5Page1, tab5Page3 });
			}
		});

		tab5Btn3.setText("قوللىنىشچان ئەپلەر");
		tab5Btn3.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		tab5Btn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changeTab(tab5Btn3, new UyghurTextView[] { tab5Btn1, tab5Btn2 }, tab5Page3, new LinearLayout[] { tab5Page1, tab5Page2 });
			}
		});

		tab1Page1Row1Link1 = (UyghurTextView) contentView.findViewById(R.id.tab1Page1Row1Link1);
		tab1Page1Row1Link2 = (UyghurTextView) contentView.findViewById(R.id.tab1Page1Row1Link2);
		tab1Page1Row1Link3 = (UyghurTextView) contentView.findViewById(R.id.tab1Page1Row1Link3);
		tab1Page1Row2Link1 = (UyghurTextView) contentView.findViewById(R.id.tab1Page1Row2Link1);
		tab1Page1Row2Link2 = (UyghurTextView) contentView.findViewById(R.id.tab1Page1Row2Link2);
		tab1Page1Row2Link3 = (UyghurTextView) contentView.findViewById(R.id.tab1Page1Row2Link3);
		tab1Page1Row3Link1 = (UyghurTextView) contentView.findViewById(R.id.tab1Page1Row3Link1);
		tab1Page1Row3Link2 = (UyghurTextView) contentView.findViewById(R.id.tab1Page1Row3Link2);
		tab1Page1Row3Link3 = (UyghurTextView) contentView.findViewById(R.id.tab1Page1Row3Link3);
		lstDangliqBeketlerName.add(tab1Page1Row1Link1);
		lstDangliqBeketlerName.add(tab1Page1Row1Link2);
		lstDangliqBeketlerName.add(tab1Page1Row1Link3);
		lstDangliqBeketlerName.add(tab1Page1Row2Link1);
		lstDangliqBeketlerName.add(tab1Page1Row2Link2);
		lstDangliqBeketlerName.add(tab1Page1Row2Link3);
		lstDangliqBeketlerName.add(tab1Page1Row3Link1);
		lstDangliqBeketlerName.add(tab1Page1Row3Link2);
		lstDangliqBeketlerName.add(tab1Page1Row3Link3);
		for (int i = 0; i < lstDangliqBeketlerName.size(); i++) {
			UyghurTextView view = lstDangliqBeketlerName.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (v.getTag() != null) {
						String url = (String) v.getTag();
						Intent intent = new Intent(FragmentMain.this, ActivityBrowser.class);
						intent.putExtra("url", url);
						startActivity(intent);
					}
				}
			});
		}

		tab1Page1Row1Icon1 = (ImageView) contentView.findViewById(R.id.tab1Page1Row1Icon1);
		tab1Page1Row1Icon2 = (ImageView) contentView.findViewById(R.id.tab1Page1Row1Icon2);
		tab1Page1Row1Icon3 = (ImageView) contentView.findViewById(R.id.tab1Page1Row1Icon3);
		tab1Page1Row2Icon1 = (ImageView) contentView.findViewById(R.id.tab1Page1Row2Icon1);
		tab1Page1Row2Icon2 = (ImageView) contentView.findViewById(R.id.tab1Page1Row2Icon2);
		tab1Page1Row2Icon3 = (ImageView) contentView.findViewById(R.id.tab1Page1Row2Icon3);
		tab1Page1Row3Icon1 = (ImageView) contentView.findViewById(R.id.tab1Page1Row3Icon1);
		tab1Page1Row3Icon2 = (ImageView) contentView.findViewById(R.id.tab1Page1Row3Icon2);
		tab1Page1Row3Icon3 = (ImageView) contentView.findViewById(R.id.tab1Page1Row3Icon3);
		lstDangliqBeketlerIcon.add(tab1Page1Row1Icon1);
		lstDangliqBeketlerIcon.add(tab1Page1Row1Icon2);
		lstDangliqBeketlerIcon.add(tab1Page1Row1Icon3);
		lstDangliqBeketlerIcon.add(tab1Page1Row2Icon1);
		lstDangliqBeketlerIcon.add(tab1Page1Row2Icon2);
		lstDangliqBeketlerIcon.add(tab1Page1Row2Icon3);
		lstDangliqBeketlerIcon.add(tab1Page1Row3Icon1);
		lstDangliqBeketlerIcon.add(tab1Page1Row3Icon2);
		lstDangliqBeketlerIcon.add(tab1Page1Row3Icon3);
		for (int i = 0; i < lstDangliqBeketlerIcon.size(); i++) {
			final ImageView view = lstDangliqBeketlerIcon.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (v.getTag() != null) {
						String url = (String) v.getTag();
						Intent intent = new Intent(FragmentMain.this, ActivityBrowser.class);
						intent.putExtra("url", url);
						startActivity(intent);
					}
				}
			});
		}

		tab2YengiXewerlerImage = (ImageView) contentView.findViewById(R.id.tab2YengiXewerlerImage);
		tab2YengiXewerlerImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tab2YengiXewerlerImage.getTag() != null) {
					String id = (String) tab2YengiXewerlerImage.getTag();
					Intent intent = new Intent(FragmentMain.this, ActivityNewsContent.class);
					intent.putExtra("url", App.SERVER_URL + "/?h=phone&t=news&f=getone&id=" + id);
					intent.putExtra("title", tab2YengiXewerlerTitle.getText().toString());
					startActivity(intent);
				}
			}
		});
		tab2YengiXewerlerTitle = (UyghurTextView) contentView.findViewById(R.id.tab2YengiXewerlerTitle);
		tab2YengiXewerlerTitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tab2YengiXewerlerTitle.getTag() != null) {
					String id = (String) tab2YengiXewerlerTitle.getTag();
					Intent intent = new Intent(FragmentMain.this, ActivityNewsContent.class);
					intent.putExtra("url", App.SERVER_URL + "/?h=phone&t=news&f=getone&id=" + id);
					intent.putExtra("title", tab2YengiXewerlerTitle.getText().toString());
					startActivity(intent);
				}
			}
		});

		tab2XeliqaraXewerlerImage = (ImageView) contentView.findViewById(R.id.tab2XeliqaraXewerlerImage);
		tab2XeliqaraXewerlerImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tab2XeliqaraXewerlerImage.getTag() != null) {
					String id = (String) tab2XeliqaraXewerlerImage.getTag();
					Intent intent = new Intent(FragmentMain.this, ActivityNewsContent.class);
					intent.putExtra("url", App.SERVER_URL + "/?h=phone&t=news&f=getone&id=" + id);
					intent.putExtra("title", tab2XeliqaraXewerlerTitle.getText().toString());
					startActivity(intent);
				}
			}
		});
		tab2XeliqaraXewerlerTitle = (UyghurTextView) contentView.findViewById(R.id.tab2XeliqaraXewerlerTitle);
		tab2XeliqaraXewerlerTitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tab2XeliqaraXewerlerTitle.getTag() != null) {
					String id = (String) tab2XeliqaraXewerlerTitle.getTag();
					Intent intent = new Intent(FragmentMain.this, ActivityNewsContent.class);
					intent.putExtra("url", App.SERVER_URL + "/?h=phone&t=news&f=getone&id=" + id);
					intent.putExtra("title", tab2XeliqaraXewerlerTitle.getText().toString());
					startActivity(intent);
				}
			}
		});

		tab2MemliketXewerlerImage = (ImageView) contentView.findViewById(R.id.tab2MemliketXewerlerImage);
		tab2MemliketXewerlerImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tab2MemliketXewerlerImage.getTag() != null) {
					String id = (String) tab2MemliketXewerlerImage.getTag();
					Intent intent = new Intent(FragmentMain.this, ActivityNewsContent.class);
					intent.putExtra("url", App.SERVER_URL + "/?h=phone&t=news&f=getone&id=" + id);
					intent.putExtra("title", tab2MemliketXewerlerTitle.getText().toString());
					startActivity(intent);
				}
			}
		});
		tab2MemliketXewerlerTitle = (UyghurTextView) contentView.findViewById(R.id.tab2MemliketXewerlerTitle);
		tab2MemliketXewerlerTitle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tab2MemliketXewerlerTitle.getTag() != null) {
					String id = (String) tab2MemliketXewerlerTitle.getTag();
					Intent intent = new Intent(FragmentMain.this, ActivityNewsContent.class);
					intent.putExtra("url", App.SERVER_URL + "/?h=phone&t=news&f=getone&id=" + id);
					intent.putExtra("title", tab2MemliketXewerlerTitle.getText().toString());
					startActivity(intent);
				}
			}
		});

		tab2Page1Link1 = (UyghurTextView) contentView.findViewById(R.id.tab2Page1Link1);
		tab2Page1Link2 = (UyghurTextView) contentView.findViewById(R.id.tab2Page1Link2);
		tab2Page1Link3 = (UyghurTextView) contentView.findViewById(R.id.tab2Page1Link3);
		tab2Page1Link4 = (UyghurTextView) contentView.findViewById(R.id.tab2Page1Link4);
		tab2Page1Link5 = (UyghurTextView) contentView.findViewById(R.id.tab2Page1Link5);
		lstYengiXewerler.add(tab2Page1Link1);
		lstYengiXewerler.add(tab2Page1Link2);
		lstYengiXewerler.add(tab2Page1Link3);
		lstYengiXewerler.add(tab2Page1Link4);
		lstYengiXewerler.add(tab2Page1Link5);
		for (int i = 0; i < lstYengiXewerler.size(); i++) {
			final UyghurTextView view = lstYengiXewerler.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String id = (String) view.getTag();
						Intent intent = new Intent(FragmentMain.this, ActivityNewsContent.class);
						intent.putExtra("url", App.SERVER_URL + "/?h=phone&t=news&f=getone&id=" + id);
						intent.putExtra("title", view.getText().toString());
						startActivity(intent);
					}
				}
			});
		}

		tab2Page2Link1 = (UyghurTextView) contentView.findViewById(R.id.tab2Page2Link1);
		tab2Page2Link2 = (UyghurTextView) contentView.findViewById(R.id.tab2Page2Link2);
		tab2Page2Link3 = (UyghurTextView) contentView.findViewById(R.id.tab2Page2Link3);
		tab2Page2Link4 = (UyghurTextView) contentView.findViewById(R.id.tab2Page2Link4);
		tab2Page2Link5 = (UyghurTextView) contentView.findViewById(R.id.tab2Page2Link5);
		lstXeliqaraXewerler.add(tab2Page2Link1);
		lstXeliqaraXewerler.add(tab2Page2Link2);
		lstXeliqaraXewerler.add(tab2Page2Link3);
		lstXeliqaraXewerler.add(tab2Page2Link4);
		lstXeliqaraXewerler.add(tab2Page2Link5);
		for (int i = 0; i < lstXeliqaraXewerler.size(); i++) {
			final UyghurTextView view = lstXeliqaraXewerler.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String id = (String) view.getTag();
						Intent intent = new Intent(FragmentMain.this, ActivityNewsContent.class);
						intent.putExtra("url", App.SERVER_URL + "/?h=phone&t=news&f=getone&id=" + id);
						intent.putExtra("title", view.getText().toString());
						startActivity(intent);
					}
				}
			});
		}

		tab2Page3Link1 = (UyghurTextView) contentView.findViewById(R.id.tab2Page3Link1);
		tab2Page3Link2 = (UyghurTextView) contentView.findViewById(R.id.tab2Page3Link2);
		tab2Page3Link3 = (UyghurTextView) contentView.findViewById(R.id.tab2Page3Link3);
		tab2Page3Link4 = (UyghurTextView) contentView.findViewById(R.id.tab2Page3Link4);
		tab2Page3Link5 = (UyghurTextView) contentView.findViewById(R.id.tab2Page3Link5);
		lstMemliketXewerler.add(tab2Page3Link1);
		lstMemliketXewerler.add(tab2Page3Link2);
		lstMemliketXewerler.add(tab2Page3Link3);
		lstMemliketXewerler.add(tab2Page3Link4);
		lstMemliketXewerler.add(tab2Page3Link5);
		for (int i = 0; i < lstMemliketXewerler.size(); i++) {
			final UyghurTextView view = lstMemliketXewerler.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String id = (String) view.getTag();
						Intent intent = new Intent(FragmentMain.this, ActivityNewsContent.class);
						intent.putExtra("url", App.SERVER_URL + "/?h=phone&t=news&f=getone&id=" + id);
						intent.putExtra("title", view.getText().toString());
						startActivity(intent);
					}
				}
			});
		}

		tab3Page1Image1 = (ImageView) contentView.findViewById(R.id.tab3Page1Image1);
		tab3Page1Image2 = (ImageView) contentView.findViewById(R.id.tab3Page1Image2);
		tab3Page1Image3 = (ImageView) contentView.findViewById(R.id.tab3Page1Image3);
		tab3Page1Image4 = (ImageView) contentView.findViewById(R.id.tab3Page1Image4);
		tab3Page1Image5 = (ImageView) contentView.findViewById(R.id.tab3Page1Image5);
		tab3Page1Image6 = (ImageView) contentView.findViewById(R.id.tab3Page1Image6);
		lstYengiFilimlerImage.add(tab3Page1Image1);
		lstYengiFilimlerImage.add(tab3Page1Image2);
		lstYengiFilimlerImage.add(tab3Page1Image3);
		lstYengiFilimlerImage.add(tab3Page1Image4);
		lstYengiFilimlerImage.add(tab3Page1Image5);
		lstYengiFilimlerImage.add(tab3Page1Image6);
		for (int i = 0; i < lstYengiFilimlerImage.size(); i++) {
			final ImageView view = lstYengiFilimlerImage.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String id = (String) view.getTag();
						Intent intent = new Intent(FragmentMain.this, ActivityVideoInfo.class);
						intent.putExtra("id", id);
						startActivity(intent);
					}
				}
			});
		}

		tab3Page1Name1 = (UyghurTextView) contentView.findViewById(R.id.tab3Page1Name1);
		tab3Page1Name2 = (UyghurTextView) contentView.findViewById(R.id.tab3Page1Name2);
		tab3Page1Name3 = (UyghurTextView) contentView.findViewById(R.id.tab3Page1Name3);
		tab3Page1Name4 = (UyghurTextView) contentView.findViewById(R.id.tab3Page1Name4);
		tab3Page1Name5 = (UyghurTextView) contentView.findViewById(R.id.tab3Page1Name5);
		tab3Page1Name6 = (UyghurTextView) contentView.findViewById(R.id.tab3Page1Name6);
		lstYengiFilimlerName.add(tab3Page1Name1);
		lstYengiFilimlerName.add(tab3Page1Name2);
		lstYengiFilimlerName.add(tab3Page1Name3);
		lstYengiFilimlerName.add(tab3Page1Name4);
		lstYengiFilimlerName.add(tab3Page1Name5);
		lstYengiFilimlerName.add(tab3Page1Name6);
		for (int i = 0; i < lstYengiFilimlerName.size(); i++) {
			final UyghurTextView view = lstYengiFilimlerName.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String id = (String) view.getTag();
						Intent intent = new Intent(FragmentMain.this, ActivityVideoInfo.class);
						intent.putExtra("id", id);
						startActivity(intent);
					}
				}
			});
		}

		tab3Page2Image1 = (ImageView) contentView.findViewById(R.id.tab3Page2Image1);
		tab3Page2Image2 = (ImageView) contentView.findViewById(R.id.tab3Page2Image2);
		tab3Page2Image3 = (ImageView) contentView.findViewById(R.id.tab3Page2Image3);
		tab3Page2Image4 = (ImageView) contentView.findViewById(R.id.tab3Page2Image4);
		tab3Page2Image5 = (ImageView) contentView.findViewById(R.id.tab3Page2Image5);
		tab3Page2Image6 = (ImageView) contentView.findViewById(R.id.tab3Page2Image6);
		lstMTVlarImage.add(tab3Page2Image1);
		lstMTVlarImage.add(tab3Page2Image2);
		lstMTVlarImage.add(tab3Page2Image3);
		lstMTVlarImage.add(tab3Page2Image4);
		lstMTVlarImage.add(tab3Page2Image5);
		lstMTVlarImage.add(tab3Page2Image6);
		for (int i = 0; i < lstMTVlarImage.size(); i++) {
			final ImageView view = lstMTVlarImage.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String id = (String) view.getTag();
						Intent intent = new Intent(FragmentMain.this, ActivityVideoInfo.class);
						intent.putExtra("id", id);
						startActivity(intent);
					}
				}
			});
		}

		tab3Page2Name1 = (UyghurTextView) contentView.findViewById(R.id.tab3Page2Name1);
		tab3Page2Name2 = (UyghurTextView) contentView.findViewById(R.id.tab3Page2Name2);
		tab3Page2Name3 = (UyghurTextView) contentView.findViewById(R.id.tab3Page2Name3);
		tab3Page2Name4 = (UyghurTextView) contentView.findViewById(R.id.tab3Page2Name4);
		tab3Page2Name5 = (UyghurTextView) contentView.findViewById(R.id.tab3Page2Name5);
		tab3Page2Name6 = (UyghurTextView) contentView.findViewById(R.id.tab3Page2Name6);
		lstMTVlarName.add(tab3Page2Name1);
		lstMTVlarName.add(tab3Page2Name2);
		lstMTVlarName.add(tab3Page2Name3);
		lstMTVlarName.add(tab3Page2Name4);
		lstMTVlarName.add(tab3Page2Name5);
		lstMTVlarName.add(tab3Page2Name6);
		for (int i = 0; i < lstMTVlarName.size(); i++) {
			final UyghurTextView view = lstMTVlarName.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String id = (String) view.getTag();
						Intent intent = new Intent(FragmentMain.this, ActivityVideoInfo.class);
						intent.putExtra("id", id);
						startActivity(intent);
					}
				}
			});
		}

		tab3Page3Image1 = (ImageView) contentView.findViewById(R.id.tab3Page3Image1);
		tab3Page3Image2 = (ImageView) contentView.findViewById(R.id.tab3Page3Image2);
		tab3Page3Image3 = (ImageView) contentView.findViewById(R.id.tab3Page3Image3);
		tab3Page3Image4 = (ImageView) contentView.findViewById(R.id.tab3Page3Image4);
		tab3Page3Image5 = (ImageView) contentView.findViewById(R.id.tab3Page3Image5);
		tab3Page3Image6 = (ImageView) contentView.findViewById(R.id.tab3Page3Image6);
		lstQiziqarliqImage.add(tab3Page3Image1);
		lstQiziqarliqImage.add(tab3Page3Image2);
		lstQiziqarliqImage.add(tab3Page3Image3);
		lstQiziqarliqImage.add(tab3Page3Image4);
		lstQiziqarliqImage.add(tab3Page3Image5);
		lstQiziqarliqImage.add(tab3Page3Image6);
		for (int i = 0; i < lstQiziqarliqImage.size(); i++) {
			final ImageView view = lstQiziqarliqImage.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String id = (String) view.getTag();
						Intent intent = new Intent(FragmentMain.this, ActivityVideoInfo.class);
						intent.putExtra("id", id);
						startActivity(intent);
					}
				}
			});
		}

		tab3Page3Name1 = (UyghurTextView) contentView.findViewById(R.id.tab3Page3Name1);
		tab3Page3Name2 = (UyghurTextView) contentView.findViewById(R.id.tab3Page3Name2);
		tab3Page3Name3 = (UyghurTextView) contentView.findViewById(R.id.tab3Page3Name3);
		tab3Page3Name4 = (UyghurTextView) contentView.findViewById(R.id.tab3Page3Name4);
		tab3Page3Name5 = (UyghurTextView) contentView.findViewById(R.id.tab3Page3Name5);
		tab3Page3Name6 = (UyghurTextView) contentView.findViewById(R.id.tab3Page3Name6);
		lstQiziqarliqName.add(tab3Page3Name1);
		lstQiziqarliqName.add(tab3Page3Name2);
		lstQiziqarliqName.add(tab3Page3Name3);
		lstQiziqarliqName.add(tab3Page3Name4);
		lstQiziqarliqName.add(tab3Page3Name5);
		lstQiziqarliqName.add(tab3Page3Name6);
		for (int i = 0; i < lstQiziqarliqName.size(); i++) {
			final UyghurTextView view = lstQiziqarliqName.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String id = (String) view.getTag();
						Intent intent = new Intent(FragmentMain.this, ActivityVideoInfo.class);
						intent.putExtra("id", id);
						startActivity(intent);
					}
				}
			});
		}

		tab4Page1Link1 = (UyghurTextView) contentView.findViewById(R.id.tab4Page1Link1);
		tab4Page1Link2 = (UyghurTextView) contentView.findViewById(R.id.tab4Page1Link2);
		tab4Page1Link3 = (UyghurTextView) contentView.findViewById(R.id.tab4Page1Link3);
		tab4Page1Link4 = (UyghurTextView) contentView.findViewById(R.id.tab4Page1Link4);
		tab4Page1Link5 = (UyghurTextView) contentView.findViewById(R.id.tab4Page1Link5);
		lstYengiYazmilar.add(tab4Page1Link1);
		lstYengiYazmilar.add(tab4Page1Link2);
		lstYengiYazmilar.add(tab4Page1Link3);
		lstYengiYazmilar.add(tab4Page1Link4);
		lstYengiYazmilar.add(tab4Page1Link5);
		for (int i = 0; i < lstYengiYazmilar.size(); i++) {
			final UyghurTextView view = lstYengiYazmilar.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {			
					if (view.getTag() != null) {
						String id = (String) view.getTag();
						Intent intent = new Intent(FragmentMain.this, ActivityNewsContent.class);
						intent.putExtra("url", App.SERVER_URL + "/?h=phone&t=news&f=getone&id=" + id);
						intent.putExtra("title", view.getText().toString());
						startActivity(intent);
					}	
				}
			});
		}

		tab4Page2Link1 = (UyghurTextView) contentView.findViewById(R.id.tab4Page2Link1);
		tab4Page2Link2 = (UyghurTextView) contentView.findViewById(R.id.tab4Page2Link2);
		tab4Page2Link3 = (UyghurTextView) contentView.findViewById(R.id.tab4Page2Link3);
		tab4Page2Link4 = (UyghurTextView) contentView.findViewById(R.id.tab4Page2Link4);
		tab4Page2Link5 = (UyghurTextView) contentView.findViewById(R.id.tab4Page2Link5);
		lstJewherYazmilar.add(tab4Page2Link1);
		lstJewherYazmilar.add(tab4Page2Link2);
		lstJewherYazmilar.add(tab4Page2Link3);
		lstJewherYazmilar.add(tab4Page2Link4);
		lstJewherYazmilar.add(tab4Page2Link5);
		for (int i = 0; i < lstJewherYazmilar.size(); i++) {
			final UyghurTextView view = lstJewherYazmilar.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String id = (String) view.getTag();
						Intent intent = new Intent(FragmentMain.this, ActivityNewsContent.class);
						intent.putExtra("url", App.SERVER_URL + "/?h=phone&t=news&f=getone&id=" + id);
						intent.putExtra("title", view.getText().toString());
						startActivity(intent);
					}
				}
			});
		}

		tab5Page1Image1 = (ImageView) contentView.findViewById(R.id.tab5Page1Image1);
		tab5Page1Image2 = (ImageView) contentView.findViewById(R.id.tab5Page1Image2);
		tab5Page1Image3 = (ImageView) contentView.findViewById(R.id.tab5Page1Image3);
		tab5Page1Image4 = (ImageView) contentView.findViewById(R.id.tab5Page1Image4);
		tab5Page1Image5 = (ImageView) contentView.findViewById(R.id.tab5Page1Image5);
		tab5Page1Image6 = (ImageView) contentView.findViewById(R.id.tab5Page1Image6);
		tab5Page1Image7 = (ImageView) contentView.findViewById(R.id.tab5Page1Image7);
		tab5Page1Image8 = (ImageView) contentView.findViewById(R.id.tab5Page1Image8);
		lstUyghurcheEplerImage.add(tab5Page1Image1);
		lstUyghurcheEplerImage.add(tab5Page1Image2);
		lstUyghurcheEplerImage.add(tab5Page1Image3);
		lstUyghurcheEplerImage.add(tab5Page1Image4);
		lstUyghurcheEplerImage.add(tab5Page1Image5);
		lstUyghurcheEplerImage.add(tab5Page1Image6);
		lstUyghurcheEplerImage.add(tab5Page1Image7);
		lstUyghurcheEplerImage.add(tab5Page1Image8);
		for (int i = 0; i < lstUyghurcheEplerImage.size(); i++) {
			final ImageView view = lstUyghurcheEplerImage.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String url = (String) view.getTag();
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					}
				}
			});
		}

		tab5Page1Name1 = (UyghurTextView) contentView.findViewById(R.id.tab5Page1Name1);
		tab5Page1Name2 = (UyghurTextView) contentView.findViewById(R.id.tab5Page1Name2);
		tab5Page1Name3 = (UyghurTextView) contentView.findViewById(R.id.tab5Page1Name3);
		tab5Page1Name4 = (UyghurTextView) contentView.findViewById(R.id.tab5Page1Name4);
		tab5Page1Name5 = (UyghurTextView) contentView.findViewById(R.id.tab5Page1Name5);
		tab5Page1Name6 = (UyghurTextView) contentView.findViewById(R.id.tab5Page1Name6);
		tab5Page1Name7 = (UyghurTextView) contentView.findViewById(R.id.tab5Page1Name7);
		tab5Page1Name8 = (UyghurTextView) contentView.findViewById(R.id.tab5Page1Name8);
		lstUyghurcheEplerName.add(tab5Page1Name1);
		lstUyghurcheEplerName.add(tab5Page1Name2);
		lstUyghurcheEplerName.add(tab5Page1Name3);
		lstUyghurcheEplerName.add(tab5Page1Name4);
		lstUyghurcheEplerName.add(tab5Page1Name5);
		lstUyghurcheEplerName.add(tab5Page1Name6);
		lstUyghurcheEplerName.add(tab5Page1Name7);
		lstUyghurcheEplerName.add(tab5Page1Name8);
		for (int i = 0; i < lstUyghurcheEplerName.size(); i++) {
			final UyghurTextView view = lstUyghurcheEplerName.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String url = (String) view.getTag();
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					}
				}
			});
		}

		tab5Page1Size1 = (TextView) contentView.findViewById(R.id.tab5Page1Size1);
		tab5Page1Size2 = (TextView) contentView.findViewById(R.id.tab5Page1Size2);
		tab5Page1Size3 = (TextView) contentView.findViewById(R.id.tab5Page1Size3);
		tab5Page1Size4 = (TextView) contentView.findViewById(R.id.tab5Page1Size4);
		tab5Page1Size5 = (TextView) contentView.findViewById(R.id.tab5Page1Size5);
		tab5Page1Size6 = (TextView) contentView.findViewById(R.id.tab5Page1Size6);
		tab5Page1Size7 = (TextView) contentView.findViewById(R.id.tab5Page1Size7);
		tab5Page1Size8 = (TextView) contentView.findViewById(R.id.tab5Page1Size8);
		lstUyghurcheEplerSize.add(tab5Page1Size1);
		lstUyghurcheEplerSize.add(tab5Page1Size2);
		lstUyghurcheEplerSize.add(tab5Page1Size3);
		lstUyghurcheEplerSize.add(tab5Page1Size4);
		lstUyghurcheEplerSize.add(tab5Page1Size5);
		lstUyghurcheEplerSize.add(tab5Page1Size6);
		lstUyghurcheEplerSize.add(tab5Page1Size7);
		lstUyghurcheEplerSize.add(tab5Page1Size8);
		for (int i = 0; i < lstUyghurcheEplerSize.size(); i++) {
			final TextView view = lstUyghurcheEplerSize.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String url = (String) view.getTag();
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					}
				}
			});
		}

		tab5Page2Image1 = (ImageView) contentView.findViewById(R.id.tab5Page2Image1);
		tab5Page2Image2 = (ImageView) contentView.findViewById(R.id.tab5Page2Image2);
		tab5Page2Image3 = (ImageView) contentView.findViewById(R.id.tab5Page2Image3);
		tab5Page2Image4 = (ImageView) contentView.findViewById(R.id.tab5Page2Image4);
		tab5Page2Image5 = (ImageView) contentView.findViewById(R.id.tab5Page2Image5);
		tab5Page2Image6 = (ImageView) contentView.findViewById(R.id.tab5Page2Image6);
		tab5Page2Image7 = (ImageView) contentView.findViewById(R.id.tab5Page2Image7);
		tab5Page2Image8 = (ImageView) contentView.findViewById(R.id.tab5Page2Image8);
		lstOyunlarImage.add(tab5Page2Image1);
		lstOyunlarImage.add(tab5Page2Image2);
		lstOyunlarImage.add(tab5Page2Image3);
		lstOyunlarImage.add(tab5Page2Image4);
		lstOyunlarImage.add(tab5Page2Image5);
		lstOyunlarImage.add(tab5Page2Image6);
		lstOyunlarImage.add(tab5Page2Image7);
		lstOyunlarImage.add(tab5Page2Image8);
		for (int i = 0; i < lstOyunlarImage.size(); i++) {
			final ImageView view = lstOyunlarImage.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String url = (String) view.getTag();
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					}
				}
			});
		}

		tab5Page2Name1 = (UyghurTextView) contentView.findViewById(R.id.tab5Page2Name1);
		tab5Page2Name2 = (UyghurTextView) contentView.findViewById(R.id.tab5Page2Name2);
		tab5Page2Name3 = (UyghurTextView) contentView.findViewById(R.id.tab5Page2Name3);
		tab5Page2Name4 = (UyghurTextView) contentView.findViewById(R.id.tab5Page2Name4);
		tab5Page2Name5 = (UyghurTextView) contentView.findViewById(R.id.tab5Page2Name5);
		tab5Page2Name6 = (UyghurTextView) contentView.findViewById(R.id.tab5Page2Name6);
		tab5Page2Name7 = (UyghurTextView) contentView.findViewById(R.id.tab5Page2Name7);
		tab5Page2Name8 = (UyghurTextView) contentView.findViewById(R.id.tab5Page2Name8);
		lstOyunlarName.add(tab5Page2Name1);
		lstOyunlarName.add(tab5Page2Name2);
		lstOyunlarName.add(tab5Page2Name3);
		lstOyunlarName.add(tab5Page2Name4);
		lstOyunlarName.add(tab5Page2Name5);
		lstOyunlarName.add(tab5Page2Name6);
		lstOyunlarName.add(tab5Page2Name7);
		lstOyunlarName.add(tab5Page2Name8);
		for (int i = 0; i < lstOyunlarName.size(); i++) {
			final UyghurTextView view = lstOyunlarName.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String url = (String) view.getTag();
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					}
				}
			});
		}

		tab5Page2Size1 = (TextView) contentView.findViewById(R.id.tab5Page2Size1);
		tab5Page2Size2 = (TextView) contentView.findViewById(R.id.tab5Page2Size2);
		tab5Page2Size3 = (TextView) contentView.findViewById(R.id.tab5Page2Size3);
		tab5Page2Size4 = (TextView) contentView.findViewById(R.id.tab5Page2Size4);
		tab5Page2Size5 = (TextView) contentView.findViewById(R.id.tab5Page2Size5);
		tab5Page2Size6 = (TextView) contentView.findViewById(R.id.tab5Page2Size6);
		tab5Page2Size7 = (TextView) contentView.findViewById(R.id.tab5Page2Size7);
		tab5Page2Size8 = (TextView) contentView.findViewById(R.id.tab5Page2Size8);
		lstOyunlarSize.add(tab5Page2Size1);
		lstOyunlarSize.add(tab5Page2Size2);
		lstOyunlarSize.add(tab5Page2Size3);
		lstOyunlarSize.add(tab5Page2Size4);
		lstOyunlarSize.add(tab5Page2Size5);
		lstOyunlarSize.add(tab5Page2Size6);
		lstOyunlarSize.add(tab5Page2Size7);
		lstOyunlarSize.add(tab5Page2Size8);
		for (int i = 0; i < lstOyunlarSize.size(); i++) {
			final TextView view = lstOyunlarSize.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String url = (String) view.getTag();
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					}
				}
			});
		}

		tab5Page3Image1 = (ImageView) contentView.findViewById(R.id.tab5Page3Image1);
		tab5Page3Image2 = (ImageView) contentView.findViewById(R.id.tab5Page3Image2);
		tab5Page3Image3 = (ImageView) contentView.findViewById(R.id.tab5Page3Image3);
		tab5Page3Image4 = (ImageView) contentView.findViewById(R.id.tab5Page3Image4);
		tab5Page3Image5 = (ImageView) contentView.findViewById(R.id.tab5Page3Image5);
		tab5Page3Image6 = (ImageView) contentView.findViewById(R.id.tab5Page3Image6);
		tab5Page3Image7 = (ImageView) contentView.findViewById(R.id.tab5Page3Image7);
		tab5Page3Image8 = (ImageView) contentView.findViewById(R.id.tab5Page3Image8);
		lstQollinishchanEplerImage.add(tab5Page3Image1);
		lstQollinishchanEplerImage.add(tab5Page3Image2);
		lstQollinishchanEplerImage.add(tab5Page3Image3);
		lstQollinishchanEplerImage.add(tab5Page3Image4);
		lstQollinishchanEplerImage.add(tab5Page3Image5);
		lstQollinishchanEplerImage.add(tab5Page3Image6);
		lstQollinishchanEplerImage.add(tab5Page3Image7);
		lstQollinishchanEplerImage.add(tab5Page3Image8);
		for (int i = 0; i < lstQollinishchanEplerImage.size(); i++) {
			final ImageView view = lstQollinishchanEplerImage.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String url = (String) view.getTag();
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					}
				}
			});
		}

		tab5Page3Name1 = (UyghurTextView) contentView.findViewById(R.id.tab5Page3Name1);
		tab5Page3Name2 = (UyghurTextView) contentView.findViewById(R.id.tab5Page3Name2);
		tab5Page3Name3 = (UyghurTextView) contentView.findViewById(R.id.tab5Page3Name3);
		tab5Page3Name4 = (UyghurTextView) contentView.findViewById(R.id.tab5Page3Name4);
		tab5Page3Name5 = (UyghurTextView) contentView.findViewById(R.id.tab5Page3Name5);
		tab5Page3Name6 = (UyghurTextView) contentView.findViewById(R.id.tab5Page3Name6);
		tab5Page3Name7 = (UyghurTextView) contentView.findViewById(R.id.tab5Page3Name7);
		tab5Page3Name8 = (UyghurTextView) contentView.findViewById(R.id.tab5Page3Name8);
		lstQollinishchanEplerName.add(tab5Page3Name1);
		lstQollinishchanEplerName.add(tab5Page3Name2);
		lstQollinishchanEplerName.add(tab5Page3Name3);
		lstQollinishchanEplerName.add(tab5Page3Name4);
		lstQollinishchanEplerName.add(tab5Page3Name5);
		lstQollinishchanEplerName.add(tab5Page3Name6);
		lstQollinishchanEplerName.add(tab5Page3Name7);
		lstQollinishchanEplerName.add(tab5Page3Name8);
		for (int i = 0; i < lstQollinishchanEplerName.size(); i++) {
			final UyghurTextView view = lstQollinishchanEplerName.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String url = (String) view.getTag();
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					}
				}
			});
		}

		tab5Page3Size1 = (TextView) contentView.findViewById(R.id.tab5Page3Size1);
		tab5Page3Size2 = (TextView) contentView.findViewById(R.id.tab5Page3Size2);
		tab5Page3Size3 = (TextView) contentView.findViewById(R.id.tab5Page3Size3);
		tab5Page3Size4 = (TextView) contentView.findViewById(R.id.tab5Page3Size4);
		tab5Page3Size5 = (TextView) contentView.findViewById(R.id.tab5Page3Size5);
		tab5Page3Size6 = (TextView) contentView.findViewById(R.id.tab5Page3Size6);
		tab5Page3Size7 = (TextView) contentView.findViewById(R.id.tab5Page3Size7);
		tab5Page3Size8 = (TextView) contentView.findViewById(R.id.tab5Page3Size8);
		lstQollinishchanEplerSize.add(tab5Page3Size1);
		lstQollinishchanEplerSize.add(tab5Page3Size2);
		lstQollinishchanEplerSize.add(tab5Page3Size3);
		lstQollinishchanEplerSize.add(tab5Page3Size4);
		lstQollinishchanEplerSize.add(tab5Page3Size5);
		lstQollinishchanEplerSize.add(tab5Page3Size6);
		lstQollinishchanEplerSize.add(tab5Page3Size7);
		lstQollinishchanEplerSize.add(tab5Page3Size8);
		for (int i = 0; i < lstQollinishchanEplerSize.size(); i++) {
			final TextView view = lstQollinishchanEplerSize.get(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (view.getTag() != null) {
						String url = (String) view.getTag();
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					}
				}
			});
		}

		View btnTeximuKopXewerlerParent = contentView.findViewById(R.id.btnTeximuKopXewerlerParent);
		btnTeximuKopXewerlerParent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(FragmentMain.this, ActivityNews.class));
			}
		});

		View btnTeximuKopFilimlerParent = contentView.findViewById(R.id.btnTeximuKopFilimlerParent);
		btnTeximuKopFilimlerParent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(FragmentMain.this, ActivityVideo.class));
			}
		});

		btnTeximuKopXewerler = (UyghurTextView) contentView.findViewById(R.id.btnTeximuKopXewerler);
		btnTeximuKopXewerler.setText("تېخىمۇ كۆپ خەۋەرلەر");
		btnTeximuKopXewerler.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);

		btnTeximuKopFilimler = (UyghurTextView) contentView.findViewById(R.id.btnTeximuKopFilimler);
		btnTeximuKopFilimler.setText("تېخىمۇ كۆپ فىلىملەر");
		btnTeximuKopFilimler.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);

		txtFooter1 = (UyghurTextView) contentView.findViewById(R.id.txtFooter1);
		txtFooter1.setText("جەزىرە ئۇلىنىش توربېكىتى");
		txtFooter1.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);

		txtFooter2 = (TextView) contentView.findViewById(R.id.txtFooter2);
		txtFooter2.setText("© 2013-2014 JEZIRE.CN");

		tab2Page1 = (LinearLayout) contentView.findViewById(R.id.tab2Page1);
		tab2Page2 = (LinearLayout) contentView.findViewById(R.id.tab2Page2);
		tab2Page3 = (LinearLayout) contentView.findViewById(R.id.tab2Page3);
		tab3Page1 = (LinearLayout) contentView.findViewById(R.id.tab3Page1);
		tab3Page2 = (LinearLayout) contentView.findViewById(R.id.tab3Page2);
		tab3Page3 = (LinearLayout) contentView.findViewById(R.id.tab3Page3);
		tab4Page1 = (LinearLayout) contentView.findViewById(R.id.tab4Page1);
		tab4Page2 = (LinearLayout) contentView.findViewById(R.id.tab4Page2);
		tab5Page1 = (LinearLayout) contentView.findViewById(R.id.tab5Page1);
		tab5Page2 = (LinearLayout) contentView.findViewById(R.id.tab5Page2);
		tab5Page3 = (LinearLayout) contentView.findViewById(R.id.tab5Page3);

		changeTab(tab2Btn1, new UyghurTextView[] { tab2Btn2, tab2Btn3 }, tab2Page1, new LinearLayout[] { tab2Page2, tab2Page3 });
		changeTab(tab3Btn1, new UyghurTextView[] { tab3Btn2, tab3Btn3 }, tab3Page1, new LinearLayout[] { tab3Page2, tab3Page3 });
		changeTab(tab4Btn1, new UyghurTextView[] { tab4Btn2 }, tab4Page1, new LinearLayout[] { tab4Page2 });
		changeTab(tab5Btn1, new UyghurTextView[] { tab5Btn2, tab5Btn3 }, tab5Page1, new LinearLayout[] { tab5Page2, tab5Page3 });
	}

	private void changeTab(UyghurTextView current, UyghurTextView[] other, LinearLayout currentPage, LinearLayout[] otherPage) {
		if (current != null) {
			current.setTextColor(Color.parseColor("#afc936"));
			current.setBackgroundColor(Color.parseColor("#ffffff"));
		}
		for (UyghurTextView v : other) {
			if (v != null) {
				v.setTextColor(Color.parseColor("#979797"));
				v.setBackgroundColor(Color.parseColor("#ebebeb"));
			}
		}

		if (currentPage != null) {
			currentPage.setVisibility(View.VISIBLE);
		}
		for (LinearLayout v : otherPage) {
			if (v != null) {
				v.setVisibility(View.GONE);
			}
		}
	}

	private void loadDangliqBeketler(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONArray array = new JSONArray(response);
				if (array != null && array.length() > 0) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						String name = obj.getString("linkname");
						String url = obj.getString("phone_linkadress");
						String img = obj.getString("orginal");

						if ((name != null && name.length() > 0) && (url != null && url.length() > 0) && (img != null && img.length() > 0)) {
							if (i < lstDangliqBeketlerName.size()) {
								lstDangliqBeketlerName.get(i).setText(name);
								lstDangliqBeketlerName.get(i).setTag(url);
								lstDangliqBeketlerIcon.get(i).setTag(url);
								App.IMAGE_LOADER.displayImage(App.SERVER_URL + "/" + img, lstDangliqBeketlerIcon.get(i), new DisplayImageOptions.Builder().cacheOnDisc(true).build());
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	private void loadYengiXewerlerOne(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONObject obj = new JSONObject(response);
				String id = obj.getString("id");
				String title = obj.getString("title");
				String img = obj.getString("orginal");

				if ((id != null && id.length() > 0) && (title != null && title.length() > 0) && (img != null && img.length() > 0)) {
					tab2YengiXewerlerTitle.setText(title);
					tab2YengiXewerlerTitle.setTag(id);
					tab2YengiXewerlerImage.setTag(id);
					App.IMAGE_LOADER.displayImage(App.SERVER_URL + "/" + img, tab2YengiXewerlerImage, new DisplayImageOptions.Builder().cacheOnDisc(true).build());
				}
			}
		} catch (Exception e) {
		}
	}

	private void loadXeliqaraXewerlerOne(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONObject obj = new JSONObject(response);
				String id = obj.getString("id");
				String title = obj.getString("title");
				String img = obj.getString("orginal");

				if ((id != null && id.length() > 0) && (title != null && title.length() > 0) && (img != null && img.length() > 0)) {
					tab2XeliqaraXewerlerTitle.setText(title);
					tab2XeliqaraXewerlerTitle.setTag(id);
					tab2XeliqaraXewerlerImage.setTag(id);
					App.IMAGE_LOADER.displayImage(App.SERVER_URL + "/" + img, tab2XeliqaraXewerlerImage, new DisplayImageOptions.Builder().cacheOnDisc(true).build());
				}
			}
		} catch (Exception e) {
		}
	}

	private void loadMemliketXewerlerOne(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONObject obj = new JSONObject(response);
				String id = obj.getString("id");
				String title = obj.getString("title");
				String img = obj.getString("orginal");

				if ((id != null && id.length() > 0) && (title != null && title.length() > 0) && (img != null && img.length() > 0)) {
					tab2MemliketXewerlerTitle.setText(title);
					tab2MemliketXewerlerTitle.setTag(id);
					tab2MemliketXewerlerImage.setTag(id);
					App.IMAGE_LOADER.displayImage(App.SERVER_URL + "/" + img, tab2MemliketXewerlerImage, new DisplayImageOptions.Builder().cacheOnDisc(true).build());
				}
			}
		} catch (Exception e) {
		}
	}

	private void loadYengiXewerler(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONArray array = new JSONArray(response);
				if (array != null && array.length() > 0) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						String id = obj.getString("id");
						String title = obj.getString("title");

						if ((id != null && id.length() > 0) && (title != null && title.length() > 0)) {
							if (i < lstXeliqaraXewerler.size()) {
								lstXeliqaraXewerler.get(i).setText(title);
								lstXeliqaraXewerler.get(i).setTag(id);
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	private void loadXeliqaraXewerler(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONArray array = new JSONArray(response);
				if (array != null && array.length() > 0) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						String id = obj.getString("id");
						String title = obj.getString("title");

						if ((id != null && id.length() > 0) && (title != null && title.length() > 0)) {
							if (i < lstYengiXewerler.size()) {
								lstYengiXewerler.get(i).setText(title);
								lstYengiXewerler.get(i).setTag(id);
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	private void loadMemliketXewerler(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONArray array = new JSONArray(response);
				if (array != null && array.length() > 0) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						String id = obj.getString("id");
						String title = obj.getString("title");

						if ((id != null && id.length() > 0) && (title != null && title.length() > 0)) {
							if (i < lstMemliketXewerler.size()) {
								lstMemliketXewerler.get(i).setText(title);
								lstMemliketXewerler.get(i).setTag(id);
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	private void loadYengiFilimler(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONArray array = new JSONArray(response);
				if (array != null && array.length() > 0) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						String id = obj.getString("id");
						String name = obj.getString("name");
						String img = obj.getString("orginal");

						if ((id != null && id.length() > 0) && (name != null && name.length() > 0) && (img != null && img.length() > 0)) {
							if (i < lstYengiFilimlerName.size()) {
								lstYengiFilimlerName.get(i).setText(name);
								lstYengiFilimlerName.get(i).setTag(id);
								lstYengiFilimlerImage.get(i).setTag(id);
								App.IMAGE_LOADER.displayImage(App.SERVER_URL + "/" + img, lstYengiFilimlerImage.get(i), new DisplayImageOptions.Builder().cacheOnDisc(true).build());
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	private void loadMTVlar(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONArray array = new JSONArray(response);
				if (array != null && array.length() > 0) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						String id = obj.getString("id");
						String name = obj.getString("name");
						String img = obj.getString("orginal");

						if ((id != null && id.length() > 0) && (name != null && name.length() > 0) && (img != null && img.length() > 0)) {
							if (i < lstMTVlarName.size()) {
								lstMTVlarName.get(i).setText(name);
								lstMTVlarName.get(i).setTag(id);
								lstMTVlarImage.get(i).setTag(id);
								App.IMAGE_LOADER.displayImage(App.SERVER_URL + "/" + img, lstMTVlarImage.get(i), new DisplayImageOptions.Builder().cacheOnDisc(true).build());
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	private void loadQiziqarliq(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONArray array = new JSONArray(response);
				if (array != null && array.length() > 0) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						String id = obj.getString("id");
						String name = obj.getString("name");
						String img = obj.getString("orginal");

						if ((id != null && id.length() > 0) && (name != null && name.length() > 0) && (img != null && img.length() > 0)) {
							if (i < lstQiziqarliqName.size()) {
								lstQiziqarliqName.get(i).setText(name);
								lstQiziqarliqName.get(i).setTag(id);
								lstQiziqarliqImage.get(i).setTag(id);
								App.IMAGE_LOADER.displayImage(App.SERVER_URL + "/" + img, lstQiziqarliqImage.get(i), new DisplayImageOptions.Builder().cacheOnDisc(true).build());
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	private void loadYengiYazmilar(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONArray array = new JSONArray(response);
				if (array != null && array.length() > 0) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						String url = obj.getString("id");
						String title = obj.getString("title");

						if ((url != null && url.length() > 0) && (title != null && title.length() > 0)) {
							if (i < lstYengiYazmilar.size()) {
								lstYengiYazmilar.get(i).setText(title);
								lstYengiYazmilar.get(i).setTag(url);
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	private void loadJewherYazmilar(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONArray array = new JSONArray(response);
				if (array != null && array.length() > 0) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						String url = obj.getString("id");
						String title = obj.getString("title");

						if ((url != null && url.length() > 0) && (title != null && title.length() > 0)) {
							if (i < lstJewherYazmilar.size()) {
								lstJewherYazmilar.get(i).setText(title);
								lstJewherYazmilar.get(i).setTag(url);
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
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

	private void loadUyghurcheEpler(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONArray array = new JSONArray(response);
				if (array != null && array.length() > 0) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);

						String name = obj.getString("name");
						String url = obj.getString("address");
						String size = obj.getString("size");
						String img = obj.getString("orginal");

						if ((name != null && name.length() > 0) && (url != null && url.length() > 0) && (size != null && size.length() > 0) && (img != null && img.length() > 0)) {
							if (i < lstUyghurcheEplerName.size()) {
								lstUyghurcheEplerName.get(i).setText(name);
								lstUyghurcheEplerSize.get(i).setText(size);
								lstUyghurcheEplerName.get(i).setTag(url);
								lstUyghurcheEplerSize.get(i).setTag(url);
								lstUyghurcheEplerImage.get(i).setTag(url);
								App.IMAGE_LOADER.displayImage(App.SERVER_URL + "/" + img, lstUyghurcheEplerImage.get(i), new DisplayImageOptions.Builder().cacheOnDisc(true).build());
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	private void loadOyunlar(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONArray array = new JSONArray(response);
				if (array != null && array.length() > 0) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);

						String name = obj.getString("name");
						String url = obj.getString("address");
						String size = obj.getString("size");
						String img = obj.getString("orginal");

						if ((name != null && name.length() > 0) && (url != null && url.length() > 0) && (size != null && size.length() > 0) && (img != null && img.length() > 0)) {
							if (i < lstOyunlarName.size()) {
								lstOyunlarName.get(i).setText(name);
								lstOyunlarSize.get(i).setText(size);
								lstOyunlarName.get(i).setTag(url);
								lstOyunlarSize.get(i).setTag(url);
								lstOyunlarImage.get(i).setTag(url);
								App.IMAGE_LOADER.displayImage(App.SERVER_URL + "/" + img, lstOyunlarImage.get(i), new DisplayImageOptions.Builder().cacheOnDisc(true).build());
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	private void loadQollinishchanEpler(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONArray array = new JSONArray(response);
				if (array != null && array.length() > 0) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);

						String name = obj.getString("name");
						String url = obj.getString("address");
						String size = obj.getString("size");
						String img = obj.getString("orginal");

						if ((name != null && name.length() > 0) && (url != null && url.length() > 0) && (size != null && size.length() > 0) && (img != null && img.length() > 0)) {
							if (i < lstQollinishchanEplerName.size()) {
								lstQollinishchanEplerName.get(i).setText(name);
								lstQollinishchanEplerSize.get(i).setText(size);
								lstQollinishchanEplerName.get(i).setTag(url);
								lstQollinishchanEplerSize.get(i).setTag(url);
								lstQollinishchanEplerImage.get(i).setTag(url);
								App.IMAGE_LOADER.displayImage(App.SERVER_URL + "/" + img, lstQollinishchanEplerImage.get(i), new DisplayImageOptions.Builder().cacheOnDisc(true).build());
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	private void loadDataDangliqBeketler() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=links&f=getDangliq", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadDangliqBeketler(responseBody);
				loadDataYengiXewerlerOne();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				showLayout(layoutFailure);
			}
		});
	}

	private void loadDataYengiXewerlerOne() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=news&f=getNadirByCat&catid=0", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				showLayout(scrollView);
				loadYengiXewerlerOne(responseBody);
				loadDataYengiXewerler();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				showLayout(layoutFailure);
			}
		});
	}

	private void loadDataYengiXewerler() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=news&f=getLatest&p=0&pagesize=5", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadYengiXewerler(responseBody);
				loadDataYengiFilimler();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				showLayout(layoutFailure);
			}
		});
	}

	private void loadDataYengiFilimler() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=video&f=getLatest&p=0&pagesize=6", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadYengiFilimler(responseBody);
				loadDataYengiYazmilar();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			//	showLayout(layoutFailure);
			}
		});
	}

	private void loadDataYengiYazmilar() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=news&f=getLatestArticle&p=5", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadYengiYazmilar(responseBody);
				loadDataXeliqaraXewerlerOne();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				//showLayout(layoutFailure);
			}
		});
	}

	private void loadDataXeliqaraXewerlerOne() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=news&f=getNadirByCat&catid=1", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadXeliqaraXewerlerOne(responseBody);
				loadDataXeliqaraXewerler();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			//	showLayout(layoutFailure);
			}
		});
	}

	private void loadDataXeliqaraXewerler() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=news&f=getByCat&catid=1&p=0&pagesize=5", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadXeliqaraXewerler(responseBody);
				loadDataMemliketXewerlerOne();
				loadDataMemliketXewerler();
				loadDataMTVlar();
				loadDataQiziqarliq();
				loadDataJewherYazmilar();
				loadDataAd();
				loadDataUyghurcheEpler();
				loadDataOyunlar();
				loadDataQollinishchanEpler();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			//	showLayout(layoutFailure);
			}
		});
	}

	private void loadDataMemliketXewerlerOne() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=news&f=getNadirByCat&catid=7", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadMemliketXewerlerOne(responseBody);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			//	showLayout(layoutFailure);
			}
		});
	}

	private void loadDataMemliketXewerler() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=news&f=getByCat&catid=7&p=0&pagesize=5", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadMemliketXewerler(responseBody);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			//	showLayout(layoutFailure);
			}
		});
	}

	private void loadDataMTVlar() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=video&f=getByBigCat&id=1&p=0&pagesize=6", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadMTVlar(responseBody);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			//	showLayout(layoutFailure);
			}
		});
	}

	private void loadDataQiziqarliq() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=video&f=getByBigCat&id=5&p=0&pagesize=6", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadQiziqarliq(responseBody);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			//	showLayout(layoutFailure);
			}
		});
	}

	private void loadDataJewherYazmilar() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=news&f=getJewherArticle&p=5", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadJewherYazmilar(responseBody);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			//	showLayout(layoutFailure);
			}
		});
	}

	private void loadDataAd() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=system&f=GetAndroidAds", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadAd(responseBody);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			//	showLayout(layoutFailure);
			}
		});
	}

	private void loadDataUyghurcheEpler() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=apps&f=getAppsByCat&catid=5&pagesize=8", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadUyghurcheEpler(responseBody);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			//	showLayout(layoutFailure);
			}
		});
	}

	private void loadDataOyunlar() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=apps&f=getAppsByCat&catid=7&pagesize=8", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadOyunlar(responseBody);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				//showLayout(layoutFailure);
			}
		});
	}

	private void loadDataQollinishchanEpler() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=apps&f=getAppsByCat&catid=9&pagesize=8", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadQollinishchanEpler(responseBody);
				showLayout(scrollView);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			//	showLayout(layoutFailure);
			}
		});
	}

	private void loadData() {
		loadDataDangliqBeketler();
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
	public class GridViewAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		private Context mContext = null;

		public GridViewAdapter(Context context) {
			mContext = context;
			mInflater = LayoutInflater.from(mContext);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return xurjunEntries.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return xurjunEntries.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			// TODO Auto-generated method stub
			UyghurTextView XurjunTitle;
			ImageView imgIcon;

			XurjunEntry entry = xurjunEntries.get(position);
			
			if (view == null) {
				view = mInflater.inflate(R.layout.index_xurjun_item, null);
			}
			XurjunTitle = (UyghurTextView) view.findViewById(R.id.txtTitle);
			XurjunTitle.setText(entry.urlname);
			imgIcon = (ImageView)view.findViewById(R.id.imgIcon);
			
			return view;
		}
		
	}
	
	public class XurjunEntry{
		public String id;
		public String urlname;
		public String urladdress;
		public boolean more;
	}
}


