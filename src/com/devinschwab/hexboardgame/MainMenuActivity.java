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
        
        Button playButton = (Button) findViewById(R.id.play_btn);
        Button highscoresButton = (Button) findViewById(R.id.highscores_btn);
        Button aboutButton = (Button) findViewById(R.id.about_btn);
        
        playButton.setOnClickListener(this);
        highscoresButton.setOnClickListener(this);
        aboutButton.setOnClickListener(this);
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
		Intent selectedActivity;
		switch(v.getId())
		{
		case R.id.play_btn:
			//! @todo play_btn case: Implement Me!
			Log.d("buton click", "MainMenu: play_btn was clicked");
			selectedActivity = new Intent(this, GameActivity.class);
			break;
		case R.id.highscores_btn:
			//! @todo highscores_btn case: Implement me!
			Log.d("button click", "MainMenu: highscores_btn was clicked");
			selectedActivity = new Intent(this, HighscoresActivity.class);
			break;
		case R.id.about_btn:
			//! @todo about_btn case: Implement me!
			Log.d("button click", "MainMenu: about_btn was clicked");
			selectedActivity = new Intent(this, AboutActivity.class);
			break;
		default:
			Log.e("button click", "MainMenu: Unknown button was clicked: " + Integer.toString(v.getId()));
			return;
		}
		startActivity(selectedActivity);
	}
    
    
    
}
