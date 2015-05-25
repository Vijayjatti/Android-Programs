package com.notes.mnotes;

//Author: Ravi Chegondi

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * This class is used to set Date and Time for the alarm activity
 * 
 * @author ravi
 */
public class AddRemainderActivity extends Activity {
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;

	private String sHour;
	private String sMin;
	private String sYear;
	private String sMonth;
	private String sDay;

	static final int DATE_DIALOG_ID = 1;
	static final int TIME_DIALOG_ID = 0;

	private Calendar c;

	private EditText editTitle;
	private EditText editNotify;

	private Context mContext;

	private boolean dateFlag = false;
	private boolean timeFlag = false;
	private boolean updateFlag = false;

	private String time;
	private String contentTitle;
	private String contentText;
	private int rowId;

	public static int notificationCount;

	public Button dateButton;
	public Button timeButton;
	public Button reminButton;

	public TextListener textListener;

	DataBaseAdapterClass dbHelper;

	/**
	 * To initialize all the widgets
	 */
	private void remainderInit() {
		mContext = this;
		textListener = new TextListener();

		dateButton = (Button) findViewById(R.id.dateButton);
		timeButton = (Button) findViewById(R.id.timeButton);
		reminButton = (Button) findViewById(R.id.reminderButton);

		dateButton.setEnabled(false);
		timeButton.setEnabled(false);
		reminButton.setEnabled(false);

		editTitle = (EditText) findViewById(R.id.nameEditText);
		editTitle.addTextChangedListener(textListener);

		editNotify = (EditText) findViewById(R.id.DescEditText);
		editNotify.addTextChangedListener(textListener);

		c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		// year = mYear+"";
		mMonth = c.get(Calendar.MONTH);
		// month=mMonth+"";
		mDay = c.get(Calendar.DAY_OF_MONTH);
		// date = mDay+"";
		mHour = c.get(Calendar.HOUR_OF_DAY);
		// hour = mHour+"";
		mMinute = c.get(Calendar.MINUTE);
		// min = mMinute+"";

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_remainder);
		Intent intent = getIntent();
		updateFlag = intent.getBooleanExtra("updateFlag", false);
		rowId = intent.getIntExtra("RowId", 1);
		// initialize the values
		remainderInit();
	}

	/**
	 * To popup Date picker
	 * 
	 * @param view
	 */
	@SuppressWarnings("deprecation")
	public void onClickDateBttn(View view) {
		showDialog(DATE_DIALOG_ID);
	}

	/**
	 * To popup timepicker
	 * 
	 * @param view
	 */
	@SuppressWarnings("deprecation")
	public void onClickTimeBttn(View view) {
		showDialog(TIME_DIALOG_ID);
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					true);
		case DATE_DIALOG_ID:
			/*
			 * return new DatePickerDialog(this, mDateSetListener, mYear,
			 * mMonth, mDay);
			 */
			DatePickerDialog _date = new DatePickerDialog(this,
					mDateSetListener, mYear, mMonth, mDay) {
				@Override
				public void onDateChanged(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					if (year < mYear)
						view.updateDate(mYear, mMonth, mDay);

					if (monthOfYear < mMonth && year == mYear)
						view.updateDate(mYear, mMonth, mDay);

					if (dayOfMonth < mDay && year == mYear
							&& monthOfYear == mMonth)
						view.updateDate(mYear, mMonth, mDay);
				}
			};
			return _date;
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			Log.d("AddReminder", "DatePicker-before");
			mYear = year;
			sYear = mYear + "";
			mMonth = monthOfYear + 1;
			// sMonth = mMonth+"";
			switch (mMonth) {
			case 1:
				sMonth = "Jan";
				break;
			case 2:
				sMonth = "Feb";
				break;
			case 3:
				sMonth = "Mar";
				break;
			case 4:
				sMonth = "Apr";
				break;
			case 5:
				sMonth = "May";
				break;
			case 6:
				sMonth = "June";
				break;
			case 7:
				sMonth = "July";
				break;
			case 8:
				sMonth = "Aug";
				break;
			case 9:
				sMonth = "Sep";
				break;
			case 10:
				sMonth = "Oct";
				break;
			case 11:
				sMonth = "Nov";
				break;
			case 12:
				sMonth = "Dec";
				break;
			}

			mDay = dayOfMonth;
			sDay = mDay + "";
			dateFlag = true;
			Log.d("AddReminder", "Inside DatePicker");
			Log.d("mYear", mYear + "");
			Log.d("mMonth", mMonth + "");
			Log.d("mDay", mDay + "");
		}
	};
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

			if (minute >= mMinute && hourOfDay >= mHour
					&& mDay >= c.get(Calendar.DAY_OF_MONTH)
					&& (mMonth + 1) >= c.get(Calendar.MONTH)
					&& mYear >= c.get(Calendar.YEAR)) {
				mHour = hourOfDay;
				// sHour = mHour + "";
				if (mHour < 10) {
					sHour = "0" + mHour + ":";
				} else
					sHour = mHour + ":";
				mMinute = minute;
				if (mMinute < 10) {
					sMin = "0" + mMinute;
				} else
					sMin = mMinute + "";
				// sMin = mMinute + "";
				timeFlag = true;

			} else {
				Toast.makeText(mContext, "PAST TIME", Toast.LENGTH_LONG).show();

			}

			Log.d("AddReminder", "Inside TimePicker");
			Log.d("mHour", mHour + "");
			Log.d("mMinute", mMinute + "");

		}
	};

	@SuppressLint("SimpleDateFormat")
	public void onClickReminderBttn(View view) {

		if (dateFlag && timeFlag) {
			notificationCount = notificationCount + 1;
			dateFlag = false;
			timeFlag = false;
			time = mYear + "-" + mMonth + "-" + mDay + " " + mHour + "-"
					+ mMinute;

			Log.d("AddRemActivity.OnClickReminderBtn", time);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm");
			Date dt = null;
			try {
				dt = df.parse(time);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long trigAtMilSec = dt.getTime();

			Log.d("AddRemActivity", "Entering to DB creation");
			contentTitle = editTitle.getText().toString();
			contentText = editNotify.getText().toString();

			dbHelper = new DataBaseAdapterClass(this);
			if (updateFlag) {
				Log.d("AddRemactivity", "in Update query");
				dbHelper.updateQuery(rowId, contentTitle, contentText, sHour,
						sMin, sYear, sMonth, sDay);

			} else {
				dbHelper.insertData(contentTitle, contentText, sHour, sMin,
						sYear, sMonth, sDay);
			}
			Log.d("AddRemActivity", "DB created");
			// Log.d("Test DB Content",ReminderDBHelper.COLUMN_YEAR.toString());
			// finish();

			AlarmManager mgr = (AlarmManager) mContext
					.getSystemService(Context.ALARM_SERVICE);
			@SuppressWarnings("deprecation")
			/*
			 * Notification notification = new Notification(
			 * android.R.drawable.ic_btn_speak_now, "hi", 100);
			 */
			Intent notificationIntent = new Intent(mContext,
					ReminderAlarm.class);

			notificationIntent.putExtra("Name", contentTitle);
			notificationIntent.putExtra("Description", contentText);
			notificationIntent.putExtra("NotifyCount", notificationCount);
			PendingIntent pi = PendingIntent.getBroadcast(mContext,
					notificationCount, notificationIntent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			mgr.set(AlarmManager.RTC_WAKEUP, trigAtMilSec, pi);

			Toast.makeText(mContext, "Your Reminder Activated",
					Toast.LENGTH_LONG).show();

			// return to previous activity
			finish();

		} else if (dateFlag == false || timeFlag == false) {
			Toast.makeText(mContext, "Please choose Date & Time",
					Toast.LENGTH_SHORT).show();
		}

	}

	public class TextListener implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (editNotify.getText().length() == 0
					|| editTitle.getText().length() == 0) {
				dateButton.setEnabled(false);
				timeButton.setEnabled(false);
				reminButton.setEnabled(false);
			} else if (editNotify.getText().length() > 0
					&& editTitle.getText().length() > 0) {
				dateButton.setEnabled(true);
				timeButton.setEnabled(true);
				reminButton.setEnabled(true);
			}
		}

	}
}
