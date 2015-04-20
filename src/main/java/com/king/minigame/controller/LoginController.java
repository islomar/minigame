package com.king.minigame.controller;

import com.king.minigame.session.UserRepository;
import com.king.minigame.session.UserSessionRepository;
import com.king.minigame.session.SessionService;

import java.time.Clock;

/**
 *
 */
public class LoginController {

  private SessionService sessionService;

  public LoginController(UserSessionRepository userSessionRepository, UserRepository userRepository) {
    sessionService = new SessionService(Clock.systemUTC(), userSessionRepository, userRepository);
  }

  public String login(Integer userId) {

    String sessionKey = sessionService.login(userId);
    return sessionKey;
  }

}
