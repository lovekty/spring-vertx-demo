package me.tony.spring.vertx.demo;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import me.tony.spring.vertx.demo.verticle.MainVerticle;
import me.tony.spring.vertx.demo.verticle.factory.SpringVerticleFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import static me.tony.spring.vertx.demo.verticle.VerticleDescriptor.PREFIX;

/**
 * @author tony.zhuby
 */
@SpringBootApplication
public class AppMain {

    private final Vertx vertx;

    private final SpringVerticleFactory verticleFactory;

    private final MainVerticle mainVerticle;

    public AppMain(Vertx vertx,
                   SpringVerticleFactory verticleFactory,
                   MainVerticle mainVerticle) {
        this.vertx = vertx;
        this.verticleFactory = verticleFactory;
        this.mainVerticle = mainVerticle;
    }

    public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
    }

    @EventListener
    public void launch(ApplicationReadyEvent event) {
        vertx.registerVerticleFactory(verticleFactory);
        vertx.deployVerticle(mainVerticle);
        vertx.deployVerticle(PREFIX + ":demo_producer", new DeploymentOptions().setWorker(true).setInstances(4).setWorkerPoolName("demo-producer"));
    }
}
