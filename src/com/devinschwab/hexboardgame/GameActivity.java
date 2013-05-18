package com.devinschwab.hexboardgame;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class GameActivity extends Activity {
	private static final String TAG = GameActivity.class.getSimpleName();
	
	public static final String GAME_TYPE_KEY = "game type";
	
	public static enum GameTypes { 
		SINGLE_PLAYER,
		PASS_AND_PLAY,
		LOCAL_NETWORK,
		INTERNET	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(new MainGamePanel(this, (GameTypes)this.getIntent().getSerializableExtra(GAME_TYPE_KEY)));
		Log.d(TAG, "View added");
	}
	
	@Override
	 protected void onDestroy() {
	  Log.d(TAG, "Destroying...");
	  super.onDestroy();
	 }

	 @Override
	 protected void onStop() {
	  Log.d(TAG, "Stopping...");
	  super.onStop();
	 }
}
