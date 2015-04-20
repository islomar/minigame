package com.king.minigame.core.model;

import com.king.minigame.core.model.User;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

@Test
public class UserTest {

  public void two_users_with_same_id_are_considered__to_be_the_same_user() {

    User user1 = new User(1);
    User user2 = new User(1);
    User user3 = new User(2);

    assertEquals(user1, user2);
    assertNotEquals(user1, user3);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void throw_IllegalArgumentException_if_userId_is_null() {

    User level = new User(null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void throw_IllegalArgumentException_if_userId_is_negative() {

    User level = new User(-1);
  }

}
