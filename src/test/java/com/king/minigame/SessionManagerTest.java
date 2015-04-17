package com.king.minigame;


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

  @InjectMocks            // do I really need it?
  private SessionManager sessionManager;

  @BeforeClass
  public void setUpBeforeClass() {
    sessionManager = new SessionManager(clockMock);
    initMocks(this);    // do I really need it?
  }

  @BeforeMethod
  public void setUpBeforeMethod() {
      sessionManager.removeAllSessions();
  }

  public void should_return_valid_session_key_for_a_user_who_just_logged_in() {

    when(clockMock.instant()).thenReturn(Instant.now());
    String sessionKey = sessionManager.login(USER_ID);

    assertThat(sessionKey, is(notNullValue()));
  }


  public void a_user_who_has_not_logged_in_should_not_be_considered_active() {

    when(clockMock.instant()).thenReturn(Instant.now());
    String sessionKey = sessionManager.login(USER_ID);
    boolean isUserActive = sessionManager.isUserActive(ANOTHER_USER_ID);

    assertFalse(isUserActive);
  }


  public void a_user_who_logged_in_9_minutes_ago_should_be_considered_an_active_user() {

    Instant tenMinutesAgo = Instant.now().minus(9, ChronoUnit.MINUTES);
    when(clockMock.instant()).thenReturn(tenMinutesAgo);
    String sessionKey = sessionManager.login(USER_ID);

    when(clockMock.instant()).thenReturn(Instant.now());
    boolean isUserActive = sessionManager.isUserActive(USER_ID);

    assertTrue(isUserActive);
  }


  public void a_user_who_logged_in_more_than_10_minutes_ago_should_not_be_considered_active() {

    Instant twelveMinutesAgo = Instant.now().minus(11, ChronoUnit.MINUTES);
    when(clockMock.instant()).thenReturn(twelveMinutesAgo);

    String sessionKey = sessionManager.login(USER_ID);

    when(clockMock.instant()).thenReturn(Instant.now());
    boolean isUserActive = sessionManager.isUserActive(USER_ID);

    assertFalse(isUserActive);
  }
}
