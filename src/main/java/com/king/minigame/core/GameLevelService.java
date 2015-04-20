package com.king.minigame.core;

import com.king.minigame.core.model.Level;
import com.king.minigame.core.model.User;
import com.king.minigame.core.model.UserScore;
import com.king.minigame.session.SessionService;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * Class responsible for actions related to a Level, like posting scores or retrieving the highest scores.
 */
public class GameLevelService {

  public static final int MAX_NUMBER_OF_SCORES_FOR_HIGHLIST = 15;
  private static final Comparator<UserScore> SCORE_COMPARATOR = Comparator.comparing(UserScore::getScoreValue);

  private Map<Integer, Level> levels;
  private Clock clock;

  private SessionService sessionService;


  public GameLevelService(SessionService sessionService, Clock clock) {
    levels = new ConcurrentHashMap<>();
    this.sessionService = sessionService;
    this.clock = clock;
  }

  public void postUserScoreToLevel(String sessionKey, Integer levelId, Integer scoreValue) {

    Optional<User> user = sessionService.findUserBySessionkey(sessionKey);
    if (!user.isPresent()) {
      throw new IllegalStateException("The sessionkey " + sessionKey + " is not active currently.");
    }

    Level level = retrieveLevel(levelId);
    addUserScoreToLevel(user.get().getUserId(), scoreValue, level);

  }

  /**
   * Currently limited to {@link com.king.minigame.core.GameLevelService#MAX_NUMBER_OF_SCORES_FOR_HIGHLIST}.
   *
   * @return - sorted list of {@link com.king.minigame.core.model.UserScore}
   */
  public List<UserScore> getHighScoreListForLevel(Integer levelId) {

    Level level = levels.get(levelId);
    if (level == null) {
      return new ArrayList<>();
    } else {
      return getMaximumScorePerUserForLevel(level);
    }
  }


  private List<UserScore> getMaximumScorePerUserForLevel(Level level) {

    List<UserScore> allUserScores = level.getAllUserScores();

    Map<Integer, Optional<UserScore>> mapUserIdToMaxUserScore = allUserScores
        .parallelStream()
        .collect(Collectors.groupingBy(UserScore::getUserId, Collectors.reducing(BinaryOperator.maxBy(SCORE_COMPARATOR))));

    List<UserScore> highUserScoreList = mapUserIdToMaxUserScore.entrySet()
        .parallelStream()
        .map(s -> s.getValue().get())
        .sorted()
        .limit(MAX_NUMBER_OF_SCORES_FOR_HIGHLIST)
        .collect(Collectors.toList());

    return highUserScoreList;
  }


  private void addUserScoreToLevel(Integer userId, Integer scoreValue, Level level) {
    UserScore userScore = new UserScore(userId, level.getLevelId(), scoreValue, clock.instant());
    level.addScoreForUser(userScore);
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
