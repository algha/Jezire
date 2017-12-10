package com.jezire;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import cn.jpush.android.api.JPushInterface;
import com.jezire.algha.AToast;
import com.jezire.fragment.FragmentLink;
import com.jezire.fragment.FragmentMain;
import com.jezire.fragment.FragmentMenuLeft;
import com.jezire.fragment.FragmentMenuRight;
import com.jezire.fragment.FragmentNote;
import com.jezire.fragment.FragmentNoteRight;
import com.jezire.fragment.FragmentVideo;
import com.jezire.slidemenu.SlideMenu;
import com.jezire.universalimageloader.ImageLoader;
import com.jezire.universalimageloader.ImageLoaderConfiguration;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public final class App extends Application {
	public static String SERVER_URL = "http://new.jezire.cn";

	public static SlideMenu SLIDE_MENU_MAIN;
	public static SlideMenu SLIDE_MENU_VIDEO;
	public static SlideMenu SLIDE_MENU_NOTE;

	public static ImageLoader IMAGE_LOADER;

	public static FragmentNote FRAGMENT_NOTE;
	public static FragmentNoteRight FRAGMENT_NOTE_RIGHT;

	public static FragmentMain FRAGMENT_MAIN;
	public static FragmentMenuLeft FRAGMENT_MENU_LEFT;
	public static FragmentMenuRight FRAGMENT_MENU_RIGHT;
	public static ArrayList<FragmentLink> FRAGMENT_LINK_LIST;

	public static FragmentVideo FRAGMENT_VIDEO;

	public static Context CONTEXT;
	public static Resources RESOURCES;
	public static Display DISPLAY;
	public static DisplayMetrics DISPLAY_METRICS;

	public static Typeface TYPE_FACE_ALKATIP_TOR_TOM;
	public static Typeface TYPE_FACE_SIMPLE_LINE_ICONS;

	
	
	@Override
	public void onCreate() {
		super.onCreate();
		
         JPushInterface.init(this); 

		CONTEXT = this;
		RESOURCES = CONTEXT.getResources();
		DISPLAY = ((WindowManager) CONTEXT.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		DISPLAY_METRICS = RESOURCES.getDisplayMetrics();

		TYPE_FACE_ALKATIP_TOR_TOM = Typeface.createFromAsset(CONTEXT.getAssets(), "ALKATIPTT.TTF");
		TYPE_FACE_SIMPLE_LINE_ICONS = Typeface.createFromAsset(CONTEXT.getAssets(), "SIMPLELINEICONS.TTF");

		IMAGE_LOADER = ImageLoader.getInstance();
		IMAGE_LOADER.init(new ImageLoaderConfiguration.Builder(getApplicationContext()).build());

		FRAGMENT_LINK_LIST = new ArrayList<FragmentLink>();

		Wechat.Register();
	}

	public static int getPixelFromSp(int size) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, App.DISPLAY_METRICS);
	}

	public static int getPixelFromDp(int size) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, App.DISPLAY_METRICS);
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		RectF rectF = new RectF(rect);

		float roundPx = pixels;
		int color = 0xff424242;

		paint.setAntiAlias(true);
		paint.setColor(color);

		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public static void startLoadingAnimation(View view) {
		Animation animation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
		animation.setInterpolator(new LinearInterpolator());
		animation.setRepeatMode(Animation.RESTART);
		animation.setRepeatCount(Animation.INFINITE);
		animation.setDuration(1000);
		view.startAnimation(animation);
	}

	public static byte[] getBytesFromBitmap(Bitmap bmp) {
		byte[] result = {};

		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			bmp.compress(CompressFormat.JPEG, 80, byteArrayOutputStream);
			result = byteArrayOutputStream.toByteArray();

			byteArrayOutputStream.close();
			bmp.recycle();
		} catch (Exception e) {
		}
		return result;
	}

	public static class Preferences {
		static SharedPreferences preferences;
		static SharedPreferences.Editor editor;

		static {
			preferences = CONTEXT.getSharedPreferences("Jezire_Preferences", Context.MODE_PRIVATE);
			editor = preferences.edit();
		}

		public static int getFontSizeFlag() {
			return preferences.getInt("Jezire_FontSizeFlag", 0);
		}

		public static void setFontSizeFlag(int flag) {
			editor.putInt("Jezire_FontSizeFlag", flag);
			editor.commit();
		}

		public static int getFontColorFlag() {
			return preferences.getInt("Jezire_FontColorFlag", 0);
		}

		public static void setFontColorFlag(int flag) {
			editor.putInt("Jezire_FontColorFlag", flag);
			editor.commit();
		}

		public static int getBackgroundColorFlag() {
			return preferences.getInt("Jezire_BackgroundColorFlag", 0);
		}

		public static void setBackgroundColorFlag(int flag) {
			editor.putInt("Jezire_BackgroundColorFlag", flag);
			editor.commit();
		}

		public static String getNotes() {
			return preferences.getString("Jezire_Notes", null);
		}

		public static void setNotes(String notes) {
			editor.putString("Jezire_Notes", notes);
			editor.commit();
		}

		public static void setFontSize(int size) {
			editor.putInt("Jezire_FontSize", size);
			editor.commit();
		}

		public static int getFontSize() {
			return preferences.getInt("Jezire_FontSize", 12);
		}

		public static void setThemeDay(boolean day) {
			editor.putBoolean("Jezire_ThemeDay", day);
			editor.commit();
		}

		public static boolean getThemeDay() {
			return preferences.getBoolean("Jezire_ThemeDay", true);
		}
		
		public static void setUserAccount(String UserAccount){
			editor.putString("UserAccount", UserAccount);
			editor.commit();
		}
		
		public static String getUserName(){
			return preferences.getString("UserAccount", "");
		}
		
		public static void setUserPassword(String Password){
			editor.putString("UserPassword", Password);
			editor.commit();
		}
		
		public static String getUserPassword(){
			return preferences.getString("UserPassword", "");
		}
		
		public static boolean getSiyrilmaUchur(){
			return preferences.getBoolean("SiyrilmaUchur", true);
		}
		
		public static void setSiyrilmaUchur(boolean isOk){
			editor.putBoolean("SiyrilmaUchur", isOk);
			editor.commit();
		}
		
	}

	public static class Wechat {
		private static String wechatappid = "wx5b0a1e7115f47c51";
		private static IWXAPI api;

		static {
			api = WXAPIFactory.createWXAPI(CONTEXT, wechatappid, true);
		}

		public static void Register() {
			api.registerApp(wechatappid);
		}

		public static void HandleIntent(Intent intent, IWXAPIEventHandler handler) {
			api.handleIntent(intent, handler);
		}

		public static void ShareToWechat(String title, String description, String url, boolean totimeline) {
			
			
			AToast toast = new AToast(CONTEXT);
			toast.show("يۆتكىلىۋاتىدۇ ...", 2000);
			
			WXWebpageObject webpage = new WXWebpageObject();
			webpage.webpageUrl = url;

			WXMediaMessage msg = new WXMediaMessage(webpage);
			msg.title = title;
			msg.description = description;
			msg.thumbData = getBytesFromBitmap(BitmapFactory.decodeResource(RESOURCES, R.drawable.wechat_share_icon));

			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.transaction = String.valueOf(System.currentTimeMillis()) + url;
			req.message = msg;
			req.scene = totimeline ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;

			api.sendReq(req);
		}

		public static void ShareToWechat(String text, boolean totimeline) {
			
			AToast toast = new AToast(CONTEXT);
			toast.show("يۆتكىلىۋاتىدۇ ...", 2000);
			
			WXTextObject textObj = new WXTextObject();
			textObj.text = text;

			WXMediaMessage msg = new WXMediaMessage(textObj);
			msg.description = text;

			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.transaction = String.valueOf(System.currentTimeMillis()) + text;
			req.message = msg;
			req.scene = totimeline ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;

			api.sendReq(req);
		}
	}
}