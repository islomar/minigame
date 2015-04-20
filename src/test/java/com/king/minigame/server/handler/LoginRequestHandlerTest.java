package com.king.minigame.server.handler;

import com.king.minigame.controller.LoginController;
import com.king.minigame.server.Response;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

@Test
public class LoginRequestHandlerTest {

  private static final Integer USER_ID = 111;
  private static final String SESSION_KEY = "4caa0fb1-67e2-4743-9f82-0543fcd7e1b7";

  @Mock
  private LoginController loginController;

  @InjectMocks
  private LoginRequestHandler loginRequestHandler;


  @BeforeMethod
  public void setUp() {
    loginRequestHandler = new LoginRequestHandler(loginController);
    initMocks(this);
  }

  public void given_an_empty_uri_then_loginController_is_never_called() throws IOException {
    URI uri = URI.create("");
    Optional<Response> response = loginRequestHandler.handleLoginRequestIfApplies(uri);

    verify(loginController, never()).login(anyInt());
    assertFalse(response.isPresent());
  }

  public void given_a_valid_login_uri_then_loginController_is_called_once_and_correct_response_returned() throws IOException {
    //GIVEN
    URI uri = URI.create(String.format("/%d/login", USER_ID));
    when(loginController.login(USER_ID)).thenReturn(SESSION_KEY);

    //WHEN
    Optional<Response> response = loginRequestHandler.handleLoginRequestIfApplies(uri);

    //THEN
    verify(loginController, times(1)).login(USER_ID);
    assertTrue(response.isPresent());
    assertThat(response.get().getHttpStatusCode(), is(HttpURLConnection.HTTP_OK));
  }

  public void a_correct_login_uri_is_considered_a_login_request() throws IOException {
    URI uri = URI.create(String.format("/%d/login", USER_ID));

    assertTrue(loginRequestHandler.isLoginRequest(uri));
  }

  public void a_login_uri_with_letters_is_not_considered_a_login_request() throws IOException {
    URI invalidUriWithString = URI.create(String.format("/%s/login", "dummyValue"));

    assertFalse(loginRequestHandler.isLoginRequest(invalidUriWithString));
  }

}
