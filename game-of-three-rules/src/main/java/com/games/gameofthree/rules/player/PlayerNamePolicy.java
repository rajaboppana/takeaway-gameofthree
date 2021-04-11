package com.games.gameofthree.rules.player;

import static com.games.gameofthree.player.exceptions.PlayerCannotBeRegistredReason.REQUIRED_PLAYER_NAME_USED;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.List;
import java.util.function.Predicate;

import com.games.gameofthree.domain.Player;
import com.games.gameofthree.player.exceptions.PlayerCannotBeRegistredException;
import com.games.gameofthree.repository.PlayerRepository;

/**
 * Policy class containing player name rules
 *
 */
public class PlayerNamePolicy {

	private PlayerRepository playerRepository;

	public PlayerNamePolicy(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	public void checkThatPlayerNameIsNotUsed(String playerName) {
		if (playerWithSameNameExists(playerName)) {
			throw new PlayerCannotBeRegistredException(REQUIRED_PLAYER_NAME_USED);
		}
	}

	private boolean playerWithSameNameExists(String playerName) {
		List<Player> playerAlreadyRegistred = playerRepository.findAll();
		return !isEmpty(playerAlreadyRegistred)
				&& playerAlreadyRegistred.stream().filter(playerWithSameName(playerName)).findFirst().isPresent();
	}

	private Predicate<Player> playerWithSameName(String playerName) {
		return player -> player.getName().equals(playerName);
	}

}
