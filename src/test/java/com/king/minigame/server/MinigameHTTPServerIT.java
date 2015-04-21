package com.king.minigame.server;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import sun.net.www.content.text.PlainTextInputStream;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.fail;

@Test
public class MinigameHTTPServerIT {

  public static final Integer USER_SCORE_100 = 100;
  public static final Integer USER_SCORE_200 = 200;
  private static final Integer USER_ID_1 = 1;
  private static final Integer USER_ID_2 = 2;
  private MinigameHTTPServer minigameHTTPServer;
  private HttpURLConnection conn;
  private int httpStatusCodeInResponse;
  private String returnedMessage;

  @BeforeClass
  public void setUp() {
    minigameHTTPServer = new MinigameHTTPServer();
  }


  public void start_server_send_requests_and_stop_server() throws IOException {

    minigameHTTPServer.startUp();

    String sessionKeyForUser1 = verifySuccessfulLoginForUser(USER_ID_1);
    String sessionKeyForUser2 = verifySuccessfulLoginForUser(USER_ID_2);

    verifyLoginWithIncorrectUrl();

    verifyPostUserScoreToLevelReturnsOKHttpStatus(sessionKeyForUser1, USER_SCORE_100);
    verifyPostUserScoreToLevelReturnsOKHttpStatus(sessionKeyForUser2, USER_SCORE_200);

    verifyPostUserScoreWithNonExistingSessionKeyToLevelReturnsForbiddenHttpStatus("non-existing-sessionkey", USER_SCORE_200);

    verifyBadMethodResponse();

    verifyHighScoreList();

    minigameHTTPServer.stop();
  }

  private void verifyBadMethodResponse() throws IOException {
    conn = sendRequestTo("http://localhost:8081/123/highscorelist", "PUT");
    httpStatusCodeInResponse = conn.getResponseCode();
    assertThat(httpStatusCodeInResponse, is(HttpURLConnection.HTTP_BAD_METHOD));
  }

  private void verifyHighScoreList() throws IOException {
    conn = sendRequestTo("http://localhost:8081/123/highscorelist", "GET");
    httpStatusCodeInResponse = conn.getResponseCode();
    returnedMessage = readMessageTextReturned(conn);
    assertThat(httpStatusCodeInResponse, is(HttpURLConnection.HTTP_OK));
    assertThat(returnedMessage, is(String.format("%d=%d,%d=%d", USER_ID_2, USER_SCORE_200, USER_ID_1, USER_SCORE_100)));
  }

  private void verifyPostUserScoreToLevelReturnsOKHttpStatus(String sessionKey, Integer userScore) throws IOException {
    conn = sendRequestWithBodyMessageTo("http://localhost:8081/123/score?sessionkey=" + sessionKey, "POST", String.valueOf(userScore));
    httpStatusCodeInResponse = conn.getResponseCode();
    returnedMessage = readMessageTextReturned(conn);
    assertThat(httpStatusCodeInResponse, is(HttpURLConnection.HTTP_OK));
    assertThat(returnedMessage, is(""));
  }

  private void verifyPostUserScoreWithNonExistingSessionKeyToLevelReturnsForbiddenHttpStatus(String sessionKey, Integer userScore) throws IOException {
    conn = sendRequestWithBodyMessageTo("http://localhost:8081/123/score?sessionkey=" + sessionKey, "POST", String.valueOf(userScore));
    httpStatusCodeInResponse = conn.getResponseCode();
    assertThat(httpStatusCodeInResponse, is(HttpURLConnection.HTTP_FORBIDDEN));
  }

  private void verifyLoginWithIncorrectUrl() throws IOException {
    conn = sendRequestTo("http://localhost:8081/1a1/login", "GET");
    int httpStatusCodeInResponse3 = conn.getResponseCode();
    assertThat(httpStatusCodeInResponse3, is(HttpURLConnection.HTTP_NOT_FOUND));
  }

  private String verifySuccessfulLoginForUser(Integer userId) throws IOException {
    conn = sendRequestTo(String.format("http://localhost:8081/%d/login", userId), "GET");
    httpStatusCodeInResponse = conn.getResponseCode();
    String sessionKey = readMessageTextReturned(conn);
    assertThat(httpStatusCodeInResponse, is(HttpURLConnection.HTTP_OK));
    assertThatSessionKeyIsValidUuid(sessionKey);
    return sessionKey;
  }

  private void assertThatSessionKeyIsValidUuid(String sessionKey) {
    try {
      assertThat(sessionKey, is(notNullValue()));
      UUID uuid = UUID.fromString(sessionKey);
    } catch (IllegalArgumentException ex) {
      fail("The session key returned is not a valid UUID");
    }
  }

  private HttpURLConnection sendRequestTo(String stringUrl, String requestMethod) throws IOException {
    URL url = new URL(stringUrl);
    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    conn.setRequestMethod(requestMethod);
    conn.connect();
    return conn;
  }

  private HttpURLConnection sendRequestWithBodyMessageTo(String stringUrl, String requestMethod, String body) throws IOException {
    URL url = new URL(stringUrl);
    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    conn.setRequestMethod(requestMethod);
    if ("POST".equalsIgnoreCase(requestMethod)) {
      conn.setRequestProperty("Content-Type", "text/plain");
      conn.setRequestProperty("charset", "utf-8");
      conn.setDoOutput( true );
      conn.setDoInput ( true );
      conn.setInstanceFollowRedirects( false );
      byte[] postData       = body.getBytes( Charset.forName("UTF-8"));
      int    postDataLength = postData.length;
      conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
      conn.setUseCaches( false );
      try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
        wr.write( postData );
      }
    }
    conn.connect();
    return conn;
  }

  private String readMessageTextReturned(HttpURLConnection conn) throws IOException {

    PlainTextInputStream content = (PlainTextInputStream) conn.getContent();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));

    String inputLine;
    StringBuilder messageReceived = new StringBuilder();
    while ((inputLine = bufferedReader.readLine()) != null)
      messageReceived.append(inputLine);
    bufferedReader.close();

    return messageReceived.toString();
  }

}
