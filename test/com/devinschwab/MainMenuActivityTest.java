package com.devinschwab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;
import android.widget.Button;

import com.devinschwab.hexboardgame.AboutActivity;
import com.devinschwab.hexboardgame.GameActivity;
import com.devinschwab.hexboardgame.HighscoresActivity;
import com.devinschwab.hexboardgame.MainMenuActivity;
import com.devinschwab.hexboardgame.R;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowActivity;
import com.xtremelabs.robolectric.shadows.ShadowIntent;

@RunWith(RobolectricTestRunner.class)
public class MainMenuActivityTest{
	
	private MainMenuActivity activity;
	private Button button;
	
	@Before
	public void setUp() throws Exception {
		activity = new MainMenuActivity();
		activity.onCreate(null);
	}
	
	@Test
	public void playButtonLaunchesGameActivity() throws Exception {
		button = (Button) activity.findViewById(R.id.play_btn);
		assertNotNull(button);
		
		button.performClick();

		ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();
        assertNotNull("No new activity created", intent);
        ShadowIntent shadowIntent = Robolectric.shadowOf(intent);
        assertEquals(shadowIntent.getComponent().getClassName(), GameActivity.class.getName());
	}
	
	@Test
	public void highscoresButtonLaunchesHighscoresActivity() throws Exception {
		button = (Button) activity.findViewById(R.id.highscores_btn);
		assertNotNull(button);
		
		button.performClick();
		
		ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();
        assertNotNull("No new activity created", intent);
        ShadowIntent shadowIntent = Robolectric.shadowOf(intent);
        assertEquals(shadowIntent.getComponent().getClassName(), HighscoresActivity.class.getName());
	}
	
	@Test
	public void aboutButtonLaunchesAboutActivity() throws Exception {
		button = (Button) activity.findViewById(R.id.about_btn);
		assertNotNull(button);
		
		button.performClick();
		
		ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();
        assertNotNull("No new activity created", intent);
        ShadowIntent shadowIntent = Robolectric.shadowOf(intent);
        assertEquals(shadowIntent.getComponent().getClassName(), AboutActivity.class.getName());
	}
}
