package com.jezire.activity;

import com.jezire.App;
import com.jezire.R;
import com.jezire.fragment.FragmentNote;
import com.jezire.fragment.FragmentNoteLeft;
import com.jezire.fragment.FragmentNoteRight;
import com.jezire.slidemenu.SlideMenu;
import com.jezire.slidemenu.SlideMenu.LayoutParams;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

@SuppressWarnings("deprecation")
public class ActivityNote extends ActivityGroup {
	private SlideMenu slideMenu;
	private LocalActivityManager activityManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.animation_in_up, R.anim.animation_out_up);

		slideMenu = new SlideMenu(this);
		App.SLIDE_MENU_NOTE = slideMenu;

		initialize();
		setContentView(slideMenu);
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.animation_in_down, R.anim.animation_out_down);
	}

	private void initialize() {
		activityManager = getLocalActivityManager();

		View centerView = activityManager.startActivity("FragmentNote", new Intent(this, FragmentNote.class)).getDecorView();
		slideMenu.addView(centerView, new SlideMenu.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, LayoutParams.ROLE_CONTENT));

		View rightView = activityManager.startActivity("FragmentNoteRight", new Intent(this, FragmentNoteRight.class)).getDecorView();
		initializeRightView(rightView);
		slideMenu.addView(rightView, new SlideMenu.LayoutParams(App.getPixelFromDp(260), LayoutParams.MATCH_PARENT, LayoutParams.ROLE_SECONDARY_MENU));

		View leftView = activityManager.startActivity("FragmentNoteLeft", new Intent(this, FragmentNoteLeft.class)).getDecorView();
		initializeLeftView(leftView);
		slideMenu.addView(leftView, new SlideMenu.LayoutParams(App.getPixelFromDp(260), LayoutParams.MATCH_PARENT, LayoutParams.ROLE_PRIMARY_MENU));

		slideMenu.setPrimaryShadowWidth(0);
		slideMenu.setSecondaryShadowWidth(0);
		slideMenu.setEdgetSlideWidth(App.getPixelFromDp(50));
		slideMenu.setEdgeSlideEnable(true);

		slideMenu.open(false, false);
		slideMenu.close(false);
	}

	private void initializeRightView(View rightView) {
	}

	private void initializeLeftView(View leftView) {

	}
}
