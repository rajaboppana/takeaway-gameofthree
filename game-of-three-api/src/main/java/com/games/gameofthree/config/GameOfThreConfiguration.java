package com.games.gameofthree.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.games.gameofthree.brain.GameManager;
import com.games.gameofthree.brain.impl.GameManagerImpl;
import com.games.gameofthree.player.PlayerManager;
import com.games.gameofthree.player.impl.PlayerManagerImpl;
import com.games.gameofthree.repository.PlayerRepository;
import com.games.gameofthree.rules.game.GamePolicy;
import com.games.gameofthree.rules.player.PlayerNamePolicy;
import com.games.gameofthree.rules.player.PlayerNumberPolicy;
import com.games.gameofthree.rules.player.PlayerPolicy;

@Configuration
public class GameOfThreConfiguration {

	@Bean
	public GameManager gameManager(PlayerManager playerManager, GamePolicy gamePolicy,
			PlayerNumberPolicy playerNumberPolicy, @Value("${gameofthree.game.min-number:2}") int minNumber,
			@Value("${gameofthree.game.max-number:1000}") int maxNumber) {
		return new GameManagerImpl(playerManager, gamePolicy, playerNumberPolicy, maxNumber, minNumber);
	}

	@Bean
	public PlayerManager playerManager(PlayerRepository playerRepository, PlayerPolicy playerPolicy) {
		return new PlayerManagerImpl(playerRepository, playerPolicy);
	}

	@Bean
	public GamePolicy gamePolicy(@Value("${gameofthree.game.over-at-number:1}") int gameOverAtNumber,
			PlayerNumberPolicy playerNumberPolicy) {
		return new GamePolicy(gameOverAtNumber, playerNumberPolicy);
	}

	@Bean
	public PlayerPolicy playerPolicy(PlayerNumberPolicy playerNumberPolicy, PlayerNamePolicy playerNamePolicy) {
		return new PlayerPolicy(playerNumberPolicy, playerNamePolicy);
	}

	@Bean
	public PlayerNamePolicy playerNamePolicy(PlayerRepository playerRepository) {
		return new PlayerNamePolicy(playerRepository);
	}

	@Bean
	public PlayerNumberPolicy playerNumberPolicy(PlayerRepository playerRepository,
			@Value("${gameofthree.player.required-number:2}") int requiredPlayerNumberToStart) {
		return new PlayerNumberPolicy(playerRepository, requiredPlayerNumberToStart);
	}

}
