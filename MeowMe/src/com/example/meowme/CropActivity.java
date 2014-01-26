package com.example.meowme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

public class CropActivity extends Activity {
    
	private Uri imageUri;
	private ImageView imageView;
	private ImageView croppedImageView;
	private Bitmap originalBitmap;
	
	private int[] viewCoords = new int[2];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crop);
		
		imageView = (ImageView) findViewById(R.id.imageView);
		croppedImageView = (ImageView) findViewById(R.id.croppedImageView);
		
		imageView.getLocationOnScreen(viewCoords);
		
		Intent intent = getIntent();
		imageUri = (Uri) intent.getExtras().get(MainActivity.PHOTO_URI);
		
		imageView.setImageURI(imageUri);
		
		try
		{
			originalBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
		} 
		catch (Exception ex) 
		{
			Toast.makeText(
    				getApplicationContext(), 
    				"FATAL ERROR!", 
    				Toast.LENGTH_LONG
    				).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.crop, menu);
		return true;
	}
	
	//TODO: Need to fix scaling of image and mapping of touch location to image location
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        Integer x = Integer.valueOf((int)event.getX()) - viewCoords[0];
        Integer y = Integer.valueOf((int)event.getY()) - viewCoords[1];
        
        //x = (int)((float)x / ImageView.SCALE_X.get(imageView));
        //y = (int)((float)y / ImageView.SCALE_Y.get(imageView));
        
        //working = original.copy(Bitmap.Config.ARGB_8888, true);
        //Canvas canvas = new Canvas(working);
        //canvas.drawCircle(x, y, 25, paint);
        //imgView.setImageBitmap(working);
        
        croppedImageView.setImageBitmap(
        		ActivityHelpers.getCroppedBitmap(originalBitmap, x, y, 100));
        
        return false;
    }

}
