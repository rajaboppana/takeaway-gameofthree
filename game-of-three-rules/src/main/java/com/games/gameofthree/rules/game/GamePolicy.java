package com.games.gameofthree.rules.game;

import com.games.gameofthree.rules.player.PlayerNumberPolicy;

/**
 * Policy Class containing the rules of the game
 * 
 *
 */
public class GamePolicy {

	private int gameOverAtNumber;

	private PlayerNumberPolicy playerNumberPolicy;

	public GamePolicy(int gameOverAtNumber, PlayerNumberPolicy playerNumberPolicy) {
		this.gameOverAtNumber = gameOverAtNumber;
		this.playerNumberPolicy = playerNumberPolicy;
	}

	public boolean isOver(int currentNumber) {
		return currentNumber == gameOverAtNumber;
	}

	public boolean isReady() {
		return playerNumberPolicy.isRequiredPlayerNumberAchieved();
	}

}
