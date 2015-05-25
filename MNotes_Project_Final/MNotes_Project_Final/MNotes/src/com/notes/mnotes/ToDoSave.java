package com.notes.mnotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

// Author Vijay
public class ToDoSave extends Activity {
	EditText savebox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.todo_save);
		savebox = (EditText) findViewById(R.id.description);
	}

	public void saveitem() {
		String item = savebox.getText().toString();
		// show a Dialog box if no title is entered
		if (item.length() == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Enter Title!")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {/* do nothing */
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		} else {
			// otherwise save the items
			SQLiteDatabase data = new ToDoDbHelper(this).getWritableDatabase();
			ContentValues newValues = new ContentValues();
			newValues.put(ToDoDbHelper.CAPTION_COLUMN, item);
			data.insert(ToDoDbHelper.DATABASE_TABLE, null, newValues);
			// return to the last activity
			finish();
		}
	}

	// this code adds the menu, which is displayed in the action bar
	@Override
	// Inflating the menu items
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.save, menu);
		return true;
	}

	@Override
	// goto savecapture.java
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		saveitem();
		return super.onMenuItemSelected(featureId, item);
	}
}