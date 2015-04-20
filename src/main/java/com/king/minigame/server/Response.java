package com.king.minigame.server;

/**
 *  Class which encapsulates the information returned to the RootHttpHandler for further processing.
 */
public class Response {

  private final int httpStatusCode;
  private final String responseMessage;

  public Response(final int httpStatusCode, final String responseMessage) {
    this.httpStatusCode = httpStatusCode;
    this.responseMessage = responseMessage;
  }

  public int getHttpStatusCode() {

    return httpStatusCode;
  }

  public String getResponseMessage() {

    return responseMessage;
  }

}
