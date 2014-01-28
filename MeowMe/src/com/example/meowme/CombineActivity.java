package com.example.meowme;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

//TODO: Need to combine cropped bitmaps with cat bitmap into 1 bitmap.
//      ***** We need to add functionality to put the Meme text in this Activity ******
public class CombineActivity extends Activity {
	
	public static String IMAGE_TO_UPLOAD = "com.example.meowme.IMAGE_TO_UPLOAD";
	
	private Bitmap leftEye, rightEye, catImage, combined;
	private ImageView combImgView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_combine);
		
		/*
		 * Junk example that shows how combining image functionality works
		 */
		
		// Get the ImageView defined in the layout
		combImgView = (ImageView) findViewById(R.id.combinedImageView);
		
		// Get the Bitmap objects of the left and right eye
		leftEye = ActivityHelpers.getBitmap(CropActivity.LEFT_EYE);
		rightEye = ActivityHelpers.getBitmap(CropActivity.RIGHT_EYE);
		
		// Get the cat image
		catImage = BitmapFactory.decodeResource(getResources(), R.drawable.cat_tie);
		
		// Make a canvas to draw on - becomes final product
		// * When this is done for real the eyes will be drawn first
		// * and then the cat image will be drawn over them
		// ** The size will be based on the cat template image's size
		combined = Bitmap.createBitmap(500, 750, Config.ARGB_8888);
		Canvas c = new Canvas(combined);
		
		@SuppressWarnings("deprecation")
		Drawable d0 = new BitmapDrawable(catImage);
		
		@SuppressWarnings("deprecation")
		Drawable d1 = new BitmapDrawable(leftEye);
		
		@SuppressWarnings("deprecation")
		Drawable d2 = new BitmapDrawable(rightEye);
		
		// Draw cat - fill canvas
		d0.setBounds(0, 0, 500, 750);
		d0.draw(c);
		// Draw left eye - 100x100
		d1.setBounds(50, 50, 150, 150);
		d1.draw(c);
		// Draw right eye
		d2.setBounds(250, 50, 350, 150);
		d2.draw(c);
		
		// Set the image
		combImgView.setImageBitmap(combined);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.combine, menu);
		return true;
	}

	/*
	 * Probably just save to gallery. If we have time use Facebook API to upload
	 * to user's wall.
	 */
	public void publish(View view)
	{
		
	}
}
