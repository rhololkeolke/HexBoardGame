package com.devinschwab.hexboardgame;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
	
	private int boardXOffset;
	private int boardYOffset;
	
	private final List<BoardIndex> startPlayerOneTiles;
	private final List<BoardIndex> startPlayerTwoTiles;
	
	private final Set<BoardIndex> goalPlayerOneTiles;
	private final Set<BoardIndex> goalPlayerTwoTiles;
	
	private final int size;
	
	public HexBoard(Context context, int size) {	
		
		this.size = size;
		
		// load the bitmaps
		blankHex = BitmapFactory.decodeResource(context.getResources(), R.drawable.blank_hex);
		blueHex = BitmapFactory.decodeResource(context.getResources(), R.drawable.blue_hex);
		redHex = BitmapFactory.decodeResource(context.getResources(), R.drawable.red_hex);

		updateScale(.65f);
		
		boardXOffset = bitmapXSpacing;
		boardYOffset = 0;
		
		startPlayerOneTiles = new LinkedList<BoardIndex>();
		startPlayerTwoTiles = new LinkedList<BoardIndex>();
		
		goalPlayerOneTiles = new HashSet<BoardIndex>();
		goalPlayerTwoTiles = new HashSet<BoardIndex>();
		
		hextiles = new HexTile[size+2][size+2];
		for(int j=1; j<size+1; j++)
		{
			hextiles[0][j] = new HexTile(1);
			hextiles[size+1][j] = new HexTile(1);
			
			startPlayerOneTiles.add(new BoardIndex(0, j));
			goalPlayerOneTiles.add(new BoardIndex(size+1, j));
		}
		
		for(int i=1; i<size+1; i++)
		{
			hextiles[i][0] = new HexTile(2);
			hextiles[i][size+1] = new HexTile(2);
			
			startPlayerTwoTiles.add(new BoardIndex(i, 0));
			goalPlayerTwoTiles.add(new BoardIndex(i, size+1));
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
		
		//goalPlayerTwoTiles.add(new BoardIndex(0, size+1));
		//goalPlayerTwoTiles.add(new BoardIndex(size+1, 0));
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
					canvas.drawBitmap(scaledBlankHex, boardXOffset+j*bitmapXSpacing, boardYOffset+i*bitmapYSpacing+j*bitmapRowOffset, null);
					break;
				case 1:
					canvas.drawBitmap(scaledBlueHex, boardXOffset+j*bitmapXSpacing, boardYOffset+i*bitmapYSpacing+j*bitmapRowOffset, null);
					break;
				case 2:
					canvas.drawBitmap(scaledRedHex, boardXOffset+j*bitmapXSpacing, boardYOffset+i*bitmapYSpacing+j*bitmapRowOffset, null);
					break;
				default:
					Log.d(TAG, "Unknown player " + hextiles[i][j].getPlayer());
				}
			}
		}
	}

	public HexTile getTouchedTile(MotionEvent touchEvent) {
		int j = (int)((touchEvent.getX()-boardXOffset)/bitmapXSpacing);
		int i = (int)((touchEvent.getY()-j*bitmapRowOffset - boardYOffset)/bitmapYSpacing);
		if(i >= 0 && i < hextiles.length && j >= 0 && j<hextiles[i].length)
		{
			Log.d(TAG, "tile (" + i + "," + j + ") was touched");
			return hextiles[i][j];
		}
		return null;
	}

	public boolean checkForWinner(int player) {
		List<BoardIndex> openNodes;
		Set<BoardIndex> goalNodes;
		if(player == 1)
		{
			openNodes = new LinkedList<BoardIndex>(startPlayerOneTiles);
			goalNodes = goalPlayerOneTiles;
		}
		else if(player == 2)
		{
			openNodes = new LinkedList<BoardIndex>(startPlayerTwoTiles);
			goalNodes = goalPlayerTwoTiles;
		}
		else
			return false; // maybe this should throw an exception
		
		Set<BoardIndex> closedNodes = new HashSet<BoardIndex>(openNodes);
		
		// loop until all contiguous nodes have been examined
		while(openNodes.size() > 0) {
			List<BoardIndex> newOpenNodes = new LinkedList<BoardIndex>();
			for(BoardIndex index : openNodes) {
				closedNodes.add(index);
				
				for(int i=index.i-1; i<=index.i+1; i++) {
					for(int j=index.j-1; j<=index.j+1; j++) {
						// the indices (i-1, j-1) and (i+1, j+1) are invalid so skip them
						if((i == index.i-1 && j == index.j-1) || (i == index.i+1 && j == index.j+1))
							continue;
						// if valid index
						if(i >= 0 && i < hextiles.length && j >= 0 && j < hextiles[i].length) {
							BoardIndex newIndex = new BoardIndex(i, j);
							
							// if tile belongs to the goal nodes then player is a winner
							if(goalNodes.contains(newIndex))
								return true;
							
							// if tile belongs to player and it hasn't already been visited
							if(hextiles[i][j].getPlayer() == player && !closedNodes.contains(newIndex) ) {
								newOpenNodes.add(newIndex); 
								// kind of abusing the closedNodes list but means 
								// that new nodes won't be added twice
								closedNodes.add(newIndex); 
							}
						}
					}
				}
			}
			openNodes = newOpenNodes;
		}
		
		return false;
	}
	
	private class BoardIndex {
		public final int i;
		public final int j;
		private final int hashCode;
		
		public BoardIndex(int i, int j) {
			this.i = i;
			this.j = j;
			hashCode = i*10^((int)(Math.log10(j)+1)) + j;
		}
		
		@Override
		public final int hashCode() {
			return hashCode;
		}
		
		@Override
		public final boolean equals(final Object obj) {
			if(!(obj instanceof BoardIndex))
				return false;
			BoardIndex other = (BoardIndex)obj;
			
			return this.i == other.i && this.j == other.j;
		}
		
		@Override
		public final String toString() {
			return "(" + i + "," + j + ")";
		}
		
	}

	public int size() {
		return size;
	}

	public boolean selectTile(int player, int i, int j) {
		if(i < 0 || i >= hextiles.length || j < 0 || j >= hextiles.length)
		{
			Log.e(TAG, "Invalid tile (" + i + "," + j + ") selected for player " + player);
			return false;
		}
		
		if(hextiles[i][j] != null && hextiles[i][j].getPlayer() == 0)
		{
			Log.d(TAG, "Setting tile (" + i + "," + j + ") to player " + player);
			hextiles[i][j].setPlayer(player);
			return true;
		} else {
			Log.d(TAG, "Tile (" + i + "," + j + ") already belongs to player " + player);
		}
		return false;
	}
}
