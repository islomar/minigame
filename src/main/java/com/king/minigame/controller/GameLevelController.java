package com.king.minigame.controller;

import com.king.minigame.core.GameLevelService;
import com.king.minigame.core.UserScore;
import com.king.minigame.session.UserRepository;
import com.king.minigame.session.UserSessionRepository;
import com.king.minigame.session.SessionService;

import java.time.Clock;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
public class GameLevelController {

  private GameLevelService gameLevelService;

  public GameLevelController(UserSessionRepository userSessionRepository, UserRepository userRepository) {
    SessionService sessionService = new SessionService(Clock.systemUTC(), userSessionRepository, userRepository);
    this.gameLevelService = new GameLevelService(sessionService, Clock.systemUTC());
  }

  public String getHighScoreListForLevel(Integer levelId) {

    Map<Integer, UserScore> highScoreList = gameLevelService.getHighScoreListForLevel(levelId);

    String highScoreListInCsvFormat = parseToCsv(highScoreList);
    return highScoreListInCsvFormat;
  }

  public void postUserScoreToLevel(String sessionKey, Integer levelId, Integer scoreValue) {
    gameLevelService.postUserScoreToLevel(sessionKey, levelId, scoreValue);
  }

  private String parseToCsv(Map<Integer, UserScore> highScoreList) {
    return highScoreList.entrySet().stream().map(s -> String.format("%d=%d", s.getKey(), s.getValue().getScoreValue())).collect(Collectors.joining(","));
  }
}
