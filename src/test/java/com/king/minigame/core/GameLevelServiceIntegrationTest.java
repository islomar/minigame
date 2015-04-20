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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

@Test
public class GameLevelServiceIntegrationTest {

  private static final Integer USER_ID_1 = 1;
  private static final Integer USER_ID_2 = 2;
  private static final String SESSION_KEY_1 = "cfa400a5-b7b4-460d-b4d6-374eb17053f1";
  private static final String SESSION_KEY_2 = "3c988c9f-971b-4ac9-9bdb-430306d0e6bc";
  private static final Integer LEVEL_ID = 123;
  private static final Integer ANOTHER_LEVEL_ID = 456;
  private static final Integer SCORE_VALUE_100 = 100;
  private static final Integer SCORE_VALUE_300 = 300;
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

    assertTrue(gameLevelService.getHighScoreListForLevel(LEVEL_ID).isEmpty());
  }

  public void given_one_user_posted_with_an_invalid_key_then_no_high_scores_exist() {

    //GIVEN
    when(sessionService.findUserBySessionkey(SESSION_KEY_1)).thenReturn(Optional.<User>empty());
    try {
      gameLevelService.postUserScoreToLevel(SESSION_KEY_1, LEVEL_ID, SCORE_VALUE_100);
      fail("It should have thrown an IllegalStateException because the session key does not exist");
    } catch (IllegalStateException ex) {
    }

    //WHEN
    List<UserScore> highScoreList = gameLevelService.getHighScoreListForLevel(LEVEL_ID);

    //THEN
    assertTrue(highScoreList.isEmpty());
  }

  public void given_one_user_posted_with_an_expired_sessionkey_then_no_high_scores_exist() {

    //GIVEN
    when(sessionService.findUserBySessionkey(SESSION_KEY_1)).thenThrow(IllegalStateException.class);
    try {
      gameLevelService.postUserScoreToLevel(SESSION_KEY_1, LEVEL_ID, SCORE_VALUE_100);
      fail("It should have thrown an IllegalStateException because the session key does not exist");
    } catch (IllegalStateException ex) {
    }

    //WHEN
    List<UserScore> highScoreList = gameLevelService.getHighScoreListForLevel(LEVEL_ID);

    //THEN
    assertTrue(highScoreList.isEmpty());
  }

  public void given_one_user_posted_two_scores_then_only_the_biggest_one_is_returned() {

    //GIVEN
    when(sessionService.findUserBySessionkey(SESSION_KEY_1)).thenReturn(Optional.of(new User(USER_ID_1)));
    gameLevelService.postUserScoreToLevel(SESSION_KEY_1, LEVEL_ID, SCORE_VALUE_100);
    gameLevelService.postUserScoreToLevel(SESSION_KEY_1, LEVEL_ID, SCORE_VALUE_300);

    //WHEN
    List<UserScore> highScoreList = gameLevelService.getHighScoreListForLevel(LEVEL_ID);

    //THEN
    UserScore expectedUserScore = new UserScore(USER_ID_1, LEVEL_ID, SCORE_VALUE_300, FIXED_INSTANT);
    assertThat(highScoreList, is(Arrays.asList(expectedUserScore)));
  }

  public void given_two_users_posted_valid_scores_then_getHighScoreListForLevel_recovers_them_successfully() {

    //GIVEN
    when(sessionService.findUserBySessionkey(SESSION_KEY_1)).thenReturn(Optional.of(new User(USER_ID_1)));
    when(sessionService.findUserBySessionkey(SESSION_KEY_2)).thenReturn(Optional.of(new User(USER_ID_2)));
    gameLevelService.postUserScoreToLevel(SESSION_KEY_1, LEVEL_ID, SCORE_VALUE_100);
    gameLevelService.postUserScoreToLevel(SESSION_KEY_1, LEVEL_ID, SCORE_VALUE_300);
    gameLevelService.postUserScoreToLevel(SESSION_KEY_2, LEVEL_ID, SCORE_VALUE_300);

    //WHEN
    List<UserScore> highScoreList = gameLevelService.getHighScoreListForLevel(LEVEL_ID);

    //THEN
    UserScore expectedUserScore1 = new UserScore(USER_ID_1, LEVEL_ID, SCORE_VALUE_300, FIXED_INSTANT);
    UserScore expectedUserScore2 = new UserScore(USER_ID_2, LEVEL_ID, SCORE_VALUE_300, FIXED_INSTANT);
    assertThat(highScoreList, is(Arrays.asList(expectedUserScore1, expectedUserScore2)));
  }

  public void given_more_than_15_users_post_scores_then_getHighScoreListForLevel_returns_only_15_top_scores() {

    //GIVEN
    List<UserScore> expectedHighScoreList = givenMoreThan15UsersWhoPostScoresForLevel(LEVEL_ID);

    //WHEN
    List<UserScore> highScoreList = gameLevelService.getHighScoreListForLevel(LEVEL_ID);

    //THEN
    assertThat(highScoreList.size(), is(GameLevelService.MAX_NUMBER_OF_SCORES_FOR_HIGHLIST));
    assertThat(highScoreList, is(expectedHighScoreList));
  }

  private List<UserScore> givenMoreThan15UsersWhoPostScoresForLevel(Integer levelId) {
    List<UserScore> expectedHighScoreList = new LinkedList<>();
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

    expectedHighScoreList.add(new UserScore(3, LEVEL_ID, 888, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(7, LEVEL_ID, 888, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(13, LEVEL_ID, 888, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(14, LEVEL_ID, 888, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(15, LEVEL_ID, 888, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(16, LEVEL_ID, 888, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(10, LEVEL_ID, 310, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(1, LEVEL_ID, 300, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(4, LEVEL_ID, 300, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(5, LEVEL_ID, 120, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(2, LEVEL_ID, 100, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(6, LEVEL_ID, 99, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(8, LEVEL_ID, 77, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(11, LEVEL_ID, 66, FIXED_INSTANT));
    expectedHighScoreList.add(new UserScore(12, LEVEL_ID, 25, FIXED_INSTANT));

    gameLevelService.postUserScoreToLevel("1", levelId, 300);
    gameLevelService.postUserScoreToLevel("1", levelId, 200);
    gameLevelService.postUserScoreToLevel("1", ANOTHER_LEVEL_ID, 50);
    gameLevelService.postUserScoreToLevel("1", levelId, 300);
    gameLevelService.postUserScoreToLevel("2", levelId, 100);
    gameLevelService.postUserScoreToLevel("3", levelId, 500);
    gameLevelService.postUserScoreToLevel("3", levelId, 888);
    gameLevelService.postUserScoreToLevel("4", levelId, 300);
    gameLevelService.postUserScoreToLevel("4", levelId, 150);
    gameLevelService.postUserScoreToLevel("4", ANOTHER_LEVEL_ID, 99999);
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
