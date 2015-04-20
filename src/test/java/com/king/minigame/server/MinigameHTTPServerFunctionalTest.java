package com.king.minigame.server;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class MinigameHTTPServerFunctionalTest {

  public void xx() {
    MinigameHTTPServer minigameHTTPServer = new MinigameHTTPServer();
    minigameHTTPServer.startUp();
  }

}
