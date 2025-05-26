package demo;

import io.helidon.logging.common.LogConfig;
import io.helidon.webserver.WebServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static void logThread() {
        var current = Thread.currentThread();
        logger.info("Thread name: {}, id: {}, isVirtual: {}", current.getName(), current.threadId(), current.isVirtual());
    }

    public static void main(String[] args) {
        LogConfig.configureRuntime();

        WebServer.builder()
                .host("localhost")
                .port(8080)
                .routing(routing -> routing
                        .get("/hello", (req, res) -> {
                            logThread();
                            res.send("Hello World!");
                        }))
                .build()
                .start();

        logger.info("Started!");
    }
}
