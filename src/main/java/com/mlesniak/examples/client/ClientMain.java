package com.mlesniak.examples.client;

/**
 * Java-based client.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */

import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;

public class ClientMain {
  public static void main(String[] args) throws Exception {

    ClientWebSocket clientWebSocket = new ClientWebSocket();
    WebSocketClient client = new WebSocketClient();

    client.start();
    client.connect(clientWebSocket, new URI("ws://localhost:8090/chat"));
    clientWebSocket.waitUntilClosed();
    client.stop();
  }
}
