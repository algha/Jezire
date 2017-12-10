package com.jezire.algha;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.Header;

import com.jezire.App;
import com.jezire.App.Preferences;
import com.jezire.R;
import com.jezire.activity.ActivityXurjun.XurjunEntry;
import com.jezire.http.AsyncHttpClient;
import com.jezire.http.AsyncHttpResponseHandler;
import com.jezire.http.RequestParams;
import com.jezire.widget.IconView;
import com.jezire.widget.UyghurTextView;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.PopupWindow;

public class XurjunPopup extends PopupWindow {
	private View xurjunPopupView;
	private UyghurTextView BtnXurjunAction, BtnXurjunCancel;
	public EditText XurjunName, XurjunLink;
	private IconView XurjunNameIcon, XurjunLinkIcon, SelectedColor;
	private int PopupType;
	private Activity contextActivity;
	public ArrayList<IconView> colorList;
	public XurjunEntry xentry;
	private OnLoadDataFinishListener onLoadDataFinishListener;

	public XurjunPopup(Activity context, View parent) {

		super(context);

		contextActivity = context;
		LayoutInflater inflater = LayoutInflater.from(contextActivity);
		xurjunPopupView = (View) inflater.inflate(R.layout.layout_xurjun_popup,
				null);
		this.setContentView(xurjunPopupView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.MATCH_PARENT);
		this.setFocusable(true);
		this.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#80000000")));

		//
		BtnXurjunAction = (UyghurTextView) xurjunPopupView
				.findViewById(R.id.BtnXurjunAction);
		BtnXurjunAction.setOnClickListener(new MyOnclickListener());

		BtnXurjunCancel = (UyghurTextView) xurjunPopupView
				.findViewById(R.id.BtnXurjunCancel);
		BtnXurjunCancel.setText("قايتىش");
		BtnXurjunCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showPopupWindow(null);
			}
		});
		//
		XurjunName = (EditText) xurjunPopupView.findViewById(R.id.XurjunName);
		XurjunName.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		//
		XurjunLink = (EditText) xurjunPopupView.findViewById(R.id.XurjunLink);

		XurjunNameIcon = (IconView) xurjunPopupView
				.findViewById(R.id.XurjunNameIcon);
		XurjunNameIcon.setText("\ue00d");

		XurjunLinkIcon = (IconView) xurjunPopupView
				.findViewById(R.id.XurjunLinkIcon);
		XurjunLinkIcon.setText("\ue08d");

		colorList = new ArrayList<IconView>();

		IconView brown = (IconView) xurjunPopupView.findViewById(R.id.brown);
		brown.setText("\ue008");
		colorList.add(brown);
		brown.setOnClickListener(new ColorClickListener());
		//
		IconView red = (IconView) xurjunPopupView.findViewById(R.id.red);
		red.setText("\ue008");
		colorList.add(red);
		red.setOnClickListener(new ColorClickListener());
		//
		IconView yellow = (IconView) xurjunPopupView.findViewById(R.id.yellow);
		yellow.setText("\ue008");
		colorList.add(yellow);
		yellow.setOnClickListener(new ColorClickListener());
		//
		IconView blue = (IconView) xurjunPopupView.findViewById(R.id.blue);
		blue.setText("\ue008");
		colorList.add(blue);
		blue.setOnClickListener(new ColorClickListener());
		//
		IconView green = (IconView) xurjunPopupView.findViewById(R.id.green);
		green.setText("\ue008");
		colorList.add(green);
		green.setOnClickListener(new ColorClickListener());
		//
		IconView cheyze = (IconView) xurjunPopupView.findViewById(R.id.cheyze);
		cheyze.setText("\ue008");
		colorList.add(cheyze);
		cheyze.setOnClickListener(new ColorClickListener());

	}

	public void InitView(int type, XurjunEntry entry) {
		if (type == 0) {
			PopupType = 0;
			SelectedColor = colorList.get(0);
			SelectedColor.setText("\ue007");
			BtnXurjunAction.setText("قوشۇش");
		} else if (type == 1) {
			BtnXurjunAction.setText("تەھرىرلەش");
			if (entry != null) {
				xentry = entry;
				PopupType = 1;
				
				XurjunLink.setText(xentry.urladdress);
				XurjunName.setText(xentry.urlname);
				
				for (IconView iconView : colorList) {
					if (iconView.getTag().toString().equals(xentry.urlsign)) {
						SelectedColor = iconView;
						SelectedColor.setText("\ue007");
					}
				}
				
			}
		}
	}

	public class ColorClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			IconView colorview = (IconView) v;
			if (colorview == SelectedColor) {
				return;
			}
			SelectedColor.setText("\ue008");
			colorview.setText("\ue007");
			SelectedColor = colorview;
		}
	}

	public void showPopupWindow(View parent) {
		if (!this.isShowing()) {
			// 以下拉方式显示popupwindow
			this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		} else {
			this.dismiss();
		}
	}

	public void PostData(String urlString,String idString) {
		
		String PostStr;
		
		if (idString == null) {
			PostStr = "{\"urlname\":\""+XurjunName.getText().toString()+"\",\"urladdress\":\""+ XurjunLink.getText().toString()+"\",\"urlsign\":\""+ SelectedColor.getTag().toString()+"\"}";
		}else{
			PostStr = "{\"id\":\""+idString+"\",\"urlname\":\""+XurjunName.getText().toString()+"\",\"urladdress\":\""+ XurjunLink.getText().toString()+"\",\"urlsign\":\""+ SelectedColor.getTag().toString()+"\"}";
		}
		
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("json", PostStr);
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(urlString, new RequestParams(hashMap), new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				// TODO Auto-generated method stub
				showPopupWindow(null);
				String reString = new String(responseBody);
			
				if (onLoadDataFinishListener != null) {
					onLoadDataFinishListener.onLoadDataFinish(reString,PopupType);
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				showPopupWindow(null);
				if (onLoadDataFinishListener != null) {
					onLoadDataFinishListener.onLoadDataFinish("NetWorkError",PopupType);
				}
			}
		});
	}

	public class MyOnclickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (XurjunName.getText().length() == 0
					|| XurjunLink.getText().length() == 0) {

				return;
			}
			if (onLoadDataFinishListener != null) {
				onLoadDataFinishListener.onClickPost(PopupType);
			}
			if (PopupType == 0) {
				String AddString = App.SERVER_URL
						+ "?h=phone&t=xurjun&f=createXurjun&nam="+Preferences.getUserName()+"&im="+Preferences.getUserPassword();
				PostData(AddString,null);
			}
			if (PopupType == 1) {
				
				
				String editString = App.SERVER_URL
						+ "?h=phone&t=xurjun&f=editXurjun&nam="+Preferences.getUserName()+"&im="+Preferences.getUserPassword();
				PostData(editString,xentry.id);
			}
		}
	}

	public void setOnLoadDataFinishListener(OnLoadDataFinishListener Listener) {
		onLoadDataFinishListener = Listener;
	}

	public interface OnLoadDataFinishListener {
		public abstract void onLoadDataFinish(String data,int type);
		public abstract void onClickPost(int type);
	}
}
