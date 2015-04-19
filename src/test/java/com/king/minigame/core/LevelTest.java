package com.king.minigame.core;

import org.testng.annotations.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class LevelTest {

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

  //This one works fine, just refactor and finish
  @Test(enabled = false)
  public void getMaximumScorePerUser() {

    Integer userId1 = 1;
    Integer userId2 = 2;
    Integer userId3 = 3;
    Integer userId4 = 4;
    Integer userId5 = 5;
    Integer userId6 = 6;
    Integer userId7 = 7;
    Integer userId8 = 8;
    Level level = new Level(1);
    Score score230 = new Score(230, Instant.now());
    Score score100 = new Score(100, Instant.now());
    Score score120 = new Score(120, Instant.now());
    Score score400 = new Score(400, Instant.now());
    level.addScoreForUser(userId1, score230);
    level.addScoreForUser(userId1, score100);
    level.addScoreForUser(userId2, score120);
    level.addScoreForUser(userId2, score120);
    level.addScoreForUser(userId3, score400);
    level.addScoreForUser(userId4, score100);
    level.addScoreForUser(userId4, score230);
    level.addScoreForUser(userId5, score120);
    level.addScoreForUser(userId6, score400);
    level.addScoreForUser(userId7, score100);
    level.addScoreForUser(userId8, score120);

    assertThat(level.getMaximumScorePerUser(), is(Arrays.asList()));
  }

//  public void getSortedSet() {
//
//    int numberOfUsersToCreate = 50;
//    int numberOfScoresPerUserToCreate = 10;
//
//    Integer userId1 = 1;
//    Integer userId2 = 2;
//    Integer userId3 = 3;
//    Integer userId4 = 4;
//    Integer userId5 = 5;
//    Integer userId6 = 6;
//    Integer userId7 = 7;
//    Integer userId8 = 8;
//    Level level = new Level(1);
//    Score score230 = new Score(230, Instant.now());
//    Score score100 = new Score(100, Instant.now());
//    Score score120 = new Score(120, Instant.now());
//    Score score400 = new Score(400, Instant.now());
//    level.addScoreForUser(userId1, score230);
//    level.addScoreForUser(userId1, score100);
//    level.addScoreForUser(userId2, score120);
//    level.addScoreForUser(userId2, score400);
//    level.addScoreForUser(userId3, score400);
//    level.addScoreForUser(userId4, score400);
//    level.addScoreForUser(userId4, score400);
//    level.addScoreForUser(userId5, score120);
//    level.addScoreForUser(userId6, score400);
//    level.addScoreForUser(userId7, score100);
//    level.addScoreForUser(userId8, score230);
//
//    //addScoresToLevel(level, numberOfUsersToCreate, numberOfScoresPerUserToCreate);
//
//    SortedSet<Score> sortedSetOfScores = level.getSortedSet();
//
//    assertThat(sortedSetOfScores.size(), is(lessThanOrEqualTo(15)));
//    assertThat(new ArrayList<>(sortedSetOfScores), is(Arrays.asList(score400, score230)));
//  }

  //TODO: use it??
  private void addScoresToLevel(Level level, int numberOfUsersToCreate, int numberOfScoresPerUserToCreate) {

    Integer userId = 1;

    for (int i = 0; i < numberOfUsersToCreate; i++) {
      Integer initialScoreValue = 100;
      generateScores(numberOfScoresPerUserToCreate, level, initialScoreValue, userId);
      userId++;
    }
  }

  private Integer generateScores(int numberOfScoresToCreate, Level level, Integer scoreValue,
                                 Integer userId) {

    for (int j = 0; j < numberOfScoresToCreate; j++) {
      Score score = new Score(scoreValue, Instant.now().minus(j, ChronoUnit.SECONDS));
      level.addScoreForUser(userId, score);
      scoreValue += 100;
    }
    return scoreValue;
  }


}
