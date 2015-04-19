package com.king.minigame.server.handler;

import com.king.minigame.controller.GameLevelController;
import com.king.minigame.server.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.king.minigame.utils.Logger.log;

/**
 *
 */
public class HighScoreListRequestHandler {

  private static final Pattern HIGH_SCORE_LIST_PATTERN = Pattern.compile("/(\\d*?)/highscorelist");

  private final GameLevelController gameLevelController;


  public HighScoreListRequestHandler(GameLevelController gameLevelController) {
    this.gameLevelController = gameLevelController;
  }

  public Optional<Response> handleHighScoreListRequestIfApplies(URI uri) throws IOException {

    log("Request forwarded to HighScoreListRequestHandler");

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

  public static boolean isHighScoreListRequest(URI uri) {

    return highScoreListRequestMatcher(uri).find();
  }

  private static Matcher highScoreListRequestMatcher(URI uri) {

    String path = uri.getPath();
    return HIGH_SCORE_LIST_PATTERN.matcher(path);
  }
}
