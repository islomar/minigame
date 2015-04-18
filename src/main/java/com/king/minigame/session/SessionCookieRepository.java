package com.king.minigame.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class responsible for storing, finding and removing SessionCookies.
 */
public final class SessionCookieRepository {

  private Map<Integer, SessionCookie> sessionKeyStorage;


  public SessionCookieRepository() {

    this.sessionKeyStorage = new HashMap<>();
  }

  public void saveSessionCookie(Integer userId, SessionCookie sessionCookie) {

    this.sessionKeyStorage.put(userId, sessionCookie);
  }


  public void removeAllSessions() {

    sessionKeyStorage.clear();
  }


  public Optional<SessionCookie> findSessionCookieForUser(Integer userId) {

    return Optional.ofNullable(this.sessionKeyStorage.get(userId));
  }


  public Optional<SessionCookie> findSessionCookieFromSessionKey(String sessionKey) {

    if (sessionKey == null) {
      return Optional.empty();
    }
    return Optional.ofNullable(sessionKeyStorage.entrySet().stream()
                                   .filter(s -> sessionKey.equalsIgnoreCase(s.getValue().getSessionKey()))
                                   .findFirst().get().getValue());
  }


  public Optional<Integer> findUserIdFromSessionKey(String sessionKey) {

//    if (sessionKey == null) {
//      return Optional.empty();
//    }
    Optional<Map.Entry<Integer, SessionCookie>> userIdFound = sessionKeyStorage.entrySet().stream()
        .filter(s -> s.getValue().getSessionKey().equalsIgnoreCase(sessionKey)).findFirst();

    if (userIdFound.isPresent()) {
      return Optional.of(userIdFound.get().getKey());
    } else {
      return Optional.empty();
    }
  }
}
