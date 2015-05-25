package com.notes.mnotes;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

//Author : Vijay : Map Implementation
//UI : Kenneth
//Referred class slides and sample code
public class PhotoNotesShow extends Activity {
	// variables
	TextView text;
	TextView address;
	TextView description;
	ImageView image;
	Cursor row;
	int pos;
	Bitmap bitmap;
	String title;
	String description1;
	String path;
	String id;
	String Mlatitude;
	String Mlongitude;
	String Maddress;
	double reallat;
	double reallong;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_notes_show);
		Bundle extras = getIntent().getExtras();
		text = (TextView) findViewById(R.id.image_title);
		address = (TextView) findViewById(R.id.address);
		description = (TextView) findViewById(R.id.caption);
		image = (ImageView) findViewById(R.id.image);
		pos = 0;

		// receive passed position
		if (extras != null) {
			pos = extras.getInt("position");
		}
		// Access row elements
		row = dbquery();
		id = row.getString(0);
		title = row.getString(1);
		description1 = row.getString(2);
		path = row.getString(3);
		Maddress = row.getString(4);
		Mlongitude = row.getString(5);
		Mlatitude = row.getString(6);
		reallat = Double.parseDouble(Mlatitude);
		reallong = Double.parseDouble(Mlongitude);
		// Set text fields
		text.setText(title);
		address.setText(description1);
		description.setText(Maddress);

		// Set image
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 10;
		bitmap = BitmapFactory.decodeFile(path, options);
		image.setImageBitmap(bitmap);
	}

	// find the entry in the db corresponding with the position passed
	private Cursor dbquery() {
		SQLiteDatabase db = new PhotoDbHelper(this).getWritableDatabase();
		String where = null;
		String whereArgs[] = null;
		String groupBy = null;
		String having = null;
		String order = null;
		String[] resultColumns = { PhotoDbHelper.ID_COLUMN,
				PhotoDbHelper.TITLE_COLUMN, PhotoDbHelper.CAPTION_COLUMN,
				PhotoDbHelper.FILE_PATH_COLUMN, PhotoDbHelper.MAP_ADD,
				PhotoDbHelper.MAP_LONG, PhotoDbHelper.MAP_LAT };
		Cursor cursor = db.query(PhotoDbHelper.DATABASE_TABLE, resultColumns,
				where, whereArgs, groupBy, having, order);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(0);
			id--;
			if (id == pos) {
				return cursor;
			}
		}
		return cursor;
	}

	void callMap(double latitude, double longitude) {

		String label = "My location";
		String uriBegin = "geo:" + latitude + "," + longitude;
		String query = latitude + "," + longitude + "(" + label + ")";
		String encodedQuery = Uri.encode(query);
		String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
		Uri uri = Uri.parse(uriString);
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	// this code adds the menu, which is displayed in the action bar
	@Override
	// Inflating the menu items
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	// goto savecapture.java
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		callMap(reallat, reallong);
		return super.onMenuItemSelected(featureId, item);
	}
}