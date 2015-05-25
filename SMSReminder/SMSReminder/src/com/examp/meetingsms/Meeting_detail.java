package com.examp.meetingsms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.gsm.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.examp.meetingsms.Helper.DataSourceDAO;
import com.examp.meetingsms.Helper.DbModel;

public class Meeting_detail extends Activity implements OnClickListener {

	// Meeting detail
	String namestr;
	String numstr;
	String[] name;
	String datetext;
	String timetext;
	String[] numbers;
	List<String> namelist = new ArrayList<String>();
	List<String> numberlist = new ArrayList<String>();
	Button btnsaver;
	Calendar c;
	String time;
	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;
	long myId;
	DataSourceDAO todb;
	DbModel elementmodel = new DbModel();
	public static String strSeparator = "\\|";
	String timeSet;
	private int mCurrentYear;
	private int mCurrentMonth;
	int j;
	int ihour;
	int imin;
	int iyear;
	int imonth;
	int iday;
	private int mCurrentDay;
	private int mCurrentHour;
	private int mCurrentMinute;
	String mMessage;
	String dateSet;
	String dateflag = "0";
	String timeflag = "0";
	// meeting detail

	// Widget GUI
	EditText titleID, VenueID, PointerID;
	Button btnCalendar, btnTimePicker;
	TextView txtDate, txtTime;
	String title, pointers, venue;
	String Title_String, Venue_String, Pointer_String;
	// Variable for storing current date and time
	private int mYear, mMonth, mDay, mHour, mMinute;
	private String sHour;
	private String sMin;
	private String sYear;
	private String sMonth;
	private String sDay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meeting_detail);
		Button SelectContacts = (Button) findViewById(R.id.Contacts);
		// Changes for date n time
		btnCalendar = (Button) findViewById(R.id.btnCalendar);
		btnTimePicker = (Button) findViewById(R.id.btnTimePicker);
		titleID = (EditText) findViewById(R.id.Title);
		VenueID = (EditText) findViewById(R.id.venue);
		PointerID = (EditText) findViewById(R.id.PointerText);
		txtDate = (TextView) findViewById(R.id.txtDate);
		txtTime = (TextView) findViewById(R.id.txtTime);
		btnsaver = (Button) findViewById(R.id.saver);

		btnCalendar.setOnClickListener(this);
		btnTimePicker.setOnClickListener(this);

		btnsaver.setOnClickListener(this);
		// Changes for date n time

	}

	public void sendMessage(View view) {

		Intent intent = new Intent(Meeting_detail.this, SetContacts.class);

		startActivityForResult(intent, 0);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		if (requestCode == 0 && resultCode == RESULT_OK) {
			namestr = data.getStringExtra("NAME");
			numstr = data.getStringExtra("NUMBER");

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void setDate() {

	}

	public void savetodb() {
		finish();
	}

	@Override
	public void onClick(View v) {

		if (v == btnCalendar) {

			// Process to get Current Date
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);

			// Launch Date Picker Dialog
			DatePickerDialog dpd = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							// Display Selected date in textbox

							datetext = dayOfMonth + "-" + (monthOfYear + 1)
									+ "-" + year;
							txtDate.setText(datetext);
							sDay = "" + dayOfMonth;
							sMonth = "" + monthOfYear;
							sYear = "" + year;

						}
					}, mYear, mMonth, mDay);
			dateflag = "1";
			dpd.show();
		}
		if (v == btnTimePicker) {

			// Process to get Current Time
			final Calendar c = Calendar.getInstance();
			mHour = c.get(Calendar.HOUR_OF_DAY);
			mMinute = c.get(Calendar.MINUTE);

			// Launch Time Picker Dialog
			TimePickerDialog tpd = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							// Display Selected time in textbox

							sHour = "" + hourOfDay;
							
							/*if (minute<10)
							sMin = "0" + minute;
							else*/
								sMin = "" + minute;
							
							System.out.println("Time Picker called: " + sHour
									+ sMin);
							
							if (minute<10)
							txtTime.setText(hourOfDay + ":" +"0"+ minute);
							else
								txtTime.setText(hourOfDay + ":" +minute);
								
							timeSet = "" + hourOfDay + ":"+ minute;
							dateSet = "" + sMonth + "-" + sDay + "-" + sYear;
						}
					}, mHour, mMinute, false);
			timeflag = "1";
			tpd.show();
		}

		if (v == btnsaver) {
			int titleflag = 0, venueflag = 0, dateflag = 0, timeflag = 0;
			Title_String = titleID.getText().toString();
			Venue_String = VenueID.getText().toString();
			Pointer_String = PointerID.getText().toString();

			title = titleID.getText().toString();
			venue = VenueID.getText().toString();
			pointers = PointerID.getText().toString();

			if (TextUtils.isEmpty(Title_String)) {
				titleID.setError("Title Can not be Empty");
				titleflag = 1;
				return;

			}
			/*
			 * if (titleflag==0) { titleID.setError(null); }
			 */

			else if (TextUtils.isEmpty(Venue_String)) {
				VenueID.setError("Venue Can not be empty");
				venueflag = 1;
				return;
			}

			/*
			 * if (venueflag==0) { VenueID.setError(null); }
			 */
			else if (TextUtils.isEmpty(datetext)) {
				txtDate.setError("Date Can not be empty");
				dateflag = 1;
				return;
			}

			/*
			 * if (dateflag==0) { txtDate.setError(null); }
			 */

			else if (TextUtils.isEmpty(timeSet)) {
				txtTime.setError("Venue Can not be empty");
				timeflag = 1;
				return;
			}

			/*
			 * if (timeflag==0) { txtTime.setError(null); }
			 */

			/*
			 * else if (dateflag.matches("0")) { Toast.makeText(this,
			 * "Select Date", Toast.LENGTH_SHORT).show(); return;
			 * 
			 * }
			 * 
			 * if (timeflag.matches("0")) { Toast.makeText(this, "Select Date",
			 * Toast.LENGTH_SHORT).show(); return;
			 * 
			 * }
			 */
			elementmodel.setTitle(title);
			elementmodel.setVenue(venue);
			elementmodel.setPointer(pointers);
			elementmodel.setDay(sDay);
			elementmodel.setMonth(sMonth);
			elementmodel.setYear(sYear);
			elementmodel.setHour(sHour);
			elementmodel.setMin(sMin);
			elementmodel.setNamestr(namestr);
			elementmodel.setNumstr(numstr);

			c = Calendar.getInstance();
			mCurrentYear = c.get(Calendar.YEAR);
			mCurrentMonth = c.get(Calendar.MONTH);
			mCurrentDay = c.get(Calendar.DAY_OF_MONTH);
			mCurrentHour = c.get(Calendar.HOUR_OF_DAY);
			mCurrentMinute = c.get(Calendar.MINUTE);
			mMessage = "Meeting Title: " + title + "\nMeeting Venue:"
					+ venue + "\nMeeting Date:" + dateSet
					+ "\n Meeting Time: " + timeSet + "\nPoints to Remind: "
					+ pointers;
			DataSourceDAO todb = new DataSourceDAO(getApplicationContext());

			todb.open();
			myId = todb.createComment(elementmodel);
			// todb.getAllComments();
			for (DbModel i : todb.getAllComments()) {
				System.out.println("title:" + i.getTitle());
				System.out.println("Numbers:" + i.getNumstr());
				System.out.println("Numbers:" + i.getNamestr());

			}
			todb.close();
			if (numstr != null && numstr.length() != 0)
				SetReminder();
			else {
				Toast.makeText(getApplicationContext(), "Select Contacts",
						Toast.LENGTH_SHORT).show();
				return;
			}

		}

	}

	public void SetReminder() {
		name = namestr.split(strSeparator);
		numbers = numstr.split(strSeparator);

		for (int i = 0; i < name.length; ++i) {
			System.out.println(name[i]);
			namelist.add(name[i]);
		}

		for (j = 0; j < numbers.length; ++j) {
			numberlist.add(numbers[j]);
		}

		for (int k = 0; k < j; ++k) {
			try {
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(numbers[k], null, mMessage
						+ " \n~V~SMS REMINDER~J~", null, null);
				Toast.makeText(getApplicationContext(), "SMS Sent!",
						Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"SMS faild, please try again later!", Toast.LENGTH_LONG)
						.show();
				e.printStackTrace();
			}
		}

		try {
			iyear = Integer.parseInt(sYear.toString());
		} catch (NumberFormatException nfe) {
			System.out.println("Could not parse " + nfe);
		}

		try {
			imonth = Integer.parseInt(sMonth.toString());
		} catch (NumberFormatException nfe) {
			System.out.println("Could not parse " + nfe);
		}

		try {
			iday = Integer.parseInt(sDay.toString());
		} catch (NumberFormatException nfe) {
			System.out.println("Could not parse " + nfe);
		}

		try {
			ihour = Integer.parseInt(sHour.toString());
			/*if (ihour > 12) {
				ihour = ihour - 12;
			}*/
		} catch (NumberFormatException nfe) {
			System.out.println("Could not parse " + nfe);
		}
		try {
			imin = Integer.parseInt(sMin.toString());
		} catch (NumberFormatException nfe) {
			System.out.println("Could not parse " + nfe);
		}

		c.set(Calendar.HOUR_OF_DAY, ihour);
		c.set(Calendar.MINUTE, imin);
		c.set(Calendar.SECOND, 0);
		System.out.println(c.getTime());
		// c.set(Calendar.DAY_OF_MONTH, mDay);
		c.set(iyear, imonth, iday);
		System.out.println("hour: " + ihour + "min:" + imin + "Year: " + iyear
				+ "Month: " + imonth + "Day: " + iday);
		long timemilli = c.getTimeInMillis();
		System.out.println("Time in Milliseconds" + timemilli);
		Intent i = new Intent(getApplicationContext(),
				SendSMSAlarmService.class);
		i.putExtra("NUMBERS", numstr);
		i.putExtra("SMSReminder", mMessage);

		time = sYear + "-" + sMonth + "-" + sDay + " " + sHour + "-" + sMin;

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm");
		Date dt = null;
		try {
			dt = df.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long trigAtMilSec = dt.getTime();
		//System.out.println("time in mili:" + trigAtMilSec);
		System.out.println("time in milli:"+ timemilli);
		long time = timemilli - SystemClock.elapsedRealtime();
		/* final int *//* mId = (int) System.currentTimeMillis(); */
		System.out.println("current time:"+SystemClock.elapsedRealtime());
		alarmMgr = (AlarmManager) getApplicationContext().getSystemService(
				Context.ALARM_SERVICE);
		// Intent intent = new Intent(getApplicationContext(),
		// SendSMSAlarmService.class);
		alarmIntent = PendingIntent.getBroadcast(getApplicationContext(),
				(int) myId, i, 0);

		alarmMgr.set(AlarmManager.RTC_WAKEUP, timemilli, alarmIntent);
		finish();

	}
	// Add to db

	// Add to db
}
