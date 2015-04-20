package com.king.minigame.core.model;

import java.time.Instant;

/**
 *  Class responsible for storing a score value and the moment when it happened.
 */
public final class UserScore implements Comparable{

  private final Integer userId;
  private final Integer scoreValue;
  private final Integer levelId;
  private final Instant creationTime;

  public UserScore(final Integer userId, final Integer levelId, final Integer scoreValue, final Instant creationTime) {

    validateParameters(userId, scoreValue, levelId, creationTime);

    this.userId = userId;
    this.scoreValue = scoreValue;
    this.levelId = levelId;
    this.creationTime = creationTime;
  }

  public Integer getUserId() {
    return userId;
  }

  public Integer getLevelId() {
    return levelId;
  }

  public Integer getScoreValue() {
    return scoreValue;
  }

  public Instant getCreationTime() {
    return creationTime;
  }

  private void validateParameters(Integer userId, Integer scoreValue, Integer levelId, Instant creationTime) {

    if (userId == null || levelId == null || scoreValue == null || scoreValue < 0 || creationTime == null) {
      throw new IllegalArgumentException("null values are not allowed");
    }
    if (scoreValue < 0 || userId < 0 || levelId < 0) {
      throw new IllegalArgumentException("Score, userId and levelId should be positive numbers");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserScore userScore = (UserScore) o;

    if (creationTime != null ? !creationTime.equals(userScore.creationTime) : userScore.creationTime != null) {
      return false;
    }
    if (levelId != null ? !levelId.equals(userScore.levelId) : userScore.levelId != null) {
      return false;
    }
    if (scoreValue != null ? !scoreValue.equals(userScore.scoreValue) : userScore.scoreValue != null) {
      return false;
    }
    if (userId != null ? !userId.equals(userScore.userId) : userScore.userId != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = userId != null ? userId.hashCode() : 0;
    result = 31 * result + (scoreValue != null ? scoreValue.hashCode() : 0);
    result = 31 * result + (levelId != null ? levelId.hashCode() : 0);
    result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return String.format("UserScore{userId=%d, levelId=%d, scoreValue=%d, creationTime=%s}", userId, levelId, scoreValue, creationTime);
  }

  @Override
  public int compareTo(Object o) {
    UserScore otherUserScore = (UserScore)o;
    return otherUserScore.getScoreValue() - this.getScoreValue();
  }
}
