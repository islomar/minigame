package com.king.minigame.core;

import org.testng.annotations.Test;

import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Test
public class ScoreTest {

  public void constructor_and_getters_work_fine() {

    Instant creationTime = Instant.now();
    Score score100 = new Score(100, creationTime);

    assertThat(score100.getScoreValue(), is(100));
    assertThat(score100.getCreationTime(), is(creationTime));
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void when_creating_new_score_throw_IllegalArgumentException_if_scoreValue_is_null() {
    new Score(null, Instant.now());
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void when_creating_new_score_throw_IllegalArgumentException_if_creationTime_is_null() {
    new Score(100, null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void when_creating_new_score_throw_IllegalArgumentException_if_both_values_are_null() {
    new Score(null, null);
  }
}
