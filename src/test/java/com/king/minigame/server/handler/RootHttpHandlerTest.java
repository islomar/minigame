package com.king.minigame.server.handler;

import com.sun.net.httpserver.HttpExchange;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

@Test
public class RootHttpHandlerTest {

  @Mock
  HttpExchange httpExchange;

  @InjectMocks
  public RootHttpHandler rootHttpHandler;

  @BeforeMethod
  public void setUp() {
    rootHttpHandler = new RootHttpHandler();
    initMocks(this);
  }

  @Test(enabled = false)
  //TODO
  public void xxx() throws IOException {

    when(httpExchange.getRequestURI()).thenReturn(URI.create(""));

    rootHttpHandler.handle(httpExchange);
  }

}
