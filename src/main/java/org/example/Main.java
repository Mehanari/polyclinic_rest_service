package org.example;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.ext.RuntimeDelegate;
import org.example.server.PolyclinicApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

import java.net.URI;

public class    Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        logger.info("Starting the server...");
        final HttpServer server = HttpServer.create(new InetSocketAddress(getBaseURI(Constants.BASE_URI, Constants.PORT, Constants.APPLICATION_PATH).getPort()), 0);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.stop(0);
            logger.info("Server stopped.");
        }));

        HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(new PolyclinicApp(), HttpHandler.class);

        server.createContext(getBaseURI(Constants.BASE_URI, Constants.PORT, Constants.APPLICATION_PATH).getPath(), handler);

        server.start();

        logger.info("Application started.\nTry accessing the application at {}", getBaseURI(Constants.BASE_URI, Constants.PORT, Constants.APPLICATION_PATH, Constants.MEDICAL_CARDS_CONTROLLER_PATH));

        Thread.currentThread().join();
    }

    public static URI getBaseURI(String basePath, int port, String... path) {
        UriBuilder builder = UriBuilder.fromUri(basePath).port(port);
        for (String part : path) {
            builder.path(part);
        }
        URI uri = builder.build();
        System.out.println("uri: " + uri);
        return uri;
    }
}