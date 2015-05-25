package com.notes.mnotes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

// author : Vijay and Ravi
public class ToDoActivity extends ListActivity {
	ListView listView;
	int time = 2;
	int flag = 0;
	final String SETTING_TODOLIST = "todolist";
	private ArrayList<String> selectedItems = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	int position;
	List<String> items, itemsID;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todo_activity);

		loadList();

		// setting onItemLongClickListener and passing the position to the
		// function
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				position = pos;
				removeItemFromList(position);

				return true;
			}
		});

	}

	/*
	 * @Override protected void onListItemClick(ListView l, View v, int
	 * position, long id) { TextView tv = (TextView)
	 * v.findViewById(android.R.id.text1); if ((time % 2) == 0) {
	 * tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	 * super.onListItemClick(l, v, position, id); time = time + 1; //
	 * tv.setTextColor(0x999999); } else { tv.setPaintFlags(tv.getPaintFlags() &
	 * (~Paint.STRIKE_THRU_TEXT_FLAG)); time = time + 1; //
	 * tv.setTextColor(0x333300); } }
	 */

	// launch intent to save a new activity
	public void additem() {
		Intent save = new Intent(this, ToDoSave.class);
		startActivity(save);
	}

	// drop the database and refresh the list (empty list)
	public void deletelist() {
		SQLiteDatabase db = new ToDoDbHelper(this).getWritableDatabase();
		db.delete(ToDoDbHelper.DATABASE_TABLE, null, null);
		loadList();
	}

	// Saving items from the database as ListView
	private List<String> getItems() {
		itemsID = new ArrayList<String>();
		List<String> list = new ArrayList<String>();
		SQLiteDatabase db = new ToDoDbHelper(this).getReadableDatabase();
		String where = null;
		String whereArgs[] = null;
		String groupBy = null;
		String having = null;
		String order = null;
		String[] result = { ToDoDbHelper.ID_COLUMN, ToDoDbHelper.CAPTION_COLUMN };
		Cursor cursor = db.query(ToDoDbHelper.DATABASE_TABLE, result, where,
				whereArgs, groupBy, having, order);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(0);
			String title = cursor.getString(1);
			list.add(title);
			itemsID.add(cursor.getString(0));
			Log.d("Gallery", String.format("%s,%s", id, title));
		}
		return list;
	}

	// method to remove list item
	protected void removeItemFromList(final int position) {
		final int deletePosition = position;

		AlertDialog.Builder alert = new AlertDialog.Builder(ToDoActivity.this);
		alert.setTitle("Delete");
		alert.setMessage("Do you want delete this item?");
		// main code on after clicking yes (remove one element)
		alert.setPositiveButton("YES", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				items.remove(deletePosition);
				adapter.notifyDataSetChanged();
				adapter.notifyDataSetInvalidated();
				SQLiteDatabase db = new ToDoDbHelper(ToDoActivity.this)
						.getWritableDatabase();
				db.delete(ToDoDbHelper.DATABASE_TABLE, ToDoDbHelper.ID_COLUMN
						+ "=?", new String[] { itemsID.get(position) });
			}
		});
		// main code on after clicking cancel (cancel)
		alert.setNegativeButton("CANCEL", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alert.show();
	}

	public void loadList() {
		// set the list adapter
		items = getItems();
		listView = getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, items);
		listView.setAdapter(adapter);

		// save list
		SharedPreferences settingsActivity = getPreferences(MODE_PRIVATE);
		if (settingsActivity.contains(SETTING_TODOLIST)) {
			String savedItems = settingsActivity
					.getString(SETTING_TODOLIST, "");
			this.selectedItems.addAll(Arrays.asList(savedItems.split(",")));
			int count = this.listView.getAdapter().getCount();
			for (int i = 0; i < count; i++) {
				String currentItem = (String) this.listView.getAdapter()
						.getItem(i);
				if (this.selectedItems.contains(currentItem)) {
					this.listView.setItemChecked(i, true);
				}
			}
		}
	}

	// save the selections in the shared preference in private mode for the user
	private void SaveSelections() {
		SharedPreferences settingsActivity = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = settingsActivity.edit();
		String savedItems = getSavedItems();
		prefEditor.putString(SETTING_TODOLIST, savedItems);
		prefEditor.commit();
	}

	private String getSavedItems() {
		String savedItems = "";
		int count = this.listView.getAdapter().getCount();
		for (int i = 0; i < count; i++) {
			if (this.listView.isItemChecked(i)) {
				if (savedItems.length() > 0) {
					savedItems += "," + this.listView.getItemAtPosition(i);
				} else {
					savedItems += this.listView.getItemAtPosition(i);
				}
			}
		}
		return savedItems;
	}

	/*
	 * debugging code public void onListItemClick(ListView parent, View v, int
	 * position, long id) { Toast.makeText(this, "You have selected " +
	 * seven[position], Toast.LENGTH_SHORT).show(); }
	 */
	// Inflate the menu, this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

	// Menu item manager
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_todo_item:
			// launches the camera intent
			additem();
			return true;
		case R.id.save_todo_list:
			// launches the save function
			Toast.makeText(getApplicationContext(), " Items are saved",
					Toast.LENGTH_SHORT).show();
			SaveSelections();
			return true;
		case R.id.delete_todo_list:
			// launches the save function
			deletelist();
			return true;
		default:
			// default code for the default case
			return super.onOptionsItemSelected(item);
		}
	}

	// Update the Table and ListView on resume
	@Override
	protected void onResume() {
		loadList();
		SaveSelections();
		super.onResume();

	}

	@Override
	protected void onPause() {
		SaveSelections();
		super.onPause();
	}
}
