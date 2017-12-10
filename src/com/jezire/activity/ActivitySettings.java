package com.jezire.activity;


import org.apache.http.Header;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.jezire.App;
import com.jezire.App.Preferences;
import com.jezire.R;
import com.jezire.algha.AToast;
import com.jezire.algha.ScreenBrightness;
import com.jezire.algha.myAlertDialog;
import com.jezire.http.AsyncHttpClient;
import com.jezire.http.AsyncHttpResponseHandler;
import com.jezire.universalimageloader.DiscCacheAware;
import com.jezire.widget.IconView;
import com.jezire.widget.UyghurTextView;

public class ActivitySettings extends Activity{
	private IconView btnClose;
	private UyghurTextView txtTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		overridePendingTransition(R.anim.animation_in_up, R.anim.animation_out_up);
		
		setContentView(R.layout.layout_activity_settings);
		
		initialize();
	}
	
	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.animation_in_down, R.anim.animation_out_down);
	}
	
	private void initialize() {
		
		btnClose = (IconView)findViewById(R.id.btnClose);
		btnClose.setText("\ue021");
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
				finish();
				overridePendingTransition(R.anim.animation_in_down, R.anim.animation_out_down);
			}
		});
		
		txtTitle = (UyghurTextView)findViewById(R.id.txtTitle);
		txtTitle.setText("تەڭشەك");
		
		TextView CacheSize = (TextView)findViewById(R.id.CacheSize);
		DiscCacheAware cAware =  App.IMAGE_LOADER.getDiscCache();
	
		
		final View siyrilmauqur = (View)findViewById(R.id.MIcon);
		
		final View SiyrilmaImage = (View)findViewById(R.id.SiyrilmaImage);
		final LayoutParams params = (LayoutParams)SiyrilmaImage.getLayoutParams();
		if (Preferences.getSiyrilmaUchur() == false) {
			siyrilmauqur.setBackgroundResource(R.drawable.siyrilma_uchur_closed);
			params.setMargins(28, 0, 0, 0);
			SiyrilmaImage.setLayoutParams(params);
		}else{
			siyrilmauqur.setBackgroundResource(R.drawable.siyrilma_uchur);
			params.setMargins(1, 0, 0, 0);
			SiyrilmaImage.setLayoutParams(params);
		}
		siyrilmauqur.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Preferences.getSiyrilmaUchur() == false) {
					siyrilmauqur.setBackgroundResource(R.drawable.siyrilma_uchur);
					params.setMargins(1, 0, 0, 0);
					SiyrilmaImage.setLayoutParams(params);
					Preferences.setSiyrilmaUchur(true);
				}else if (Preferences.getSiyrilmaUchur() == true){
					siyrilmauqur.setBackgroundResource(R.drawable.siyrilma_uchur_closed);
					params.setMargins(28, 0, 0, 0);
					SiyrilmaImage.setLayoutParams(params);
					Preferences.setSiyrilmaUchur(false);
				}
			}
		});
		
		View ScreenBright = (View)findViewById(R.id.ScreenBrightness);
		ScreenBright.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new ScreenBrightness(ActivitySettings.this, arg0);
			}
		});
		
		View ClearGhemlek = (View)findViewById(R.id.ClearGhemlek);
		ClearGhemlek.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AToast toast = new AToast(ActivitySettings.this);
				toast.show("مۇۋاپىقىيەتلىك تازىلاندى .", 3);
			}
		});
		
//		View Qisturmilar = (View)findViewById(R.id.Qisturmilar);
//		Qisturmilar.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
		View teklip = (View)findViewById(R.id.teklippost);
		teklip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ActivitySettings.this, ActivityTeklipPikir.class));
			}
		});
		
		
		View NewVersion = (View)findViewById(R.id.NewVersion);
		NewVersion.setOnClickListener(new VersionListener());
		
		View AppHeqqide = (View)findViewById(R.id.AppHeqqide);
		AppHeqqide.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ActivitySettings.this, ActivityAboutApp.class));
			}
		});
	}
	
	public class VersionListener implements OnClickListener{
		AToast LoadingToast;
		boolean isLoading = false;
		@Override
		public void onClick(View arg0) {
			
			if (isLoading == true) {
				return;
			}
			
			isLoading = true;
			
			LoadingToast = new AToast(ActivitySettings.this);
			LoadingToast.show("تەكشۈرۈۋاتىدۇ ، تەخىر قىلىڭ ...", 1000*60);
			
			
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(App.SERVER_URL+"?h=phone&t=system&f=version", new AsyncHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
					isLoading = false;
					LoadingToast.cancel();
					// TODO Auto-generated method stub
					String string = new String(responseBody);
					try {
						JSONObject object = new JSONObject(string);
						String Version = object.getString("Android");
						final String Address = object.getString("AndroidAddress");
						
						if (Version.equals("2.0.1") == false) {

							final myAlertDialog ad  =  new myAlertDialog(ActivitySettings.this);
							ad.setTitle("ئەسكەرتىش");
							ad.setMessage("يىڭى نەشىرى چىقىپتۇ ، يىڭى نەشىرىدە تېخىمۇ ئەپ تېخىمۇ ئەلالاشتۇرۇلغان ، يېڭىلامسىز ؟");
							ad.setPositiveButton("ھازىرلا يېڭىلاي", new OnClickListener() {
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Uri uri = Uri.parse(Address);
									Intent it = new Intent(Intent.ACTION_VIEW,uri);
									startActivity(it);
									ad.dismiss();
								}
							});
							ad.setNegativeButton("كىينچە يېڭىلاي", new OnClickListener() {
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									ad.dismiss();
								}
							});
						}else{
							AToast toast = new AToast(ActivitySettings.this);
							toast.show("نۆۋەتتىكى نەشىرى ئەڭ يېڭىسى ئىكەن ، يىڭىلاش ھاجەتسىز .", 3);
						}
						
					} catch (Exception e) {
						LoadingToast.cancel();
						// TODO: handle exception
						AToast toast = new AToast(ActivitySettings.this);
						toast.show("توردىن ئوقۇش مەغلوب بولدى ، قايتا سىناڭ .", 3);
					}
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					isLoading = false;
					AToast toast = new AToast(ActivitySettings.this);
					toast.show("توردىن ئوقۇش مەغلوب بولدى ، قايتا سىناڭ .", 3);
				}
			});
		}
	}
	
}
