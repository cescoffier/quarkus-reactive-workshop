package org.acme;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class MyApp {

    @Outgoing("source")
    public Multi<Message<Integer>> generate() {
        return Multi.createFrom().range(0, 10)
                .onItem().transform(i -> Message.of(i, () -> {
                    System.out.println("Acked " + i);
                    return CompletableFuture.completedFuture(null);
                }));
    }

    @Incoming("source")
    @Acknowledgment(Acknowledgment.Strategy.NONE)
    public void consume(int s) {
        System.out.println(s);
    }

}
