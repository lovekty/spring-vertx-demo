package me.tony.spring.vertx.demo.config;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import me.tony.spring.vertx.demo.route.RouteInfo;
import me.tony.spring.vertx.demo.verticle.*;
import me.tony.spring.vertx.demo.verticle.factory.SpringVerticleFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author tony.zhuby
 */
@Configuration
public class VertxConfiguration {

    @Value("${vertx.http.port:#{8080}}")
    private int httpPort;

    @Value("${vertx.verticle.producer.delay:#{100}}")
    private long delay;

    @Bean
    public Vertx vertx() {
        return Vertx.vertx();
    }

    @Bean
    public SpringVerticleFactory verticleFactory(@Nullable List<VerticleDescriptor> descriptors) {
        return new SpringVerticleFactory(descriptors);
    }

    @Bean
    public SelfDeployVerticleDescriptor<HttpServerVerticle> httpVerticle(@Nullable List<RouteInfo> routeInfos) {
        return new SelfDeployVerticleDescriptor<>() {
            @Override
            public String verticleName() {
                return "http_server";
            }

            @Override
            public HttpServerVerticle createVerticle() {
                return new HttpServerVerticle(httpPort, routeInfos);
            }

            @Override
            public boolean isWorker() {
                return false;
            }
        };
    }

    @Bean
    public SelfDeployVerticleDescriptor<ConsumerVerticle> consumerVerticle() {
        return new SelfDeployVerticleDescriptor<>() {
            @Override
            public String verticleName() {
                return "demo_consumer";
            }

            @Override
            public Verticle createVerticle() {
                return new ConsumerVerticle();
            }
        };
    }

    @Bean
    public VerticleDescriptor<ProducerVerticle> producerVerticle() {
        return new VerticleDescriptor<>() {
            @Override
            public String verticleName() {
                return "demo_producer";
            }

            @Override
            public ProducerVerticle createVerticle() {
                return new ProducerVerticle(delay);
            }
        };
    }
}
