package com.example.meowme;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/*
 * Let's just use built-in reliable cropping.
 */
public class CropActivity extends Activity {
	
	private static Context context;
	
	public static final String LEFT_EYE = "com_example_meowme_LEFT_EYE";
	public static final String RIGHT_EYE = "com_example_meowme_RIGHT_EYE";
	public static final String NOSE = "com_example_meowme_NOSE";
	public static final String MOUTH = "com_example_meowme_MOUTH";
	
	private ImageView imageView;
	private Bitmap temp;
	private Button label;
	private Uri origUri;
	
	private final int PIC_CROP = 1;
	private final int SET_LEFT_EYE = 1;
	private final int SET_RIGHT_EYE = 2;
	private final int SET_NOSE = 3;
	private final int SET_MOUTH = 4;
	private final int DONE = 0xFFFF;
	// Let's crop left eye first
	private int status = SET_LEFT_EYE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crop);
		
		context = getApplicationContext();
		
		label = (Button) findViewById(R.id.what_todo);
		label.setText("Crop your left eye and then press here to continue.");
		
		imageView = (ImageView) findViewById(R.id.imageView);
		
		Intent intent = getIntent();
		String imgPath = intent.getStringExtra(MainActivity.PHOTO_PATH);
		Bitmap src = null;
		
		if(!imgPath.equals(""))
		{
			origUri = Uri.fromFile(new File(imgPath));
			src = BitmapFactory.decodeFile(imgPath);
		}
		else
		{
			origUri = (Uri)intent.getExtras().get(MainActivity.PHOTO_URI);
			try
			{
				src = MediaStore.Images.Media.getBitmap(this.getContentResolver(), origUri);
			}
			catch (Exception ex)
			{
			
			}
		}
		
		int orientation = ActivityHelpers.getCameraPhotoOrientation(context, origUri, origUri.getPath());
		Matrix matrix = new Matrix();
		matrix.postRotate(orientation);
		
		Bitmap fixed = 
				Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
		
		String newImg = "pic123456";
		
		try
		{
			ActivityHelpers.saveBitmap(newImg, fixed);
		}
		catch (Exception ex) 
		{
			
		}
		
		origUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/" + newImg));
		
		System.out.println("ORIENTATINO:" + orientation);
		
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
			label.setText("Crop your right eye and then press here to continue.");
			// Force another crop
			temp = null;
			break;
			
		case SET_RIGHT_EYE:
			status = SET_NOSE;
			ActivityHelpers.saveBitmap(RIGHT_EYE, temp);
			label.setText("Crop your nose and then press here to continue.");
			// Force another crop
			temp = null;
			break;
			
		case SET_NOSE:
			status = SET_MOUTH;
			ActivityHelpers.saveBitmap(NOSE, temp);
			label.setText("Crop your mouth and then press here to continue.");
			// Force another crop
			temp = null;
			break;
			
		case SET_MOUTH:
			status = DONE;
			ActivityHelpers.saveBitmap(MOUTH, temp);
			label.setText("Continue");
			// Move to next step
			Intent intent = new Intent(this, MemeTextActivity.class);
			startActivity(intent);
			break;
			
		case DONE:
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
	            temp = extras.getParcelable("data");
	        }
	    }
	}
	
}
