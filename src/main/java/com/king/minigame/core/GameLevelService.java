package com.king.minigame.core;

import com.google.common.collect.ListMultimap;

import com.king.minigame.session.SessionService;

import java.time.Clock;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 */
public class GameLevelService {

  private static final int MAX_NUMBER_OF_SCORES_FOR_HIGHLIST = 15;
  private Map<Integer, Level> levels;
  private Clock clock;

  private SessionService sessionService;

  public GameLevelService(SessionService sessionService, Clock clock) {
    levels = new HashMap<>();
    this.sessionService = sessionService;
    this.clock = clock;
  }

  public void postUserScoreToLevel(String sessionKey, Integer levelId, Integer scoreValue) {

    Optional<Integer> userId = sessionService.getUserIdForSessionKey(sessionKey);
    if (!userId.isPresent()) {
      throw new IllegalStateException("The sessionkey " + sessionKey + " is not active currently.");
    }

    Level level = retrieveLevel(levelId);
    addScoreToLevel(userId.get(), scoreValue, level);
    levels.put(levelId, level);
  }


  public Map<Integer, Score> getHighScoreListForLevel(Integer levelId) {

    Level level = levels.get(levelId);
    if (level == null) {
      return new HashMap<>();
    } else {
      return getMaximumScorePerUserForLevel(level);
    }
  }


  private Map<Integer, Score> getMaximumScorePerUserForLevel(Level level) {

    ListMultimap<Integer, Score> allUserScores = level.getAllUserScores();

    Map<Integer, Score> unsortedUserScores = mapToMaximumScorePerUser(allUserScores);
    return sortUserScores(unsortedUserScores);
  }


  private Map<Integer, Score> sortUserScores(Map<Integer, Score> unsortedUserScores) {

    List list = new LinkedList(unsortedUserScores.entrySet());
    Collections.sort(list, new MapComparatorByValueDesc());

    Map result = new LinkedHashMap();
    for (Iterator it = list.iterator(); it.hasNext();) {
      if (result.size() >= MAX_NUMBER_OF_SCORES_FOR_HIGHLIST) {
        break;
      }
      Map.Entry entry = (Map.Entry) it.next();
      result.put(entry.getKey(), entry.getValue());
    }
    return result;
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
    Score score = new Score(scoreValue, clock.instant());
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
