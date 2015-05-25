package com.notes.mnotes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

/**
 * This class is used to implement broadcast receiver
 * @author ravi
 *
 */
public class ReminderAlarm extends BroadcastReceiver{
	
	

	@Override
	public void onReceive(Context context, Intent intent) {
		String Noti_title = intent.getExtras().getString("Name");
		String Noti_message = intent.getExtras().getString("Description");
		int Noti_code = intent.getExtras().getInt("NotifyCount");
		
		Intent serviceIntent = new Intent(context,MyService.class);
		serviceIntent.putExtra("title", Noti_title);
		serviceIntent.putExtra("notes", Noti_message);
		serviceIntent.putExtra("code", Noti_code);
		Log.d("ReminderAlarm", Noti_title + " " + Noti_message);
        context.startService(serviceIntent);
       
        
		
		/*Toast.makeText(context, "YOUR TIME IS UP!!! GET READY!!!",
				Toast.LENGTH_LONG).show();
		*/
		
	 /*
	 Vibrator vibrator = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
	 vibrator.vibrate(1000);
	 
	 Uri notification_uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
     Ringtone r = RingtoneManager.getRingtone(context, notification_uri);
     r.play();*/
	 
	 
     //context.startService(serviceIntent);
	 
	 
		
	}

}
