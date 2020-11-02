package org.acme.kafka;

import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class KafkaProducerBean {

    @Outgoing("my-kafka-producer")
    public Multi<Record<String, Integer>> generate() {
        return Multi.createFrom().range(0, 10)
                .onItem().transform(i -> Record.of("my-key", i));
    }

}
