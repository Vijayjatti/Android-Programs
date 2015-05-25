package com.notes.mnotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Author : Vijay
public class ToDoDbHelper extends SQLiteOpenHelper {

	public static final String ID_COLUMN = "_id";
	public static final String CAPTION_COLUMN = "CAPTION_COLUMN";
	public static final String DATABASE_TABLE = "Gallery";
	public static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE = String.format(
			"CREATE TABLE %s (" + "  %s integer primary key autoincrement, "
					+ "  %s text)", DATABASE_TABLE, ID_COLUMN, CAPTION_COLUMN);

	public ToDoDbHelper(Context context) {
		super(context, DATABASE_TABLE, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase data) {
		data.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase data, int oldVersion, int newVersion) {
		data.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
		onCreate(data);
	}
}