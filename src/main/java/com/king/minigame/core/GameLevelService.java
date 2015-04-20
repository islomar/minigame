package com.king.minigame.core;

import com.king.minigame.core.model.Level;
import com.king.minigame.core.model.User;
import com.king.minigame.core.model.UserScore;
import com.king.minigame.session.SessionService;

import java.time.Clock;
import java.util.ArrayList;
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
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 *
 */
public class GameLevelService {

  private static final int MAX_NUMBER_OF_SCORES_FOR_HIGHLIST = 15;
  private static final Comparator<UserScore> SCORE_COMPARATOR = Comparator.comparing(UserScore::getScoreValue);

  private Map<Integer, Level> levels;
  private Clock clock;

  private SessionService sessionService;


  public GameLevelService(SessionService sessionService, Clock clock) {
    levels = new HashMap<>();
    this.sessionService = sessionService;
    this.clock = clock;
  }

//  public void postUserScoreToLevel(String sessionKey, Integer levelId, Integer scoreValue) {
//
//    Optional<Integer> userId = sessionService.getUserIdForSessionKey(sessionKey);
//    if (!userId.isPresent()) {
//      throw new IllegalStateException("The sessionkey " + sessionKey + " is not active currently.");
//    }
//
//    Level level = retrieveLevel(levelId);
//    addUserScoreToLevel(userId.get(), scoreValue, level);
//    levels.put(levelId, level);
//  }

  public void postUserScoreToLevel(String sessionKey, Integer levelId, Integer scoreValue) {

    Optional<User> user = sessionService.findUserBySessionkey(sessionKey);
    if (!user.isPresent()) {
      throw new IllegalStateException("The sessionkey " + sessionKey + " is not active currently.");
    }

    Level level = retrieveLevel(levelId);
    addUserScoreToLevel(user.get().getUserId(), scoreValue, level);

  }

//
//  public Map<Integer, UserScore> getHighScoreListForLevel(Integer levelId) {
//
//    Level level = levels.get(levelId);
//    if (level == null) {
//      return new HashMap<>();
//    } else {
//      return getMaximumScorePerUserForLevel(level);
//    }
//  }

  public List<UserScore> getHighScoreListForLevel2(Integer levelId) {

    Level level = levels.get(levelId);
    if (level == null) {
      return new ArrayList<>();
    } else {
      return getMaximumScorePerUserForLevel2(level);
    }
  }


//  private Map<Integer, UserScore> getMaximumScorePerUserForLevel(Level level) {
//
//    ListMultimap<Integer, UserScore> allUserScores = level.getAllUserScores();
//
//    Map<Integer, UserScore> unsortedUserScores = mapToMaximumScorePerUser(allUserScores);
//    return sortUserScores(unsortedUserScores);
//  }

  // https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html
  private List<UserScore> getMaximumScorePerUserForLevel2(Level level) {

    List<UserScore> allUserScores = level.getAllUserScores2();

//    List<UserScore> sorted = allUserScores.stream().sorted().collect(Collectors.toList());
//
//    Map<Integer, List<UserScore>> collect = allUserScores.stream()
//        .collect(Collectors.groupingBy(UserScore::getUserId));
//
//    Map<Integer, Long> collect1 = allUserScores.stream()
//        .collect(Collectors.groupingBy(UserScore::getUserId, Collectors.counting()));

    Map<Integer, Optional<UserScore>> mapUserIdToMaxUserScore = allUserScores
        .stream()
        .collect(Collectors.groupingBy(UserScore::getUserId, Collectors.reducing(BinaryOperator.maxBy(SCORE_COMPARATOR))));

    List<UserScore> highUserScoreList = mapUserIdToMaxUserScore.entrySet()
            .stream()
            .map(s -> s.getValue().get())
            .sorted()
            .limit(MAX_NUMBER_OF_SCORES_FOR_HIGHLIST)
            .collect(Collectors.toList());

    return highUserScoreList;
  }


  private Map<Integer, UserScore> sortUserScores(Map<Integer, UserScore> unsortedUserScores) {

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


//  private Map<Integer, UserScore> mapToMaximumScorePerUser(ListMultimap<Integer, UserScore> userScores) {
//    Map<Integer, UserScore> unsortedUserScores = new HashMap<>();
//    userScores.asMap().forEach((k, v) -> unsortedUserScores.put(k, getMaximumScore(v).get()));
//    return unsortedUserScores;
//  }


  private Optional<UserScore> getMaximumScore(Collection<UserScore> v) {

    Optional<UserScore> max = v.stream().max(Comparator.comparing(item -> item.getScoreValue()));
    return max;
  }

  private void addUserScoreToLevel(Integer userId, Integer scoreValue, Level level) {
    UserScore userScore = new UserScore(userId, level.getLevelId(), scoreValue, clock.instant());
    level.addScoreForUser2(userScore);
  }

  private Level retrieveLevel(Integer levelId) {

    Level level = levels.get(levelId);
    if (level == null) {
      level = new Level(levelId);
      this.levels.put(levelId, level);
    }
    return level;
  }

}
