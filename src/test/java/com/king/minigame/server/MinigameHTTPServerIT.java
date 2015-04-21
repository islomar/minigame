package com.king.minigame.server;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import sun.net.www.content.text.PlainTextInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertTrue;

@Test
public class MinigameHTTPServerIT {

  private MinigameHTTPServer minigameHTTPServer;

  @BeforeClass
  public void setUp() {
    minigameHTTPServer = new MinigameHTTPServer();
  }


  public void start_server_send_requests_and_stop_server() throws IOException {
    minigameHTTPServer.startUp();

    URL url = new URL("http://localhost:8081/123/highscorelist");
    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    conn.setRequestMethod("GET");
    conn.connect();

    int httpStatusCodeInResponse = conn.getResponseCode();
    String returnedMessage = readMessageTextReturned(conn);
    assertThat(httpStatusCodeInResponse, is(HttpURLConnection.HTTP_OK));
    assertThat(returnedMessage, is(""));

    URL url2 = new URL("http://localhost:8081/111/login");
    HttpURLConnection conn2 = (HttpURLConnection)url2.openConnection();
    conn2.setRequestMethod("GET");
    conn2.connect();
    int httpStatusCodeInResponse2 = conn2.getResponseCode();
    String sessionKey = readMessageTextReturned(conn2);
    assertThat(httpStatusCodeInResponse2, is(HttpURLConnection.HTTP_OK));
    assertThat(sessionKey, is(notNullValue()));


    URL url3 = new URL("http://localhost:8081/1a1/login");
    HttpURLConnection conn3 = (HttpURLConnection)url3.openConnection();
    conn3.setRequestMethod("GET");
    conn3.connect();
    int httpStatusCodeInResponse3 = conn3.getResponseCode();
    assertThat(httpStatusCodeInResponse3, is(HttpURLConnection.HTTP_NOT_FOUND));

    minigameHTTPServer.stop();
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
