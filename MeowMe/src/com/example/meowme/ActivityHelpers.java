package com.example.meowme;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;

public class ActivityHelpers {
	
	public static File createImageFile() throws IOException {
        // Create an image file name
        return new File(Environment.getExternalStorageDirectory(),  
        		        "photo_" + Long.valueOf(new Date().getTime()).toString());
    }
	
	public static void saveBitmap(String filename, Bitmap bmp) throws IOException
	{
       FileOutputStream out = new FileOutputStream(
    		   Environment.getExternalStorageDirectory() + "/" + filename
    		   );
       bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
       out.close();
	}
	
	public static Bitmap getBitmap(String name)
	{
		return BitmapFactory.decodeFile(
				Environment.getExternalStorageDirectory() + "/" + name);
	}
	
	// For some reason we get rotated images now and then
	// Thank you stackoverflow for how to figure out this rotation so we can undo it
	// http://stackoverflow.com/questions/4517634/picture-orientation-from-gallery-camera-intent
	public static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath){
	     int rotate = 0;
	     try {
	         context.getContentResolver().notifyChange(imageUri, null);
	         File imageFile = new File(imagePath);
	         ExifInterface exif = new ExifInterface(
	                 imageFile.getAbsolutePath());
	         int orientation = exif.getAttributeInt(
	                 ExifInterface.TAG_ORIENTATION,
	                 ExifInterface.ORIENTATION_NORMAL);

	         switch (orientation) {
	         case ExifInterface.ORIENTATION_ROTATE_270:
	             rotate = 270;
	             break;
	         case ExifInterface.ORIENTATION_ROTATE_180:
	             rotate = 180;
	             break;
	         case ExifInterface.ORIENTATION_ROTATE_90:
	             rotate = 90;
	             break;
	         }

	     } catch (Exception e) {
	     }
	    return rotate;
	 }
}
