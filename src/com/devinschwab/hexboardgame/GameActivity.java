package com.devinschwab.hexboardgame;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.devinschwab.hexboardgame.rules.GameRules;
import com.devinschwab.hexboardgame.rules.LocalNetworkRules;
import com.devinschwab.hexboardgame.rules.PassAndPlayRules;
import com.devinschwab.hexboardgame.rules.SinglePlayerRules;

public class GameActivity extends Activity {
	
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
		GameSurfaceView surface_view = new GameSurfaceView(this);
		
		GameRules rules = null;
		switch((GameActivity.GameTypes) this.getIntent().getSerializableExtra(GameActivity.GAME_TYPE_KEY))
		{
		case SINGLE_PLAYER:
			rules = new SinglePlayerRules();
			break;
		case PASS_AND_PLAY:
			rules = new PassAndPlayRules();
			break;
		case LOCAL_NETWORK:
			rules = new LocalNetworkRules();
			break;
		default:
			Log.e("game type", "Unrecognized game type");
			System.exit(1);
		}
		rules.start();
		
		setContentView(surface_view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

}
