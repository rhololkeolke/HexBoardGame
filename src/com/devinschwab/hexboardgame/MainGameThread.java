package com.devinschwab.hexboardgame;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;


public class MainGameThread extends Thread {
	
	private static final String TAG = MainGameThread.class.getSimpleName();

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
	
	@SuppressLint("WrongCall")
	@Override
	public void run() {
		long tickCount = 0L;
		Log.d(TAG, "Starting game loop");
		while(running) {
			tickCount++;
			Canvas canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();
				if(canvas == null)
					continue;
				synchronized(surfaceHolder) {
					// update game state
					//draws the canvas on the panel
					this.gamePanel.onDraw(canvas);
				}
			} finally {
				// in case of an exception the surface is not left in
				// an inconsistent state
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
		Log.d(TAG, "Game loop executed " + tickCount + " times");
	}
}