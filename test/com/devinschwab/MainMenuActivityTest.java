package com.devinschwab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;
import android.widget.Button;

import com.devinschwab.hexboardgame.GameActivity;
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
	public void singlePlayerButtonLaunchesSinglePlayerGame() throws Exception {
		button = (Button) activity.findViewById(R.id.single_player_btn);
		assertNotNull(button);
		
		button.performClick();

		ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();
        assertNotNull("No new activity created", intent);
        ShadowIntent shadowIntent = Robolectric.shadowOf(intent);
        assertEquals(shadowIntent.getComponent().getClassName(), GameActivity.class.getName());
        assertEquals(GameActivity.GameTypes.SINGLE_PLAYER, intent.getExtras().get(GameActivity.GAME_TYPE_KEY));
	}
	
	@Test
	public void passAndPlayButtonLaunchesPassAndPlayGame() throws Exception {
		button = (Button) activity.findViewById(R.id.pass_and_play_btn);
		assertNotNull(button);
		
		button.performClick();
		
		ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();
        assertNotNull("No new activity created", intent);
        ShadowIntent shadowIntent = Robolectric.shadowOf(intent);
        assertEquals(shadowIntent.getComponent().getClassName(), GameActivity.class.getName());
        assertEquals(GameActivity.GameTypes.PASS_AND_PLAY, intent.getExtras().get(GameActivity.GAME_TYPE_KEY));
	}
	
	@Test
	public void localNetworkButtonLaunchesLocalNetworkGame() throws Exception {
		button = (Button) activity.findViewById(R.id.local_network_btn);
		assertNotNull(button);
		
		button.performClick();
		
		ShadowActivity shadowActivity = Robolectric.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();
        assertNotNull("No new activity created", intent);
        ShadowIntent shadowIntent = Robolectric.shadowOf(intent);
        assertEquals(shadowIntent.getComponent().getClassName(), GameActivity.class.getName());
        assertEquals(GameActivity.GameTypes.LOCAL_NETWORK, intent.getExtras().get(GameActivity.GAME_TYPE_KEY));
	}
}
