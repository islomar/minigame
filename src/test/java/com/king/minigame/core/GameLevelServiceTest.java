package com.king.minigame.core;

import com.king.minigame.core.model.User;
import com.king.minigame.core.model.UserScore;
import com.king.minigame.session.SessionService;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Clock;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
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

    assertTrue(gameLevelService.getHighScoreListForLevel2(levelId).isEmpty());
  }

  public void given_more_than_15_users_post_scores_then_getHighScoreListForLevel_returns_only_15_top_scores() {

    Integer levelId = 123;

    //GIVEN
    List<UserScore> expectedHighScoreList = givenMoreThan15UsersWhoPostScores(levelId);

    //WHEN
    List<UserScore> highScoreList = gameLevelService.getHighScoreListForLevel2(levelId);

    //THEN
    assertThat(highScoreList.size(), is(15));
    assertThat(highScoreList, is(expectedHighScoreList));
  }

  //TODO: refactor, please!!!
  private List<UserScore> givenMoreThan15UsersWhoPostScores(Integer levelId) {
    Integer anotherLevelId = 456;
    Integer scoreValue = 300;
    List<UserScore> expectedHighScoreList = new LinkedList<>();
    //TODO: hacer tests con sesión inválida, ver que no funciona
    when(sessionService.findUserBySessionkey("1")).thenReturn(Optional.of(new User(1)));

    when(sessionService.findUserBySessionkey("2")).thenReturn(Optional.of(new User(2)));
    when(sessionService.findUserBySessionkey("3")).thenReturn(Optional.of(new User(3)));
    when(sessionService.findUserBySessionkey("4")).thenReturn(Optional.of(new User(4)));
    when(sessionService.findUserBySessionkey("5")).thenReturn(Optional.of(new User(5)));
    when(sessionService.findUserBySessionkey("6")).thenReturn(Optional.of(new User(6)));
    when(sessionService.findUserBySessionkey("7")).thenReturn(Optional.of(new User(7)));
    when(sessionService.findUserBySessionkey("8")).thenReturn(Optional.of(new User(8)));
    when(sessionService.findUserBySessionkey("9")).thenReturn(Optional.of(new User(9)));
    when(sessionService.findUserBySessionkey("10")).thenReturn(Optional.of(new User(10)));
    when(sessionService.findUserBySessionkey("11")).thenReturn(Optional.of(new User(11)));
    when(sessionService.findUserBySessionkey("12")).thenReturn(Optional.of(new User(12)));
    when(sessionService.findUserBySessionkey("13")).thenReturn(Optional.of(new User(13)));
    when(sessionService.findUserBySessionkey("14")).thenReturn(Optional.of(new User(14)));
    when(sessionService.findUserBySessionkey("15")).thenReturn(Optional.of(new User(15)));
    when(sessionService.findUserBySessionkey("16")).thenReturn(Optional.of(new User(16)));

    expectedHighScoreList.add(new UserScore(3, VALID_LEVEL_ID, 888, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(7, VALID_LEVEL_ID, 888, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(13, VALID_LEVEL_ID, 888, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(14, VALID_LEVEL_ID, 888, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(15, VALID_LEVEL_ID, 888, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(16, VALID_LEVEL_ID, 888, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(10, VALID_LEVEL_ID, 310, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(1, VALID_LEVEL_ID, 300, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(4, VALID_LEVEL_ID, 300, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(5, VALID_LEVEL_ID, 120, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(2, VALID_LEVEL_ID, 100, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(6, VALID_LEVEL_ID, 99, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(8, VALID_LEVEL_ID, 77, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(11, VALID_LEVEL_ID, 66, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(12, VALID_LEVEL_ID, 25, FIXED_INSTANT));

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
    return expectedHighScoreList;
  }

}
