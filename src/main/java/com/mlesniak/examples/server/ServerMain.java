package com.mlesniak.examples.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * Configure and start local jetty.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
public class ServerMain {
  public static void main(String[] args) throws Exception {
    Server server = new Server(8090);

    // WebSocket handler.
    ServletContextHandler ctx = new ServletContextHandler();
    ctx.setContextPath("/");
    ctx.addServlet(ChatServlet.class, "/chat");

    // Static content.
    ResourceHandler resourceHandler = new ResourceHandler();
    resourceHandler.setBaseResource(Resource.newClassPathResource("/"));
    resourceHandler.setDirectoriesListed(true);

    HandlerList handlerList = new HandlerList();
    handlerList.addHandler(resourceHandler);
    handlerList.addHandler(ctx);
    server.setHandler(handlerList);

    server.start();
    server.join();
  }

  public static class ChatServlet extends WebSocketServlet {
    @Override
    public void configure(WebSocketServletFactory factory) {
      factory.register(ServerWebSocket.class);
    }
  }
}
