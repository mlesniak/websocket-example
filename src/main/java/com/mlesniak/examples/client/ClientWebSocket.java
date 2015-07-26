package com.mlesniak.examples.client;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

/**
 * Java-based client.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@WebSocket
public class ClientWebSocket {
  private Session session;

  @OnWebSocketClose
  public void onClose(int statusCode, String reason) {
    System.out.printf("Connection closed: statusCode=%d, reason=%s%n", statusCode, reason);
    session = null;
  }

  @OnWebSocketConnect
  public void onConnect(Session session) throws IOException {
    System.out.printf("Connected.");
    this.session = session;
    session.getRemote().sendString("start");
  }

  @OnWebSocketMessage
  public void onMessage(String msg) {
    System.out.printf("Message received: %s%n", msg);
  }

  public void waitUntilClosed() {
    while (session == null || session.isOpen()) {
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
