package com.notes.mnotes;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * This class is used to display the list of scheduled activities
 * and to add a new alarm activity.
 * @author ravi 
 */
public class ReminderActivity extends Activity {

	DataBaseAdapterClass dataBaseAdapter;
	SimpleCursorAdapter adapter;
	ListView listView1;

	private int rowId;
	private int deleteRow;
	private boolean removeFlag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminder);
		Log.d("ReminderActivity", "inOncreate before getting data");
		dataBaseAdapter = new DataBaseAdapterClass(this);
		//To display the past activities
		listView1 = (ListView) findViewById(R.id.reminders_list);
		getListItems();
		registerForContextMenu(listView1);
	}

	/**
	 * This method is used to popup an option to Edit or Remove
	 * on holding a particular item 
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		MenuInflater menuInflater = new MenuInflater(this);
		super.onCreateContextMenu(menu, v, menuInfo);
		menuInflater.inflate(R.menu.edit_remove_menu, menu);
	}

	/**
	 * This method is used to select either Edit or Remove option
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.edit:
			rowId=info.position+1;
			Log.d("ReminderActivity",rowId+"");
			
			Intent i = new Intent(getApplicationContext(),
					   AddRemainderActivity.class);
			i.putExtra("updateFlag", true);
			i.putExtra("RowId", rowId);
			startActivity(i);
			getListItems();
			//registerForContextMenu(listView1);
			break;

		case R.id.remove:
			
			 deleteRow = info.position;
			 Log.d("ReminderActivity",info.position+"");
			 //Log.d("ReminderActivity",rowId+"");
			 dataBaseAdapter = new DataBaseAdapterClass(ReminderActivity.this);
			 dataBaseAdapter.deleteQuery(deleteRow);
			 
			 getListItems();
			 registerForContextMenu(listView1);
			 
			 //finish();
			
						
            break;
		}

		return super.onContextItemSelected(item);
	}

	/**
	 * To retrieve items from the saved list in Database
	 */
	private void getListItems() {
		Log.d("ReminderActivity", "in get data");
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
				this,
				R.layout.custom_list_view,
				dataBaseAdapter.fetchData(),
				new String[] { dataBaseAdapter.COLUMN_TITLE,
						dataBaseAdapter.COLUMN_YEAR,
						dataBaseAdapter.COLUMN_MONTH,
						dataBaseAdapter.COLUMN_DATE,
						dataBaseAdapter.COLUMN_HOUR, dataBaseAdapter.COLUMN_MIN },
				new int[] { R.id.title_textView, R.id.yearTextView,
						R.id.month_textView, R.id.Day_textView,
						R.id.Hours_textView, R.id.Minutes_textView });
		Log.d("ReminderActivity", "in Getdata after simplecursor");

		listView1.setAdapter(adapter);
	}

	/**
	 * This method is used to start a new activity called AddReminderActivity
	 * @param view
	 */
	public void addReminder(View view) {
		Intent addReminderIntent = new Intent(this, AddRemainderActivity.class);
		startActivity(addReminderIntent);
	}

	// Update the Table and ListView on resume
	@Override
	protected void onResume() {
		super.onResume();
		getListItems();
		registerForContextMenu(listView1);
	}
}
