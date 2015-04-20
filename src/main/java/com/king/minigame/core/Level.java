package com.king.minigame.core;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class representing a level and all its associated user scores.
 */
public class Level {

  private final Integer levelId;
  private ListMultimap<Integer, UserScore> userScores;
  private List<UserScore> userScoreList;

  public Level(final Integer levelId) {

    validateParameters(levelId);
    userScores = ArrayListMultimap.create();
    userScoreList = new ArrayList<>();
    this.levelId = levelId;
  }

  public int getLevelId() {

    return this.levelId;
  }

  public ListMultimap<Integer, UserScore> getAllUserScores() {

    return ImmutableListMultimap.copyOf(userScores);
  }

  public void addScoreForUser(Integer userId, UserScore userScore) {

    validateUserScoreParameters(userId, userScore);

    userScores.put(userId, userScore);
  }

  public void addScoreForUser2(UserScore userScore) {

    userScoreList.add(userScore);
  }


  private void validateUserScoreParameters(Integer userId, UserScore userScore) {

    if (userId == null || userScore == null) {
      throw new IllegalArgumentException("null values are not allowed");
    }
  }

  private void validateParameters(Integer levelId) {

    if (levelId == null) {
      throw new IllegalArgumentException("level id can not be null");
    }
    if (levelId < 0) {
      throw new IllegalArgumentException("The level id should be a positive number");
    }
  }

}
