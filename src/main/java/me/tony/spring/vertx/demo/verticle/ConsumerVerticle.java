package me.tony.spring.vertx.demo.verticle;

import io.vertx.core.AbstractVerticle;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author tony.zhuby
 */
@Slf4j
public class ConsumerVerticle extends AbstractVerticle {

    private final Random r = new Random();

    @Override
    public void start() {
        vertx.eventBus().<String>localConsumer("demo", msg -> {
            final var body = msg.body();
            log.info("got a message: {}", body);
            try {
                Thread.sleep(r.nextInt(100));
                log.info("consumer end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
