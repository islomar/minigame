package com.king.minigame.core.model;

import com.king.minigame.core.model.Level;

import org.testng.annotations.Test;

@Test
public class LevelTest {

  private static final Integer VALID_USER_ID = 1;
  private static final Integer VALID_LEVEL_ID = 123;
  private static final Integer VALID_SCORE_VALUE = 100;

  public void getAllUserScores_should_return_all_user_scores_saved_grouped_by_user() {

    //GIVEN
//    Integer userId1 = 1;
//    Integer userId2 = 2;
//    Level level = new Level(1);
//    UserScore userScore1 = new UserScore(VALID_USER_ID, VALID_LEVEL_ID, 230, Instant.now());
//    UserScore userScore2 = new UserScore(VALID_USER_ID, VALID_LEVEL_ID, 100, Instant.now());
//    UserScore userScore3 = new UserScore(VALID_USER_ID, VALID_LEVEL_ID, 120, Instant.now());
//    level.addScoreForUser(userId1, userScore1);
//    level.addScoreForUser(userId1, userScore2);
//    level.addScoreForUser(userId2, userScore3);
//
//    //WHEN
//    ListMultimap<Integer, UserScore> allUserScores = level.getAllUserScores();
//
//    //THEN
//    assertThat(allUserScores.size(), is(3));
//    assertTrue(allUserScores.get(userId1).contains(userScore1));
//    assertTrue(allUserScores.get(userId1).contains(userScore2));
//    assertFalse(allUserScores.get(userId1).contains(userScore3));
//    assertTrue(allUserScores.get(userId2).contains(userScore3));
//    assertFalse(allUserScores.get(userId2).contains(userScore1));
//    assertFalse(allUserScores.get(userId2).contains(userScore2));
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
