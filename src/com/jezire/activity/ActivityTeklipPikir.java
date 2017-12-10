package com.jezire.activity;

import java.util.HashMap;
import org.apache.http.Header;

import com.jezire.App;
import com.jezire.R;
import com.jezire.algha.AToast;
import com.jezire.http.AsyncHttpClient;
import com.jezire.http.AsyncHttpResponseHandler;
import com.jezire.http.RequestParams;
import com.jezire.widget.IconView;
import com.jezire.widget.UyghurTextView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class ActivityTeklipPikir extends Activity {
	private IconView btnClose;
	private EditText txtName,txtContact,txtContent;
	private boolean IsSending = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		overridePendingTransition(R.anim.animation_to_left, R.anim.animation_to_left_out);
		
		setContentView(R.layout.layout_teklip);
		initialize();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		overridePendingTransition(R.anim.animation_to_right_out, R.anim.animation_to_right);
		finish();
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
		
		txtName = (EditText)findViewById(R.id.txtName);
		txtContact = (EditText)findViewById(R.id.txtContact);
		txtContent = (EditText)findViewById(R.id.txtContent);
		
	
		
		UyghurTextView Post = (UyghurTextView)findViewById(R.id.Post);
		Post.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (IsSending == false) {
					IsSending = true;
					
					
					if (txtName.getText().length() == 0) {
						AToast toast = new AToast(ActivityTeklipPikir.this);
						toast.show("ئىسمىڭىزنى يېزىڭ !", 3);
						IsSending = false;
						return;
					}
					
					if (txtContact.getText().length() == 0) {
						AToast toast = new AToast(ActivityTeklipPikir.this);
						toast.show("ئىسمىڭىزنى يېزىڭ !", 3);
						IsSending = false;
						return;
					}
					
					if (txtContent.getText().length() == 0) {
						AToast toast = new AToast(ActivityTeklipPikir.this);
						toast.show("ئىسمىڭىزنى يېزىڭ !", 3);
						IsSending = false;
						return;
					}
					
					HashMap<String, String> hashMap = new HashMap<String, String>();
					hashMap.put("name", txtName.getText().toString());
					hashMap.put("alaqe", txtContact.getText().toString());
					hashMap.put("content", txtContent.getText().toString());
					hashMap.put("type", "Android");
					
					AsyncHttpClient client = new AsyncHttpClient();
					client.post(App.SERVER_URL+"?h=phone&t=system&f=Sugguest", new RequestParams(hashMap), new AsyncHttpResponseHandler() {
						
						@Override
						public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
							// TODO Auto-generated method stub
							
							
							String reString = new String(responseBody);
							
							if (reString.equals("OK")) {
								AToast toast = new AToast(ActivityTeklipPikir.this);
								toast.show("مۇۋاپىقىيەتلىك يوللاندى ، ھازىر ئالدىنقى بەتكە قايتىدۇ .", 3);
								
								new Thread(new Runnable() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										try {
											Thread.sleep(3000);
										} catch (InterruptedException e) {
											// TODO: handle exception
											e.printStackTrace();
										}
										handler.sendMessage(handler.obtainMessage(1));
										IsSending = false;
									 	
										 
									}
								}).start();
								
							}else if(reString.equals("ERROR")){
								AToast toast = new AToast(ActivityTeklipPikir.this);
								toast.show("خاتالىق كۆرۈلدى ، قايتا سىناڭ .", 3);
								IsSending = false;
							}
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							// TODO Auto-generated method stub
							IsSending = false;
							AToast toast = new AToast(ActivityTeklipPikir.this);
							toast.show("يوللاش مەغلۇب بولدى ، تورىڭىزنى تەكشۈرۈڭ !", 3);
						}
					});
				}
			}
		});
	}
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			onBackPressed();
		};
	};
}


