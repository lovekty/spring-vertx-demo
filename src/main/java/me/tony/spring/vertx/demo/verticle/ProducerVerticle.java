package me.tony.spring.vertx.demo.verticle;

import io.vertx.core.AbstractVerticle;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tony.zhuby
 */
@Slf4j
public class ProducerVerticle extends AbstractVerticle {

    private final long delay;

    public ProducerVerticle(long delay) {
        this.delay = delay;
    }

    @Override
    public void start() throws Exception {
        vertx.setPeriodic(delay, id -> {
            log.info("send message to address demo");
            vertx.eventBus().send("demo", String.format("msg from periodic:%d", id));
        });
    }
}
