package com.devinschwab.hexboardgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

public class HexBoard {
	
	private static final String TAG = HexBoard.class.getSimpleName();
	
	private HexTile hextiles[][];
	
	private final Context context;
	
	private Bitmap blankHex;
	private Bitmap blueHex;
	private Bitmap redHex;
	
	private int bitmapXSpacing;
	private int bitmapYSpacing;
	private int bitmapRowOffset;
	private int bitmapScale;
	
	public HexBoard(Context context, int size)
	{
		this.context = context;
		
		bitmapXSpacing = 51;
		bitmapYSpacing = 70;
		bitmapRowOffset = bitmapYSpacing/2;
		bitmapScale = 70;
		
		// load the bitmaps
		blankHex = BitmapFactory.decodeResource(context.getResources(), R.drawable.blank_hex);
		blueHex = BitmapFactory.decodeResource(context.getResources(), R.drawable.blue_hex);
		redHex = BitmapFactory.decodeResource(context.getResources(), R.drawable.red_hex);
		
		// scale the bitmaps
		blankHex = Bitmap.createScaledBitmap(blankHex, bitmapScale, bitmapScale, false);
		blueHex = Bitmap.createScaledBitmap(blueHex, bitmapScale, bitmapScale, false);
		redHex = Bitmap.createScaledBitmap(redHex, bitmapScale, bitmapScale, false);
		
		
		hextiles = new HexTile[size][size];
		for(int i=0; i<size; i++)
		{
			for(int j=0; j<size; j++)
			{
				hextiles[i][j] = new HexTile(0);
			}
		}
	}
	
	public void draw(Canvas canvas) {
		for(int i=0; i<hextiles.length; i++)
		{
			for(int j=0; j<hextiles[i].length; j++)
			{
				switch(hextiles[i][j].getPlayer())
				{
				case 0:
					canvas.drawBitmap(blankHex, j*bitmapXSpacing, i*bitmapYSpacing+j*bitmapRowOffset, null);
					break;
				case 1:
					canvas.drawBitmap(blueHex, j*bitmapXSpacing-i*bitmapYSpacing, i*bitmapRowOffset, null);
					break;
				case 2:
					canvas.drawBitmap(redHex, j*bitmapXSpacing-i*bitmapYSpacing, i*bitmapRowOffset, null);
					break;
				default:
					Log.d(TAG, "Unknown player " + hextiles[i][j].getPlayer());
				}
			}
		}
	}

}
