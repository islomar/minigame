package com.king.minigame;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 */
public class SessionManager {

  private static Map<Integer, SessionCookie> sessionKeyStorage;
  private final static int SESSION_TIMEOUT_IN_MINUTES = 10;
  private Clock clock;

  public SessionManager(final Clock clock) {
    this.sessionKeyStorage = new HashMap<>();
    this.clock = clock;
  }

  public String login(Integer userId) {

    if (isUserSessionStillActive(userId)) {
      return this.sessionKeyStorage.get(userId).getSessionKey();
    } else {
      String sessionKey = UUID.randomUUID().toString();
      SessionCookie sessionCookie = new SessionCookie(sessionKey, clock.instant());
      this.sessionKeyStorage.put(userId, sessionCookie);
      return sessionKey;
    }
  }

  public boolean isUserActive(Integer userId) {

    SessionCookie sessionCookie = this.sessionKeyStorage.get(userId);
    if (sessionCookie == null) {
      return false;
    } else {
      Instant max = Instant.now().minus(SESSION_TIMEOUT_IN_MINUTES, ChronoUnit.MINUTES);
      return sessionCookie.getCreationInstant().isAfter(max);
    }
  }

  public void removeAllSessions() {
    sessionKeyStorage.clear();
  }

  private boolean isUserSessionStillActive(Integer userId) {

    return userHasSessionCookie(userId) && sessionCookieIsStillActive();
  }

  private boolean sessionCookieIsStillActive() {

    return false;
  }

  private boolean userHasSessionCookie(Integer userId) {

    if (this.sessionKeyStorage.containsKey(userId)) {
      return true;
    } else {
      return false;
    }
  }

}
