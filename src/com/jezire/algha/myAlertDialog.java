package com.jezire.algha;

import com.jezire.App;
import com.jezire.R;
import com.jezire.text.Syntax;
import com.jezire.widget.UyghurTextView;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class myAlertDialog {
	Activity context;
	android.app.AlertDialog ad;
	UyghurTextView titleView;
	UyghurTextView messageView;
	LinearLayout buttonLayout;
	public myAlertDialog(Activity context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		ad = new android.app.AlertDialog.Builder(context).create();
		ad.show();
		//关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
		Window window = ad.getWindow();
		
		View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog,null);
		
		window.setContentView(view);
		titleView=(UyghurTextView)window.findViewById(R.id.title);
		messageView=(UyghurTextView)window.findViewById(R.id.message);
		buttonLayout=(LinearLayout)window.findViewById(R.id.buttonLayout);
	}
	public void setTitle(int resId)
	{
		titleView.setText(resId);
	}
	public void setTitle(String title) {
		titleView.setText(title);
	}
	public void setMessage(int resId) {
		messageView.setText(resId);
	}
 
	public void setMessage(String message)
	{
		messageView.setText(message);
	}
	/**
	 * 设置按钮
	 * @param text
	 * @param listener
	 */
	public void setPositiveButton(String text,final View.OnClickListener listener)
	{
		Button button=new Button(context);
		LinearLayout.LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.weight = 1;
		params.setMargins(10, 0, 10, 0);
		button.setLayoutParams(params);
		//button.setBackgroundResource(R.drawable.alertdialog_button);
		button.setText(Syntax.getUyPFStr(text));
		button.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		button.setTextColor(Color.BLACK);
		button.setTextSize(14);
		button.setOnClickListener(listener);
		buttonLayout.addView(button);
	}
 
	/**
	 * 设置按钮
	 * @param text
	 * @param listener
	 */
	public void setNegativeButton(String text,final View.OnClickListener listener)
	{
		Button button=new Button(context);
		LinearLayout.LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.weight = 1;
		button.setLayoutParams(params);
		//button.setBackgroundResource(R.drawable.alertdialog_button);
		button.setText(Syntax.getUyPFStr(text));
		button.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		button.setTextColor(Color.BLACK);
		button.setTextSize(14);
		button.setOnClickListener(listener);
		params.setMargins(10, 0, 10, 0);
		button.setLayoutParams(params);
		buttonLayout.addView(button);
	
	}
	/**
	 * 关闭对话框
	 */
	public void dismiss() {
		ad.dismiss();
	}
 
}