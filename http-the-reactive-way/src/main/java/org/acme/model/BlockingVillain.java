
package org.acme.model;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import io.smallrye.mutiny.Uni;

import java.util.Random;

@MongoEntity(collection = "villains")
public class BlockingVillain extends PanacheMongoEntity {

    public String name;
    public int level;
    public String image;

    public static BlockingVillain findRandom() {
        Random random = new Random();
        long c  = BlockingVillain.count();
        int idx = random.nextInt((int) c);
        return BlockingVillain.findAll().page(idx, 1).firstResult();
    }
}
