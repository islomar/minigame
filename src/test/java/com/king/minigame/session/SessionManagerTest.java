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
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Test
public class SessionManagerTest {

  private static final Integer USER_ID = 123456789;
  private static final Integer ANOTHER_USER_ID = 1;

  @Mock
  private Clock clockMock;

  @InjectMocks
  private SessionManager sessionManager;

  @BeforeClass
  public void setUpBeforeClass() {

    sessionManager = new SessionManager(clockMock);
    initMocks(this);
  }

  @BeforeMethod
  public void setUpBeforeMethod() {

    sessionManager.removeAllSessions();
  }

  public void should_return_valid_session_key_for_a_user_who_just_logged_in() {

    setTimeToNow();
    String sessionKey = sessionManager.login(USER_ID);

    assertThat(sessionKey, is(notNullValue()));
  }


  public void a_user_who_has_not_logged_in_should_not_be_considered_active() {

    setTimeToNow();
    String sessionKey = sessionManager.login(USER_ID);
    boolean isUserActive = sessionManager.isUserActive(ANOTHER_USER_ID);

    assertFalse(isUserActive);
  }


  public void a_user_who_logged_in_9_minutes_ago_should_be_considered_an_active_user() {

    setTimeMinutesAgo(9);
    String sessionKey = sessionManager.login(USER_ID);

    setTimeToNow();
    boolean isUserActive = sessionManager.isUserActive(USER_ID);

    assertTrue(isUserActive);
  }

  public void a_user_who_logged_in_more_than_10_minutes_ago_should_not_be_considered_active() {

    setTimeMinutesAgo(11);

    String sessionKey = sessionManager.login(USER_ID);

    setTimeToNow();
    boolean isUserActive = sessionManager.isUserActive(USER_ID);

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
