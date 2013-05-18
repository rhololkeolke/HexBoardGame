package com.devinschwab.hexboardgame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = MainGamePanel.class.getSimpleName();
	private MainGameThread thread;
	
	private HexBoard board;
		
	public MainGamePanel(Context context) {
		super(context);
		getHolder().addCallback(this);
		
		// create the board
		board = new HexBoard(context, 11);
		
		// create the game loop thread
		thread = new MainGameThread(this.getHolder(), this);
		
		// allow the game to handle input events
		setFocusable(true);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
		while(retry) {
			try {
				thread.join();
				retry = false;
			} catch(InterruptedException e) {
				// try again shutting down the thread
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			if(event.getY() > getHeight() - 50) {
				thread.setRunning(false);
				((Activity)getContext()).finish();
			} else {
				Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
			}
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		render(canvas);
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}

	public void render(Canvas canvas) {
		// fills the canvas with white
		canvas.drawColor(Color.WHITE);
		board.draw(canvas);
	}

}