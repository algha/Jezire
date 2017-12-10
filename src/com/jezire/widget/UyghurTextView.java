package com.jezire.widget;

import com.jezire.App;
import com.jezire.text.Syntax;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class UyghurTextView extends TextView {

	private String NAME_SPACE = "http://schemas.android.com/apk/res/android";

	public UyghurTextView(Context context) {
		super(context);
		initialize(null);
	}

	public UyghurTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(attrs);
	}

	public UyghurTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize(attrs);
	}

	private void initialize(AttributeSet attrs) {
		if (attrs != null) {
			String textvalue = attrs.getAttributeValue(NAME_SPACE, "text");
			if (textvalue != null) {
				if (textvalue.startsWith("@")) {
					int textresid = attrs.getAttributeResourceValue(NAME_SPACE,
							"text", 0);
					textvalue = App.RESOURCES.getString(textresid);
				}
				setText(textvalue);
			}
		}
		super.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
	}

	public void setText(String str) {
		super.setText(Syntax.getUyPFStr(str));
	}
}