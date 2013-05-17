package com.devinschwab.hexboardgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * @author Devin
 * 
 * @brief Default Activity. Displays the game's main menu
 *
 */
public class MainMenuActivity extends Activity implements OnClickListener {

	/**
	 * @brief Creates the MainMenu activity
	 * 
	 * Sets up the click listener for the buttons.
	 */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_main_menu);
        
        Button single_player_btn = (Button) findViewById(R.id.single_player_btn);
        Button pass_and_play_btn = (Button) findViewById(R.id.pass_and_play_btn);
        Button local_network_btn = (Button) findViewById(R.id.local_network_btn);
        
        single_player_btn.setOnClickListener(this);
        pass_and_play_btn.setOnClickListener(this);
        local_network_btn.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    /**
     * @brief Provides the OnClickListener Callback function
     * 
     * checks which button was clicked and responds appropriately.
     */
	@Override
	public void onClick(View v) {
		Intent selected_activity;
		switch(v.getId())
		{
		case R.id.single_player_btn:
			//! @todo play_btn case: Implement Me!
			Log.d("buton click", "MainMenu: play_btn was clicked");
			selected_activity = new Intent(this, GameActivity.class);
			selected_activity.putExtra(GameActivity.GAME_TYPE_KEY, GameActivity.GameTypes.SINGLE_PLAYER);
			break;
		case R.id.pass_and_play_btn:
			//! @todo highscores_btn case: Implement me!
			Log.d("button click", "MainMenu: highscores_btn was clicked");
			selected_activity = new Intent(this, GameActivity.class);
			selected_activity.putExtra(GameActivity.GAME_TYPE_KEY, GameActivity.GameTypes.PASS_AND_PLAY);
			break;
		case R.id.local_network_btn:
			//! @todo about_btn case: Implement me!
			Log.d("button click", "MainMenu: about_btn was clicked");
			selected_activity = new Intent(this, GameActivity.class);
			selected_activity.putExtra(GameActivity.GAME_TYPE_KEY, GameActivity.GameTypes.LOCAL_NETWORK);
			break;
		default:
			Log.e("button click", "MainMenu: Unknown button was clicked: " + Integer.toString(v.getId()));
			return;
		}
		startActivity(selected_activity);
	}
    
    
    
}
