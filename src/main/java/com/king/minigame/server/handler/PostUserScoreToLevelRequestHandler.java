package com.king.minigame.server.handler;

import com.king.minigame.controller.GameLevelController;
import com.king.minigame.server.Response;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.king.minigame.utils.Logger.log;

/**
 *
 */
public class PostUserScoreToLevelRequestHandler {

  private static final Pattern POST_USER_SCORE_TO_LEVEL_PATTERN = Pattern.compile("/(\\d*?)/score");

  private final GameLevelController gameLevelController;


  public PostUserScoreToLevelRequestHandler(GameLevelController gameLevelController) {

    this.gameLevelController = gameLevelController;
  }

  public Optional<Response> handlePostUserScoreToLevel(HttpExchange he, URI uri) throws IOException {

    log("Request forwarded to PostUserScoreToLevelRequestHandler");

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


  public static boolean isPostUserScoreToALevelRequest(URI uri) {

    return postUserScoreToALevelMatcher(uri).find();
  }


  private static Matcher postUserScoreToALevelMatcher(URI uri) {

    String path = uri.getPath();
    return POST_USER_SCORE_TO_LEVEL_PATTERN.matcher(path);
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
