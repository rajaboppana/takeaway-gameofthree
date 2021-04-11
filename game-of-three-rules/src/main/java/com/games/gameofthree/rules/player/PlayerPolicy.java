package com.games.gameofthree.rules.player;

/**
 * Policy class containing player rules
 *
 */
public class PlayerPolicy {
	private PlayerNumberPolicy playerNumberPolicy;

	private PlayerNamePolicy playerNamePolicy;

	public PlayerPolicy(PlayerNumberPolicy playerNumberPolicy, PlayerNamePolicy playerNamePolicy) {
		this.playerNumberPolicy = playerNumberPolicy;
		this.playerNamePolicy = playerNamePolicy;
	}

	public void checkThatThePlayerCanBeRegistred(String playerName) {
		playerNumberPolicy.checkThatOtherPlayerIsRequired();

		playerNamePolicy.checkThatPlayerNameIsNotUsed(playerName);
	}

}
