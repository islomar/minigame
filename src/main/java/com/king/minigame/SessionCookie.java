package com.king.minigame;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 *  Immutable class to store xxxx.
 *  TODO: sobrescribir equals y hashCode para poder comparar... ¿solo por sessionKey?
 */
public final class SessionCookie {

  private final String sessionKey;
  private final Instant creationInstant;

  public SessionCookie(final String sessionKey, final Instant creationInstant) {
    this.sessionKey = sessionKey;
    this.creationInstant = creationInstant;
  }

  public String getSessionKey() {

    return sessionKey;
  }

  //TODO: return a copy???
  public Instant getCreationInstant() {

    return Instant.from(creationInstant);
  }
}
