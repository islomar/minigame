package com.king.minigame.session;

import com.king.minigame.core.model.User;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class UserRepository {

  private Map<Integer, User> userStore;


  public UserRepository() {

    this.userStore = new ConcurrentHashMap<>();
  }

  public void createUser(Integer userId) {
    if (userAlreadyExists(userId)) {
      return;
    } else {
      userStore.put(userId, new User(userId));
    }
  }

  public Optional<User> findUserById(Integer userId) {
    return Optional.ofNullable(userStore.get(userId));
  }

  public boolean userAlreadyExists(Integer userId) {
    return userStore.get(userId) != null;
  }

  public Map<Integer, User> findAllUsers() {
    return userStore;
  }

}
