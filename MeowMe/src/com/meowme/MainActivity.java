package com.meowme;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface tf = Typeface.createFromAsset(getAssets(), "Walkway UltraBold.ttf");
        Button takePic = (Button) findViewById(R.id.takePicBtn);
        Button fromLib = (Button) findViewById(R.id.fromLibBtn);
        takePic.setTypeface(tf);
        fromLib.setTypeface(tf);
        
        takePic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), TakePictureActivity.class);
        		startActivityForResult(i, 0);				
			}
        });
        fromLib.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), TakePictureActivity.class);
        		startActivityForResult(i, 0);				
			}
        });
    }
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
