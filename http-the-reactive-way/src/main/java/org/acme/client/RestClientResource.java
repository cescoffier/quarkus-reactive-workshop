package org.acme.client;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;

/**
 * DO NOT FORGET TO START THE SERVICE!
 * Delegate all methods, duration 500 ms.
 */
@Path("/rest-client")
@Produces(MediaType.TEXT_PLAIN)
public class RestClientResource {

    @Inject
    @RestClient MyRemoteService rest;

    @Path("/greetings") @GET
    public Uni<String> greeting() {
        return rest.greeting();
    }

    @Path("/delayed") @GET
    public Uni<String> delayed() {
        return rest.delayed()
                .ifNoItem().after(Duration.ofMillis(500)).recoverWithItem("Bonjour!");
    }

    @Path("/random-delay") @GET
    public Uni<String> randomDelay() {
        return rest.randomDelay()
                .ifNoItem().after(Duration.ofMillis(500)).recoverWithItem("Bonjour!");
    }

    @Path("/random-delay-retry") @GET
    public Uni<String> randomDelayWithRetry() {
        return rest.randomDelay()
                .onFailure().retry().withBackOff(Duration.ofMillis(10)).atMost(3)
                .onFailure().recoverWithItem("D'oh!");
    }

    @Path("/malformed") @GET
    public Uni<String> malformed() {
        return rest.malformed()
                .onFailure().recoverWithItem(f -> "Malformed: " + f);
    }

    @Path("/empty") @GET
    public Uni<String> empty() {
        return rest.empty()
                .onFailure().recoverWithItem(f -> "Empty: " + f);
    }

    @Path("/random") @GET
    public Uni<String> random() {
        return rest.random()
                .onFailure().recoverWithItem(f -> "Random: " + f);
    }

    @Path("/reset") @GET
    public Uni<String> reset() {
        return rest.reset()
                .onFailure().recoverWithItem(f -> "Reset: " + f);
    }
}
