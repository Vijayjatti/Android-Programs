package com.notes.mnotes;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 * @author ravi
 *
 */
public class DataBaseAdapterClass {

	DataBaseHelper helper;
	Context context;

	public static final String DATABASE_TABLE = "REMINDER_DB";
	public static final int DATABASE_VERSION = 4;

	// Columns in DataBase table
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "COLUMN_TITLE";
	public static final String COLUMN_NOTIFICATION = "COLUMN_NOTIFICATION";
	public static final String COLUMN_YEAR = "COLUMN_YEAR";
	public static final String COLUMN_MONTH = "COLUMN_MONTH";
	public static final String COLUMN_DATE = "COLUMN_DATE";
	public static final String COLUMN_HOUR = "COLUMN_HOUR";
	public static final String COLUMN_MIN = "COLUMN_MIN";

	private static final String DATABASE_CREATE = String.format(
			"CREATE TABLE %s (" + "  %s integer primary key autoincrement, "
					+ "  %s text," + "  %s text," + "  %s text," + "  %s text,"
					+ "  %s text," + "  %s text," + "  %s text)",
			DATABASE_TABLE, COLUMN_ID, COLUMN_TITLE, COLUMN_NOTIFICATION,
			COLUMN_YEAR, COLUMN_MONTH, COLUMN_DATE, COLUMN_HOUR, COLUMN_MIN);

	public static final String DROP_TABLE = "DROP TABLE IF EXISTS "
			+ DATABASE_TABLE;

	public DataBaseAdapterClass(Context context) {
		helper = new DataBaseHelper(context);
		this.context = context;
	}

	public long insertData(String title, String notification, String hrs, String mins,
			String year, String month, String date) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_TITLE, title);
		contentValues.put(COLUMN_NOTIFICATION,notification );
		contentValues.put(COLUMN_YEAR,year);
		contentValues.put(COLUMN_MONTH,month);
		contentValues.put(COLUMN_DATE,date);
		contentValues.put(COLUMN_HOUR,hrs);
		contentValues.put(COLUMN_MIN, mins);
		
		long id = db.insert(DATABASE_TABLE, null, contentValues);
		return id;
	}
	public long updateQuery(int id,String title, String notification, String hrs, String mins,
			String year, String month, String date) {
		String strFilter = "_id=" + id;
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues updateValues = new ContentValues();
		updateValues.put(COLUMN_TITLE, title);
		updateValues.put(COLUMN_NOTIFICATION,notification );
		updateValues.put(COLUMN_YEAR,year);
		updateValues.put(COLUMN_MONTH,month);
		updateValues.put(COLUMN_DATE,date);
		updateValues.put(COLUMN_HOUR,hrs);
		updateValues.put(COLUMN_MIN, mins);
		
		//long id = db.insert(DATABASE_TABLE, null, contentValues);
		long _id = db.update(DATABASE_TABLE, updateValues, strFilter, null);
		return _id;
	}
	
	public long deleteQuery(int id){
		
		String rowFilter = "_id=" + id;
		Log.d("row id:"+rowFilter,"0");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		long _id= db.delete(DATABASE_TABLE, rowFilter, null);
		return _id;
		
		
	}
    /*public Cursor deleteQuery(int id){
		
		String rowFilter = "_id=" + id;
		SQLiteDatabase db = helper.getWritableDatabase();
		
		long _id= db.delete(DATABASE_TABLE, rowFilter, null);
		return _id;
		
		
	}*/
	
	

	public Cursor fetchData() {
		Log.d("DBAdapter","fetchdata");
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] columns = {COLUMN_ID,COLUMN_TITLE,COLUMN_NOTIFICATION,COLUMN_YEAR,
				COLUMN_MONTH,COLUMN_DATE,COLUMN_HOUR,COLUMN_MIN};
		Cursor cursor = db.query(DATABASE_TABLE, columns, null, null, null, null, null);
		if (cursor != null) 
			cursor.moveToFirst();
		return cursor;	

	}
	public Cursor fetchDataWhere(int id) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String where = COLUMN_ID+ " = " +id;
		String[] columns ={COLUMN_ID,COLUMN_TITLE,COLUMN_NOTIFICATION,COLUMN_YEAR,
				COLUMN_MONTH,COLUMN_DATE,COLUMN_HOUR,COLUMN_MIN};
		Cursor cursor = db.query(DATABASE_TABLE, columns , where, null, null, null, null, null); 
		if (cursor != null) 
			cursor.moveToFirst();
		return cursor;
		
	}

	// static inner class
	static class DataBaseHelper extends SQLiteOpenHelper {
		Context context;

		public DataBaseHelper(Context context) {
			super(context, DATABASE_TABLE, null, DATABASE_VERSION);
			this.context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(DATABASE_CREATE);
			} catch (SQLException e) {
				Log.d("DataBaseAdapterClass.DataBaseHelper.oncreate",
						"Exception");
			}

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

}
