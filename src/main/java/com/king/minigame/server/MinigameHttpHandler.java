package com.king.minigame.server;

import com.king.minigame.controller.LoginController;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.king.minigame.server.HttpRequestMethod.*;

/**
 * https://leonardom.wordpress.com/2009/08/06/getting-parameters-from-httpexchange/
 */
public class MinigameHttpHandler implements HttpHandler {

  private static final Pattern LOGIN_PATTERN = Pattern.compile("/(\\d*?)/login");
  private final LoginController loginController;

  public MinigameHttpHandler() {
    this.loginController = new LoginController();
  }

  public void handle(HttpExchange he) throws IOException {

    URI uri = he.getRequestURI();
    String response = "";//"Path: " + uri.getPath() + "\n";

    InputStream requestBody = he.getRequestBody();

    int statusCode;
    if (isRequest(POST, he)) {
      response += " - POST\n";
      statusCode = HttpURLConnection.HTTP_OK;
    } else if (isRequest(GET, he)) {
      //response += " - GET\n";
      response += handleLoginRequest(he, uri);
    } else {
      statusCode = HttpURLConnection.HTTP_BAD_METHOD;
    }

//    String query = he.getRequestURI().getQuery();
//    response += "query: " + query + "\n";

    Headers h = he.getResponseHeaders();
    h.set("Content-Type", "text/plain");

    OutputStream os = he.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }

  private boolean isRequest(HttpRequestMethod method, HttpExchange he) {

    return method.name().equalsIgnoreCase(he.getRequestMethod());
  }

  public String handleLoginRequest(HttpExchange he, URI uri) throws IOException {

    String sesskionKey = "";
    int statusCode;
    Matcher loginMatcher = getLoginUrlRequestMatcher(uri);
    if (loginMatcher.find()) {
      Integer userId = Integer.valueOf(loginMatcher.group(1));
      sesskionKey = loginController.login(userId);
      statusCode = HttpURLConnection.HTTP_OK;
    } else {
      statusCode = HttpURLConnection.HTTP_NOT_FOUND;
    }
    he.sendResponseHeaders(statusCode, sesskionKey.length());
    return sesskionKey;
  }

  private Matcher getLoginUrlRequestMatcher(URI uri) {

    String path = uri.getPath();
    return LOGIN_PATTERN.matcher(path);
  }
}
