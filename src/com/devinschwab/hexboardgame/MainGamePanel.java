package com.devinschwab.hexboardgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = MainGamePanel.class.getSimpleName();
	private MainGameThread thread;
	
	private HexBoard board;
	
	private final Object touchEventLock = new Object();
	private MotionEvent touchEvent;
	private int playerTurn;
	
	private GameActivity.GameTypes gameType;
	
	private boolean gameOver = false;
	
	private final Context context;
		
	public MainGamePanel(Context context, GameActivity.GameTypes gameType) {
		super(context);
		this.context = context;
		this.gameType = gameType;
		getHolder().addCallback(this);
		
		touchEvent = null;
		playerTurn = 1;
		
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
			synchronized(touchEventLock) {
				Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
				touchEvent = event;
			}
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		render(canvas);
	}

	// handles user input and updates the game state
	public void update() {
		if(gameOver)
			return;
		
		if(gameType == GameActivity.GameTypes.SINGLE_PLAYER && playerTurn == 2)
		{
			// AI agent goes here
			while(!board.selectTile(playerTurn, (int)(Math.random()*board.size()), (int)(Math.random()*board.size())))
			{}
			playerTurn = 1;
		}
		
		HexTile touchedTile = null;
		synchronized(touchEventLock) {
			if(touchEvent != null)
			{
				touchedTile = board.getTouchedTile(touchEvent);
				touchEvent = null;
			}
		}
		
		if(touchedTile != null)
		{
			Log.d(TAG, "Tile was touched");
			if(touchedTile.getPlayer() != 0)
				return;
			
			touchedTile.setPlayer(playerTurn);
			
			if(board.checkForWinner(playerTurn))
			{
				gameOver = true;
				return;
			}
			
			if(playerTurn == 1)
				playerTurn = 2;
			else
				playerTurn = 1;
		}
		
		
	}

	// draws the game
	public void render(Canvas canvas) {
		// fills the canvas with white
		canvas.drawColor(Color.WHITE);
		board.draw(canvas);
		if(gameOver) {
			Paint paint = new Paint(); 
			paint.setColor(Color.WHITE); 
			paint.setStyle(Style.FILL);
			canvas.drawRect(new Rect(0, canvas.getHeight()/2-160, canvas.getWidth(), canvas.getHeight()/2+60), paint);
			
			paint.setColor(Color.BLACK);
			paint.setTextSize(120);
			canvas.drawText("Player " + playerTurn + " Won!", 0, canvas.getHeight()/2, paint);  
		}
	}

}