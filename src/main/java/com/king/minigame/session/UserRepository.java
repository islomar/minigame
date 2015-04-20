package com.king.minigame.session;

import com.king.minigame.core.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 */
public class UserRepository {

  private Map<Integer, User> userStore;


  public UserRepository() {

    this.userStore = new HashMap();
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
