package org.acme.client;

import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;

/**
 * DO NOT FORGET TO START THE SERVICE!
 * Need: the smallrye-mutiny-vertx-web-client dependency
 */
@Path("/web-client")
@Produces(MediaType.TEXT_PLAIN)
public class WebClientResource {

    @Inject Vertx vertx;


    private WebClient client;

    @PostConstruct
    public void init() {
        client = WebClient.create(vertx, new WebClientOptions()
                .setDefaultHost("localhost")
                .setDefaultPort(8082)
                .setMaxPoolSize(5) // Concurrency
        );
    }

    private Uni<String> call(String path) {
        return client
                .get(path).send()
                .onItem().transform(HttpResponse::bodyAsString);
    }


    @Path("/greetings") @GET
    public Uni<String> greeting() {
        return call("/greetings");
    }

    @Path("/delayed") @GET
    public Uni<String> delayed() {
        return call("/delayed")
                .ifNoItem().after(Duration.ofMillis(500)).recoverWithItem("Bonjour!");
    }

    @Path("/random-delay") @GET
    public Uni<String> randomDelay() {
        return call("/random-delay")
                .ifNoItem().after(Duration.ofMillis(500)).recoverWithItem("Bonjour!");
    }

    @Path("/random-delay-retry") @GET
    public Uni<String> randomDelayWithRetry() {
        return call("/random-delay")
                .onFailure().retry().withBackOff(Duration.ofMillis(10)).atMost(3)
                .onFailure().recoverWithItem("D'oh!");
    }

    @Path("/malformed") @GET
    public Uni<String> malformed() {
        return call("/malformed")
                .onFailure().recoverWithItem(f -> "Malformed: " + f);
    }

    @Path("/empty") @GET
    public Uni<String> empty() {
        return call("/empty")
                .onFailure().recoverWithItem(f -> "Empty: " + f);
    }

    @Path("/random") @GET
    public Uni<String> random() {
        return call("/random")
                .onFailure().recoverWithItem(f -> "Random: " + f);
    }

    @Path("/reset") @GET
    public Uni<String> reset() {
        return call("/reset")
                .onFailure().recoverWithItem(f -> "Reset: " + f);
    }
}
