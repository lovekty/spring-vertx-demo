package me.tony.spring.vertx.demo.verticle;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

/**
 * @author tony.zhuby
 */
public interface VerticleDescriptor<T extends Verticle> {
    String PREFIX = "spring";

    /**
     * {@link me.tony.spring.vertx.demo.verticle.factory.SpringVerticleFactory} 创建verticle时会用到
     *
     * @return name of the verticle for deploy
     * @see Vertx#deployVerticle(String)
     */
    String verticleName();

    /**
     * {@link me.tony.spring.vertx.demo.verticle.factory.SpringVerticleFactory} 创建verticle时底层调用的实际逻辑
     *
     * @return verticle instance of Verticle T
     * @see me.tony.spring.vertx.demo.verticle.factory.SpringVerticleFactory#createVerticle(String, ClassLoader)
     */
    T createVerticle();

}
