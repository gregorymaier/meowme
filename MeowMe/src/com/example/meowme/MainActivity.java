package com.example.meowme;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public static final String PHOTO_URI = "PHOTO_URI";
	public static final String PHOTO_PATH = "PHOTO_PATH";
	
	private static final int REQUEST_TAKE_PHOTO = 1;
	private Uri selectedImageUri;
	private boolean newPhoto;
    private String photoPath;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public static File createImageFile() throws IOException {
        // Create an image file name
        return new File(Environment.getExternalStorageDirectory(),  
        		        "photo_" + Long.valueOf(new Date().getTime()).toString());
    }
	
	// Thank you to developer.android.com tutorials - use camera to take picture
	// http://developer.android.com/training/camera/photobasics.html
	public void takePicture(View view) throws IOException {
		newPhoto = true;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = createImageFile();
            photoPath = photoFile.getAbsolutePath();
            // Continue only if the File was successfully created
            if (photoFile != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
        }
    } // end 
	
	public void selectPicture(View view)
	{
		Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select file to upload "), 10);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
        if (resultCode == RESULT_OK && data != null) {
        	
        	if(data.getData() != null)
        	{
        		selectedImageUri = data.getData();
        		newPhoto = false;
            }
        	else
        	{
        		Toast.makeText(
        				getApplicationContext(), 
        				"failed to get Image!", 
        				Toast.LENGTH_LONG
        				).show();
            }
        }
    }
	
	public void showCropActivity(View view)
    {
		if(selectedImageUri == null && photoPath == null)
		{
			Toast.makeText(
    				getApplicationContext(), 
    				"We need a photo first!", 
    				Toast.LENGTH_LONG
    				).show();
		}
		else
		{
			// Goto cropping
            Intent intent = new Intent(this, CropActivity.class);
            if(!newPhoto)
            {
            	intent.putExtra(PHOTO_URI, selectedImageUri);
            	intent.putExtra(PHOTO_PATH, "");
            }
            else
            {
            	intent.putExtra(PHOTO_PATH, photoPath);
            }
    	    startActivity(intent);
		}
    }

}
