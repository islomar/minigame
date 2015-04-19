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

  //PENDING TO MAKE IT WORK
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

    gameLevelService.postUserScoreToList("1", levelId, 300);
    expectedUserScoreMap.put(1, new Score(300, Instant.now()));

    gameLevelService.postUserScoreToList("1", levelId, 200);

    gameLevelService.postUserScoreToList("2", levelId, 100);
    expectedUserScoreMap.put(2, new Score(100, Instant.now()));

    gameLevelService.postUserScoreToList("3", levelId, 500);

    gameLevelService.postUserScoreToList("3", levelId, 888);
    expectedUserScoreMap.put(3, new Score(888, Instant.now()));

    gameLevelService.postUserScoreToList("4", anotherLevelId, 300);


    assertThat(gameLevelService.getHighScoreListForLevel(levelId).size(), is(3));
    assertThat(gameLevelService.getHighScoreListForLevel(levelId), is(expectedUserScoreMap));
  }

}
