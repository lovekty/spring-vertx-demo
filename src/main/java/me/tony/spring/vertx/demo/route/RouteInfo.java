package me.tony.spring.vertx.demo.route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;

/**
 * @author tony.zhuby
 */
public interface RouteInfo {
    /**
     * 将route信息配置到router中
     *
     * @param router for route
     * @param info   to describe a route
     */
    static void route(Router router, RouteInfo info) {
        if (router == null || info == null || info.handler() == null) {
            return;
        }
        final var route = router.route();
        if (StringUtils.isNotBlank(info.path())) {
            route.path(info.path().trim());
        } else if (StringUtils.isNotBlank(info.pathRegex())) {
            route.pathRegex(info.pathRegex().trim());
        }
        if (info.method() != null) {
            route.method(info.method());
        }
        route.handler(info.handler());
    }

    /**
     * 支持的http method。null 表示不限制
     *
     * @return http method to route
     * @see io.vertx.ext.web.Route#method(HttpMethod)
     */
    default HttpMethod method() {
        return null;
    }

    /**
     * route的path
     *
     * @return path to route
     * @see io.vertx.ext.web.Route#path(String)
     */
    default String path() {
        return null;
    }

    /**
     * ${@link this#path()} 没有设置时，正则才可以生效
     *
     * @return path regex to route
     * @see io.vertx.ext.web.Route#pathRegex(String)
     */
    default String pathRegex() {
        return null;
    }

    /**
     * 该route的处理器
     *
     * @return handler for this route
     * @see io.vertx.ext.web.Route#handler(Handler)
     */
    Handler<RoutingContext> handler();

}
