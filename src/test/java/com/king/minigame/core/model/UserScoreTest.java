package com.king.minigame.core.model;

import com.king.minigame.core.model.UserScore;

import org.testng.annotations.Test;

import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Test
public class UserScoreTest {

  private static final Integer VALID_USER_ID = 1;
  private static final Integer VALID_LEVEL_ID = 123;
  private static final Integer VALID_SCORE_VALUE = 100;

  public void constructor_and_getters_work_fine() {

    Instant creationTime = Instant.now();
    UserScore userScore100 = new UserScore(VALID_USER_ID, VALID_LEVEL_ID, VALID_SCORE_VALUE, creationTime);

    assertThat(userScore100.getUserId(), is(VALID_USER_ID));
    assertThat(userScore100.getLevelId(), is(VALID_LEVEL_ID));
    assertThat(userScore100.getScoreValue(), is(VALID_SCORE_VALUE));
    assertThat(userScore100.getCreationTime(), is(creationTime));
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void when_creating_new_score_throw_IllegalArgumentException_if_userId_is_null() {
    new UserScore(null, VALID_LEVEL_ID, VALID_SCORE_VALUE, Instant.now());
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void when_creating_new_score_throw_IllegalArgumentException_if_userId_is_negative() {
    new UserScore(-1, VALID_LEVEL_ID, VALID_SCORE_VALUE, Instant.now());
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void when_creating_new_score_throw_IllegalArgumentException_if_levelId_is_null() {
    new UserScore(VALID_USER_ID, null, VALID_SCORE_VALUE, Instant.now());
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void when_creating_new_score_throw_IllegalArgumentException_if_levelId_is_negative() {
    new UserScore(VALID_USER_ID, -1, VALID_SCORE_VALUE, Instant.now());
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void when_creating_new_score_throw_IllegalArgumentException_if_scoreValue_is_null() {
    new UserScore(VALID_USER_ID, VALID_LEVEL_ID, null, Instant.now());
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void when_creating_new_score_throw_IllegalArgumentException_if_scoreValue_is_negative() {
    new UserScore(VALID_USER_ID, VALID_LEVEL_ID, -1, Instant.now());
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void when_creating_new_score_throw_IllegalArgumentException_if_creationTime_is_null() {
    new UserScore(VALID_USER_ID, VALID_LEVEL_ID, VALID_SCORE_VALUE, null);
  }


}
