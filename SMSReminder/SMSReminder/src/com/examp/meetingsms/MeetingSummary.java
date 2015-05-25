package com.examp.meetingsms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.gsm.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.examp.meetingsms.Helper.DataSourceDAO;
import com.examp.meetingsms.Helper.DbModel;
import com.examp.meetingsms.Helper.MeetingdbHelper;



public class MeetingSummary extends Activity {
	String etitle;
    String evenue;
    String epointer;
    String eDay;
    String eMonth;
    String eYear;
    String eHour;
    String emin;
    String namestr;
    String numstr;
   //Changes for schedular
    int ihour;
    int imin;
    int iyear;
    int imonth;
    int iday;
    private int mId;
    List<String> namelist = new ArrayList<String>();
	List<String> numberlist = new ArrayList<String>();
	String[] name;
	String[] numbers;
	DataSourceDAO todb;
	DbModel elementmodel = new DbModel();
	public static String strSeparator = "\\|";
	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;
	String mysId;
	Context context;
	int j=0;
	private String time;
    //Schedular
    MeetingdbHelper dbHelper;
    Button reminder;
    long myId;
    String mMessage;
    private int mCurrentYear;
	private int mCurrentMonth;
	private int mCurrentDay;
	private int mCurrentHour;
	private int mCurrentMinute;
	Calendar c;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_summary);
        Button reminder = (Button)findViewById(R.id.SetReminder);
        TextView title=(TextView)findViewById(R.id.user_title);
        TextView venue=(TextView)findViewById(R.id.user_venue);
        TextView pointer=(TextView)findViewById(R.id.User_pointer);
        TextView date =(TextView) findViewById(R.id.User_Date);
        TextView time = (TextView) findViewById(R.id.user_time);
        
        etitle=getIntent().getStringExtra("TITLE");
	    evenue=getIntent().getStringExtra("POINTER");
        epointer=getIntent().getStringExtra("VENUE");
        eHour=getIntent().getStringExtra("HOUR");
	    emin =getIntent().getStringExtra("MIN");
	    eYear=getIntent().getStringExtra("YEAR");
	    eMonth=getIntent().getStringExtra("MONTH");
	    eDay=getIntent().getStringExtra("DAY");
	    namestr=getIntent().getStringExtra("NAME");
	    numstr=getIntent().getStringExtra("NUMBER");
	    String dateSet = ""+eMonth+"-"+eDay+"-"+eYear;
	    String timeSet = ""+eHour+":"+emin;
	    
	    //Schedular Start
	    c = Calendar.getInstance();
		mCurrentYear = c.get(Calendar.YEAR);
		mCurrentMonth = c.get(Calendar.MONTH);
		mCurrentDay = c.get(Calendar.DAY_OF_MONTH);
		mCurrentHour = c.get(Calendar.HOUR_OF_DAY);
		mCurrentMinute = c.get(Calendar.MINUTE);
		mMessage="Meeting Title: "+etitle+"\nMeeting Venue:"+epointer+"\nMeeting Date:"+dateSet+"\n Meeting Time: "+timeSet+"\nPoints to Remind: "+evenue;
		DataSourceDAO todb = new DataSourceDAO(getApplicationContext());
		/*mysId = getIntent().getStringExtra("ID");
		System.out.println("DB ID:"+mysId);
		DbModel elementmodel = new DbModel();
		todb.open();
		elementmodel = todb.selectrow(mysId);
		todb.close();
		String namestring = elementmodel.getNamestr();
		String numstring = elementmodel.getNumstr();
		System.out.println(namestring);
		System.out.println(numstring);
		name = namestring.split(strSeparator);
		numbers = numstring.split(strSeparator);
	    *///Schedular end
	   
        
        elementmodel.setTitle(etitle);
        elementmodel.setVenue(evenue);
        elementmodel.setPointer(epointer);
        elementmodel.setDay(eDay);
        elementmodel.setMonth(eMonth);
        elementmodel.setYear(eYear);
        elementmodel.setHour(eHour);
        elementmodel.setMin(emin);
        elementmodel.setNamestr(namestr);
        elementmodel.setNumstr(numstr);
	   
        
        //Set TextView
	    title.setText(etitle);
	    venue.setText(epointer);
	    pointer.setText(evenue);
	    date.setText(dateSet);
	    time.setText(timeSet);
	
	    //Setting it to db
	    todb.open();
	    myId = todb.createComment(elementmodel);
	    //todb.getAllComments();
	    for (DbModel i : todb.getAllComments()) {
			System.out.println("title:"+i.getTitle());
			System.out.println("Numbers:"+i.getNumstr());
			System.out.println("Numbers:"+i.getNamestr());
			
		}
	    todb.close();
	    
	
	}
	
