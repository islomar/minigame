package com.king.minigame.score;

import org.testng.annotations.Test;

import java.time.Instant;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class LevelTest {

  public void getMaximumScore_should_return_the_max_score() {
    Level level = new Level(1);
    level.addScoreForUser(1, new Score(230, Instant.now()));
    level.addScoreForUser(1, new Score(100, Instant.now()));
    level.addScoreForUser(2, new Score(120, Instant.now()));

    assertThat(level.getMaximumScore2(), is(230));
  }

  public void getUserScores_should_return_all_user_scores_saved_grouped_by_user() {
    Integer userId1 = 1;
    Integer userId2 = 2;
    Level level = new Level(1);
    Score score1 = new Score(230, Instant.now());
    Score score2 = new Score(100, Instant.now());
    Score score3 = new Score(120, Instant.now());
    level.addScoreForUser(userId1, score1);
    level.addScoreForUser(userId1, score2);
    level.addScoreForUser(userId2, score3);

    assertTrue(level.getUserScores().get(userId1).contains(score1));
    assertTrue(level.getUserScores().get(userId1).contains(score2));
    assertFalse(level.getUserScores().get(userId1).contains(score3));
    assertTrue(level.getUserScores().get(userId2).contains(score3));
    assertFalse(level.getUserScores().get(userId2).contains(score1));
    assertFalse(level.getUserScores().get(userId2).contains(score2));
  }

  public void getOrderListOfScores_should_return_a_descending_list_of_scores() {
    Integer userId1 = 1;
    Integer userId2 = 2;
    Level level = new Level(1);
    Score score230 = new Score(230, Instant.now());
    Score score100 = new Score(100, Instant.now());
    Score score120 = new Score(120, Instant.now());
    level.addScoreForUser(userId1, score230);
    level.addScoreForUser(userId1, score100);
    level.addScoreForUser(userId2, score120);

    assertThat(level.getScoreListOrderedByValueDesc(), is(Arrays.asList(score230, score120, score100)));
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void throw_IllegalArgumentException_if_levelId_is_null() {
    Level level = new Level(null);
  }

  public void xxx() {
    Integer userId1 = 1;
    Integer userId2 = 2;
    Level level = new Level(1);
    Score score230 = new Score(230, Instant.now());
    Score score100 = new Score(100, Instant.now());
    Score score120 = new Score(120, Instant.now());
    Score score400 = new Score(400, Instant.now());
    level.addScoreForUser(userId1, score230);
    level.addScoreForUser(userId1, score100);
    level.addScoreForUser(userId2, score120);
    level.addScoreForUser(userId2, score400);

    System.out.println(level.getMaximumScoreForUser());
  }

}
