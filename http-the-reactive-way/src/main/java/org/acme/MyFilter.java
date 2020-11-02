package org.acme;

import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;

public class MyFilter {


    // @RouteFilter, RoutingContext as param
    // end vs. next
    // Do not block
    @RouteFilter
    public void filter(RoutingContext rc) {
        rc.response().putHeader("X-Name", "Clement");
//        rc.response().end("NOPE!");
        rc.next();
    }

//    @RouteFilter
//    public void block(RoutingContext rc) throws InterruptedException {
//        Thread.sleep(10000);
//        rc.next();
//    }

}
