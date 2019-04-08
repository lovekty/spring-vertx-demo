package me.tony.spring.vertx.demo.verticle.factory;

import io.vertx.core.Verticle;
import io.vertx.core.VertxException;
import io.vertx.core.spi.VerticleFactory;
import me.tony.spring.vertx.demo.verticle.VerticleDescriptor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static me.tony.spring.vertx.demo.verticle.VerticleDescriptor.PREFIX;

/**
 * @author tony.zhuby
 */
public class SpringVerticleFactory implements VerticleFactory {

    private final Map<String, List<VerticleDescriptor>> map;

    public SpringVerticleFactory(List<VerticleDescriptor> descriptors) {
        map = descriptors == null || descriptors.isEmpty()
                ? Collections.emptyMap()
                : descriptors.stream().collect(Collectors.groupingBy(VerticleDescriptor::verticleName));
    }

    @Override
    public String prefix() {
        return PREFIX;
    }

    @Override
    public Verticle createVerticle(String verticleName, ClassLoader classLoader) {
        final var name = VerticleFactory.removePrefix(verticleName);
        var opt = Optional.<Verticle>empty();
        for (VerticleDescriptor descriptor : map.getOrDefault(name, Collections.emptyList())) {
            try {
                final var instance = descriptor.createVerticle();
                if (instance != null) {
                    opt = Optional.of(instance);
                    break;
                }
            } catch (Exception ignored) {

            }
        }
        return opt.orElseThrow(() -> new VertxException(String.format("create verticle for name: %s failed!", verticleName)));
    }
}
