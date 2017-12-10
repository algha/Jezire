package com.jezire.activity;

import com.jezire.App;
import com.jezire.R;
import com.jezire.widget.IconView;
import com.jezire.widget.UyghurTextView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


public class ActivityAboutApp extends Activity {
	private IconView btnClose;
	private UyghurTextView qollanchi_kilishimi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		overridePendingTransition(R.anim.animation_to_left, R.anim.animation_to_left_out);
		
		setContentView(R.layout.layout_activity_aboutapp);
		initialize();
		}
		
		public void initialize(){
			btnClose = (IconView)findViewById(R.id.btnClose);
			btnClose.setText("\ue006");
			btnClose.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onBackPressed();
					overridePendingTransition(R.anim.animation_to_right, R.anim.animation_to_right_out);
				}
			});
	
			qollanchi_kilishimi = (UyghurTextView)findViewById(R.id.KilishimButton);
			qollanchi_kilishimi.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(ActivityAboutApp.this, ActivityBrowser.class);
					intent.putExtra("url", App.SERVER_URL+"?h=phone&t=system&f=Qollanchi");
					startActivity(intent);
				}
			});
		}
		
		
}
