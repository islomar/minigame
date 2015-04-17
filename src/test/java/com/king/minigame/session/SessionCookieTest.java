package com.king.minigame.session;

import org.testng.annotations.Test;

import java.time.Instant;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Test
public class SessionCookieTest {

  public void a_session_cookie_returns_the_values_stored() {
    //GIVEN
    String sessionKey = UUID.randomUUID().toString();
    Instant creationInstant = Instant.now();

    //WHEN
    SessionCookie sessionCookie = new SessionCookie(sessionKey, creationInstant);

    //THEN
    assertThat(sessionCookie.getSessionKey(), is(sessionKey));
    assertThat(sessionCookie.getCreationInstant(), is(creationInstant));
  }

  public void a_session_cookie_should_be_immutable() {
    //GIVEN
    String sessionKey = UUID.randomUUID().toString();
    Instant creationInstant = Instant.now();

    //WHEN
    SessionCookie sessionCookie = new SessionCookie(sessionKey, creationInstant);

    String recoveredSessionKey = sessionCookie.getSessionKey();
    recoveredSessionKey = "dummyValue";

    Instant recoveredCreationInstant = sessionCookie.getCreationInstant();
    recoveredCreationInstant = Instant.MIN;

    //THEN
    assertThat(sessionCookie.getSessionKey(), is(sessionKey));
    assertThat(sessionCookie.getCreationInstant(), is(creationInstant));
  }

}
