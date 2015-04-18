package com.king.minigame.score;

import com.king.minigame.session.SessionService;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import jdk.nashorn.internal.ir.annotations.Ignore;

@Test
public class ScoreServiceTest {

  ScoreService scoreService;

  @Mock
  SessionService sessionService;

  @BeforeMethod
  public void setUp() {
    scoreService = new ScoreService(sessionService);
  }

  @Ignore
  public void xxx() {
     //scoreService.saveScoreFor(1, "levelId1", 1);
  }

}
