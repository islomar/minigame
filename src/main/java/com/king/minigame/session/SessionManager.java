package com.king.minigame.session;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

/**
 * Class responsible for xxxx.
 */
public class SessionManager {

  private final static int SESSION_TIMEOUT_IN_MINUTES = 10;
  private SessionCookieRepository sessionCookieRepository;

  private Clock clock;

  public SessionManager(final Clock clock) {

    sessionCookieRepository = new SessionCookieRepository();
    this.clock = clock;
  }

  public String login(Integer userId) {

      Optional<SessionCookie> sessionCookie = this.sessionCookieRepository.getSessionCookie(userId);

      if (sessionCookie.isPresent()) {
        return sessionCookie.get().getSessionKey();
      } else {
        SessionCookie newSessionCookie = createSessionCookie();
        saveSessionCookie(userId, newSessionCookie);
        return newSessionCookie.getSessionKey();
      }
  }


  public boolean isUserActive(Integer userId) {

    Optional<SessionCookie> sessionCookie = this.sessionCookieRepository.getSessionCookie(userId);
    if (sessionCookie.isPresent()) {
      return isSessionCookieStillActive(sessionCookie.get());
    } else {
      return false;
    }
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


  private boolean isSessionCookieStillActive(SessionCookie sessionCookie) {

    Instant sessionTimeout = Instant.now().minus(SESSION_TIMEOUT_IN_MINUTES, ChronoUnit.MINUTES);
    return sessionCookie.getCreationInstant().isAfter(sessionTimeout);
  }


  private boolean isUserSessionStillActive(Integer userId) {

    Optional<SessionCookie> sessionCookie = this.sessionCookieRepository.getSessionCookie(userId);
    if (sessionCookie.isPresent()) {
      return isSessionCookieStillActive(sessionCookie.get());
    } else {
      return false;
    }
  }
}
