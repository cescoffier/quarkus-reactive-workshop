package org.acme;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.model.BlockingVillain;
import org.acme.model.Villain;
import org.jboss.resteasy.annotations.SseElementType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/villains")
@Produces(MediaType.APPLICATION_JSON)
public class MyResource {


    // GET /blocking -> Blocking Villain

    @GET
    @Path("/blocking")
    public BlockingVillain getRandomVillain() {
        return BlockingVillain.findRandom();
    }

    // GET /async -> Villain as Uni
    @GET
    @Path("/async")
    public Uni<Villain> getRandomVillainAsync() {
        return Villain.findRandom();
    }

    // GET /stream -> stream all villains

    @GET
    @Path("/stream")
    public Multi<Villain> getVillainsAsBlob() {
        return Villain.streamAll();
    }

    // GET /sse -> SSE (Need @Produces(MediaType.SERVER_SENT_EVENTS) and @SseElementType(MediaType.APPLICATION_JSON))

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @Path("/sse")
    @SseElementType(MediaType.APPLICATION_JSON)
    public Multi<Villain> getVillainsAsSse() {
        return Villain.streamAll();
    }

}
