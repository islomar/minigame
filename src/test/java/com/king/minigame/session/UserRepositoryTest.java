package com.king.minigame.session;

import com.king.minigame.core.model.User;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class UserRepositoryTest {

  private final static Integer USER_ID = 1;
  private final static Integer ANOTHER_USER_ID = 2;

  public UserRepository userRepository;

  @BeforeMethod
  public void setUp() {
    userRepository = new UserRepository();
  }

  public void a_user_not_created_is_not_considered_to_exist() {
     assertFalse(userRepository.userAlreadyExists(USER_ID));
  }

  public void when_create_one_user_then_the_user_exists() {
    userRepository.createUser(USER_ID);
    assertTrue(userRepository.userAlreadyExists(USER_ID));
  }

  public void when_a_user_is_created_twice_then_it_is_saved_only_once() {
    //GIVEN
    userRepository.createUser(USER_ID);
    userRepository.createUser(USER_ID);

    //WHEN
    Map<Integer, User> allUsers = userRepository.findAllUsers();

    //THEN
    assertThat(allUsers.size(), is(1));
    assertTrue(userRepository.userAlreadyExists(USER_ID));
  }

  public void when_create_two_users_then_individual_users_can_be_found_by_userId() {
    //GIVEN
    userRepository.createUser(USER_ID);
    userRepository.createUser(ANOTHER_USER_ID);
    User expectedUser = new User(USER_ID);

    //WHEN
    Optional<User> userFound = userRepository.findUserById(USER_ID);

    //THEN
    assertTrue(userFound.isPresent());
    assertThat(userFound.get(), is(expectedUser));
  }

  public void a_non_created_user_is_not_found_by_userId() {
    //GIVEN
    userRepository.createUser(USER_ID);

    //WHEN
    Optional<User> userFound = userRepository.findUserById(ANOTHER_USER_ID);

    //THEN
    assertFalse(userFound.isPresent());
  }

  public void when_two_users_are_created_then_findAllUsers_returns_both() {
    //GIVEN
    userRepository.createUser(USER_ID);
    userRepository.createUser(ANOTHER_USER_ID);

    //WHEN
    Map<Integer, User> allUsers = userRepository.findAllUsers();

    //THEN
    assertThat(allUsers.size(), is(2));
    assertTrue(allUsers.containsValue(new User(USER_ID)));
    assertTrue(allUsers.containsValue(new User(ANOTHER_USER_ID)));
  }

}
