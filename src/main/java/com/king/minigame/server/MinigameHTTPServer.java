package com.king.minigame.server;

import com.sun.net.httpserver.HttpServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 *        http://www.microhowto.info/howto/serve_web_pages_using_an_embedded_http_server_in_java.html
 *        http://www.programcreek.com/java-api-examples/index.php?api=com.sun.net.httpserver.HttpServer
 */
public class MinigameHTTPServer {

  private final static Logger LOG = LoggerFactory.getLogger(MinigameHTTPServer.class);
  private static final int PORT = 8081;

  public static void main(String[] args) {

    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(PORT),0);
      server.createContext("/", new MinigameHttpHandler());
      server.setExecutor(Executors.newCachedThreadPool());
      LOG.debug("Starting server in port " + PORT);
      server.start();
      LOG.debug("Server successfully started in port " + PORT);
    } catch (IOException e) {
      e.printStackTrace();
      LOG.error(e.getMessage());
    }
  }

}
