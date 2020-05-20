package me.tony.spring.vertx.demo.verticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import org.apache.commons.lang3.StringUtils;

/**
 * @author tony.zhuby
 */
public interface SelfDeployVerticleDescriptor<T extends Verticle> extends VerticleDescriptor<T> {
    /**
     * 是否worker verticle
     *
     * @return is worker verticle
     */
    default boolean isWorker() {
        return true;
    }

    /**
     * worker verticle的实例数量
     *
     * @return num of instances
     */
    default int instances() {
        return Runtime.getRuntime().availableProcessors() * 2;
    }

    /**
     * worker verticle独立线程池（如果需要）的名称
     *
     * @return name of worker pool
     */
    default String workerPoolName() {
        return null;
    }

    /**
     * worker verticle独立线程池（如果需要）的大小
     *
     * @return size of worker pool
     */
    default int workerPoolSize() {
        return instances();
    }

    /**
     * 将本descriptor描述的verticle部署到参数Vertx中<br>
     * 这里需要注意的是，不一定非得用这个方法部署。<br>
     * 在其他地方使用{@link Vertx#deployVerticle(String)}或者{@link Vertx#deployVerticle(String, DeploymentOptions)}等方式部署依然是被允许的。<br>
     * 此外对于标准verticle，用spring di的方式使用{@link Vertx#deployVerticle(Verticle)} 去部署似乎更合适一点。<br>
     * 参考{@link me.tony.spring.vertx.demo.AppMain#launch}
     *
     * @param vertx for deploy
     */
    default void deploy(Vertx vertx) {
        final var options = new DeploymentOptions();
        final var isWorker = isWorker();
        options.setWorker(isWorker);
        final var instances = instances();
        if (isWorker && instances > 1) {
            options.setInstances(instances);
        }
        final var workerPoolName = workerPoolName();
        if (isWorker && StringUtils.isNotBlank(workerPoolName)) {
            options.setWorkerPoolName(workerPoolName());
        }
        final var workerPoolSize = workerPoolSize();
        if (isWorker && StringUtils.isNotBlank(workerPoolName) && workerPoolSize > 0) {
            options.setWorkerPoolSize(workerPoolSize());
        }
        vertx.deployVerticle(PREFIX + ":" + verticleName(), options);
    }
}
