package com.devinschwab.hexboardgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

public class HexBoard {
	
	private static final String TAG = HexBoard.class.getSimpleName();
	
	private HexTile hextiles[][];
		
	private Bitmap blankHex;
	private Bitmap blueHex;
	private Bitmap redHex;
	
	private Bitmap scaledBlankHex;
	private Bitmap scaledBlueHex;
	private Bitmap scaledRedHex;
	
	private int bitmapXSpacing;
	private int bitmapYSpacing;
	private int bitmapRowOffset;
	private int bitmapScale;
	
	public HexBoard(Context context, int size)
	{		
		// load the bitmaps
		blankHex = BitmapFactory.decodeResource(context.getResources(), R.drawable.blank_hex);
		blueHex = BitmapFactory.decodeResource(context.getResources(), R.drawable.blue_hex);
		redHex = BitmapFactory.decodeResource(context.getResources(), R.drawable.red_hex);

		updateScale(.65f);
		
		hextiles = new HexTile[size+2][size+2];
		for(int j=1; j<size+1; j++)
		{
			hextiles[0][j] = new HexTile(1);
			hextiles[size+1][j] = new HexTile(1);
		}
		for(int i=1; i<size+1; i++)
		{
			hextiles[i][0] = new HexTile(2);
			hextiles[i][size+1] = new HexTile(2);
		}

		for(int i=1; i<size+1; i++)
		{
			for(int j=1; j<size+1; j++)
			{
				hextiles[i][j] = new HexTile(0);
			}
		}
		
		// set these tiles not to be drawn
		hextiles[0][0] = new HexTile(-1);
		hextiles[0][size+1] = new HexTile(2);
		hextiles[size+1][0] = new HexTile(2);
		hextiles[size+1][size+1] = new HexTile(-1);
	}
	
	public void updateScale(float scale) {
		bitmapScale = (int)(100*scale);
		
		bitmapXSpacing = (int)((51.0/70.0)*bitmapScale);
		bitmapYSpacing = bitmapScale;
		bitmapRowOffset = bitmapYSpacing/2;
		
		// scale the bitmaps
		scaledBlankHex = Bitmap.createScaledBitmap(blankHex, bitmapScale, bitmapScale, false);
		scaledBlueHex = Bitmap.createScaledBitmap(blueHex, bitmapScale, bitmapScale, false);
		scaledRedHex = Bitmap.createScaledBitmap(redHex, bitmapScale, bitmapScale, false);
	}
	
	public void draw(Canvas canvas) {
		for(int i=0; i<hextiles.length; i++)
		{
			for(int j=0; j<hextiles[i].length; j++)
			{
				if(hextiles[i][j] == null)
				{
					Log.d(TAG, "Null hextile: (" + i + "," + j + ")");
					continue;
				}
				switch(hextiles[i][j].getPlayer())
				{
				case -1:
					// do nothing (this is for the corners)
					break;
				case 0:
					canvas.drawBitmap(scaledBlankHex, j*bitmapXSpacing, i*bitmapYSpacing+j*bitmapRowOffset, null);
					break;
				case 1:
					canvas.drawBitmap(scaledBlueHex, j*bitmapXSpacing, i*bitmapYSpacing+j*bitmapRowOffset, null);
					break;
				case 2:
					canvas.drawBitmap(scaledRedHex, j*bitmapXSpacing, i*bitmapYSpacing+j*bitmapRowOffset, null);
					break;
				default:
					Log.d(TAG, "Unknown player " + hextiles[i][j].getPlayer());
				}
			}
		}
	}

	public HexTile getTouchedTile(MotionEvent touchEvent) {
		int j = (int)(touchEvent.getX()/bitmapXSpacing);
		int i = (int)((touchEvent.getY()-j*bitmapRowOffset)/bitmapYSpacing);
		if(i >= 0 && i < hextiles.length && j >= 0 && j<hextiles[i].length)
			return hextiles[i][j];
		return null;
	}
}
