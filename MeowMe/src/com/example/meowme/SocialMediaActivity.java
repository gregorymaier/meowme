package com.example.meowme;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

//TODO: Need a way for the user to select from multiple social media sites
//      so that combined image can be uploaded. On selection the image will
//      be uploaded and the user will be sent back to MainActivity.
public class SocialMediaActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_social_media);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.social_media, menu);
		return true;
	}

}
