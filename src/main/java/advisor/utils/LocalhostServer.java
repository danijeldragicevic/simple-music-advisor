package advisor.utils;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class  LocalhostServer {
    private static final int HTTP_HOME_PORT = 8080;
    public static final int BACKLOG = 0;

    public LocalhostServer() {}

    public static HttpServer initAndStart() {
        HttpServer server = null;
        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress(HTTP_HOME_PORT), BACKLOG);
            server.start();
            
            return server;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
