package com.king.minigame.controller;

import com.king.minigame.score.Score;
import com.king.minigame.score.ScoreService;
import com.king.minigame.session.SessionService;

import java.time.Clock;
import java.util.Map;

/**
 *
 */
public class ListController {

  private ScoreService scoreService;

  public ListController() {
    SessionService sessionService = new SessionService(Clock.systemUTC());
    this.scoreService = new ScoreService(sessionService);
  }

  public String getHighScoreListForLevel(Integer levelId) {

    Map<Integer, Score> highScoreList = scoreService.getHighScoreListForLevel(levelId);
    String highScoreListInCsvFormat = parseToCsv(highScoreList);
    return highScoreListInCsvFormat;
  }

  private String parseToCsv(Map<Integer, Score> highScoreList) {

    return null;
  }
}
