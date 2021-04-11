package com.games.gameofthree.brain.impl;

import static com.games.gameofthree.domain.GameModeEnum.AI;
import static com.games.gameofthree.domain.GameModeEnum.HUMAN;
import static com.games.gameofthree.domain.GameStateEnum.IN_PROGRESS;
import static com.games.gameofthree.domain.GameStateEnum.OVER;
import static com.games.gameofthree.domain.GameStateEnum.WAITING_FOR_PLAYER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.games.gameofthree.domain.GameRound;
import com.games.gameofthree.domain.GameState;
import com.games.gameofthree.player.PlayerManager;
import com.games.gameofthree.rules.game.GamePolicy;
import com.games.gameofthree.rules.player.PlayerNumberPolicy;

@RunWith(MockitoJUnitRunner.class)
public class GameManagerImplTest {

	private GameManagerImpl beanUnderTest;

	@Mock
	private PlayerManager playerManager;

	@Mock
	private GamePolicy gamePolicy;

	@Mock
	private PlayerNumberPolicy playerNumberPolicy;

	@Test
	public void should_playRound_returnGameStateInProgress_whenItIsNotOver() {
		// Given
		beanUnderTest = new GameManagerImpl(playerManager, gamePolicy, playerNumberPolicy, 30, 10);
		GameRound gameRound = new GameRound(10, "Rachid", -1, HUMAN);
		when(gamePolicy.isOver(3)).thenReturn(false);

		// When
		GameState gameState = beanUnderTest.playRound(gameRound);

		// Then
		assertThat(gameState.getCurrentNumber()).isEqualTo(3);
		assertThat(gameState.getLastNumberAdded()).isEqualTo(-1);
		assertThat(gameState.getLastPlayer()).isEqualTo("Rachid");
		assertThat(gameState.getState()).isEqualTo(IN_PROGRESS);
	}

	@Test
	public void should_playRound_returnGameStateOver_whenItIsOver() {
		// Given
		beanUnderTest = new GameManagerImpl(playerManager, gamePolicy, playerNumberPolicy, 30, 10);
		GameRound gameRound = new GameRound(3, "Rachid", 0, AI);
		when(gamePolicy.isOver(1)).thenReturn(true);

		// When
		GameState gameState = beanUnderTest.playRound(gameRound);

		// Then
		assertThat(gameState.getCurrentNumber()).isEqualTo(1);
		assertThat(gameState.getLastNumberAdded()).isEqualTo(0);
		assertThat(gameState.getLastPlayer()).isEqualTo("Rachid");
		assertThat(gameState.getState()).isEqualTo(OVER);
	}

	@Test
	public void should_addNewPlayer_returnGameStateWaitingForPlayer_whenRequiredPlayerNumberIsNotAchieved() {
		// Given
		beanUnderTest = new GameManagerImpl(playerManager, gamePolicy, playerNumberPolicy, 30, 10);
		when(playerNumberPolicy.isRequiredPlayerNumberAchieved()).thenReturn(false);

		// When
		GameState gameState = beanUnderTest.addNewPlayer("Rachid", HUMAN);

		// Then
		assertThat(gameState.getState()).isEqualTo(WAITING_FOR_PLAYER);
		assertThat(gameState.getCurrentNumber()).isNull();
		assertThat(gameState.getLastNumberAdded()).isNull();
		assertThat(gameState.getLastPlayer()).isEqualTo("Rachid");
	}

	@Test
	public void should_addNewPlayer_returnGameStateInProgress_whenRequiredPlayerNumberIsAchieved() {
		// Given
		beanUnderTest = new GameManagerImpl(playerManager, gamePolicy, playerNumberPolicy, 30, 10);
		when(playerNumberPolicy.isRequiredPlayerNumberAchieved()).thenReturn(true);

		// When
		GameState gameState = beanUnderTest.addNewPlayer("Rachid", HUMAN);

		// Then
		assertThat(gameState.getState()).isEqualTo(IN_PROGRESS);
		assertThat(gameState.getCurrentNumber()).isNotNull();
		assertThat(gameState.getLastNumberAdded()).isNull();
		assertThat(gameState.getLastPlayer()).isEqualTo("Rachid");
	}

}
