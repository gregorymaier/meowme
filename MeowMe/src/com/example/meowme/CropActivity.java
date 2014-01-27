package com.example.meowme;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CropActivity extends Activity {
	
	public static final String LEFT_EYE = "com_example_meowme_LEFT_EYE";
	public static final String RIGHT_EYE = "com_example_meowme_RIGHT_EYE";
	
	private Uri imageUri;
	private ImageView imageView, croppedImageView;
	private Bitmap originalBitmap, temp;
	private Button label;
	
	private final int SET_LEFT_EYE = 0;
	private final int SET_RIGHT_EYE = 1;
	private final int DONE = 0xFFFF;
	// Let's crop left eye first
	private int status = SET_LEFT_EYE;
	
	private int[] viewCoords = new int[2];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crop);
		
		label = (Button) findViewById(R.id.what_todo);
		label.setText("Press your left eye and then press here to continue.");
		
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
        		temp = ActivityHelpers.getCroppedBitmap(originalBitmap, x, y, 100));
        
        return false;
    }

	public void setBitmap(View view) throws IOException
	{
		switch (status)
		{
		case SET_LEFT_EYE:
			status = SET_RIGHT_EYE;
			ActivityHelpers.saveBitmap(LEFT_EYE, temp);
			label.setText("Press your right eye and then press here to continue.");
			break;
			
		case SET_RIGHT_EYE:
			status = DONE;
			ActivityHelpers.saveBitmap(RIGHT_EYE, temp);
			label.setText("Done.");
			break;
			
		case DONE:
			Intent intent = new Intent(this, CombineActivity.class);
			startActivity(intent);
			break;
			
		default:
			break;
		}
	}
	
}
