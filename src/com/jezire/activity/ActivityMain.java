package com.jezire.activity;

import com.jezire.App;
import com.jezire.R;
import com.jezire.fragment.FragmentLink;
import com.jezire.fragment.FragmentMain;
import com.jezire.fragment.FragmentMenuLeft;
import com.jezire.fragment.FragmentMenuRight;
import com.jezire.slidemenu.SlideMenu;
import com.jezire.slidemenu.SlideMenu.LayoutParams;
import com.jezire.widget.MenuButtonList.OnMenuButtonClickListener;
import com.jezire.widget.UyghurTextView;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;

@SuppressWarnings("deprecation")
public class ActivityMain extends ActivityGroup {
	private SlideMenu slideMenu;
	private LocalActivityManager activityManager;
	private Toast exitToast;
	private boolean exitFlag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		slideMenu = new SlideMenu(this);
		App.SLIDE_MENU_MAIN = slideMenu;

		initialize();
		setContentView(slideMenu);
	}

	@Override
	public void onBackPressed() {
		if (exitFlag) {
			if (exitToast != null) {
				exitToast.cancel();
			}

			super.onBackPressed();
		} else {
			showToast();
			exitFlag = true;
			new Handler().postDelayed(new Runnable() {
				public void run() {
					exitFlag = false;
				}
			}, 2000);
		}
	}

	private void showToast() {
		View view = LayoutInflater.from(App.CONTEXT).inflate(R.layout.layout_message_exit, null);
		UyghurTextView utv = (UyghurTextView) view.findViewById(R.id.txtTip);
		utv.setText("يەنە بىر قېتىم باسسىڭىز چېكىنىدۇ");

		exitToast = new Toast(App.CONTEXT);
		exitToast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, App.getPixelFromDp(20));
		exitToast.setDuration(Toast.LENGTH_SHORT);
		exitToast.setView(view);
		exitToast.show();

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				exitToast.cancel();
			}
		}, 2000);
	}

	private void initialize() {
		activityManager = getLocalActivityManager();

		View leftView = activityManager.startActivity("FragmentMenuLeft", new Intent(this, FragmentMenuLeft.class)).getDecorView();
		initializeLeftView(leftView);
		slideMenu.addView(leftView, new SlideMenu.LayoutParams(App.getPixelFromDp(240), LayoutParams.MATCH_PARENT, LayoutParams.ROLE_PRIMARY_MENU));

		View rightView = activityManager.startActivity("FragmentMenuRight", new Intent(this, FragmentMenuRight.class)).getDecorView();
		initializeRightView(rightView);
		slideMenu.addView(rightView, new SlideMenu.LayoutParams(App.getPixelFromDp(220), LayoutParams.MATCH_PARENT, LayoutParams.ROLE_SECONDARY_MENU));

		View centerView = activityManager.startActivity("FragmentMain", new Intent(this, FragmentMain.class)).getDecorView();
		slideMenu.addView(centerView, new SlideMenu.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, LayoutParams.ROLE_CONTENT));

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
		App.FRAGMENT_MENU_LEFT.getMenuButtonList().setOnMenuButtonClickListener(new OnMenuButtonClickListener() {
			@Override
			public void onMenuButtonClick(int idx) {
				try {
					if (idx == 0) {
						slideMenu.addView(App.FRAGMENT_MAIN.getWindow().getDecorView(), new SlideMenu.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, LayoutParams.ROLE_CONTENT));
					} else {
						if (App.FRAGMENT_LINK_LIST.get(idx - 1) == null) {
							Intent intent = new Intent(ActivityMain.this, FragmentLink.class);
							intent.putExtra("index", String.valueOf(idx - 1));
							intent.putExtra("title", App.FRAGMENT_MENU_LEFT.getMenuButtonList().getMenuButton(idx).getText());
							intent.putExtra("category", String.valueOf(App.FRAGMENT_MENU_LEFT.getMenuButtonList().getMenuButton(idx).getTag()));

							View centerView = activityManager.startActivity("FragmentLink" + String.valueOf(idx - 1), intent).getDecorView();
							slideMenu.addView(centerView, new SlideMenu.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, LayoutParams.ROLE_CONTENT));
						} else {
							slideMenu.addView(App.FRAGMENT_LINK_LIST.get(idx - 1).getWindow().getDecorView(), new SlideMenu.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, LayoutParams.ROLE_CONTENT));
						}
					}

					slideMenu.close(true);
				} catch (Exception e) {
				}
			}
		});
	}
}