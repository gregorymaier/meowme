package com.example.meowme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MemeTextActivity extends Activity {

	public static final String TOP_TEXT = "TOP_TEXT";
	public static final String BOTTOM_TEXT = "BOTTOM_TEXT";
	private String topText = "";
	private String bottomText = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meme_text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.meme_text, menu);
		return true;
	}
	
	public void done(View view)
	{
		// Move to next step
		Intent intent = new Intent(this, CombineActivity.class);
		startActivity(intent);
		intent.putExtra(TOP_TEXT, topText);
		intent.putExtra(BOTTOM_TEXT, bottomText);
	}
}
