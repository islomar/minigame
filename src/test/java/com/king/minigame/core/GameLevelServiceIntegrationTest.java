package com.king.minigame.core;

import com.king.minigame.session.SessionService;
import com.king.minigame.session.UserRepository;
import com.king.minigame.session.UserSessionRepository;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * Integration test for GameLevelService.
 */
@Test
public class GameLevelServiceIntegrationTest {

  @Mock
  private Clock clockMock;

  @InjectMocks
  GameLevelService gameLevelService;


  @BeforeMethod
  public void setUp() {

    SessionService sessionService = new SessionService(clockMock, new UserSessionRepository(), new UserRepository());
    gameLevelService = new GameLevelService(sessionService, clockMock);
    initMocks(this);
  }



  private void setTimeToNow() {
    when(clockMock.instant()).thenReturn(Instant.now());
  }


  private void setTimeMinutesAgo(int minutesAgo) {

    Instant instantAgo = Instant.now().minus(minutesAgo, ChronoUnit.MINUTES);
    when(clockMock.instant()).thenReturn(instantAgo);
  }

}
