package com.king.minigame.score;

import java.time.Instant;

/**
 *
 */
public final class Score {

  private final Integer scoreValue;
  private final Instant creationTime;

  public Score(Integer scoreValue, Instant creationTime) {
    this.scoreValue = scoreValue;
    this.creationTime = creationTime;
  }

  public Integer getScoreValue() {
    return scoreValue;
  }

  public Instant getCreationTime() {
    return creationTime;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Score other = (Score) obj;

    return   com.google.common.base.Objects.equal(this.scoreValue, other.scoreValue)
             && com.google.common.base.Objects.equal(this.creationTime, other.creationTime);
  }

  @Override
  public int hashCode() {
    return com.google.common.base.Objects.hashCode(this.scoreValue, this.creationTime);
  }

  @Override
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
        .add("scoreValue", this.scoreValue)
        .add("creationTime", this.creationTime)
        .toString();
  }
}
