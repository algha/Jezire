package com.jezire.algha;

import com.jezire.App;
import com.jezire.text.Syntax;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyEditText extends EditText {
	
	private String NAME_SPACE = "http://schemas.android.com/apk/res/android";
	
	public MyEditText(Context context) {  
        super(context); 
        initialize(null);
    }
      
    public MyEditText(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
        initialize(attrs);
    }  
  
    public MyEditText(Context context, AttributeSet attrs) {  
        super(context, attrs);  
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
