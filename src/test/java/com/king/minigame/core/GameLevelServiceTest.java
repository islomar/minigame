package com.king.minigame.core;

import com.king.minigame.session.SessionService;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Clock;
import java.time.Instant;
import java.util.LinkedHashMap;
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

  private static final Integer VALID_USER_ID = 1;
  private static final Integer VALID_LEVEL_ID = 123;
  private static final Integer VALID_SCORE_VALUE = 100;
  private static final Instant FIXED_INSTANT = Instant.parse("2015-01-01T10:15:30.00Z");

  @Mock
  SessionService sessionService;

  @Mock
  private Clock clockMock;

  @InjectMocks
  GameLevelService gameLevelService;

  @BeforeMethod
  public void setUp() {

    gameLevelService = new GameLevelService(sessionService, clockMock);
    initMocks(this);

    when(clockMock.instant()).thenReturn(FIXED_INSTANT);
  }

  public void should_return_empty_map_if_level_has_no_scores() {

    Integer userId = 1;
    Integer levelId = 123;
    Integer scoreValue = 300;

    //TODO: hacer tests con sesión inválida, ver que no funciona
    when(sessionService.hasUserValidSessionKey(anyInt())).thenReturn(true);

    assertTrue(gameLevelService.getHighScoreListForLevel(levelId).isEmpty());
  }

  @Test(enabled = false)
  public void given_more_than_15_users_post_scores_then_getHighScoreListForLevel_returns_only_15_top_scores() {

    Integer levelId = 123;

    //GIVEN
    Map<Integer, UserScore> expectedUserScoreMap = givenMoreThan15UsersWhoPostScores(levelId);

    //WHEN
    Map<Integer, UserScore> highScoreList = gameLevelService.getHighScoreListForLevel(levelId);

    //THEN
    assertThat(highScoreList.size(), is(15));
    assertThat(highScoreList, is(expectedUserScoreMap));
  }

  //TODO: refactor, please!!!
  private Map<Integer, UserScore> givenMoreThan15UsersWhoPostScores(Integer levelId) {
    Integer anotherLevelId = 456;
    Integer scoreValue = 300;
    Map<Integer, UserScore> expectedUserScoreMap = new LinkedHashMap<>();
    //TODO: hacer tests con sesión inválida, ver que no funciona
    when(sessionService.getUserIdForSessionKey("1")).thenReturn(Optional.of(1));
    when(sessionService.findUserBySessionkey("1")).thenReturn(Optional.of(new User(1)));

    when(sessionService.getUserIdForSessionKey("2")).thenReturn(Optional.of(2));
    when(sessionService.findUserBySessionkey("2")).thenReturn(Optional.of(new User(2)));
    when(sessionService.getUserIdForSessionKey("3")).thenReturn(Optional.of(3));
    when(sessionService.findUserBySessionkey("3")).thenReturn(Optional.of(new User(3)));
    when(sessionService.getUserIdForSessionKey("4")).thenReturn(Optional.of(4));
    when(sessionService.findUserBySessionkey("4")).thenReturn(Optional.of(new User(4)));
    when(sessionService.getUserIdForSessionKey("5")).thenReturn(Optional.of(5));
    when(sessionService.findUserBySessionkey("5")).thenReturn(Optional.of(new User(5)));
    when(sessionService.getUserIdForSessionKey("6")).thenReturn(Optional.of(6));
    when(sessionService.findUserBySessionkey("6")).thenReturn(Optional.of(new User(6)));
    when(sessionService.getUserIdForSessionKey("7")).thenReturn(Optional.of(7));
    when(sessionService.findUserBySessionkey("7")).thenReturn(Optional.of(new User(7)));
    when(sessionService.getUserIdForSessionKey("8")).thenReturn(Optional.of(8));
    when(sessionService.findUserBySessionkey("8")).thenReturn(Optional.of(new User(8)));
    when(sessionService.getUserIdForSessionKey("9")).thenReturn(Optional.of(9));
    when(sessionService.findUserBySessionkey("9")).thenReturn(Optional.of(new User(9)));
    when(sessionService.getUserIdForSessionKey("10")).thenReturn(Optional.of(10));
    when(sessionService.findUserBySessionkey("10")).thenReturn(Optional.of(new User(10)));
    when(sessionService.getUserIdForSessionKey("11")).thenReturn(Optional.of(11));
    when(sessionService.findUserBySessionkey("11")).thenReturn(Optional.of(new User(11)));
    when(sessionService.getUserIdForSessionKey("12")).thenReturn(Optional.of(12));
    when(sessionService.findUserBySessionkey("12")).thenReturn(Optional.of(new User(12)));
    when(sessionService.getUserIdForSessionKey("13")).thenReturn(Optional.of(13));
    when(sessionService.findUserBySessionkey("13")).thenReturn(Optional.of(new User(13)));
    when(sessionService.getUserIdForSessionKey("14")).thenReturn(Optional.of(14));
    when(sessionService.findUserBySessionkey("14")).thenReturn(Optional.of(new User(14)));
    when(sessionService.getUserIdForSessionKey("15")).thenReturn(Optional.of(15));
    when(sessionService.findUserBySessionkey("15")).thenReturn(Optional.of(new User(15)));
    when(sessionService.getUserIdForSessionKey("16")).thenReturn(Optional.of(16));
    when(sessionService.findUserBySessionkey("16")).thenReturn(Optional.of(new User(16)));

    expectedUserScoreMap.put(3, new UserScore(VALID_USER_ID, VALID_LEVEL_ID, 888, FIXED_INSTANT));
    expectedUserScoreMap.put(7, new UserScore(VALID_USER_ID, VALID_LEVEL_ID, 888, FIXED_INSTANT));
    expectedUserScoreMap.put(13, new UserScore(VALID_USER_ID, VALID_LEVEL_ID, 888, FIXED_INSTANT));
    expectedUserScoreMap.put(14, new UserScore(VALID_USER_ID, VALID_LEVEL_ID, 888, FIXED_INSTANT));
    expectedUserScoreMap.put(15, new UserScore(VALID_USER_ID, VALID_LEVEL_ID, 888, FIXED_INSTANT));
    expectedUserScoreMap.put(16, new UserScore(VALID_USER_ID, VALID_LEVEL_ID, 888, FIXED_INSTANT));
    expectedUserScoreMap.put(10, new UserScore(VALID_USER_ID, VALID_LEVEL_ID, 310, FIXED_INSTANT));
    expectedUserScoreMap.put(1, new UserScore(VALID_USER_ID, VALID_LEVEL_ID, 300, FIXED_INSTANT));
    expectedUserScoreMap.put(4, new UserScore(VALID_USER_ID, VALID_LEVEL_ID, 300, FIXED_INSTANT));
    expectedUserScoreMap.put(5, new UserScore(VALID_USER_ID, VALID_LEVEL_ID, 120, FIXED_INSTANT));
    expectedUserScoreMap.put(2, new UserScore(VALID_USER_ID, VALID_LEVEL_ID, 100, FIXED_INSTANT));
    expectedUserScoreMap.put(6, new UserScore(VALID_USER_ID, VALID_LEVEL_ID, 99, FIXED_INSTANT));
    expectedUserScoreMap.put(8, new UserScore(VALID_USER_ID, VALID_LEVEL_ID, 77, FIXED_INSTANT));
    expectedUserScoreMap.put(11, new UserScore(VALID_USER_ID, VALID_LEVEL_ID, 66, FIXED_INSTANT));
    expectedUserScoreMap.put(12, new UserScore(VALID_USER_ID, VALID_LEVEL_ID, 25, FIXED_INSTANT));

    gameLevelService.postUserScoreToLevel("1", levelId, 300);
    gameLevelService.postUserScoreToLevel("1", levelId, 200);
    gameLevelService.postUserScoreToLevel("1", anotherLevelId, 50);
    gameLevelService.postUserScoreToLevel("1", levelId, 300);
    gameLevelService.postUserScoreToLevel("2", levelId, 100);
    gameLevelService.postUserScoreToLevel("3", levelId, 500);
    gameLevelService.postUserScoreToLevel("3", levelId, 888);
    gameLevelService.postUserScoreToLevel("4", levelId, 300);
    gameLevelService.postUserScoreToLevel("4", levelId, 150);
    gameLevelService.postUserScoreToLevel("4", anotherLevelId, 99999);
    gameLevelService.postUserScoreToLevel("5", levelId, 120);
    gameLevelService.postUserScoreToLevel("6", levelId, 99);
    gameLevelService.postUserScoreToLevel("7", levelId, 888);
    gameLevelService.postUserScoreToLevel("8", levelId, 77);
    gameLevelService.postUserScoreToLevel("9", levelId, 12);
    gameLevelService.postUserScoreToLevel("10", levelId, 300);
    gameLevelService.postUserScoreToLevel("10", levelId, 310);
    gameLevelService.postUserScoreToLevel("11", levelId, 66);
    gameLevelService.postUserScoreToLevel("12", levelId, 25);
    gameLevelService.postUserScoreToLevel("13", levelId, 888);
    gameLevelService.postUserScoreToLevel("14", levelId, 888);
    gameLevelService.postUserScoreToLevel("15", levelId, 888);
    gameLevelService.postUserScoreToLevel("16", levelId, 888);
    return expectedUserScoreMap;
  }

}
