package com.devinschwab.hexboardgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	
	private GameRenderThread thread_;
	private SurfaceHolder sh_;
	private final Paint paint_ = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Context ctx_;

	public GameSurfaceView(Context context) {
		super(context);
		sh_ = getHolder();
		sh_.addCallback(this);
		ctx_ = context;
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		thread_.setSurfaceSize(width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread_ = new GameRenderThread(holder);
		thread_.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		boolean retry = true;
		while(retry)
		{
			try {
				thread_.interrupt();
				thread_.join(100);
				retry = false;
			} catch(InterruptedException e) {
			}
		}
	}
	
	class GameRenderThread extends Thread {
		private int canvas_width_ = 200;
		private int canvas_height_ = 400;
		
		SurfaceHolder sh_;
		
		public GameRenderThread(SurfaceHolder surface_holder) {
			sh_ = surface_holder;
		}
		
		public void run() {
			while(!Thread.interrupted())
			{
				Canvas c = null;
				try {
					c = sh_.lockCanvas(null);
					synchronized(sh_) {
						drawCanvas(c);
					}
				} finally {
					if (c != null) {
						sh_.unlockCanvasAndPost(c);
					}
				}
			}
		}
		
		private void drawCanvas(Canvas c) {
			
		}
		
		public void setSurfaceSize(int width, int height) {
			synchronized(sh_) {
				canvas_width_ = width;
				canvas_height_ = height;
				Log.d("canvas", "width: " + Integer.toString(canvas_width_));
				Log.d("canvas", "height: " + Integer.toString(canvas_height_));
			}
		}
	}

}
