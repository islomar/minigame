package com.king.minigame.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Class responsible for storing, finding and removing SessionCookies.
 */
public final class SessionCookieRepository {

  private Map<Integer, SessionCookie> userSessionKeyStore;


  public SessionCookieRepository() {

    this.userSessionKeyStore = new HashMap<>();
  }

  public void saveSessionCookie(Integer userId, SessionCookie sessionCookie) {

    this.userSessionKeyStore.put(userId, sessionCookie);
  }


  public void removeAllSessions() {

    userSessionKeyStore.clear();
  }


  public Optional<SessionCookie> findSessionCookieForUser(Integer userId) {

    return Optional.ofNullable(this.userSessionKeyStore.get(userId));
  }


  public Optional<SessionCookie> findSessionCookieFromSessionKey(String sessionKey) {

    if (sessionKey == null) {
      return Optional.empty();
    }
    return Optional.ofNullable(userSessionKeyStore.entrySet().stream()
                                   .filter(s -> sessionKey.equalsIgnoreCase(s.getValue().getSessionKey()))
                                   .findFirst().get().getValue());
  }


  public Optional<Integer> findUserIdFromSessionKey(String sessionKey) {

    Optional<Map.Entry<Integer, SessionCookie>> userIdFound = userSessionKeyStore.entrySet().stream()
        .filter(s -> s.getValue().getSessionKey().equalsIgnoreCase(sessionKey)).findFirst();

    if (userIdFound.isPresent()) {
      return Optional.of(userIdFound.get().getKey());
    } else {
      return Optional.empty();
    }
  }
}
