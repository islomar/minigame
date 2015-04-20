package com.king.minigame.app;

import com.king.minigame.server.MinigameHTTPServer;

import static com.king.minigame.utils.Logger.log;

/**
 * Executable class to launch the Minigame web server.
 */
public class MinigameApp {

  public static void main(String[] args) {

    log("Starting Minigame App...");
    new MinigameHTTPServer().startUp();
  }
}
