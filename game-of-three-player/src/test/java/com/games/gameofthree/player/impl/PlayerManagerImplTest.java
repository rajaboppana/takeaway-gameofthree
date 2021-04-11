package com.games.gameofthree.player.impl;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.games.gameofthree.domain.Player;
import com.games.gameofthree.player.exceptions.PlayerCannotBeRegistredException;
import com.games.gameofthree.repository.PlayerRepository;
import com.games.gameofthree.rules.player.PlayerPolicy;

@RunWith(MockitoJUnitRunner.class)
public class PlayerManagerImplTest {

	@InjectMocks
	private PlayerManagerImpl beanUnderTest;

	@Mock
	private PlayerRepository playerRepository;

	@Mock
	private PlayerPolicy playerPolicy;

	@Test
	public void should_add_savePlayer_whenThePlayerCanBeRegistred() {
		// Given
		Player player = new Player("Rachid");

		// When
		beanUnderTest.add(player);

		// Then
		verify(playerPolicy).checkThatThePlayerCanBeRegistred("Rachid");
		verify(playerRepository).save(player);
	}

	@Test
	public void should_add_throwException_whenThePlayerCannotBeRegistred() {
		// Given
		Player player = new Player("Rachid");
		doThrow(PlayerCannotBeRegistredException.class).when(playerPolicy).checkThatThePlayerCanBeRegistred("Rachid");

		// When
		ThrowingRunnable runnable = () -> beanUnderTest.add(player);

		// Then
		assertThrows(PlayerCannotBeRegistredException.class, runnable);
		verify(playerRepository, never()).save(player);
	}

	@Test
	public void should_reinitPlayers_deleteAllPlayers() {
		// When
		beanUnderTest.reinitPlayers();

		// Then
		verify(playerRepository).deleteAll();
	}

}
