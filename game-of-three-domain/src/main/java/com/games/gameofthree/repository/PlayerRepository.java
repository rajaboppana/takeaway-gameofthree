package com.games.gameofthree.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.games.gameofthree.domain.Player;

public interface PlayerRepository extends JpaRepository<Player, String> {

}
