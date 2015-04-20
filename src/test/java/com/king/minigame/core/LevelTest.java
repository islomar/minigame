package com.king.minigame.core;

import com.google.common.collect.ListMultimap;

import org.testng.annotations.Test;

import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class LevelTest {

  public void getAllUserScores_should_return_all_user_scores_saved_grouped_by_user() {

    //GIVEN
    Integer userId1 = 1;
    Integer userId2 = 2;
    Level level = new Level(1);
    Score score1 = new Score(230, Instant.now());
    Score score2 = new Score(100, Instant.now());
    Score score3 = new Score(120, Instant.now());
    level.addScoreForUser(userId1, score1);
    level.addScoreForUser(userId1, score2);
    level.addScoreForUser(userId2, score3);

    //WHEN
    ListMultimap<Integer, Score> allUserScores = level.getAllUserScores();

    //THEN
    assertThat(allUserScores.size(), is(3));
    assertTrue(allUserScores.get(userId1).contains(score1));
    assertTrue(allUserScores.get(userId1).contains(score2));
    assertFalse(allUserScores.get(userId1).contains(score3));
    assertTrue(allUserScores.get(userId2).contains(score3));
    assertFalse(allUserScores.get(userId2).contains(score1));
    assertFalse(allUserScores.get(userId2).contains(score2));
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void throw_IllegalArgumentException_if_levelId_is_null() {

    Level level = new Level(null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void throw_IllegalArgumentException_if_levelId_is_negative() {

    Level level = new Level(-1);
  }

}
