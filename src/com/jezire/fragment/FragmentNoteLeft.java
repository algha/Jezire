package com.jezire.fragment;

import com.jezire.App;
import com.jezire.R;
import com.jezire.widget.IconView;
import com.jezire.widget.UyghurTextView;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class FragmentNoteLeft extends FragmentActivity {
	private UyghurTextView txtFontSizeName;
	private IconView btnFontColor1, btnFontColor2, btnFontColor3, btnFontColor4, btnFontColor5, btnFontColor6;
	private IconView btnRight1, btnRight2;
	private LinearLayout backgroundColor1, backgroundColor2;

	private int fontSizeFlag = 0;
	private int fontColorFlag = 0;
	private int backgroundColorFlag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize();
	}

	private void initialize() {
		View contentView = getLayoutInflater().inflate(R.layout.layout_fragment_note_left, null);
		initializeView(contentView);
		setContentView(contentView);
	}

	private void initializeView(View contentView) {
		btnFontColor1 = (IconView) contentView.findViewById(R.id.btnFontColor1);
		btnFontColor2 = (IconView) contentView.findViewById(R.id.btnFontColor2);
		btnFontColor3 = (IconView) contentView.findViewById(R.id.btnFontColor3);
		btnFontColor4 = (IconView) contentView.findViewById(R.id.btnFontColor4);
		btnFontColor5 = (IconView) contentView.findViewById(R.id.btnFontColor5);
		btnFontColor6 = (IconView) contentView.findViewById(R.id.btnFontColor6);

		btnFontColor1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fontColorFlag = 0;
				setFontColor();
			}
		});

		btnFontColor2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fontColorFlag = 1;
				setFontColor();
			}
		});

		btnFontColor3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fontColorFlag = 2;
				setFontColor();
			}
		});

		btnFontColor4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fontColorFlag = 3;
				setFontColor();
			}
		});

		btnFontColor5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fontColorFlag = 4;
				setFontColor();
			}
		});

		btnFontColor6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fontColorFlag = 5;
				setFontColor();
			}
		});

		txtFontSizeName = (UyghurTextView) contentView.findViewById(R.id.txtFontSizeName);

		btnRight1 = (IconView) contentView.findViewById(R.id.btnRight1);
		btnRight2 = (IconView) contentView.findViewById(R.id.btnRight2);

		backgroundColor1 = (LinearLayout) contentView.findViewById(R.id.backgroundColor1);
		backgroundColor1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				backgroundColorFlag = 0;
				setBackgroundColor();
			}
		});

		backgroundColor2 = (LinearLayout) contentView.findViewById(R.id.backgroundColor2);
		backgroundColor2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				backgroundColorFlag = 1;
				setBackgroundColor();
			}
		});

		UyghurTextView txtSetting = (UyghurTextView) contentView.findViewById(R.id.txtSetting);
		UyghurTextView txtBackgroundColor = (UyghurTextView) contentView.findViewById(R.id.txtBackgroundColor);
		UyghurTextView txtFontColor = (UyghurTextView) contentView.findViewById(R.id.txtFontColor);
		UyghurTextView txtFontSize = (UyghurTextView) contentView.findViewById(R.id.txtFontSize);
		IconView btnFontSmall = (IconView) contentView.findViewById(R.id.btnFontSmall);
		IconView btnFontBig = (IconView) contentView.findViewById(R.id.btnFontBig);

		txtSetting.setText("تەڭشەك");
		txtBackgroundColor.setText("تەگلىك رەڭگى");
		txtFontColor.setText("خەتنىڭ رەڭگى");
		txtFontSize.setText("خەتنىڭ چوڭلۇقى");
		btnFontSmall.setText("\ue026");
		btnFontBig.setText("\ue025");

		btnFontSmall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (fontSizeFlag > -1) {
					fontSizeFlag -= 1;
					setFontSize();
				}
			}
		});

		btnFontBig.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (fontSizeFlag < 1) {
					fontSizeFlag += 1;
					setFontSize();
				}
			}
		});

		fontSizeFlag = App.Preferences.getFontSizeFlag();
		fontColorFlag = App.Preferences.getFontColorFlag();
		backgroundColorFlag = App.Preferences.getBackgroundColorFlag();

		setFontSize();
		setFontColor();
		setBackgroundColor();
	}

	private void setFontSize() {
		if (fontSizeFlag == -1) {
			txtFontSizeName.setText("كىچىك");
			App.FRAGMENT_NOTE.getEditText().setTextSize(14);
			App.FRAGMENT_NOTE.getEditTextTitle().setTextSize(14);
		} else if (fontSizeFlag == 0) {
			txtFontSizeName.setText("نورمال");
			App.FRAGMENT_NOTE.getEditText().setTextSize(18);
			App.FRAGMENT_NOTE.getEditTextTitle().setTextSize(18);
		} else if (fontSizeFlag == 1) {
			txtFontSizeName.setText("چوڭ");
			App.FRAGMENT_NOTE.getEditText().setTextSize(22);
			App.FRAGMENT_NOTE.getEditTextTitle().setTextSize(22);
		}

		App.Preferences.setFontSizeFlag(fontSizeFlag);
	}

	private void setFontColor() {
		IconView[] views = { btnFontColor1, btnFontColor2, btnFontColor3, btnFontColor4, btnFontColor5, btnFontColor6 };
		for (IconView view : views) {
			view.setText("\ue008");
		}
		views[fontColorFlag].setText("\ue007");
		App.FRAGMENT_NOTE.getEditText().setTextColor(views[fontColorFlag].getTextColors().getDefaultColor());
		App.FRAGMENT_NOTE.getEditTextTitle().setTextColor(views[fontColorFlag].getTextColors().getDefaultColor());

		App.Preferences.setFontColorFlag(fontColorFlag);
	}

	private void setBackgroundColor() {
		int color = Color.parseColor("#fdfbe9");

		btnRight1.setText("\ue007");
		btnRight2.setText("\ue007");
		btnRight1.setVisibility(View.GONE);
		btnRight2.setVisibility(View.GONE);

		if (backgroundColorFlag == 0) {
			color = Color.parseColor("#fdfbe9");
			btnRight1.setVisibility(View.VISIBLE);
		} else if (backgroundColorFlag == 1) {
			color = Color.parseColor("#fefefe");
			btnRight2.setVisibility(View.VISIBLE);
		}

		App.FRAGMENT_NOTE.getEditText().setBackgroundColor(color);
		App.FRAGMENT_NOTE.getEditTextTitle().setBackgroundColor(color);

		App.Preferences.setBackgroundColorFlag(backgroundColorFlag);
	}
}