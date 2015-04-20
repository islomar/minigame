package com.king.minigame.core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing a level and all its associated user scores.
 */
public class Level {

  private final Integer levelId;
  private List<UserScore> userScoreList;

  public Level(final Integer levelId) {

    validateParameters(levelId);
    userScoreList = new ArrayList<>();
    this.levelId = levelId;
  }

  public int getLevelId() {

    return this.levelId;
  }

  public List<UserScore> getAllUserScores() {

    return Collections.synchronizedList(userScoreList);
  }

  public void addScoreForUser(UserScore userScore) {

    validateUserScoreParameters(userScore);
    userScoreList.add(userScore);
  }


  private void validateUserScoreParameters(UserScore userScore) {

    if (userScore == null) {
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
