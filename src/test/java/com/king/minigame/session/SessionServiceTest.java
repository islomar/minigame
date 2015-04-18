package com.king.minigame.session;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class SessionServiceTest {

  private static final Integer USER_ID = 123456789;
  private static final Integer ANOTHER_USER_ID = 1;

  @Mock
  private Clock clockMock;

  @InjectMocks
  private SessionService sessionService;

  @BeforeClass
  public void setUpBeforeClass() {

    sessionService = new SessionService(clockMock, new SessionCookieRepository());
    initMocks(this);
  }

  @BeforeMethod
  public void setUpBeforeMethod() {

    sessionService.removeAllSessions();
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


  public void a_user_who_has_not_logged_in_should_not_be_considered_active() {

    setTimeToNow();
    String sessionKey = sessionService.login(USER_ID);
    boolean isUserActive = sessionService.hasUserValidSessionKey(ANOTHER_USER_ID);

    assertFalse(isUserActive);
  }


  public void a_user_who_logged_in_9_minutes_ago_should_be_considered_an_active_user() {

    setTimeMinutesAgo(9);
    String sessionKey = sessionService.login(USER_ID);

    setTimeToNow();
    boolean isUserActive = sessionService.hasUserValidSessionKey(USER_ID);

    assertTrue(isUserActive);
  }

  public void a_user_who_logged_in_more_than_10_minutes_ago_should_not_be_considered_active() {

    setTimeMinutesAgo(11);

    String sessionKey = sessionService.login(USER_ID);

    setTimeToNow();
    boolean isUserActive = sessionService.hasUserValidSessionKey(USER_ID);

    assertFalse(isUserActive);
  }


  private void setTimeToNow() {
    when(clockMock.instant()).thenReturn(Instant.now());
  }


  private void setTimeMinutesAgo(int minutesAgo) {

    Instant instantAgo = Instant.now().minus(minutesAgo, ChronoUnit.MINUTES);
    when(clockMock.instant()).thenReturn(instantAgo);
  }
}
