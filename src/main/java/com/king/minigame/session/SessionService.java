package com.king.minigame.session;

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
  private SessionCookieRepository sessionCookieRepository;

  private Clock clock;

  public SessionService(final Clock clock, SessionCookieRepository sessionCookieRepository) {

    this.sessionCookieRepository = sessionCookieRepository;
    this.clock = clock;
  }

  public String login(Integer userId) {

    Optional<String> activeSessionKeyForUser = getActiveSessionKeyForUser(userId);

    if (activeSessionKeyForUser.isPresent()) {
      return activeSessionKeyForUser.get();
    } else {
      SessionCookie newSessionCookie = createSessionCookie();
      saveSessionCookie(userId, newSessionCookie);
      return newSessionCookie.getSessionKey();
    }
  }

  public boolean isSessionKeyValid(String sessionKey) {

    Optional<SessionCookie> sessionCookie = this.sessionCookieRepository.findSessionCookieFromSessionKey(sessionKey);
    return sessionCookie.isPresent();
  }

  public boolean hasUserValidSessionKey(Integer userId) {

    Optional<SessionCookie> sessionCookie = this.sessionCookieRepository.findSessionCookieForUser(userId);
    return sessionCookie.isPresent() && isSessionCookieStillActive(sessionCookie.get());
  }

  public Optional<Integer> getUserIdForSessionKey(String sessionKey) {
    return this.sessionCookieRepository.findUserIdFromSessionKey(sessionKey);
  }


  public void removeAllSessions() {

    this.sessionCookieRepository.removeAllSessions();
  }


  private void saveSessionCookie(Integer userId, SessionCookie sessionCookie) {

    this.sessionCookieRepository.saveSessionCookie(userId, sessionCookie);
  }


  private SessionCookie createSessionCookie() {

    String sessionKey = UUID.randomUUID().toString();
    return new SessionCookie(sessionKey, clock.instant());
  }


  private Optional<String> getActiveSessionKeyForUser(Integer userId) {

    Optional<SessionCookie> sessionCookie = this.sessionCookieRepository.findSessionCookieForUser(userId);
    if (sessionCookie.isPresent() && isSessionCookieStillActive(sessionCookie.get())) {
      return Optional.of(sessionCookie.get().getSessionKey());
    } else {
      return Optional.empty();
    }
  }


  private boolean isSessionCookieStillActive(SessionCookie sessionCookie) {

    Instant sessionTimeout = Instant.now().minus(SESSION_TIMEOUT_IN_MINUTES, ChronoUnit.MINUTES);
    return sessionCookie.getCreationInstant().isAfter(sessionTimeout);
  }

}
