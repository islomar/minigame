package com.king.minigame.utils;

import java.time.Instant;

/**
 *  Class responsible for logging the messages (currently only to the default exit).
 */
public class Logger {

  public static void log(String message) {
    System.out.println(String.format("%s - %s", Instant.now(), message));
  }

}
