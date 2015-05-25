package com.examp.meetingsms;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;

public class SendSMSAlarmService extends BroadcastReceiver {
	int j;
	private String mNumberToSend;
	private String mSMSMessage;
	String[] numbers;
	List<String> numberlist = new ArrayList<String>();
	public static String strSeparator = "\\|";

	@Override
	public void onReceive(Context context, Intent intent) {

		// Set the alarm here.

		mNumberToSend = intent.getStringExtra("NUMBERS");
		System.out.println("Numstr: " + mNumberToSend);
		mSMSMessage = intent.getStringExtra("SMSReminder");

		numbers = mNumberToSend.split(strSeparator);

		for (int k = 0; k < numbers.length; ++k) {
			try {
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(numbers[k], null, mSMSMessage+ " \n~V~SMS REMINDER~J~", null,
						null);
				Toast.makeText(context, "SMS Sent!", Toast.LENGTH_LONG).show();
				System.out.println("sending sms");
			} catch (Exception e) {
				System.out.println("not sending sms, failed to sms");
				Toast.makeText(context, "SMS faild, please try again later!",
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		}

	}

}
