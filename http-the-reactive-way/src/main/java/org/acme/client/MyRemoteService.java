package org.acme.client;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@RegisterRestClient(baseUri = "http://localhost:8082") // Don't do this ;-)
public interface MyRemoteService {


    @GET @Path("/greetings") Uni<String> greeting();

    @GET @Path("/delayed") Uni<String> delayed();

    @GET @Path("/random-delay") Uni<String> randomDelay();

    @GET @Path("/malformed") Uni<String> malformed();

    @GET @Path("/empty") Uni<String> empty();

    @GET @Path("/random") Uni<String> random();

    @GET @Path("/reset") Uni<String> reset();
}
