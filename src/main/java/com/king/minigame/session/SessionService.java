package com.king.minigame.session;

import com.king.minigame.core.User;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

/**
 * Class responsible for login users and querying if a user session is still active.
 */
public class SessionService {

  private final static int SESSION_TIMEOUT_IN_MINUTES = 10;
  private UserSessionRepository userSessionRepository;
  private UserRepository userRepository;

  private Clock clock;

  public SessionService(final Clock clock, UserSessionRepository userSessionRepository, UserRepository userRepository) {

    this.userSessionRepository = userSessionRepository;
    this.userRepository = userRepository;
    this.clock = clock;
  }

  public String login(Integer userId) {

    Optional<String> activeSessionKeyForUser = getActiveSessionKeyForUser(userId);

    if (activeSessionKeyForUser.isPresent()) {
      return activeSessionKeyForUser.get();
    } else {
      createUserIfDoesNotExist(userId);
      UserSession newUserSession = createUserSession();
      saveUserSession(userId, newUserSession);
      return newUserSession.getSessionKey();
    }
  }

  public boolean isSessionKeyValid(String sessionKey) {

    Optional<UserSession> sessionCookie = this.userSessionRepository.findSessionCookieFromSessionKey(sessionKey);
    return sessionCookie.isPresent();
  }

  public boolean hasUserValidSessionKey(Integer userId) {

    Optional<UserSession> sessionCookie = this.userSessionRepository.findUserSessionByUserId(userId);
    return sessionCookie.isPresent() && isUserSessionStillActive(sessionCookie.get());
  }

  public Optional<Integer> getUserIdForSessionKey(String sessionKey) {
    return this.userSessionRepository.findUserIdFromSessionKey(sessionKey);
  }


  public void removeAllSessions() {

    this.userSessionRepository.removeAllSessions();
  }

  private void saveUserSession(Integer userId, UserSession userSession) {

    Optional<User> user = this.userRepository.findUserById(userId);
    if (user.isPresent()) {
      this.userSessionRepository.saveUserSession(user.get(), userSession);
    }
  }

  private void createUserIfDoesNotExist(Integer userId) {

    this.userRepository.createUser(userId);
  }


  private UserSession createUserSession() {

    String sessionKey = UUID.randomUUID().toString();
    return new UserSession(sessionKey, clock.instant());
  }


//  private Optional<String> getActiveSessionKeyForUser(Integer userId) {
//
//    Optional<UserSession> sessionCookie = this.userSessionRepository.findUserSessionByUserId(userId);
//    if (sessionCookie.isPresent() && isUserSessionStillActive(sessionCookie.get())) {
//      return Optional.of(sessionCookie.get().getSessionKey());
//    } else {
//      return Optional.empty();
//    }
//  }

  private Optional<String> getActiveSessionKeyForUser(Integer userId) {

    Optional<UserSession> userSession = this.userSessionRepository.findUserSessionByUserId(userId);
    if (userSession.isPresent() && isUserSessionStillActive(userSession.get())) {
      return Optional.of(userSession.get().getSessionKey());
    } else {
      return Optional.empty();
    }
  }


  private boolean isUserSessionStillActive(UserSession userSession) {

    Instant sessionTimeout = Instant.now().minus(SESSION_TIMEOUT_IN_MINUTES, ChronoUnit.MINUTES);
    return userSession.getCreationInstant().isAfter(sessionTimeout);
  }

}
