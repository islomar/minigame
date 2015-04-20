package com.king.minigame.utils;

import java.time.Instant;

/**
 *
 */
public class Logger {

  public static void log(String message) {
    System.out.println(String.format("%s - %s", Instant.now(), message));
  }

}
