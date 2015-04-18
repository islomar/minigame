package com.king.minigame.core;

import com.king.minigame.session.SessionService;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ScoreService {

  private static final int MAX_NUMBER_OF_SCORES_FOR_HIGHLIST = 15;
  private Map<Integer, Level> levels;

  private SessionService sessionService;

  public ScoreService(SessionService sessionService) {
    levels = new HashMap<>();
    this.sessionService = sessionService;
  }

  public void postUserScoreToList(Integer userId, Integer levelId, Integer scoreValue) {

    if (!sessionService.hasUserValidSessionKey(userId)) {
      throw new IllegalStateException("The user " + userId + " is not active currently.");
    }

    Level level = retrieveLevel(levelId);

    addScoreToLevel(userId, scoreValue, level);

    levels.put(levelId, level);
  }

  private void addScoreToLevel(Integer userId, Integer scoreValue, Level level) {Score score = new Score(scoreValue, Instant
      .now());
    level.addScoreForUser(userId, score);
  }

  private Level retrieveLevel(Integer levelId) {

    Level level = levels.get(levelId);
    if (level == null) {
      level = new Level(levelId);
    }
    return level;
  }

  /**
   * Limited to 15.
   */
  public Map<Integer, Score> getHighScoreListForLevel(Integer levelId) {

    Level level = levels.get(levelId);
    if (level == null) {
      return new HashMap<>();
    } else {
      return level.getMaximumScorePerUser();
    }
  }

}
