package com.example.photonotes;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

// Author: Vijay  UI design: Kenneth
public class MainActivity extends Activity {
	List<String> items, itemsID;
	ArrayAdapter<String> listAdapter;
	ListView listView;
	int deletePosition;
	int position1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loadList();
		getItems();
		ListView listView = (ListView) findViewById(R.id.list1);
		// setting on click function and passing the position of the element
		// this is for removing an element (but this function does not remove it)
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
		@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				position1 = pos;
				removeItemFromList(position1);
				return true;
			}
		});
	}

	// removes an element from the list
	protected void removeItemFromList(final int position1) {
		deletePosition = position1;
		
		AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
		alert.setTitle("Delete");
		alert.setMessage("Do you want delete this item?");
		// main code on after clicking yes (removes the item)
		alert.setPositiveButton("YES", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				items.remove(deletePosition);
				listAdapter.notifyDataSetChanged();
				listAdapter.notifyDataSetInvalidated();
				SQLiteDatabase db = new PhotoDbHelper(MainActivity.this)
						.getWritableDatabase();
				db.delete(PhotoDbHelper.DATABASE_TABLE, PhotoDbHelper.ID_COLUMN
						+ "=?", new String[] { itemsID.get(position1) });
			}
		});
		// main code on after clicking cancel (cancels)
		alert.setNegativeButton("CANCEL", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alert.show();
	}

	// Referred to class slides and sample code
	// Saving items into the ListView
	private List<String> getItems() {
		itemsID = new ArrayList<String>();
		List<String> list = new ArrayList<String>();
		SQLiteDatabase db = new PhotoDbHelper(this).getWritableDatabase();
		String where = null;
		String whereArgs[] = null;
		String groupBy = null;
		String having = null;
		String order = null;
		Log.d("Photo Activity","Before resultset");
		String[] result = { PhotoDbHelper.ID_COLUMN, PhotoDbHelper.TITLE_COLUMN,
				PhotoDbHelper.CAPTION_COLUMN, PhotoDbHelper.FILE_PATH_COLUMN,PhotoDbHelper.MAP_ADD,PhotoDbHelper.MAP_LONG,PhotoDbHelper.MAP_LAT };
		Log.d("Photo Activity","After resultset");
		Cursor cursor = db.query(PhotoDbHelper.DATABASE_TABLE, result, where,
				whereArgs, groupBy, having, order);
		Log.d("Photo Activity","After cursor");
		while (cursor.moveToNext()) {
			int id = cursor.getInt(0);
			String title = cursor.getString(1);
			list.add(title);
			String caption = cursor.getString(2);
			String file = cursor.getString(3);
			String address = cursor.getString(4);
			String longitude = cursor.getString(5);
			String latitude = cursor.getString(6);
			
			itemsID.add(cursor.getString(0));
			Log.d("Pictab", String.format("%s,%s, %s,%s,%s,%s,%s", id, title, caption, file,address,longitude,latitude));
		}
		return list;
	}

	// prepares list in main display
	public void loadList() {
		items = getItems();
		ListView listView = (ListView) findViewById(R.id.list1);
		listAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items); // Set list Adapter
		listView.setAdapter(listAdapter);
		// on click for list, it will display the photo notes
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// call intent for show activity
				Intent show = new Intent(MainActivity.this, PhotoNotesShow.class);
				show.putExtra("position", position);
				startActivity(show);
			}
		});
	}

	//this code adds the menu, which is displayed in the action bar
	@Override
	// Inflating the menu items
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	// goto savecapture.java
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent open = new Intent(this, PhotoNotesSave.class);
		startActivity(open);
		return super.onMenuItemSelected(featureId, item);
	}

	// Update the Table and ListView on resume
	@Override
	protected void onResume() {
		super.onResume();
		loadList();
	}
}