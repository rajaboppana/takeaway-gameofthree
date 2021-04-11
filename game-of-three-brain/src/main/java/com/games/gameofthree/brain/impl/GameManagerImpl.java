package com.games.gameofthree.brain.impl;

import static com.games.gameofthree.domain.GameStateEnum.IN_PROGRESS;
import static com.games.gameofthree.domain.GameStateEnum.OVER;
import static com.games.gameofthree.domain.GameStateEnum.WAITING_FOR_PLAYER;

import java.util.Random;

import com.games.gameofthree.brain.GameManager;
import com.games.gameofthree.domain.GameModeEnum;
import com.games.gameofthree.domain.GameRound;
import com.games.gameofthree.domain.GameState;
import com.games.gameofthree.domain.GameStateEnum;
import com.games.gameofthree.domain.Player;
import com.games.gameofthree.player.PlayerManager;
import com.games.gameofthree.rules.game.GamePolicy;
import com.games.gameofthree.rules.player.PlayerNumberPolicy;

/**
 * Class manager of the game
 *
 */
public class GameManagerImpl implements GameManager {

	private PlayerManager playerManager;

	private GamePolicy gamePolicy;

	private PlayerNumberPolicy playerNumberPolicy;

	private int maxNumber;

	private int minNumber;

	public GameManagerImpl(PlayerManager playerManager, GamePolicy gamePolicy, PlayerNumberPolicy playerNumberPolicy,
			int maxNumber, int minNumber) {
		this.playerManager = playerManager;
		this.gamePolicy = gamePolicy;
		this.playerNumberPolicy = playerNumberPolicy;
		this.maxNumber = maxNumber;
		this.minNumber = minNumber;
	}

	public GameState playRound(GameRound gameRound) {
		int newCurrentNumber = countCurrentNumber(gameRound);
		GameStateEnum state = getGameState(newCurrentNumber);
		if (state == OVER) {
			playerManager.reinitPlayers();
		}
		return new GameState(newCurrentNumber, gameRound.getPlayer(), gameRound.getInputNumber(), state,
				gameRound.getGameMode());
	}

	public GameState addNewPlayer(String playerName, GameModeEnum gameMode) {
		playerManager.add(new Player(playerName));
		if (playerNumberPolicy.isRequiredPlayerNumberAchieved()) {
			return new GameState(getARandomNumber(), playerName, IN_PROGRESS, gameMode);
		}
		return new GameState(playerName, WAITING_FOR_PLAYER, gameMode);
	}

	private GameStateEnum getGameState(int currentNumber) {
		if (gamePolicy.isOver(currentNumber)) {
			return OVER;
		}
		return IN_PROGRESS;
	}

	private int countCurrentNumber(GameRound gameRound) {
		return (gameRound.getCurrentNumber() + gameRound.getInputNumber()) / 3;
	}

	private int getARandomNumber() {
		return new Random().ints(minNumber, maxNumber).findFirst().getAsInt();
	}

}
