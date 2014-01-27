package com.example.meowme;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;

//TODO: Need to combine cropped bitmaps with cat bitmap into 1 bitmap.
//      Then save that bitmap and move to SocialMediaActivity to upload
//      the image.
public class CombineActivity extends Activity {
	
	public static String IMAGE_TO_UPLOAD = "com.example.meowme.IMAGE_TO_UPLOAD";
	
	private Bitmap leftEye, rightEye, catImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_combine);
		
		leftEye = ActivityHelpers.getBitmap(CropActivity.LEFT_EYE);
		rightEye = ActivityHelpers.getBitmap(CropActivity.RIGHT_EYE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.combine, menu);
		return true;
	}

}
