package com.king.minigame.server;

import com.sun.net.httpserver.HttpServer;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 *        http://www.microhowto.info/howto/serve_web_pages_using_an_embedded_http_server_in_java.html
 *        http://www.programcreek.com/java-api-examples/index.php?api=com.sun.net.httpserver.HttpServer
 */
public class MinigameHTTPServer {

  private static final int PORT = 8081;

  public static void main(String[] args) {

    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(PORT),0);
      server.createContext("/", new MinigameHttpHandler());
      server.setExecutor(Executors.newCachedThreadPool());
      System.out.println("Starting server in port " + PORT);
      server.start();
      System.out.println("Server successfully started in port " + PORT);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println(e.getMessage());
    }
  }

}
