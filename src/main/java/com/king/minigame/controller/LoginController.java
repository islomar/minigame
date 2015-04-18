package com.king.minigame.controller;

import com.king.minigame.session.SessionService;

import java.time.Clock;

/**
 *
 */
public class LoginController {

  private SessionService sessionService;

  public LoginController() {
    sessionService = new SessionService(Clock.systemUTC());
  }

  public String login(Integer userId) {

    String sessionKey = sessionService.login(userId);
    return sessionKey;
  }

}
