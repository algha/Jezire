package com.jezire.activity;

import com.jezire.App;
import com.jezire.R;
import com.jezire.fragment.FragmentVideo;
import com.jezire.fragment.FragmentVideoRight;
import com.jezire.slidemenu.SlideMenu;
import com.jezire.slidemenu.SlideMenu.LayoutParams;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

@SuppressWarnings("deprecation")
public class ActivityVideo extends ActivityGroup {
	private SlideMenu slideMenu;
	private LocalActivityManager activityManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.animation_in_up, R.anim.animation_out_up);

		slideMenu = new SlideMenu(this);
		App.SLIDE_MENU_VIDEO = slideMenu;

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

		View rightView = activityManager.startActivity("FragmentVideoRight", new Intent(this, FragmentVideoRight.class)).getDecorView();
		slideMenu.addView(rightView, new SlideMenu.LayoutParams(App.getPixelFromDp(220), LayoutParams.MATCH_PARENT, LayoutParams.ROLE_SECONDARY_MENU));

		View centerView = activityManager.startActivity("FragmentVideo", new Intent(this, FragmentVideo.class)).getDecorView();
		slideMenu.addView(centerView, new SlideMenu.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, LayoutParams.ROLE_CONTENT));

		slideMenu.setPrimaryShadowWidth(0);
		slideMenu.setSecondaryShadowWidth(0);
		slideMenu.setEdgetSlideWidth(App.getPixelFromDp(50));
		slideMenu.setEdgeSlideEnable(true);

		slideMenu.open(false, false);
		slideMenu.close(false);
	}
}
