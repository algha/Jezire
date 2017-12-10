package com.jezire.activity;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.Header;
import org.json.JSONObject;
import com.jezire.App;
import com.jezire.App.Preferences;
import com.jezire.R;
import com.jezire.algha.AToast;
import com.jezire.http.AsyncHttpClient;
import com.jezire.http.AsyncHttpResponseHandler;
import com.jezire.http.RequestParams;
import com.jezire.widget.IconView;
import com.jezire.widget.UyghurTextView;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

public class ActivityLogin extends Activity {
	private View login_main, Register, GetPassword;
	private HorizontalScrollView HorizontalView;
	private EditText LoginAccount, LoginPassword;
	private Boolean isUploading = false;
	private String imageSrc = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.animation_in_up,R.anim.animation_out_up);

		setContentView(R.layout.layout_activity_login);

		initialize();
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.animation_in_down,R.anim.animation_out_down);
	}

	private void initialize() {

		HorizontalView = (HorizontalScrollView)findViewById(R.id.HorizontalView);

		HorizontalView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(50);
				} catch (Exception e) {
					// TODO: handle exception
				}
				handler2.sendMessage(handler2.obtainMessage(0));
			}
		}).start();
		
		InitLogin();
		InitRegister();
		InitForget();
	}

	Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
			HorizontalView.smoothScrollTo(App.DISPLAY_METRICS.widthPixels, 0);
		};

	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		ImageView Registeravatar = (ImageView) findViewById(R.id.Registeravatar);
		final ImageView registerLoading = (ImageView) findViewById(R.id.registerLoading);
		if (resultCode == Activity.RESULT_OK) {
			/**
			 * 当选择的图片不为空的话，在获取到图片的途径
			 */
			Uri uri = data.getData();
			try {
				String[] pojo = { MediaStore.Images.Media.DATA };

				@SuppressWarnings("deprecation")
				Cursor cursor = managedQuery(uri, pojo, null, null, null);
				if (cursor != null) {
					ContentResolver cr = this.getContentResolver();
					int colunm_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					String path = cursor.getString(colunm_index);
					/***
					 * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，
					 * 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
					 */
					if (path.endsWith("jpg") || path.endsWith("png")) {

						isUploading = true;

						Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
						Registeravatar.setImageBitmap(bitmap);

						File file = new File(path);

						if (file.exists() == false || file.length() <= 0) {
							AToast toast = new AToast(ActivityLogin.this);
							toast.show("ھۆججەت تېپلىمىدى ، قايتا سىناڭ !",3000);
							isUploading = false;
							return;
						}

						isShowingLoading(registerLoading);
						RequestParams params = new RequestParams();
						params.put("file", file);

						AsyncHttpClient clinet = new AsyncHttpClient();
						clinet.post(App.SERVER_URL+"?h=common&t=upload&f=upload&folder=user&type=image",
								params, new AsyncHttpResponseHandler() {
									@Override
									public void onSuccess(int statusCode,Header[] headers,byte[] responseBody) {
										isUploading = false;
										// TODO Auto-generated method stub
										isShowingLoading(registerLoading);
										String reString = new String(responseBody);
										
										try {
											JSONObject object = new JSONObject(reString);
											if (object.get("address") != null) {
												imageSrc = object.get("id").toString();
												AToast toast = new AToast(ActivityLogin.this);
												toast.show("مۇۋاپىقىيەتلىك يۈكلەندى .",3000);
											} else {
												AToast toast = new AToast(ActivityLogin.this);
												toast.show("رەسىمنى يۈكلەشتە خاتالىق كۆرۈلدى ، قايتا سىناڭ !",3000);
											}
										} catch (Exception e) {
											// TODO: handle exception
											AToast toast = new AToast(ActivityLogin.this);
											toast.show("رەسىمنى يۈكلەشتە خاتالىق كۆرۈلدى ، قايتا سىناڭ !",3000);
										}
									}

									@Override
									public void onFailure(int statusCode,
											Header[] headers,
											byte[] responseBody, Throwable error) {
										isUploading = false;
										isShowingLoading(registerLoading);
										// TODO Auto-generated method stub
										AToast toast = new AToast(ActivityLogin.this);
										toast.show("رەسىمنى يۈكلەشتە خاتالىق كۆرۈلدى ، قايتا سىناڭ !",3000);
									}
								});

					} else {
						AToast toast = new AToast(ActivityLogin.this);
						toast.show("پەقەت رەسىملا تاللاشقا بولىدۇ.", 3000);
					}
				} else {
					AToast toast = new AToast(ActivityLogin.this);
					toast.show("پەقەت رەسىملا تاللاشقا بولىدۇ.", 3000);
				}

			} catch (Exception e) {
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public void InitLogin() {
		IconView btnClose = (IconView) findViewById(R.id.btnClose);
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
				overridePendingTransition(R.anim.animation_in_down,R.anim.animation_out_down);
			}
		});

		ImageView Registeravatar = (ImageView) findViewById(R.id.Registeravatar);
		Registeravatar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.setType("image/*");
