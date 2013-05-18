package com.devinschwab.hexboardgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class HexTile {
	
	private static final String TAG = HexTile.class.getSimpleName();
	
	private Bitmap bitmap; // the actual bitmap
	private int x; // the X coordinate
	private int y; // the Y coordinate
	
	public HexTile(Bitmap bitmap, int x, int y) {
		this.bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
		this.x = x;
		this.y = y;
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}

	
}