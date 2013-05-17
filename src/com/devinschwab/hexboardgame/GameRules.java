package com.devinschwab.hexboardgame;

public interface GameRules {
	void beforeInput();
	void handleInput();
	void afterRender();
}
