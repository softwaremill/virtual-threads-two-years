package org.acme;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Path("/hello")
public class GreetingResource {

    private static final Logger logger = LoggerFactory.getLogger(GreetingResource.class);

    private static void logThread() {
        var current = Thread.currentThread();
        logger.info("Thread name: {}, id: {}, isVirtual: {}", current.getName(), current.threadId(), current.isVirtual());
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RunOnVirtualThread
    public String hello() {
        logThread();

        List<Greeting> greetings = Greeting.listAll();

        logThread();

        String names = greetings.stream().map(g-> g.name)
                .collect(Collectors.joining (", "));
        return "I've said hello to " + names;
    }
}
