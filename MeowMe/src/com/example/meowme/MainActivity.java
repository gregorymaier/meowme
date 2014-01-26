package com.example.meowme;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public static final String PHOTO_URI = "PHOTO_URI";
	
	private static final int REQUEST_TAKE_PHOTO = 1;
	private Uri selectedImageUri = null;
	private boolean newPhoto = false;
    private File photoFile;
    
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
	
	public void takePicture(View view) throws IOException {
		newPhoto = true;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = ActivityHelpers.createImageFile();
            // Continue only if the File was successfully created
            if (photoFile != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
            System.out.println("got here");
        }
    } // end 
	
	public void selectPicture(View view)
	{
		newPhoto = false;
		Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select file to upload "), 10);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		//Toast.makeText(
		//		getApplicationContext(), 
		//		"onActivityResult called", 
		//		Toast.LENGTH_SHORT
		//		).show();
		
        if (resultCode == RESULT_OK) {
        	
        	if(data.getData() != null)
        	{
        		selectedImageUri = data.getData();
            }
        	else
        	{
        		Toast.makeText(
        				getApplicationContext(), 
        				"failed to get Image!", 
        				Toast.LENGTH_LONG
        				).show();
            }
        	
        	if (requestCode == 100 && resultCode == RESULT_OK) { 
                //Bitmap photo = (Bitmap) data.getExtras().get("data");
                //path = getPath(selectedImageUri);
                //preview.setImageURI(selectedImageUri);
        	}
        	
        	if (requestCode == 10)
        	{
        		//selectedPath = getPath(selectedImageUri);
                //preview.setImageURI(selectedImageUri);
        	}
        }
    }
	
	public void showCropActivity(View view)
    {
		if(selectedImageUri == null && !newPhoto)
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
            }
            else
            {
            	intent.putExtra(PHOTO_URI, photoFile.toURI());
            }
    	    startActivity(intent);
		}
    }

}
