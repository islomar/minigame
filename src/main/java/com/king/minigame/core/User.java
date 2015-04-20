package com.king.minigame.core;

import com.king.minigame.session.UserSession;

/**
 *
 */
public class User {

  private final Integer userId;

  public User(final Integer userId) {

    validateParameter(userId);
    this.userId = userId;
  }

  public Integer getUserId() {
    return userId;
  }

  private void validateParameter(Integer userId) {
    if (userId == null || userId < 0) {
      throw new IllegalArgumentException("userId should be a positive value");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;

    if (userId != null ? !userId.equals(user.userId) : user.userId != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return userId != null ? userId.hashCode() : 0;
  }

  @Override
  public String toString() {
    return String.format("User{userId=%d}", userId);
  }

}
