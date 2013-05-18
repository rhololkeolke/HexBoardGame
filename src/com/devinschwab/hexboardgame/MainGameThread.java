package com.devinschwab.hexboardgame;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;


public class MainGameThread extends Thread {
	
	private static final String TAG = MainGameThread.class.getSimpleName();
	
	//desired fps
	private final static int 	MAX_FPS = 50;
	//maximum number of frames to be skipped
	private final static int	MAX_FRAME_SKIPS = 5;
	//the frame period
	private final static int	FRAME_PERIOD = 1000 / MAX_FPS;	

	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanel;
	private boolean running;
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public MainGameThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void run() {
		Canvas canvas;
		Log.d(TAG, "Starting game loop");
		
		long beginTime; // the time when the cycle began
		long timeDiff; // the time it took for the cycle to execute
		int sleepTime = 0; // ms to sleep (<0 if we're behind)
		int framesSkipped; // number of frames being skipped
				
		while(running) {
			canvas = null;
			// try locking the canvas for exclusive pixel editing
			// in the surface
			try {
				canvas = this.surfaceHolder.lockCanvas();
				if(canvas == null)
					continue;
				synchronized(surfaceHolder) {
					beginTime = System.currentTimeMillis();
					framesSkipped = 0; // resetting the frames skipped
					// update game state
					this.gamePanel.update();
					// render state to the screen
					//draws the canvas on the panel
					this.gamePanel.render(canvas);
					// calculate how long the cycle took
					timeDiff = System.currentTimeMillis() - beginTime;
					// calculate sleep time
					sleepTime = (int)(FRAME_PERIOD - timeDiff);
					
					if(sleepTime > 0) {
						// if sleepTime > 0 we're ok
						try {
							// send the thread to sleep for a short period
							// very useful for battery saving
							Thread.sleep(sleepTime);
						} catch(InterruptedException e) {}
					}
					
					while(sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
						// wee need to catch up
						// update without rendering
						this.gamePanel.update();
						// add frame period to check if in next frame
						sleepTime += FRAME_PERIOD;
						framesSkipped++;
					}
				}
			} finally {
				// in case of an exception the surface is not left in
				// an inconsistent state
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
		Log.d(TAG, "Game loop finished");
	}
}
