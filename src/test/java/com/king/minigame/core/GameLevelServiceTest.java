package com.king.minigame.core;

import com.king.minigame.session.SessionService;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertTrue;

@Test
public class GameLevelServiceTest {

  @Mock
  SessionService sessionService;

  @InjectMocks
  GameLevelService gameLevelService;

  @BeforeMethod
  public void setUp() {

    gameLevelService = new GameLevelService(sessionService);
    initMocks(this);
  }

  public void should_return_empty_map_if_level_has_no_scores() {

    Integer userId = 1;
    Integer levelId = 123;
    Integer scoreValue = 300;

    //TODO: hacer tests con sesión inválida, ver que no funciona
    when(sessionService.hasUserValidSessionKey(anyInt())).thenReturn(true);

    assertTrue(gameLevelService.getHighScoreListForLevel(levelId).isEmpty());
  }

  public void xx1() {

  }

  //PENDING TO MAKE IT WORK
  @Test(enabled = false)
  public void xxx() {

    Integer levelId = 123;
    Integer anotherLevelId = 456;
    Integer scoreValue = 300;

    Map<Integer, Score> expectedUserScoreMap = new HashMap<>();

    //TODO: hacer tests con sesión inválida, ver que no funciona
    when(sessionService.getUserIdForSessionKey("1")).thenReturn(Optional.of(1));
    when(sessionService.getUserIdForSessionKey("2")).thenReturn(Optional.of(2));
    when(sessionService.getUserIdForSessionKey("3")).thenReturn(Optional.of(3));
    when(sessionService.getUserIdForSessionKey("4")).thenReturn(Optional.of(4));
    when(sessionService.getUserIdForSessionKey("5")).thenReturn(Optional.of(5));
    when(sessionService.getUserIdForSessionKey("6")).thenReturn(Optional.of(6));
    when(sessionService.getUserIdForSessionKey("7")).thenReturn(Optional.of(7));
    when(sessionService.getUserIdForSessionKey("8")).thenReturn(Optional.of(8));
    when(sessionService.getUserIdForSessionKey("9")).thenReturn(Optional.of(9));
    when(sessionService.getUserIdForSessionKey("10")).thenReturn(Optional.of(10));
    when(sessionService.getUserIdForSessionKey("11")).thenReturn(Optional.of(11));
    when(sessionService.getUserIdForSessionKey("12")).thenReturn(Optional.of(12));
    when(sessionService.getUserIdForSessionKey("13")).thenReturn(Optional.of(13));
    when(sessionService.getUserIdForSessionKey("14")).thenReturn(Optional.of(14));
    when(sessionService.getUserIdForSessionKey("15")).thenReturn(Optional.of(15));
    when(sessionService.getUserIdForSessionKey("16")).thenReturn(Optional.of(16));

    gameLevelService.postUserScoreToLevel("1", levelId, 300);
    gameLevelService.postUserScoreToLevel("1", levelId, 200);
    gameLevelService.postUserScoreToLevel("1", anotherLevelId, 50);
    gameLevelService.postUserScoreToLevel("1", levelId, 300);
    expectedUserScoreMap.put(1, new Score(300, Instant.now()));

    gameLevelService.postUserScoreToLevel("2", levelId, 100);
    expectedUserScoreMap.put(2, new Score(100, Instant.now()));

    gameLevelService.postUserScoreToLevel("3", levelId, 500);
    gameLevelService.postUserScoreToLevel("3", levelId, 888);
    expectedUserScoreMap.put(3, new Score(888, Instant.now()));

    gameLevelService.postUserScoreToLevel("4", levelId, 150);
    gameLevelService.postUserScoreToLevel("4", anotherLevelId, 99999);
    expectedUserScoreMap.put(4, new Score(150, Instant.now()));

    gameLevelService.postUserScoreToLevel("5", levelId, 120);
    expectedUserScoreMap.put(5, new Score(120, Instant.now()));

    gameLevelService.postUserScoreToLevel("6", levelId, 99);
    expectedUserScoreMap.put(6, new Score(99, Instant.now()));

    gameLevelService.postUserScoreToLevel("7", levelId, 300);
    expectedUserScoreMap.put(7, new Score(888, Instant.now()));

    gameLevelService.postUserScoreToLevel("8", levelId, 77);
    expectedUserScoreMap.put(8, new Score(77, Instant.now()));

    gameLevelService.postUserScoreToLevel("9", levelId, 12);
    expectedUserScoreMap.put(9, new Score(12, Instant.now()));

    gameLevelService.postUserScoreToLevel("10", levelId, 300);
    gameLevelService.postUserScoreToLevel("10", levelId, 310);
    expectedUserScoreMap.put(10, new Score(310, Instant.now()));

    gameLevelService.postUserScoreToLevel("11", levelId, 66);
    expectedUserScoreMap.put(11, new Score(66, Instant.now()));

    gameLevelService.postUserScoreToLevel("12", levelId, 25);
    expectedUserScoreMap.put(12, new Score(25, Instant.now()));

    gameLevelService.postUserScoreToLevel("13", levelId, 300);
    expectedUserScoreMap.put(13, new Score(888, Instant.now()));

    gameLevelService.postUserScoreToLevel("14", levelId, 300);
    expectedUserScoreMap.put(14, new Score(888, Instant.now()));

    gameLevelService.postUserScoreToLevel("15", levelId, 300);
    expectedUserScoreMap.put(15, new Score(888, Instant.now()));

    gameLevelService.postUserScoreToLevel("16", levelId, 300);
    expectedUserScoreMap.put(16, new Score(888, Instant.now()));

    gameLevelService.postUserScoreToLevel("4", levelId, 300);

    assertThat(gameLevelService.getHighScoreListForLevel(levelId).size(), is(3));
    assertThat(gameLevelService.getHighScoreListForLevel(levelId), is(expectedUserScoreMap));
  }


  private void postUserScoresToLevel(Level level, int numberOfUsersToCreate, int numberOfScoresPerUserToCreate) {

    Integer userId = 1;

    for (int i = 0; i < numberOfUsersToCreate; i++) {
      Integer initialScoreValue = 100;
      generateScores(numberOfScoresPerUserToCreate, level.getLevelId(), initialScoreValue, userId);
      userId++;
    }
  }

  private Integer generateScores(int numberOfScoresToCreate, Integer levelId, Integer scoreValue,
                                 Integer userId) {

    for (int j = 0; j < numberOfScoresToCreate; j++) {
      gameLevelService.postUserScoreToLevel("ABC", levelId, scoreValue);
      scoreValue += 100;
    }
    return scoreValue;
  }

//
//  //TODO: use it??
//  private void addScoresToLevel(Level level, int numberOfUsersToCreate, int numberOfScoresPerUserToCreate) {
//
//    Integer userId = 1;
//
//    for (int i = 0; i < numberOfUsersToCreate; i++) {
//      Integer initialScoreValue = 100;
//      generateScores(numberOfScoresPerUserToCreate, level, initialScoreValue, userId);
//      userId++;
//    }
//  }
//
//  private Integer generateScores(int numberOfScoresToCreate, Level level, Integer scoreValue,
//                                 Integer userId) {
//
//    for (int j = 0; j < numberOfScoresToCreate; j++) {
//      Score score = new Score(scoreValue, Instant.now().minus(j, ChronoUnit.SECONDS));
//      //level.addScoreForUser(userId, score);
//      scoreValue += 100;
//    }
//    return scoreValue;
//  }

}
