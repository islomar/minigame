package com.king.minigame.server;

import com.king.minigame.controller.GameLevelController;
import com.king.minigame.controller.LoginController;
import com.king.minigame.session.SessionCookieRepository;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.king.minigame.server.HttpRequestMethod.GET;
import static com.king.minigame.server.HttpRequestMethod.POST;
import static com.king.minigame.server.HttpRequestMethod.UNKNOWN;

/**
 * https://leonardom.wordpress.com/2009/08/06/getting-parameters-from-httpexchange/
 */
public class MinigameHttpHandler implements HttpHandler {

  private final static Logger LOG = LoggerFactory.getLogger(MinigameHttpHandler.class);

  private static final Pattern LOGIN_PATTERN = Pattern.compile("/(\\d*?)/login");
  private static final Pattern HIGH_SCORE_LIST_PATTERN = Pattern.compile("/(\\d*?)/highscorelist");
  private static final Pattern POST_USER_SCORE_TO_LEVEL_PATTERN = Pattern.compile("/(\\d*?)/score");
  ;

  private final LoginController loginController;
  private final GameLevelController gameLevelController;

  public MinigameHttpHandler() {
    SessionCookieRepository sessionCookieRepository = new SessionCookieRepository();
    this.loginController = new LoginController(sessionCookieRepository);
    this.gameLevelController = new GameLevelController(sessionCookieRepository);
  }

  public void handle(HttpExchange he) throws IOException {

    Optional<Response> response = Optional.empty();

    try {
      URI uri = he.getRequestURI();
      LOG.debug("Received request for " + uri);

      InputStream requestBody = he.getRequestBody();

      HttpRequestMethod requestMethod = retrieveHttpRequestMethod(he);

      switch (requestMethod) {
        case GET:
          response = handleGetRequest(uri);
          break;
        case POST:
          response = handlePostRequest(he, uri);
          break;
        default:
          response = Optional.of(new Response(HttpURLConnection.HTTP_BAD_METHOD, ""));
      }
    } catch (IllegalStateException ex) {
      response = Optional.of(new Response(HttpURLConnection.HTTP_FORBIDDEN, ""));
    } catch (IllegalArgumentException ex) {
      response = Optional.of(new Response(HttpURLConnection.HTTP_BAD_REQUEST, ""));
    } catch (Exception ex) {
      response = Optional.of(new Response(HttpURLConnection.HTTP_INTERNAL_ERROR, ""));
    }

    response = handleHttpNotFound(response);

    he.sendResponseHeaders(response.get().getHttpStatusCode(), response.get().getResponseMessage().length());
    Headers h = he.getResponseHeaders();
    h.set("Content-Type", "text/plain");

    writeOutputStream(he, response.get().getResponseMessage());
  }

  private void writeOutputStream(HttpExchange he, String responseMessage) throws IOException {

    OutputStream
        os = he.getResponseBody();
    os.write(responseMessage.getBytes());
    os.close();
  }

  private Optional<Response> handleGetRequest(URI uri)
      throws IOException {

    Optional<Response> response = Optional.empty();
    if (isLoginRequest(uri)) {
      response = handleLoginRequestIfApplies(uri);
    } else if (isHighScoreListRequest(uri)) {
      response = handleHighScoreListRequestIfApplies(uri);
    }
    return response;
  }

  private Optional<Response> handlePostRequest(HttpExchange he, URI uri) throws IOException {

    Optional<Response> response = Optional.empty();
    if (isPostUserScoreToALevelRequest(uri)) {
      response = handlePostUserScoreToLevel(he, uri);
    }
    return response;
  }

  private Optional<Response> handleHttpNotFound(Optional<Response> response) {

    if (!response.isPresent()) {
      response = Optional.of(new Response(HttpURLConnection.HTTP_NOT_FOUND, ""));
    }
    return response;
  }

  private HttpRequestMethod retrieveHttpRequestMethod(HttpExchange he) {

    if (isRequest(POST, he)) {
      return POST;
    } else if (isRequest(GET, he)) {
      return GET;
    } else {
      return UNKNOWN;
    }
  }

