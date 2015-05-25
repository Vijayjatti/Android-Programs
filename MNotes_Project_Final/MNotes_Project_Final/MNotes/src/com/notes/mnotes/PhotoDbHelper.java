package com.notes.mnotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
//Taken from class slides and sample code
public class PhotoDbHelper extends SQLiteOpenHelper {

	public static final String ID_COLUMN = "_id";
	public static final String TITLE_COLUMN = "TITLE_COLUMN";
	public static final String CAPTION_COLUMN = "CAPTION_COLUMN";
	public static final String FILE_PATH_COLUMN = "FILE_PATH_COLUMN";
	public static final String MAP_ADD = "MAP_ADD";
	public static final String MAP_LONG= "MAP_LONG";
	public static final String MAP_LAT= "MAP_LAT";
	public static final String DATABASE_TABLE = "Pictab";
	public static final int DATABASE_VERSION = 5;
	private static final String DATABASE_CREATE = String.format(
			"CREATE TABLE %s (" + "  %s integer primary key autoincrement, "
					+ "%s text, " + "%s text, " + " %s text," + " %s text, "+ "%s text," + " %s text)", DATABASE_TABLE, ID_COLUMN,
					TITLE_COLUMN, CAPTION_COLUMN, FILE_PATH_COLUMN, MAP_ADD, MAP_LONG, MAP_LAT);

	public PhotoDbHelper(Context context) {
		super(context, DATABASE_TABLE, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase data) {
		data.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase data, int oldVersion, int newVersion) {
		Log.d("Photo DB Helper"," Inside onUpgrade");
		data.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		Log.d("Photo DB Helper"," After onUpgrade");
		onCreate(data);
	}
}