package com.examp.meetingsms.Helper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MeetingdbHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 5;

	// Columns in DataBase table
	public static final String DATABASE_NAME = "REMINDER_DB";
	public static final String MID_COLUMN = "_id";
	public static final String TITLE_COLUMN = "TITLE_COLUMN";
	public static final String VENUE_COLUMN = "VENUE_COLUMN";
	public static final String POINTER_COLUMN = "POINTER_COLUMN";
	public static final String DATE_COLUMN = "DATE_COLUMN";
	public static final String MONTH_COLUMN = "MONTH_COLUMN";
	public static final String YEAR_COLUMN = "YEAR_COLUMN";
	public static final String HOUR_COLUMN = "HOUR_COLUMN";
	public static final String MIN_COLUMN = "MIN_COLUMN";
	public static final String RECEPIENT_NAME = "RECEPIENT_NAME";
	public static final String RECEPIENT_NUMBER = "RECEPIENT_NUMBER";

	private static final String DATABASE_CREATE = String.format(
			"CREATE TABLE %s (" + "  %s integer primary key autoincrement, "
					+ "%s text, " + "%s text, " + " %s text," + " %s text, "
					+ "%s text, " + "%s text, " + "%s text, " + "%s text, "
					+ "%s text," + "%s text )", DATABASE_NAME, MID_COLUMN,
			TITLE_COLUMN, DATE_COLUMN, MONTH_COLUMN, YEAR_COLUMN, HOUR_COLUMN,
			MIN_COLUMN, VENUE_COLUMN, POINTER_COLUMN, RECEPIENT_NAME,
			RECEPIENT_NUMBER);

	public static final String DROP_TABLE = "DROP TABLE IF EXISTS "
			+ DATABASE_NAME;

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public MeetingdbHelper(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			db.execSQL(DROP_TABLE);
			onCreate(db);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
