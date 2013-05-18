package com.devinschwab.hexboardgame;


public class HexTile {	
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