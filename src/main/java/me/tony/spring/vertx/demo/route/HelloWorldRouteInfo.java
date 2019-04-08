package me.tony.spring.vertx.demo.route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author tony.zhuby
 */
@Component
public class HelloWorldRouteInfo implements RouteInfo, Handler<RoutingContext> {

    @Value("${vertx.http.route.hello_world:#{\"/helloworld\"}}")
    private String path;

    @Override
    public HttpMethod method() {
        return HttpMethod.GET;
    }

    @Override
    public String path() {
        return path;
    }

    @Override
    public Handler<RoutingContext> handler() {
        return this;
    }

    @Override
    public void handle(RoutingContext ctx) {
        ctx.response().end("hello world!");
    }
}
