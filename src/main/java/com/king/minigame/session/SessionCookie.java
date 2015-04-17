package com.king.minigame.session;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 *  Immutable class to store session cookies.
 */
public final class SessionCookie {

  private final String sessionKey;
  private Instant creationInstant;

  public SessionCookie(final String sessionKey, final Instant creationInstant) {
    this.sessionKey = sessionKey;
    this.creationInstant = creationInstant;
  }

  public String getSessionKey() {

    return sessionKey;
  }

  public Instant getCreationInstant() {

    return creationInstant;
  }
}