  private boolean isLoginRequest(URI uri) {

    return loginUrlRequestMatcher(uri).find();
  }

  private boolean isHighScoreListRequest(URI uri) {

    return highScoreListRequestMatcher(uri).find();
  }

  private boolean isPostUserScoreToALevelRequest(URI uri) {

    return postUserScoreToALevelMatcher(uri).find();
  }

  private boolean isRequest(HttpRequestMethod method, HttpExchange he) {

    return method.name().equalsIgnoreCase(he.getRequestMethod());
  }

  private Optional<Response> handleLoginRequestIfApplies(URI uri) throws IOException {

    String sesskionKey = "";
    int statusCode;
    Matcher loginMatcher = loginUrlRequestMatcher(uri);
    if (loginMatcher.find()) {
      Integer userId = Integer.valueOf(loginMatcher.group(1));
      sesskionKey = loginController.login(userId);
      statusCode = HttpURLConnection.HTTP_OK;
      return Optional.of(new Response(statusCode, sesskionKey));
    } else {
      return Optional.empty();
    }
  }


  private Optional<Response> handleHighScoreListRequestIfApplies(URI uri)
      throws IOException {

    String highScoreListInCsvFormat = "";
    int statusCode;
    Matcher highScoreListMatcher = highScoreListRequestMatcher(uri);
    if (highScoreListMatcher.find()) {
      Integer listId = Integer.valueOf(highScoreListMatcher.group(1));
      highScoreListInCsvFormat = gameLevelController.getHighScoreListForLevel(listId);
      statusCode = HttpURLConnection.HTTP_OK;
      return Optional.of(new Response(statusCode, highScoreListInCsvFormat));
    } else {
      return Optional.empty();
    }
  }

  private Optional<Response> handlePostUserScoreToLevel(HttpExchange he, URI uri) throws IOException {

    Matcher postUserScoreToALevelMatcher = postUserScoreToALevelMatcher(uri);

    if (postUserScoreToALevelMatcher.find()) {
      Integer levelId = retrieveLevelId(postUserScoreToALevelMatcher);
      String sessionKey = retrieveSessionKey(he);
      Integer scoreValue = retrieveScoreValue(he);

      gameLevelController.postUserScoreToLevel(sessionKey, levelId, scoreValue);
      return Optional.of(new Response(HttpURLConnection.HTTP_OK, ""));
    } else {
      return Optional.empty();
    }

  }

  private Integer retrieveLevelId(Matcher postUserScoreToALevelMatcher) {
    return Integer.valueOf(postUserScoreToALevelMatcher.group(1));
  }

  private Integer retrieveScoreValue(HttpExchange he) throws IOException {

    InputStream is = he.getRequestBody();
    byte[] buf = new byte[1000];
    int len = is.read(buf);
    String bodyString = new String(buf, 0, len);
    return Integer.valueOf(bodyString);
  }

  private String retrieveSessionKey(HttpExchange he) {

    String query = he.getRequestURI().getQuery();
    Map<String, String> params = getQueryMap(query);
    return params.get("sessionkey");
  }

  private Matcher loginUrlRequestMatcher(URI uri) {

    String path = uri.getPath();
    return LOGIN_PATTERN.matcher(path);
  }

  private Matcher highScoreListRequestMatcher(URI uri) {

    String path = uri.getPath();
    return HIGH_SCORE_LIST_PATTERN.matcher(path);
  }

  private Matcher postUserScoreToALevelMatcher(URI uri) {

    String path = uri.getPath();
    return POST_USER_SCORE_TO_LEVEL_PATTERN.matcher(path);
  }

  private Map<String, String> getQueryMap(String query) {

    String[] params = query.split("&");
    Map<String, String> map = new HashMap<String, String>();
    for (String param : params) {
      String name = param.split("=")[0];
      String value = param.split("=")[1];
      map.put(name, value);
    }
    return map;
  }
}
