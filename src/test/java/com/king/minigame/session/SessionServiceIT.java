package com.king.minigame.session;


import com.king.minigame.core.model.User;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class SessionServiceIT {

  private static final Integer USER_ID = 123456789;

  @Mock
  private Clock clockMock;

  @InjectMocks
  private SessionService sessionService;

  @BeforeMethod
  public void setUpBeforeMethod() {

    sessionService = new SessionService(clockMock, new UserSessionRepository(), new UserRepository());
    initMocks(this);
  }

  public void should_return_valid_session_key_for_a_user_who_just_logged_in() {

    setTimeToNow();
    String sessionKey = sessionService.login(USER_ID);

    assertThat(sessionKey, is(notNullValue()));
  }


  public void a_user_who_logs_in_twice_in_9_minutes_should_get_the_same_session_key() {

    setTimeMinutesAgo(9);
    String oldSessionKey = sessionService.login(USER_ID);

    setTimeToNow();
    String newSessionKey = sessionService.login(USER_ID);

    assertThat(oldSessionKey, is(newSessionKey));
  }

  public void a_user_who_logs_in_twice_in_more_than_10_minutes_should_get_a_different_session_key() {

    setTimeMinutesAgo(11);
    String oldSessionKey = sessionService.login(USER_ID);

    setTimeToNow();
    String newSessionKey = sessionService.login(USER_ID);

    assertThat(oldSessionKey, is(not(newSessionKey)));
  }

  public void a_user_who_logged_in_more_than_10_minutes_ago_should_not_be_found() {

    setTimeMinutesAgo(11);
    String sessionKey = sessionService.login(USER_ID);

    setTimeToNow();
    Optional<User> user = sessionService.findUserBySessionkey(sessionKey);

    assertFalse(user.isPresent());
  }

  public void no_user_should_be_found_for_a_session_not_belonging_to_anyone() {

    setTimeToNow();
    Optional<User> user = sessionService.findUserBySessionkey(null);

    assertFalse(user.isPresent());
  }

  @Test(expectedExceptions = IllegalStateException.class)
  public void throw_IllegalStateException_if_no_user_is_found_for_the_sessionkey() {

    setTimeToNow();
    Optional<User> user = sessionService.findUserBySessionkey("NON_EXISTING_SESSION_KEY");
  }

  public void a_user_who_logged_in_9_minutes_ago_should_be_found() {

    setTimeMinutesAgo(9);
    String sessionKey = sessionService.login(USER_ID);

    setTimeToNow();
    Optional<User> user = sessionService.findUserBySessionkey(sessionKey);

    assertTrue(user.isPresent());
  }


  private void setTimeToNow() {
    when(clockMock.instant()).thenReturn(Instant.now());
  }


  private void setTimeMinutesAgo(int minutesAgo) {

    Instant instantAgo = Instant.now().minus(minutesAgo, ChronoUnit.MINUTES);
    when(clockMock.instant()).thenReturn(instantAgo);
  }
}
