package com.jezire.fragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.jezire.App;
import com.jezire.R;
import com.jezire.http.AsyncHttpClient;
import com.jezire.http.AsyncHttpResponseHandler;
import com.jezire.widget.MenuButton;
import com.jezire.widget.MenuButtonList;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class FragmentMenuLeft extends FragmentActivity {

	private MenuButtonList menuButtonList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize();
	}

	private void initialize() {
		View contentView = getLayoutInflater().inflate(R.layout.layout_fragment_menu_left, null);
		initializeView(contentView);
		setContentView(contentView);

		App.FRAGMENT_MENU_LEFT = this;

		loadData();
	}

	private void initializeView(View contentView) {
		menuButtonList = (MenuButtonList) contentView.findViewById(R.id.menuButtonList);
	}

	private void loadData() {
		MenuButton homeMenu = new MenuButton();
		homeMenu.setText("باشبەت");
		homeMenu.setTag(0);
		homeMenu.setButtonClick(true);

		App.FRAGMENT_MENU_LEFT.getMenuButtonList().addMenuButton(homeMenu);

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(App.SERVER_URL + "/?h=phone&t=links&f=getCat", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				loadLeftMenu(responseBody);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

			}
		});
	}

	private void loadLeftMenu(byte[] responseBody) {
		try {
			String response = new String(responseBody);
			if (response != null && response.length() > 0) {
				JSONArray array = new JSONArray(response);
				if (array != null && array.length() > 0) {
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);

						String id = obj.getString("id");
						String name = obj.getString("name");

						if ((id != null && id.length() > 0) && (name != null && name.length() > 0)) {
							MenuButton menu = new MenuButton();
							menu.setText(name);
							menu.setTag(id);
							if (i == 0)
								menu.setButtonClickFix();

							App.FRAGMENT_MENU_LEFT.getMenuButtonList().addMenuButton(menu);
							App.FRAGMENT_LINK_LIST.add(null);
						}
					}
				}
			}
		} catch (Exception e) {

		}
	}

	public MenuButtonList getMenuButtonList() {
		return menuButtonList;
	}
}
