package com.king.minigame.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 *
 */
public class MinigameHttpHandler implements HttpHandler {
  public void handle(HttpExchange he) throws IOException {

    URI uri = he.getRequestURI();
    String response = "Path: " + uri.getPath() + "\n";

    if (he.getRequestMethod().equalsIgnoreCase("POST")) {
      response += " - POST\n";
    } else {
      response += " - GET\n";
    }

    he.sendResponseHeaders(200, response.length());
    OutputStream os = he.getResponseBody();
    os.write(response.getBytes());
    os.close();
  }
}
