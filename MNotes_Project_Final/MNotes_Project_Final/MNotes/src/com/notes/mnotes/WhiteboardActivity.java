package com.notes.mnotes;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
// Implementation : Vijay, Ravi
// UI Design : Kenneth

public class WhiteboardActivity extends Activity {

	private ImageView eraser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.whiteboard_activity);

		final WhiteboardDraw drawingView = (WhiteboardDraw) findViewById(R.id.drawing);

		eraser = (ImageView) findViewById(R.id.eraser);
		eraser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (drawingView.isEraserActive) {
					drawingView.isEraserActive = false;
					eraser.setImageResource(R.drawable.ic_pencil);
				} else {
					drawingView.isEraserActive = true;
					eraser.setImageResource(R.drawable.ic_eraser);
				}
			}
		});
	}
}
