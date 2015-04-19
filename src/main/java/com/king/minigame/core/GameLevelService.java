package com.king.minigame.core;

import com.google.common.collect.ListMultimap;

import com.king.minigame.session.SessionCookie;
import com.king.minigame.session.SessionService;

import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 *
 */
public class GameLevelService {

  private static final int MAX_NUMBER_OF_SCORES_FOR_HIGHLIST = 15;
  private Map<Integer, Level> levels;

  private SessionService sessionService;

  public GameLevelService(SessionService sessionService) {
    levels = new HashMap<>();
    this.sessionService = sessionService;
  }

  public void postUserScoreToList(String sessionKey, Integer levelId, Integer scoreValue) {

    Optional<Integer> userId = sessionService.getUserIdForSessionKey(sessionKey);
    if (!userId.isPresent()) {
      throw new IllegalArgumentException("The sessionkey " + sessionKey + " is not active currently.");
    }

    Level level = retrieveLevel(levelId);
    addScoreToLevel(userId.get(), scoreValue, level);
    levels.put(levelId, level);
  }


  /**
   * Limited to 15.
   */
  public Map<Integer, Score> getHighScoreListForLevel(Integer levelId) {

    Level level = levels.get(levelId);
    if (level == null) {
      return new HashMap<>();
    } else {
      return getMaximumScorePerUserForLevel(level);
    }
  }


  //WORKS PERFECT, but it should be in the Service
  private Map<Integer, Score> getMaximumScorePerUserForLevel(Level level) {

    ListMultimap<Integer, Score> allUserScores = level.getAllUserScores();

    Map<Integer, Score> unsortedUserScores = mapToMaximumScorePerUser(allUserScores);
    return sortUserScores(unsortedUserScores);
  }


  private Map<Integer, Score> sortUserScores(Map<Integer, Score> unsortedUserScores) {


    Map<Integer, Score> sortedUserScores = new TreeMap<>(new MapComparatorByValueDesc(unsortedUserScores));
    sortedUserScores.putAll(unsortedUserScores);

    return sortedUserScores;
  }


  private Map<Integer, Score> mapToMaximumScorePerUser(ListMultimap<Integer, Score> userScores) {
    Map<Integer, Score> unsortedUserScores = new HashMap<>();
    userScores.asMap().forEach((k, v) -> unsortedUserScores.put(k, getMaximumScore(v).get()));
    return unsortedUserScores;
  }


  private Optional<Score> getMaximumScore(Collection<Score> v) {

    Optional<Score> max = v.stream().max(Comparator.comparing(item -> item.getScoreValue()));
    return max;
  }

  private void addScoreToLevel(Integer userId, Integer scoreValue, Level level) {
    Score score = new Score(scoreValue, Instant.now());
    level.addScoreForUser(userId, score);
  }

  private Level retrieveLevel(Integer levelId) {

    Level level = levels.get(levelId);
    if (level == null) {
      level = new Level(levelId);
    }
    return level;
  }

}
