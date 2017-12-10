package com.jezire.fragment;

import com.jezire.App;
import com.jezire.NoteEntry;
import com.jezire.R;
import com.jezire.widget.IconView;
import com.jezire.widget.LinedEditText;
import com.jezire.widget.UyghurTextView;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class FragmentNote extends FragmentActivity {
	private IconView btnClose, btnMenu, btnSetting, btnDelete, btnAdd, btnShare, btnSave;
	private LinearLayout noteControlLayout, shareParent;
	private LinedEditText txtText, txtTextTitle;
	private String currentId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize();
	}

	private void initialize() {
		View contentView = getLayoutInflater().inflate(R.layout.layout_fragment_note, null);
		initializeView(contentView);
		setContentView(contentView);

		App.FRAGMENT_NOTE = this;
	}

	private void initializeView(View contentView) {
		UyghurTextView title = (UyghurTextView) contentView.findViewById(R.id.txtTitle);
		title.setTypeface(App.TYPE_FACE_ALKATIP_TOR_TOM);
		title.setText("خاتىرە دەپتەر");

		txtText = (LinedEditText) contentView.findViewById(R.id.txtText);
		txtTextTitle = (LinedEditText) contentView.findViewById(R.id.txtTextTitle);

		btnClose = (IconView) contentView.findViewById(R.id.btnClose);
		btnMenu = (IconView) contentView.findViewById(R.id.btnMenu);
		btnSetting = (IconView) contentView.findViewById(R.id.btnSetting);
		btnDelete = (IconView) contentView.findViewById(R.id.btnDelete);
		btnAdd = (IconView) contentView.findViewById(R.id.btnAdd);
		btnShare = (IconView) contentView.findViewById(R.id.btnShare);
		btnSave = (IconView) contentView.findViewById(R.id.btnSave);

		noteControlLayout = (LinearLayout) contentView.findViewById(R.id.noteControlLayout);
		shareParent = (LinearLayout) contentView.findViewById(R.id.shareParent);

		txtText.requestFocus();
		txtTextTitle.setHorizontalLineEnabled(false);
		txtTextTitle.setText("نامسىز خاتىرە");

		btnClose.setText("\ue021");
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentNote.this.getParent().onBackPressed();
			}
		});

		btnMenu.setText("\ue01c");
		btnMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				App.SLIDE_MENU_NOTE.open(true, true);
			}
		});

		btnSetting.setText("\ue022");
		btnSetting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				App.SLIDE_MENU_NOTE.open(false, true);
			}
		});

		btnDelete.setText("\ue024");
		btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!currentId.equals("")) {
					App.FRAGMENT_NOTE_RIGHT.removeNote(currentId);
					currentId = "";
				}

				txtText.setText("");
				txtTextTitle.setText("");
			}
		});

		btnAdd.setText("\ue012");
		btnAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currentId = "";
				txtText.setText("");
				txtTextTitle.setText("نامسىز خاتىرە");
				App.FRAGMENT_NOTE_RIGHT.loadNotes();
			}
		});

		btnSave.setText("\ue023");
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (txtTextTitle.getText().toString().trim().length() > 0) {
					NoteEntry entry = new NoteEntry();
					entry.Id = currentId;
					entry.Title = txtTextTitle.getText().toString();
					entry.Content = txtText.getText().toString();

					App.FRAGMENT_NOTE_RIGHT.saveNote(entry);
				}
			}
		});

		btnShare.setText("\ue011");
		btnShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupShare();
			}
		});
	}

	@SuppressWarnings("deprecation")
	private void popupShare() {
		LinearLayout popupLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_widget_popup_share, null);
		IconView iconDelta = (IconView) popupLayout.findViewById(R.id.iconDelta);
		iconDelta.setText("\ue028");

		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iconDelta.getLayoutParams();
		layoutParams.leftMargin = shareParent.getLeft() - ((App.DISPLAY.getWidth() - App.getPixelFromDp(320)) / 2) + shareParent.getWidth() / 2 - App.getPixelFromDp(15) / 2;
		iconDelta.setLayoutParams(layoutParams);

		IconView btnShareToWechat = (IconView) popupLayout.findViewById(R.id.btnShareToWechat);
		btnShareToWechat.setText("\ue029");
		btnShareToWechat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				App.Wechat.ShareToWechat(txtText.getText().toString(), false);
			}
		});

		IconView btnShareToTimeline = (IconView) popupLayout.findViewById(R.id.btnShareToTimeline);
		btnShareToTimeline.setText("\ue02a");
		btnShareToTimeline.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				App.Wechat.ShareToWechat(txtText.getText().toString(), true);
			}
		});

		PopupWindow popupWindow = new PopupWindow(popupLayout, App.getPixelFromDp(320), App.getPixelFromDp(95));
		popupWindow.setContentView(popupLayout);
		popupWindow.setOutsideTouchable(false);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

		popupWindow.showAtLocation(btnShare, Gravity.BOTTOM | Gravity.CENTER, 0, noteControlLayout.getHeight() + App.getPixelFromDp(2));
	}

	public LinedEditText getEditText() {
		return txtText;
	}

	public LinedEditText getEditTextTitle() {
		return txtTextTitle;
	}

	public String getCurrentId() {
		return currentId;
	}

	public void setCurrentId(String id) {
		currentId = id;
	}
}
