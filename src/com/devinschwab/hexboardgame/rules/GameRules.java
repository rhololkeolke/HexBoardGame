package com.devinschwab.hexboardgame.rules;

public interface GameRules {
	void beforeInput();
	void handleInput();
	void afterRender();
}
