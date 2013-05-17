package com.devinschwab.hexboardgame;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GameActivity extends Activity {
	
	public static final String GAME_TYPE_KEY = "game type";
	
	public static enum GameTypes { 
		SINGLE_PLAYER,
		PASS_AND_PLAY,
		LOCAL_NETWORK,
		INTERNET	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GameSurfaceView surface_view = new GameSurfaceView(this);
		setContentView(surface_view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

}
