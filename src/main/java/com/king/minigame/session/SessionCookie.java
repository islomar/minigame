package com.king.minigame.session;

import java.time.Instant;

/**
 *  Immutable class to store session cookies.
 */
public final class SessionCookie {

  private final String sessionKey;
  private Instant creationInstant;

  public SessionCookie(final String sessionKey, final Instant creationInstant) {

    validateParameters(sessionKey, creationInstant);
    this.sessionKey = sessionKey;
    this.creationInstant = creationInstant;
  }

  private void validateParameters(String sessionKey, Instant creationInstant) {

    if (sessionKey == null || creationInstant == null) {
      throw new IllegalArgumentException("Null values are not allowed!");
    }
    if (creationInstant.isAfter(Instant.now())) {
      throw new IllegalArgumentException("You can not create a session cookie in the future.");
    }
  }

  public String getSessionKey() {

    return sessionKey;
  }

  public Instant getCreationInstant() {

    return creationInstant;
  }
}
