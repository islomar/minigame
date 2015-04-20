package com.king.minigame.session;

import com.king.minigame.core.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Class responsible for storing, finding and removing SessionCookies.
 */
public class UserSessionRepository {

  private Map<User, UserSession> userSessionStore;


  public UserSessionRepository() {

    this.userSessionStore = new HashMap<>();
  }

  public void saveUserSession(User user, UserSession userSession) {

    this.userSessionStore.put(user, userSession);
  }


  public void removeAllSessions() {

    userSessionStore.clear();
  }


  public Optional<UserSession> findUserSessionByUserId(Integer userId) {

    User user = new User(userId);
    return Optional.ofNullable(this.userSessionStore.get(user));
  }


  public Optional<UserSession> findUserSessionBySessionKey(String sessionKey) {

    if (sessionKey == null) {
      return Optional.empty();
    }
    return Optional.of(userSessionStore.entrySet().stream()
        .filter(s -> sessionKey.equalsIgnoreCase(s.getValue().getSessionKey()))
        .findFirst().orElseThrow(IllegalStateException::new).getValue());
  }


  public Optional<Integer> findUserIdFromSessionKey(String sessionKey) {

//    Optional<Map.Entry<Integer, UserSession>> userIdFound = userSessionStore.entrySet().stream()
//        .filter(s -> s.getValue().getSessionKey().equalsIgnoreCase(sessionKey)).findFirst();
//
//    if (userIdFound.isPresent()) {
//      return Optional.of(userIdFound.get().getKey());
//    } else {
//      return Optional.empty();
//    }
    return null;
  }

  public Optional<User> findUserBySessionKey(String sessionKey) {

    Optional<Map.Entry<User, UserSession>> userIdFound = userSessionStore.entrySet().stream()
        .filter(s -> s.getValue().getSessionKey().equalsIgnoreCase(sessionKey)).findFirst();

    if (userIdFound.isPresent()) {
      return Optional.of(userIdFound.get().getKey());
    } else {
      return Optional.empty();
    }
  }
}
