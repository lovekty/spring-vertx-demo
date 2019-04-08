package me.tony.spring.vertx.demo.verticle;

import io.vertx.core.AbstractVerticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author tony.zhuby
 */
@Component
public class MainVerticle extends AbstractVerticle {

    private final List<SelfDeployVerticleDescriptor> descriptors;

    @Autowired
    public MainVerticle(@Nullable List<SelfDeployVerticleDescriptor> descriptors) {
        this.descriptors = descriptors;
    }

    @Override
    public void start() {
        Optional.ofNullable(descriptors).ifPresent(they -> they.forEach(it -> it.deploy(vertx)));
    }
}
