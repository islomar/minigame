package com.king.minigame.score;

import com.google.common.collect.Lists;

import com.king.minigame.session.SessionService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ScoreService {

  private Map<Integer, Level> levels;

  private SessionService sessionService;

  public ScoreService(SessionService sessionService) {
    this.sessionService = sessionService;
  }

  public void saveScoreFor(Integer userId, String levelId, Integer scoreValue) {

    if (!sessionService.hasUserValidSessionKey(userId)) {
      throw new IllegalStateException("The user " + userId + " is not active currently.");
    }
    //recover level
    Level level = levels.get(levelId);
    //create level if it does not exist
    //create score
    Score score = new Score(scoreValue, Instant.now());
    //add score to level
    level.addScoreForUser(userId, score);
  }

  /**
   * Limited to 15.
   */
//  public Map<Integer, Score> getHighScoreListForLevel(Integer levelId) {
//
//    Level level = levels.get(levelId);
//    if (level == null) {
//      return new HashMap<>();
//    } else {
//      return level.getScoreListOrderedByValueDesc();
//    }
//  }

}
