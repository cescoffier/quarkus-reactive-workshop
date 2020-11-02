package org.acme;

import io.vertx.ext.web.Router;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class RouterUsage {

    // @Observer the router
    // Example of handler
    // Example with filter (add header + order)
    public void init(@Observes Router router) {
        router.get().order(0).handler(rc -> {
            rc.response().putHeader("X-Vendor", "acme");
            rc.next();
        });
        router.get("/foo").handler(rc -> rc.response().end("hello"));
    }

}