//				intent.setAction(Intent.ACTION_GET_CONTENT);
//				startActivityForResult(intent, 1);
			}
		});

		login_main = (View) findViewById(R.id.login_main);
		LayoutParams p = (LayoutParams) login_main.getLayoutParams();
		p.width = App.DISPLAY_METRICS.widthPixels;
		login_main.setLayoutParams(p);

		LoginAccount = (EditText) findViewById(R.id.LoginAccount);
		onFocus(LoginAccount);
		LoginPassword = (EditText) findViewById(R.id.LoginPassword);
		onFocus(LoginPassword);

		UyghurTextView LoginButton = (UyghurTextView) findViewById(R.id.LoginButton);

		final ImageView loginLoading = (ImageView) findViewById(R.id.loginLoading);

		onPress(LoginButton, "#afc936", "#ffffff");
		LoginButton.setOnClickListener(new OnClickListener() {
			boolean logging = false;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (LoginAccount.getText().length() == 0
						|| LoginPassword.getText().length() == 0) {
					return;
				}
				if (logging == false) {

					logging = true;

					isShowingLoading(loginLoading);

					String PostStr = "{\"nam\":\""
							+ LoginAccount.getText().toString()
							+ "\",\"im\":\""
							+ LoginPassword.getText().toString() + "\"}";

					HashMap<String, String> PostMap = new HashMap<String, String>();
					PostMap.put("json", PostStr);

					AsyncHttpClient client = new AsyncHttpClient();
					client.post(App.SERVER_URL + "?h=phone&t=user&f=login",
							new RequestParams(PostMap),
							new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(int statusCode,
										Header[] headers, byte[] responseBody) {
									// TODO Auto-generated method stub
									logging = false;
									isShowingLoading(loginLoading);
									AToast toast = new AToast(
											ActivityLogin.this);
									String string = new String(responseBody);

									if (string.equals("POSTERROR")
											|| string.equals("LOGIN_ERROR")) {
										toast.show(
												"ئىسىم ياكى پارول خاتا ، قايتا سىناپ كۆرۈڭ .",
												3);
									} else if (string.equals("BLOCKUSER")) {
										toast.show(
												"ئەزالىق ئىقتىدارىڭىز بىكار قىلىنغان !",
												3);
									} else {
										try {

											toast.show(
													"مۇۋاپىقىيەتلىك كىردىڭىز ، ھازىر ئالدىنقى كۆزنەككە يۆتكىلىدۇ .",
													3);

											new JSONObject(string);

											Preferences
													.setUserAccount(LoginAccount
															.getText()
															.toString());
											Preferences
													.setUserPassword(LoginPassword
															.getText()
															.toString());

											new Thread(new Runnable() {

												@Override
												public void run() {
													// TODO Auto-generated
													// method stub
													try {
														Thread.sleep(3000);
													} catch (InterruptedException e) {
														// TODO Auto-generated
														// catch block
														e.printStackTrace();
													}
													handler.sendMessage(handler
															.obtainMessage(1));
												}
											}).start();

										} catch (Exception e) {
											// TODO: handle exception
											toast.show(
													"ئىسىم ياكى پارول خاتا ، قايتا سىناپ كۆرۈڭ .",
													3);
										}
									}
								}

								@Override
								public void onFailure(int statusCode,
										Header[] headers, byte[] responseBody,
										Throwable error) {
									// TODO Auto-generated method stub
									logging = false;
									isShowingLoading(loginLoading);
									AToast toast = new AToast(ActivityLogin.this);
									toast.show("توردىن ئوقۇش مەغلۇب بولدى ، قايتا سىناڭ !",3);
								}
							});

				}
			}
		});

		View GoRegister = (View) findViewById(R.id.GoRegister);
		GoRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//HorizontalView.smoothScrollTo(0, 0);
			}
		});

		View GoForgot = (View) findViewById(R.id.GoForgot);
		GoForgot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HorizontalView.smoothScrollTo(
						2 * App.DISPLAY_METRICS.widthPixels, 0);
			}
		});
	}

	public void InitRegister() {
		Register = (View) findViewById(R.id.Register);
		LayoutParams p = (LayoutParams) Register.getLayoutParams();
		p.width = App.DISPLAY_METRICS.widthPixels;
		Register.setLayoutParams(p);

		final EditText RegisterAccount = (EditText) findViewById(R.id.RegisterAccount);
		onFocus(RegisterAccount);

		final EditText RegisterPassword = (EditText) findViewById(R.id.RegisterPassword);
		onFocus(RegisterPassword);

		final EditText RegisterRePassword = (EditText) findViewById(R.id.RegisterRePassword);
		onFocus(RegisterRePassword);

		final EditText RegisterEmail = (EditText) findViewById(R.id.RegisterEmail);
		onFocus(RegisterEmail);

		final ImageView registerLoading = (ImageView) findViewById(R.id.registerLoading);

		UyghurTextView RegisterButton = (UyghurTextView) findViewById(R.id.RegisterButton);
		onPress(RegisterButton, "#afc936", "#ffffff");
		RegisterButton.setOnClickListener(new OnClickListener() {
			boolean logging = false;
			@Override
			public void onClick(View v) {

				if (isUploading == true) {
					AToast toast = new AToast(ActivityLogin.this);
					toast.show("ئەزالىق باش رەسىمىڭىز يوللىنىۋاتىدۇ ، يوللىنىپ بولغاندىن كىيىن مەشخۇلات قىلىڭ !",3000);
					return;
				}

				// TODO Auto-generated method stub
				if (RegisterAccount.getText().length() < 5) {
					AToast toast = new AToast(ActivityLogin.this);
					toast.show("نامىڭىز بەش ھەرىپتىن چوڭ بولسۇن .", 3000);
					return;
				}
				if (RegisterPassword.getText().length() < 6) {
					AToast toast = new AToast(ActivityLogin.this);
					toast.show("پارولىڭىز ئالتە ھەرىپتىن چوڭ بولسۇن .", 3000);
					return;
				}
				if (isEmail(RegisterEmail.getText().toString()) == false) {
					AToast toast = new AToast(ActivityLogin.this);
					toast.show("ئېلخەت ئادرىسىڭىزنى توغرا يېزىڭ .", 3000);
					return;
				}
				if (RegisterPassword.getText().toString()
						.equals(RegisterRePassword.getText().toString()) == false) {

					AToast toast = new AToast(ActivityLogin.this);
					toast.show(
							"ئىككى قېتىملىق پارول ئوخشاش ئەمەس ، قايتا سىناڭ !",
							3000);
					return;
				}
				logging = true;

				isShowingLoading(registerLoading);

				String PostStr = "";

				if (imageSrc != null) {
					PostStr = "{\"nam\":\""
							+ RegisterAccount.getText().toString()
							+ "\",\"im\":\""
							+ RegisterPassword.getText().toString()
							+ "\",\"ilhet\":\""
							+ RegisterEmail.getText().toString() + "\",\"pic\":\""+imageSrc+"\"}";
				} else {
					PostStr = "{\"nam\":\""
							+ RegisterAccount.getText().toString()
							+ "\",\"im\":\""
							+ RegisterPassword.getText().toString()
							+ "\",\"ilhet\":\""
							+ RegisterEmail.getText().toString() + "\"}";
				}

				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("json", PostStr);

				AsyncHttpClient clinet = new AsyncHttpClient();
				clinet.post(App.SERVER_URL + "?h=phone&t=user&f=reg",
						new RequestParams(hashMap),
						new AsyncHttpResponseHandler() {

							@Override
							public void onSuccess(int statusCode,
									Header[] headers, byte[] responseBody) {
								// TODO Auto-generated method stub
								logging = false;
								isShowingLoading(registerLoading);

								String reString = new String(responseBody);

								AToast toast = new AToast(ActivityLogin.this);

								if (reString.equals("POSTERROR")
										|| reString.equals("REG_ERROR")) {
									toast.show(
											"تىزىملىتىشتا مەسىلە كۆرۈلدى ، قايتا سىناڭ .",
											3000);
								} else if (reString.equals("ACCOUNT_EXISTS")) {
									toast.show(
											"بۇ ئەزالىق نامنى باشقىلار ئىشلىتىپ بولغان ، باشقىنى سىناپ بېقىڭ .",
											3000);
								} else if (reString.equals("OK")) {
									toast.show(
											"مۇۋاپىقىيەتلىك تىزىملاتتىڭىز ، ھازىر كىرىش كۆزنىكىگە قايتىدۇ .",
											3000);
									LoginAccount.setText(RegisterAccount
											.getText());
									new Thread(new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											try {
												Thread.sleep(3000);
											} catch (Exception e) {
												// TODO: handle exception
											}
											handler3.sendMessage(handler3
													.obtainMessage(0));
										}
									}).start();
								} else {
									toast.show(
											"تىزىملىتىشتا مەسىلە كۆرۈلدى ، قايتا سىناڭ .",
											3000);
								}

							}

							@Override
							public void onFailure(int statusCode,
									Header[] headers, byte[] responseBody,
									Throwable error) {
								// TODO Auto-generated method stub

								logging = false;
								isShowingLoading(registerLoading);
								AToast toast = new AToast(ActivityLogin.this);
								toast.show(
										"توردىن ئوقۇش مەغلۇب بولدى ، قايتا سىناڭ !",
										3);
							}
						});
			}
		});

		IconView btnRegisterBack = (IconView) findViewById(R.id.btnRegisterBack);
		btnRegisterBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HorizontalView.smoothScrollTo(
						1 * App.DISPLAY_METRICS.widthPixels, 0);
			}
		});
	}

	Handler handler3 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			HorizontalView.smoothScrollTo(1 * App.DISPLAY_METRICS.widthPixels,
					0);
			super.handleMessage(msg);
		};
	};

	public void InitForget() {

		GetPassword = (View) findViewById(R.id.GetPassword);
		LayoutParams p = (LayoutParams) GetPassword.getLayoutParams();
		p.width = App.DISPLAY_METRICS.widthPixels;
		GetPassword.setLayoutParams(p);

		IconView btnForgetBack = (IconView) findViewById(R.id.btnForgetBack);
		btnForgetBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HorizontalView.smoothScrollTo(
						1 * App.DISPLAY_METRICS.widthPixels, 0);
			}
		});
	}

	public void onFocus(EditText Text) {
		Text.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				View parentView = (View) v.getParent();
				if (hasFocus == true) {
					parentView.setBackgroundColor(Color.parseColor("#212528"));
				} else {
					parentView.setBackgroundColor(Color.parseColor("#272b2e"));
				}
			}
		});
	}

	public void onPress(final UyghurTextView view, final String normal,
			final String Down) {

		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundColor(Color.parseColor(Down));
					view.setTextColor(Color.parseColor(normal));
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					v.setBackgroundColor(Color.parseColor(normal));
					view.setTextColor(Color.parseColor(Down));
				}
				if (event.getAction() == MotionEvent.ACTION_CANCEL) {
					v.setBackgroundColor(Color.parseColor(normal));
					view.setTextColor(Color.parseColor(Down));
				}
				return false;
			}
		});
	}

	public boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

	public void isShowingLoading(ImageView v) {
		if (v.isShown() == true) {
			v.setVisibility(View.GONE);
			v.clearAnimation();
		} else if (v.isShown() == false) {
			v.setVisibility(View.VISIBLE);
			App.startLoadingAnimation(v);
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			onBackPressed();
		};
	};
}
