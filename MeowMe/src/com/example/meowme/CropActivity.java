package com.example.meowme;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/*
 * Let's just use built-in reliable cropping.
 */
public class CropActivity extends Activity {
	
	public static final String LEFT_EYE = "com_example_meowme_LEFT_EYE";
	public static final String RIGHT_EYE = "com_example_meowme_RIGHT_EYE";
	
	private ImageView imageView, croppedImageView;
	private Bitmap temp;
	private Button label;
	private Uri origUri;
	
	private final int PIC_CROP = 1;
	private final int SET_LEFT_EYE = 0;
	private final int SET_RIGHT_EYE = 1;
	private final int DONE = 0xFFFF;
	// Let's crop left eye first
	private int status = SET_LEFT_EYE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crop);
		
		label = (Button) findViewById(R.id.what_todo);
		label.setText("Press your left eye and then press here to continue.");
		
		imageView = (ImageView) findViewById(R.id.imageView);
		croppedImageView = (ImageView) findViewById(R.id.croppedImageView);
		
		Intent intent = getIntent();
		String imgPath = intent.getStringExtra(MainActivity.PHOTO_PATH);
		
		if(!imgPath.equals(""))
		{
			origUri = Uri.fromFile(new File(imgPath));
		}
		else
		{
			origUri = (Uri)intent.getExtras().get(MainActivity.PHOTO_URI);
		}
		imageView.setImageURI(origUri);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.crop, menu);
		return true;
	}

	public void setBitmap(View view) throws IOException
	{
		if (temp == null) return;
		
		switch (status)
		{
		case SET_LEFT_EYE:
			status = SET_RIGHT_EYE;
			ActivityHelpers.saveBitmap(LEFT_EYE, temp);
			label.setText("Press your right eye and then press here to continue.");
			// Force another crop
			temp = null;
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
	
	// Onclick handler
	public void imageClicked(View view)
	{
		performCrop(origUri);
	}
	
	// Thank you stackoverflow
	// http://stackoverflow.com/questions/15228812/crop-image-android-android
	private void performCrop(Uri picUri) {
	    try {

	        Intent cropIntent = new Intent("com.android.camera.action.CROP");
	        // indicate image type and Uri
	        cropIntent.setDataAndType(picUri, "image/*");
	        // set crop properties
	        cropIntent.putExtra("crop", "true");
	        // indicate aspect of desired crop
	        cropIntent.putExtra("aspectX", 1);
	        cropIntent.putExtra("aspectY", 1);
	        // indicate output X and Y
	        //cropIntent.putExtra("outputX", 128);
	        //cropIntent.putExtra("outputY", 128);
	        // retrieve data on return
	        cropIntent.putExtra("return-data", true);
	        // start the activity - we handle returning in onActivityResult
	        startActivityForResult(cropIntent, PIC_CROP);
	    }
	    // respond to users whose devices do not support the crop action
	    catch (ActivityNotFoundException anfe) {
	        // display an error message
	        String errorMessage = "Whoops - your device doesn't support the crop action!";
	        Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
	        toast.show();
	    }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    if (requestCode == PIC_CROP) {
	        if (data != null) {
	            // get the returned data
	            Bundle extras = data.getExtras();
	            // get the cropped bitmap
	            croppedImageView.setImageBitmap(
	            		temp = extras.getParcelable("data"));
	        }
	    }
	}
}
