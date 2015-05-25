package com.notes.mnotes;
//Author: Vijaykumar Jatti and Keenith
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// on click of the photo notes button
		Button picture = (Button) findViewById(R.id.Pictures);
		picture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this,
						PhotoActivity.class);
				startActivity(intent);
			}
		});
	}

	// on click of the todo activity button
	public void todo(View view) {
		Intent todointent = new Intent(this, ToDoActivity.class);
		startActivity(todointent);
	}
	public void remind_me(View reminderView){
		Intent reminderIntent = new Intent(this,ReminderActivity.class);
		startActivity(reminderIntent);
	}
	public void whiteboard(View view){
		Intent wbIntent = new Intent(this,WhiteboardActivity.class);
		startActivity(wbIntent);
	}
}