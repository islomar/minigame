package com.king.minigame.session;

import org.testng.annotations.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Test
public class UserSessionTest {

  public void a_session_cookie_returns_the_values_stored() {
    //GIVEN
    String sessionKey = UUID.randomUUID().toString();
    Instant creationInstant = Instant.now();

    //WHEN
    UserSession userSession = new UserSession(sessionKey, creationInstant);

    //THEN
    assertThat(userSession.getSessionKey(), is(sessionKey));
    assertThat(userSession.getCreationInstant(), is(creationInstant));
  }

  public void a_session_cookie_should_be_immutable() {
    //GIVEN
    String sessionKey = UUID.randomUUID().toString();
    Instant creationInstant = Instant.now();

    //WHEN
    UserSession userSession = new UserSession(sessionKey, creationInstant);

    String recoveredSessionKey = userSession.getSessionKey();
    recoveredSessionKey = "dummyValue";

    Instant recoveredCreationInstant = userSession.getCreationInstant();
    recoveredCreationInstant = Instant.MIN;

    //THEN
    assertThat(userSession.getSessionKey(), is(sessionKey));
    assertThat(userSession.getCreationInstant(), is(creationInstant));
  }


  @Test(expectedExceptions = IllegalArgumentException.class)
  public void throw_IllegalArgumentException_if_session_key_is_null() {
    new UserSession(null, Instant.now());
  }


  @Test(expectedExceptions = IllegalArgumentException.class)
  public void throw_IllegalArgumentException_if_instant_is_null() {
    new UserSession("anySessionKey", null);
  }


  @Test(expectedExceptions = IllegalArgumentException.class)
  public void throw_IllegalArgumentException_if_instant_is_in_the_future() {
    new UserSession("anySessionKey", Instant.now().plus(1, ChronoUnit.MINUTES));
  }


}
