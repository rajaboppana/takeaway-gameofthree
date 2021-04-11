package com.games.gameofthree.brain;

import com.games.gameofthree.domain.GameModeEnum;
import com.games.gameofthree.domain.GameRound;
import com.games.gameofthree.domain.GameState;

public interface GameManager {

	GameState playRound(GameRound gameRound);

	GameState addNewPlayer(String player, GameModeEnum gameMode);

}
