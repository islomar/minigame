package com.king.minigame.session;

import com.king.minigame.core.model.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class responsible for storing, finding and removing SessionCookies.
 */
public class UserSessionRepository {

  private Map<User, UserSession> userSessionStore;


  public UserSessionRepository() {

    this.userSessionStore = new ConcurrentHashMap<>();
  }

  public void saveUserSession(User user, UserSession userSession) {

    this.userSessionStore.put(user, userSession);
  }


  public Optional<UserSession> findUserSessionByUserId(Integer userId) {

    User user = new User(userId);
    return Optional.ofNullable(this.userSessionStore.get(user));
  }


  public Optional<UserSession> findUserSessionBySessionKey(String sessionKey) {

    if (sessionKey == null) {
      return Optional.empty();
    }
    return Optional.of(userSessionStore.entrySet()
                           .parallelStream()
                           .filter(s -> sessionKey.equalsIgnoreCase(s.getValue().getSessionKey()))
                           .findFirst().orElseThrow(IllegalStateException::new).getValue());
  }


  public Optional<User> findUserBySessionKey(String sessionKey) {

    Optional<Map.Entry<User, UserSession>> userIdFound = userSessionStore.entrySet()
        .parallelStream()
        .filter(s -> s.getValue().getSessionKey().equalsIgnoreCase(sessionKey))
        .findFirst();

    if (userIdFound.isPresent()) {
      return Optional.of(userIdFound.get().getKey());
    } else {
      return Optional.empty();
    }
  }
}
