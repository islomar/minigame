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

  public static void main(String[] args) {

    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(8081),0);
      server.createContext("/", new MinigameHttpHandler());
      server.setExecutor(Executors.newCachedThreadPool());
      server.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
