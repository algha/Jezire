package com.jezire.fragment;

import com.jezire.App;
import com.jezire.R;
import com.jezire.widget.MenuButton;
import com.jezire.widget.MenuButtonList;
import com.jezire.widget.MenuButtonList.OnMenuButtonClickListener;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class FragmentVideoRight extends FragmentActivity {
	private MenuButtonList buttonList1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize();
	}

	private void initialize() {
		View contentView = getLayoutInflater().inflate(R.layout.layout_fragment_video_right, null);
		initializeView(contentView);
		setContentView(contentView);
	}

	private void initializeView(View contentView) {
		buttonList1 = (MenuButtonList) contentView.findViewById(R.id.buttonList1);
		buttonList1.setOnMenuButtonClickListener(new OnMenuButtonClickListener() {
			@Override
			public void onMenuButtonClick(int idx) {
				if (idx == 0) {
					App.FRAGMENT_VIDEO.setUrl(App.SERVER_URL + "/?h=phone&t=video&f=gethome");
				} else if (idx == 1) {
					App.FRAGMENT_VIDEO.setUrl(App.SERVER_URL + "/?h=phone&t=video&f=getWithOpt&ord=click");
				} else if (idx == 2) {
					App.FRAGMENT_VIDEO.setUrl(App.SERVER_URL + "/?h=phone&t=video&f=getWithOpt&ord=created_at");
				} else if (idx == 3) {
					App.FRAGMENT_VIDEO.setUrl(App.SERVER_URL + "/?h=phone&t=video&f=getcategory&bcatid=1");
				} else if (idx == 4) {
					App.FRAGMENT_VIDEO.setUrl(App.SERVER_URL + "/?h=phone&t=video&f=getcategory&bcatid=2");
				} else if (idx == 5) {
					App.FRAGMENT_VIDEO.setUrl(App.SERVER_URL + "/?h=phone&t=video&f=getcategory&bcatid=3");
				} else if (idx == 6) {
					App.FRAGMENT_VIDEO.setUrl(App.SERVER_URL + "/?h=phone&t=video&f=getcategory&bcatid=4");
				} else if (idx == 7) {
					App.FRAGMENT_VIDEO.setUrl(App.SERVER_URL + "/?h=phone&t=video&f=getcategory&bcatid=5");
				}

				App.SLIDE_MENU_VIDEO.close(true);
			}
		});

		MenuButton btn0 = new MenuButton(true);
		btn0.setText("باشبەت");
		btn0.setButtonClick(true);
		buttonList1.addMenuButton(btn0);

		MenuButton btn1 = new MenuButton(true);
		btn1.setText("ئەڭ ئاۋات فىلىملەر");
		buttonList1.addMenuButton(btn1);

		MenuButton btn2 = new MenuButton(true);
		btn2.setText("ئەڭ يېڭى فىلىملەر");
		buttonList1.addMenuButton(btn2);

		MenuButton btn3 = new MenuButton(true);
		btn3.setText("كىنولار");
		buttonList1.addMenuButton(btn3);

		MenuButton btn4 = new MenuButton(true);
		btn4.setText("تېلېۋىزىيە تىياتىرى");
		buttonList1.addMenuButton(btn4);

		MenuButton btn5 = new MenuButton(true);
		btn5.setText("ئۇنىۋېرسال سەنئەت");
		buttonList1.addMenuButton(btn5);

		MenuButton btn6 = new MenuButton(true);
		btn6.setText("MTV");
		buttonList1.addMenuButton(btn6);

		MenuButton btn7 = new MenuButton(true);
		btn7.setText("كارتون فىلىم");
		buttonList1.addMenuButton(btn7);
	}
}