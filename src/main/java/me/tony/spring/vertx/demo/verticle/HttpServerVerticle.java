package me.tony.spring.vertx.demo.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import me.tony.spring.vertx.demo.route.RouteInfo;

import java.util.List;
import java.util.Optional;

/**
 * @author tony.zhuby
 */
public class HttpServerVerticle extends AbstractVerticle {

    private final int port;
    private final List<RouteInfo> routeInfos;

    public HttpServerVerticle(int port, List<RouteInfo> routeInfos) {
        this.port = port;
        this.routeInfos = routeInfos;
    }

    @Override
    public void start(Future<Void> startFuture) {
        final var router = Router.router(vertx);
        Optional.ofNullable(routeInfos).ifPresent(infos -> infos.forEach(info -> RouteInfo.route(router, info)));
        vertx.createHttpServer().requestHandler(router).listen(port, ar -> {
            if (ar.succeeded()) {
                startFuture.complete();
            } else {
                startFuture.fail(ar.cause());
            }
        });
    }
}
