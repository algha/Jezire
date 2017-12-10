package com.jezire.fragment;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jezire.App;
import com.jezire.NoteEntry;
import com.jezire.R;
import com.jezire.widget.MenuButton;
import com.jezire.widget.MenuButtonList;
import com.jezire.widget.MenuButtonList.OnMenuButtonClickListener;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class FragmentNoteRight extends FragmentActivity {
	private ArrayList<NoteEntry> entryList;
	private MenuButtonList buttonList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize();
	}

	private void initialize() {
		View contentView = getLayoutInflater().inflate(R.layout.layout_fragment_note_right, null);
		initializeView(contentView);
		setContentView(contentView);

		App.FRAGMENT_NOTE_RIGHT = this;
	}

	private void initializeView(View contentView) {
		entryList = new ArrayList<NoteEntry>();

		buttonList = (MenuButtonList) contentView.findViewById(R.id.buttonList);
		buttonList.setOnMenuButtonClickListener(new OnMenuButtonClickListener() {
			@Override
			public void onMenuButtonClick(int idx) {
				NoteEntry entry = entryList.get(idx);
				App.FRAGMENT_NOTE.setCurrentId(entry.Id);
				App.FRAGMENT_NOTE.getEditText().setText(entry.Content);
				App.FRAGMENT_NOTE.getEditTextTitle().setText(entry.Title);
			}
		});

		loadNotes();
	}

	public void saveNote(NoteEntry entry) {
		int index = -1;
		JSONArray array = new JSONArray();
		String notes = App.Preferences.getNotes();

		try {
			if (notes != null) {
				array = new JSONArray(notes);
			}

			if (entry.Id.equals("")) {
				entry.Id = UUID.randomUUID().toString();
				App.FRAGMENT_NOTE.setCurrentId(entry.Id);

				JSONObject obj = new JSONObject();
				obj.put("Id", entry.Id);
				obj.put("Title", entry.Title);
				obj.put("Content", entry.Content);
				array.put(obj);

				App.Preferences.setNotes(array.toString());

				index = entryList.size();
			} else {
				JSONArray array2 = new JSONArray();
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (obj.getString("Id").equals(entry.Id)) {
						index = i;

						JSONObject obj2 = new JSONObject();
						obj2.put("Id", entry.Id);
						obj2.put("Title", entry.Title);
						obj2.put("Content", entry.Content);
						array2.put(obj2);
					} else {
						array2.put(obj);
					}
				}
				App.Preferences.setNotes(array2.toString());
			}

			loadNotes();
			if (index != -1) {
				buttonList.getMenuButton(index).setButtonClick(true);
				buttonList.getMenuButton(index).setButtonClickFix();
			}
		} catch (Exception ex) {
		}
	}

	public void loadNotes() {
		entryList.clear();
		buttonList.clearMenuButton();
		String notes = App.Preferences.getNotes();

		try {
			if (notes != null) {
				JSONArray array = new JSONArray(notes);
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.getJSONObject(i);

					NoteEntry entry = new NoteEntry();
					entry.Id = obj.getString("Id");
					entry.Title = obj.getString("Title");
					entry.Content = obj.getString("Content");

					entryList.add(entry);
				}

				for (NoteEntry entry : entryList) {
					MenuButton btn = new MenuButton(true);
					btn.setText(entry.Title);
					buttonList.addMenuButton(btn);
				}
			}
		} catch (Exception ex) {
		}
	}

	public void removeNote(String id) {
		JSONArray array = new JSONArray();
		String notes = App.Preferences.getNotes();

		try {
			if (notes != null) {
				array = new JSONArray(notes);
			}

			JSONArray array2 = new JSONArray();
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				if (!obj.getString("Id").equals(id)) {
					array2.put(obj);
				}
			}

			App.Preferences.setNotes(array2.toString());

			loadNotes();
		} catch (Exception ex) {
		}
	}
}