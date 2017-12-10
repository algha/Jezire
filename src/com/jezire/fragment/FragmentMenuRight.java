package com.jezire.fragment;

import com.jezire.App;
import com.jezire.App.Preferences;
import com.jezire.R;
import com.jezire.activity.ActivityBrowser;
import com.jezire.activity.ActivityNews;
import com.jezire.activity.ActivityNote;
import com.jezire.activity.ActivityVideo;
import com.jezire.widget.IconView;
import com.jezire.widget.UyghurTextView;
import com.jezire.activity.ActivityXurjun;
import com.jezire.activity.ActivitySettings;
import com.jezire.activity.ActivityLogin;
import com.jezire.algha.AToast;
import com.jezire.algha.myAlertDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class FragmentMenuRight extends FragmentActivity {

	private ImageView avatar;
	private ImageView btnBrowser, btnXatire, btnVideo, btnNews, btnXurjun;
	private UyghurTextView txtBrowser, txtXatire, txtVideo, txtNews, txtXurjun;
	private IconView btnSettings, btnExit, btnOut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize();
	}

	private void initialize() {
		View contentView = getLayoutInflater().inflate(
				R.layout.layout_fragment_menu_right, null);
		initializeView(contentView);
		setContentView(contentView);

		App.FRAGMENT_MENU_RIGHT = this;
	}

	private void initializeView(View contentView) {
		avatar = (ImageView) contentView.findViewById(R.id.avatar);
		btnBrowser = (ImageView) contentView.findViewById(R.id.btnBrowser);
		txtBrowser = (UyghurTextView) contentView.findViewById(R.id.txtBrowser);
		btnXatire = (ImageView) contentView.findViewById(R.id.btnXatire);
		txtXatire = (UyghurTextView) contentView.findViewById(R.id.txtXatire);
		btnVideo = (ImageView) contentView.findViewById(R.id.btnVideo);
		txtVideo = (UyghurTextView) contentView.findViewById(R.id.txtVideo);
		btnNews = (ImageView) contentView.findViewById(R.id.btnNews);
		txtNews = (UyghurTextView) contentView.findViewById(R.id.txtNews);

		btnXurjun = (ImageView) contentView.findViewById(R.id.btnXurjun);
		txtXurjun = (UyghurTextView) contentView.findViewById(R.id.txtXurjun);

		avatar.setImageBitmap(App.getRoundedCornerBitmap(BitmapFactory
				.decodeResource(getResources(), R.drawable.ic_launcher), 360));
		avatar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Preferences.getUserName().trim().length() == 0
						|| Preferences.getUserPassword().trim().length() == 0) {
					startActivity(new Intent(FragmentMenuRight.this,
							ActivityLogin.class));
				} else {
					final myAlertDialog ad = new myAlertDialog(
							FragmentMenuRight.this);
					ad.setTitle("ئەسكەرتىش");
					ad.setMessage("سېستىمىغا كىرىپ بولۇپسىز ، چىكىنىپ چىققاندىن كىيىن ئاندىن كىرەلەيسىز !");
					ad.setPositiveButton("بىلدىم", new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ad.dismiss();
						}
					});
				}
			}
		});

		txtBrowser.setText("تور كۆرگۈ");
		txtBrowser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FragmentMenuRight.this,
						ActivityBrowser.class);
				intent.putExtra("url", "about:blank");
				startActivity(intent);
			}
		});
		btnBrowser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FragmentMenuRight.this,
						ActivityBrowser.class);
				intent.putExtra("url", "about:blank");
				startActivity(intent);
			}
		});

		txtXatire.setText("خاتىرە");
		txtXatire.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(FragmentMenuRight.this,
						ActivityNote.class));
			}
		});
		btnXatire.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(FragmentMenuRight.this,
						ActivityNote.class));
			}
		});

		txtVideo.setText("فىلىملەر");
		txtVideo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(FragmentMenuRight.this,
						ActivityVideo.class));
			}
		});
		btnVideo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(FragmentMenuRight.this,
						ActivityVideo.class));
			}
		});

		txtNews.setText("خەۋەرلەر");
		txtNews.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(FragmentMenuRight.this,
						ActivityNews.class));
			}
		});
		btnNews.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(FragmentMenuRight.this,
						ActivityNews.class));
			}
		});

		txtXurjun.setText("خۇرجۇن");
		txtXurjun.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (Preferences.getUserName().trim().length() == 0
						|| Preferences.getUserPassword().trim().length() == 0) {
					final myAlertDialog ad = new myAlertDialog(
							FragmentMenuRight.this);
					ad.setTitle("ئەسكەرتىش");
					ad.setMessage("تېخى كىرمەپسىز ، كىرگەندىن كىيىن ئاندىن مەشخۇلات قىلالايسىز !");
					ad.setPositiveButton("بىلدىم", new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ad.dismiss();
						}
					});
					return;
				}
				// TODO Auto-generated method stub
				startActivity(new Intent(FragmentMenuRight.this,
						ActivityXurjun.class));
			}
		});
		btnXurjun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Preferences.getUserName().trim().length() == 0
						|| Preferences.getUserPassword().trim().length() == 0) {
					final myAlertDialog ad = new myAlertDialog(
							FragmentMenuRight.this);
					ad.setTitle("ئەسكەرتىش");
					ad.setMessage("تېخى كىرمەپسىز ، كىرگەندىن كىيىن ئاندىن مەشخۇلات قىلالايسىز !");
					ad.setPositiveButton("بىلدىم", new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ad.dismiss();
						}
					});
					return;
				}
				startActivity(new Intent(FragmentMenuRight.this,
						ActivityXurjun.class));
			}
		});

		btnExit = (IconView) contentView.findViewById(R.id.btnExit);
		btnExit.setText("\ue097");
		btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				System.exit(0);
			}
		});

		btnSettings = (IconView) contentView.findViewById(R.id.btnSettings);
		btnSettings.setText("\ue052");
		btnSettings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(FragmentMenuRight.this,
						ActivitySettings.class));
			}
		});

		btnOut = (IconView) contentView.findViewById(R.id.btnOut);
		btnOut.setText("\ue065");
		btnOut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Preferences.getUserName().trim().length() == 0
						|| Preferences.getUserPassword().trim().length() == 0) {
					final myAlertDialog ad = new myAlertDialog(
							FragmentMenuRight.this);
					ad.setTitle("ئەسكەرتىش");
					ad.setMessage("سېستىما كىرمەپسىز ، كىرگەندىن كىيىن ئاندىن چىكىنەلەيسىز !");
					ad.setPositiveButton("بىلدىم", new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ad.dismiss();
						}
					});
				} else {
					// TODO Auto-generated method stub
					Preferences.setUserAccount(null);
					Preferences.setUserPassword(null);
					myExit();
				}
			}
		});
	}
	 protected void myExit() {  
	     Intent intent = new Intent();  
	     intent.setAction("LogOut");  
	     this.sendBroadcast(intent);  
	 }  
}