public void selectedcontacts(View view)
{
	 Intent intent= new Intent(MeetingSummary.this, ShowContacts.class);
	intent.putExtra("ID", Long.toString(myId));
	startActivity(intent);
}
//Schedular
public void SetReminder(View view)
{
	name = namestr.split(strSeparator);
	numbers = numstr.split(strSeparator);

	for (int i = 0; i < name.length; ++i) {
		System.out.println(name[i]);
		namelist.add(name[i]);
	}

	for (j = 0; j < numbers.length; ++j) {
		numberlist.add(numbers[j]);
	}
	
	for (int k=0;k<j;++k)
	{
		try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(numbers[k], null, mMessage+" from Summary", null, null);
			Toast.makeText(getApplicationContext(), "SMS Sent!",
						Toast.LENGTH_LONG).show();
		  } catch (Exception e) {
			Toast.makeText(getApplicationContext(),
				"SMS faild, please try again later!",
				Toast.LENGTH_LONG).show();
			e.printStackTrace();
		  }
	}
	
	
	try {
	    iyear = Integer.parseInt(eYear.toString());
	} catch(NumberFormatException nfe) {
	   System.out.println("Could not parse " + nfe);
	} 
	
	try {
	    imonth = Integer.parseInt(eMonth.toString());
	} catch(NumberFormatException nfe) {
	   System.out.println("Could not parse " + nfe);
	}
	
	try {
	    iday = Integer.parseInt(eDay.toString());
	} catch(NumberFormatException nfe) {
	   System.out.println("Could not parse " + nfe);
	}
	
	try {
	    ihour = Integer.parseInt(eHour.toString());
	    		if (ihour>12)
	    		{
	    			ihour=ihour-12;
	    		}
	} catch(NumberFormatException nfe) {
	   System.out.println("Could not parse " + nfe);
	}
	try {
	    imin = Integer.parseInt(emin.toString());
	} catch(NumberFormatException nfe) {
	   System.out.println("Could not parse " + nfe);
	}
	
	c.set(Calendar.HOUR_OF_DAY, ihour);
	c.set(Calendar.MINUTE, imin);
	c.set(Calendar.SECOND, 0);
	
	// c.set(Calendar.DAY_OF_MONTH, mDay);
	c.set(iyear, imonth, iday);
	System.out.println("hour: "+ihour+"min:"+imin+"Year: "+iyear+"Month: "+imonth+"Day: "+iday);
	long timemilli= c.getTimeInMillis();
	System.out.println("Time in Milliseconds"+timemilli);
	Intent i = new Intent(getApplicationContext(),
			SendSMSAlarmService.class);
	i.putExtra("NUMBERS",
			numstr);
	i.putExtra("SMSReminder",
			mMessage);
	
	time = eYear + "-" + eMonth + "-" + eDay + " " + eHour + "-"
			+ emin;
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm");
	Date dt = null;
	try {
		dt = df.parse(time);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	long trigAtMilSec = dt.getTime();
	System.out.println("time in mili:" + trigAtMilSec);
	
	long time = timemilli-SystemClock.elapsedRealtime();
	/* final int */mId = (int) System.currentTimeMillis();
	
	
	alarmMgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
	//Intent intent = new Intent(getApplicationContext(), SendSMSAlarmService.class);
	alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);
	
	alarmMgr.set(AlarmManager.RTC_WAKEUP,
			timemilli, alarmIntent);	
Intent intent=new Intent(MeetingSummary.this, MainActivity.class);
startActivity(intent);

}

//schedular
	@Override
	// Inflating the menu items
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
	







