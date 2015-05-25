package com.notes.mnotes;

import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

/**
 * Author: ravi
 */
public class MyService extends Service{
	
	private NotificationManager mManager;

	/**
     * Simply return null, since our Service will not be communicating with
     * any other components. It just does its work silently.
     */
	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}	
	
	@SuppressWarnings({ "static-access", "deprecation" })
	@Override
	public void onStart(Intent intent, int startId){		
		//super.onStart(intent, startId);
		
		if(null!= intent){
			String Noti_title = intent.getExtras().getString("title");
	        String Noti_message = intent.getExtras().getString("notes");
	        int Noti_Code = intent.getExtras().getInt("code");
	        
	        Log.d("Myservice","In Onstart method");
	        
	        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
	        
	          Intent intent1 = new Intent(this.getApplicationContext(), ReminderActivity.class);
	        Notification notification = new Notification(R.drawable.ic_launcher , Noti_title , System.currentTimeMillis());
	        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

	        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
	        notification.flags |= Notification.FLAG_AUTO_CANCEL;
	        

	        notification.setLatestEventInfo(this.getApplicationContext(),Noti_title , Noti_message , pendingNotificationIntent);
	        notification.vibrate = new long[] { 100L, 100L, 200L, 500L };
	        mManager.notify(Noti_Code , notification);
	        Log.d("Myservice","Before vibrator");
	        Toast.makeText(this, "Alarm:"+"test"+"Message:"+"test",
	 				Toast.LENGTH_LONG).show();
	        //long time = Calendar.getInstance().getTimeInMillis();
	        //Toast.makeText(this, Long.toString(time), Toast.LENGTH_LONG).show();
	        Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(this.VIBRATOR_SERVICE);
	   	   vibrator.vibrate(1000);
	        try {
	            Uri notification_uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification_uri);
	            r.play();
	        } catch (Exception e) {}			
		}
		
	}
		
		        
       
	
	
	public void onDestroyed() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
	
	
}
