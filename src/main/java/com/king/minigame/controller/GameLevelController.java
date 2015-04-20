package com.king.minigame.controller;

import com.king.minigame.core.GameLevelService;
import com.king.minigame.core.model.UserScore;
import com.king.minigame.session.UserRepository;
import com.king.minigame.session.UserSessionRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class GameLevelController {

  private GameLevelService gameLevelService;

  public GameLevelController(GameLevelService gameLevelService, UserSessionRepository userSessionRepository, UserRepository userRepository) {
    this.gameLevelService = gameLevelService;
  }

  public String getHighScoreListForLevel(Integer levelId) {

    List<UserScore> highScoreList = gameLevelService.getHighScoreListForLevel2(levelId);

    String highScoreListInCsvFormat = parseToCsv(highScoreList);
    return highScoreListInCsvFormat;
  }

  public void postUserScoreToLevel(String sessionKey, Integer levelId, Integer scoreValue) {
    gameLevelService.postUserScoreToLevel(sessionKey, levelId, scoreValue);
  }

  private String parseToCsv(List<UserScore> highScoreList) {
    return highScoreList.stream().map(s -> String.format("%d=%d", s.getUserId(), s.getScoreValue())).collect(Collectors.joining(","));
  }
}
