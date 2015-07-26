package com.mlesniak.examples.server;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Simple websocket example.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@WebSocket
public class ServerWebSocket {
  private Session session;
  private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

  @OnWebSocketConnect
  public void handleConnect(Session session) {
    System.out.println("Connection opened.");
    this.session = session;
  }

  @OnWebSocketClose
  public void handleClose(int statusCode, String reason) {
    System.out.println("Connection closed with statusCode=" + statusCode + ", reason=" + reason);
  }

  @OnWebSocketMessage
  public void handleMessage(String message) {
    System.out.println("Message received. message=" + message);
    switch (message) {
      case "start":
        send("Starting.");
        executor.scheduleAtFixedRate(() -> send(new Date().toString()), 0, 1, TimeUnit.SECONDS);
        break;
      default:
        send("Message not understood.");
        break;
    }
  }

  private void send(String message) {
    try {
      if (session.isOpen()) {
        session.getRemote().sendString(message);
      }
    } catch (IOException e) {
      System.out.println("Error. message=" + e.getMessage());
    }
  }

  /**
   * Not used yet.
   */
  private void stop() {
    try {
      session.disconnect();
    } catch (IOException e) {
      System.out.println("Error. message=" + e.getMessage());
    }
  }
}
