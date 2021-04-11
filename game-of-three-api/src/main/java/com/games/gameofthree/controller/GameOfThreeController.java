package com.games.gameofthree.controller;

import static com.games.gameofthree.controller.GameOfThreeHelper.addAiParticipant;
import static com.games.gameofthree.controller.GameOfThreeHelper.shouldAddAiPlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.games.gameofthree.brain.GameManager;
import com.games.gameofthree.domain.GameState;
import com.games.gameofthree.dto.GameParticipateRequest;
import com.games.gameofthree.dto.GamePlayRoundRequest;
import com.games.gameofthree.player.PlayerManager;

@Controller
public class GameOfThreeController {

	@Autowired
	private GameManager gameManager;

	@Autowired
	private PlayerManager playerManager;

	@Value("${gameofthree.player.ai-name}")
	private String aiPlayerName;

	@MessageMapping("/participate")
	@SendTo("/topic/gameOfThree")
	public GameState participate(GameParticipateRequest gameParticipateRequest) throws InterruptedException {
		GameState gameState = gameManager.addNewPlayer(gameParticipateRequest.getPlayerName(),
				gameParticipateRequest.getGameMode());
		if (shouldAddAiPlayer(gameParticipateRequest, aiPlayerName)) {
			addAiParticipant(aiPlayerName);
		}
		return gameState;
	}

	@MessageMapping("/play")
	@SendTo("/topic/gameOfThree")
	public GameState play(GamePlayRoundRequest gamePlayRoundRequest) throws InterruptedException {
		return gameManager.playRound(gamePlayRoundRequest.getGameRound());
	}

	@GetMapping("/isAPlayerWaiting")
	@ResponseBody
	public boolean isAPlayerWaiting() {
		return playerManager.isAPlayerWaiting();
	}

}
