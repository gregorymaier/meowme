package com.example.meowme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
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
	
	private Bitmap leftEye, rightEye, mouth, nose, catImage, combined;
	private ImageView combImgView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_combine);
		Intent intent = getIntent();
		
		String topText = intent.getStringExtra(MemeTextActivity.TOP_TEXT);
		String bottomText = intent.getStringExtra(MemeTextActivity.BOTTOM_TEXT);
		
		// Get the ImageView defined in the layout
		combImgView = (ImageView) findViewById(R.id.combinedImageView);
		
		// Get the Bitmap objects of the left and right eye
		leftEye = ActivityHelpers.getBitmap(CropActivity.LEFT_EYE);
		rightEye = ActivityHelpers.getBitmap(CropActivity.RIGHT_EYE);
		mouth = ActivityHelpers.getBitmap(CropActivity.MOUTH);
		nose = ActivityHelpers.getBitmap(CropActivity.NOSE);
		
		// Get the cat image
		catImage = BitmapFactory.decodeResource(getResources(), R.drawable.cat2_template_720w);
		
		// Make a canvas to draw on - becomes final product
		// * When this is done for real the eyes will be drawn first
		// * and then the cat image will be drawn over them
		// ** The size will be based on the cat template image's size
		combined = Bitmap.createBitmap(720, 1008, Config.ARGB_8888);
		Canvas c = new Canvas(combined);
		
		@SuppressWarnings("deprecation")
		Drawable d0 = new BitmapDrawable(catImage);
		
		@SuppressWarnings("deprecation")
		Drawable d1 = new BitmapDrawable(leftEye);
		
		@SuppressWarnings("deprecation")
		Drawable d2 = new BitmapDrawable(rightEye);
		
		@SuppressWarnings("deprecation")
		Drawable d3 = new BitmapDrawable(mouth);
		
		@SuppressWarnings("deprecation")
		Drawable d4 = new BitmapDrawable(nose);
		
		// Draw left eye - 
		d1.setBounds(152, 312, 290, 420);
		d1.draw(c);
		// Draw right eye - 
		d2.setBounds(448, 312, 576, 420);
		d2.draw(c);
		// Draw mouth -
		d3.setBounds(282, 622, 460, 732);
		d3.draw(c);
		// Draw nose - 
		d4.setBounds(310, 522, 438, 604);
		d4.draw(c);
		
		// Draw cat - fill canvas
		d0.setBounds(0, 0, 720, 1008);
		d0.draw(c);
		
		// How to draw text on image for Meme
		Paint paint = new Paint(); // Create a pen
		paint.setColor(Color.RED); // Set color of pen
		paint.setTextSize(60); // Set text size
		paint.setTypeface( // Set font
				Typeface
				.createFromAsset(getAssets(), "fonts/Walkway UltraBold.ttf"));
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
		
		// Draw meme text
		c.drawText(topText, 25, 75, paint);
		c.drawText(bottomText, 25, 695, paint);
		
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
