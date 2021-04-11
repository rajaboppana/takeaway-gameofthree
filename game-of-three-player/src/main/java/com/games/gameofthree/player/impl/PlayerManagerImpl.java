package com.games.gameofthree.player.impl;

import com.games.gameofthree.domain.Player;
import com.games.gameofthree.player.PlayerManager;
import com.games.gameofthree.repository.PlayerRepository;
import com.games.gameofthree.rules.player.PlayerPolicy;

/**
 * Class manager of the player model
 *
 */
public class PlayerManagerImpl implements PlayerManager {

	private PlayerRepository playerRepository;

	private PlayerPolicy playerPolicy;

	public PlayerManagerImpl(PlayerRepository playerRepository, PlayerPolicy playerPolicy) {
		this.playerRepository = playerRepository;
		this.playerPolicy = playerPolicy;
	}

	@Override
	public void add(Player player) {
		playerPolicy.checkThatThePlayerCanBeRegistred(player.getName());

		playerRepository.save(player);
	}

	@Override
	public void reinitPlayers() {
		playerRepository.deleteAll();
	}

	public boolean isAPlayerWaiting() {
		return playerRepository.findAll().size() == 1;
	}

}
