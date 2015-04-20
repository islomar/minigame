package com.king.minigame.controller;

import com.king.minigame.core.GameLevelService;
import com.king.minigame.core.model.UserScore;
import com.king.minigame.session.UserRepository;
import com.king.minigame.session.UserSessionRepository;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@Test
public class GameLevelControllerTest {

  private static final Integer LEVEL_ID = 123;
  @Mock
  GameLevelService gameLevelService;
  @Mock
  UserSessionRepository userSessionRepository;
  @Mock
  UserRepository userRepository;

  @InjectMocks
  private GameLevelController gameLevelController;

  @BeforeMethod
  public void setUp() {
    gameLevelController = new GameLevelController(gameLevelService, userSessionRepository, userRepository);
    initMocks(this);
  }

  public void calling_getHighScoreListForLevel_for_a_level_with_no_scores_returns_an_empty_string() {

    String highScoreListForLevel = gameLevelController.getHighScoreListForLevel(LEVEL_ID);

    assertThat(highScoreListForLevel, is(""));
  }

  public void calling_getHighScoreListForLevel_for_a_level_with_no_scores_returns_an_empty_string2() {

    //GIVEN
    String expectedHighScoreListInCsvFormat = "6=800,5=800,2=200,3=100";
    List<UserScore> expectedUserScoreList = generateExpectedUserScoreList();
    when(gameLevelService.getHighScoreListForLevel2(LEVEL_ID)).thenReturn(expectedUserScoreList);

    //WHEN
    String highScoreListForLevel = gameLevelController.getHighScoreListForLevel(LEVEL_ID);

    //THEN
    assertThat(highScoreListForLevel, is(expectedHighScoreListInCsvFormat));
  }

  private List<UserScore> generateExpectedUserScoreList() {

    List<UserScore> userScores = new LinkedList<>();
    userScores.add(new UserScore(6, 123, 800, Instant.now()));
    userScores.add(new UserScore(5, 123, 800, Instant.now()));
    userScores.add(new UserScore(2, 123, 200, Instant.now()));
    userScores.add(new UserScore(3, 123, 100, Instant.now()));
    return userScores;
  }

}
