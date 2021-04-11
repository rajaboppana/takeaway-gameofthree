package com.games.gameofthree.player;

import com.games.gameofthree.domain.Player;

public interface PlayerManager {

	void add(Player player);

	void reinitPlayers();

	boolean isAPlayerWaiting();
}
