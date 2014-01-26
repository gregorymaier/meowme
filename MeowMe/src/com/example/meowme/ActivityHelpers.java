package com.example.meowme;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.os.Environment;

public class ActivityHelpers {
	
	public static File createImageFile() throws IOException {
        // Create an image file name
        return new File(Environment.getExternalStorageDirectory(),  
        		        Long.valueOf(new Date().getTime()).toString());
    }
	
	public static Bitmap getCroppedBitmap(Bitmap bitmap, int centerX, int centerY, int radius) {
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
	            bitmap.getHeight(), Config.ARGB_8888);
	    
	    Canvas canvas = new Canvas(output);
	    Path path = new Path();
	    
	    path.addCircle(centerX, centerY, radius, Path.Direction.CCW);
	    canvas.clipPath(path);
	    
	    Bitmap sourceBitmap = bitmap;
	    canvas.drawBitmap(sourceBitmap, 
                new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()), 
                new Rect(0, 0, 2*radius, 2*radius), null);
	    
	    return output;
	}
}
