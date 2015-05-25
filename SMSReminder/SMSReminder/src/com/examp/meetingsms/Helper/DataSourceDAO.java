package com.examp.meetingsms.Helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSourceDAO {

	private SQLiteDatabase database;
	private MeetingdbHelper dbHelper;
	private String[] allColumns = { MeetingdbHelper.MID_COLUMN,
			MeetingdbHelper.TITLE_COLUMN, MeetingdbHelper.VENUE_COLUMN,
			MeetingdbHelper.POINTER_COLUMN, MeetingdbHelper.DATE_COLUMN,
			MeetingdbHelper.MONTH_COLUMN, MeetingdbHelper.YEAR_COLUMN,
			MeetingdbHelper.HOUR_COLUMN, MeetingdbHelper.MIN_COLUMN,
			MeetingdbHelper.RECEPIENT_NAME, MeetingdbHelper.RECEPIENT_NUMBER };

	public DataSourceDAO(Context context) {
		dbHelper = new MeetingdbHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public long createComment(DbModel rows) {
		ContentValues values = new ContentValues();
		values.put(MeetingdbHelper.TITLE_COLUMN, rows.getTitle());
		values.put(MeetingdbHelper.VENUE_COLUMN, rows.getVenue());
		values.put(MeetingdbHelper.POINTER_COLUMN, rows.getPointer());
		values.put(MeetingdbHelper.DATE_COLUMN, rows.getDay());
		values.put(MeetingdbHelper.MONTH_COLUMN, rows.getMonth());
		values.put(MeetingdbHelper.YEAR_COLUMN, rows.getYear());
		values.put(MeetingdbHelper.HOUR_COLUMN, rows.getHour());
		values.put(MeetingdbHelper.MIN_COLUMN, rows.getMin());
		values.put(MeetingdbHelper.RECEPIENT_NAME, rows.getNamestr());
		values.put(MeetingdbHelper.RECEPIENT_NUMBER, rows.getNumstr());
		long insertId = database.insert(MeetingdbHelper.DATABASE_NAME, null,
				values);
		System.out.println("All Rows:" + insertId);
		return insertId;
	}

	public void deleteComment(DbModel comment) {
		long id = comment.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(MeetingdbHelper.DATABASE_NAME,
				MeetingdbHelper.MID_COLUMN + " = " + id, null);
	}

	public List<DbModel> getAllComments() {
		List<DbModel> dbModels = new ArrayList<DbModel>();

		Cursor cursor = database.query(MeetingdbHelper.DATABASE_NAME,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			DbModel comment = cursorToComment(cursor);
			dbModels.add(comment);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return dbModels;
	}
	
	public DbModel getelementbyid(String id)
	{
		
		Cursor cursor=database.query(MeetingdbHelper.DATABASE_NAME,allColumns, MeetingdbHelper.MID_COLUMN+"=?", new String[]{id},null,null,null);
		cursor.moveToFirst();
		return cursorToComment(cursor);
	}

	private DbModel cursorToComment(Cursor cursor) {
		
		DbModel cursortoDb = new DbModel();
		cursortoDb.setId(cursor.getLong(0));
		cursortoDb.setTitle(cursor.getString(1));
		cursortoDb.setVenue(cursor.getString(2));
		cursortoDb.setPointer(cursor.getString(3));
		cursortoDb.setDay(cursor.getString(4));
		cursortoDb.setMonth(cursor.getString(5));
		cursortoDb.setYear(cursor.getString(6));
		cursortoDb.setHour(cursor.getString(7));
		cursortoDb.setMin(cursor.getString(8));
		cursortoDb.setNamestr(cursor.getString(9));
		cursortoDb.setNumstr(cursor.getString(10));
		return cursortoDb;
	}

	public DbModel selectrow(String id) {

		Cursor cursor = database.query(MeetingdbHelper.DATABASE_NAME,
				allColumns, MeetingdbHelper.MID_COLUMN + "=?",
				new String[] { id }, null, null, null);
		cursor.moveToFirst();
		return cursorToComment(cursor);

	}
	
	public void delete_byID(long id)
	{
		
		 database.delete(MeetingdbHelper.DATABASE_NAME,
				MeetingdbHelper.MID_COLUMN + "=?",
                new String[]{Long.toString(id)});
		
	}

}
