package com.king.minigame.server;

import com.king.minigame.server.handler.RootHttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import static com.king.minigame.utils.Logger.log;
import static java.lang.System.exit;



public class MinigameHTTPServer {

  private static final int PORT = 8081;

  public void startUp() {
    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
      server.createContext("/", new RootHttpHandler());
      server.setExecutor(Executors.newCachedThreadPool());

      log("Starting server in port " + PORT);
      server.start();
      log("Server successfully started in port " + PORT);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(e.getMessage());
      exit(-1);
    }
  }

}
