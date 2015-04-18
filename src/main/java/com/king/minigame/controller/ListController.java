package com.king.minigame.controller;

import com.king.minigame.score.Score;
import com.king.minigame.score.ScoreService;
import com.king.minigame.session.SessionService;

import java.time.Clock;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    //highScoreList = hardcode();

    String highScoreListInCsvFormat = parseToCsv(highScoreList);
    return highScoreListInCsvFormat;
  }

  private Map<Integer, Score> hardcode() {

    Map<Integer, Score> response = new HashMap<Integer, Score>();
    response.put(111, new Score(100, Instant.now()));
    response.put(111, new Score(300, Instant.now()));
    response.put(222, new Score(200, Instant.now()));

    return response;
  }

  private String parseToCsv(Map<Integer, Score> highScoreList) {
    return highScoreList.entrySet().stream().map(s -> String.format("%d=%d", s.getKey(), s.getValue().getScoreValue())).collect(Collectors.joining(","));
  }
}
