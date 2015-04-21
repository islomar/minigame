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

@Test
public class MinigameHTTPServerIT {

  private MinigameHTTPServer minigameHTTPServer;

  @BeforeClass
  public void setUp() {
    minigameHTTPServer = new MinigameHTTPServer();
  }

  //TODO
  @Test(enabled = false)
  public void start_server_send_requests_and_stop_server() throws IOException {
    minigameHTTPServer.startUp();

    URL url = new URL("http://localhost:8081/123/highscorelist");
    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    conn.setRequestMethod("GET");
    conn.connect();

    int httpStatusCodeInResponse = conn.getResponseCode();
    assertThat(httpStatusCodeInResponse, is(HttpURLConnection.HTTP_OK));
    //URLConnection conn = url.openConnection();
    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
   // assertEquals("<?xml version=\"1.0\"?>", in.readLine());
//    assertEquals("<resource id=\"1234\" name=\"test\" />", in.lines().toString());

    URL url2 = new URL("http://localhost:8081/111/login");
    HttpURLConnection conn2 = (HttpURLConnection)url2.openConnection();
    conn2.setRequestMethod("GET");
    conn2.connect();

    int httpStatusCodeInResponse2 = conn2.getResponseCode();
    //assertThat(httpStatusCodeInResponse2, is(HttpURLConnection.HTTP_OK));

    BufferedReader in2 = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    //BufferedReader in3 = new BufferedReader((PlainTextInputStream)(conn.getContent()));
    //assertThat(conn2.getContent(), is("hola"));

    System.out.println(in2.readLine());
    //System.out.println(in3.readLine());

    PlainTextInputStream in3 = (PlainTextInputStream)conn.getContent();
    //Iterate over the InputStream and print it out.
    int c;
    while ((c = in3.read()) != -1) {
      System.out.print((char) c);
    }

    String inputLine;
    while ((inputLine = in.readLine()) != null)
      System.out.println(inputLine);
    in.close();

    minigameHTTPServer.stop();
  }

}
