package org.acme.kafka;

import io.smallrye.reactive.messaging.kafka.IncomingKafkaRecordMetadata;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class KafkaConsumerBean {

    @Incoming("my-kafka-consumer")
    public CompletionStage<Void> consume(Message<Record<String, Integer>> m) {
        System.out.println("Got " + m.getPayload().key() + " / " + m.getPayload().value());

        m.getMetadata(IncomingKafkaRecordMetadata.class).ifPresent(meta -> {
            System.out.println("Topic: " + meta.getTopic() + ", Partition: " + meta.getPartition() + ", Offset:" + meta.getOffset());
        });

        return m.ack();
    }

}
