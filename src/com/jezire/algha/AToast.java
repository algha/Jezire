package com.jezire.algha;

import com.jezire.R;
import com.jezire.widget.UyghurTextView;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class AToast extends Toast {
	private Context contentContext;
	private UyghurTextView txtToast;

	public AToast(Context context) {
		super(context);
		contentContext = context;
		// TODO Auto-generated constructor stub
	}
	
	public void show(String contentString,int duration) {
		
		View contentView = (View) LayoutInflater.from(contentContext).inflate(R.layout.layout_toast, null);
		
		txtToast = (UyghurTextView)contentView.findViewById(R.id.txtToast);
		txtToast.setText(contentString);
		
		this.setView(contentView);
		
		this.setDuration(duration);
		this.setGravity(Gravity.CENTER, 0, 0);
		
		this.show();
	}
	
}

