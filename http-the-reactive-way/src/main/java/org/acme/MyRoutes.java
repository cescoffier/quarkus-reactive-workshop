package org.acme;

import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.ReactiveRoutes;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.acme.model.BlockingVillain;
import org.acme.model.Hero;
import org.acme.model.Villain;
import org.jboss.resteasy.annotations.SseElementType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;

@RouteBase(path = "/heroes")
public class MyRoutes {

    // /hero1, application/json using routing context

    @Route(path = "/hero1", produces = "application/json")
    public void produceHero(RoutingContext rc) {
        Hero.findRandom()
                .subscribe().with(
                        h -> rc.response().end(Json.encode(h)),
                rc::fail
        );
    }

    // /hero2, application/json returning Uni

    @Route(path = "/hero2", produces = "application/json")
    public Uni<Hero> produceHero() {
        return Hero.findRandom();
    }

    // /multi, application/json returning Multi - streamAll

    @Route(path = "/multi", produces = "application/json")
    public Multi<Hero> produceHeroes() {
        return Hero.streamAll();
    }

    // /json-array, application/json returning Multi - ReactiveRoutes.asJsonArray
    // Throttled with Multi.createBy().combining().streams(Multi.createFrom().ticks().every(Duration.ofSeconds(1)), Hero.streamAll())
    // curl http://localhost:8080/heroes/json-array -v -N

    @Route(path = "/json-array")
    public Multi<Hero> produceHeroesAsJsonArray() {
        return ReactiveRoutes.asJsonArray(
                Multi.createBy().combining().streams(Multi.createFrom().ticks().every(Duration.ofSeconds(1)), Hero.streamAll())
                    .asTuple()
                        .onItem().transform(t -> (Hero) t.getItem2())
        );
    }

    // /json-array/:count, application/json returning Multi - ReactiveRoutes.asJsonArray, stream at most count
    // curl http://localhost:8080/heroes/json-array -v -N
    @Route(path = "/json-array/:count")
    // curl http://localhost:8080/heroes/json-array/5 -v -N
    public Multi<Hero> produceHeroesAsJsonArray(@Param(value = "count") String count) {
        return ReactiveRoutes.asJsonArray(
                Multi.createBy().combining().streams(Multi.createFrom().ticks().every(Duration.ofSeconds(1)), Hero.streamAll())
                        .asTuple()
                        .transform().byTakingFirstItems(Integer.parseInt(count))
                        .onItem().transform(t -> (Hero) t.getItem2())
        );
    }

    // /sse, ReactiveRoutes.asEventStream, throttled
    @Route(path = "/sse")
    public Multi<Hero> produceHeroesAsSSE() {
        return ReactiveRoutes.asEventStream(
                Multi.createBy().combining().streams(Multi.createFrom().ticks().every(Duration.ofSeconds(1)), Hero.streamAll())
                        .asTuple()
                        .onItem().transform(t -> (Hero) t.getItem2())
        );
    }

    // Other features:
    // * bean validation

}
