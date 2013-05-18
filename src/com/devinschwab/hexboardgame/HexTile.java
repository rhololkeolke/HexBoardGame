package com.devinschwab.hexboardgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class HexTile {
	
	private static final String TAG = HexTile.class.getSimpleName();
	
	private int player;
	
	public HexTile(int player) {
		this.player = player;
	}
	
	public int getPlayer() {
		return player;
	}
	
	public void setPlayer(int player) {
		this.player = player;
	}
	
}