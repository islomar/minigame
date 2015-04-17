package com.king.minigame.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *  Class responsible for storing, finding and removing SessionCookies.
 */
public class SessionCookieRepository {

  private Map<Integer, SessionCookie> sessionKeyStorage;


  public SessionCookieRepository() {

    this.sessionKeyStorage = new HashMap<>();
  }

  public Optional<SessionCookie> getSessionCookie(Integer userId) {
    return Optional.ofNullable(this.sessionKeyStorage.get(userId));
  }

  public void saveSessionCookie(Integer userId, SessionCookie sessionCookie) {

    this.sessionKeyStorage.put(userId, sessionCookie);
  }


  public void removeAllSessions() {

    sessionKeyStorage.clear();
  }

}
