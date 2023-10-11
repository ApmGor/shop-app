package ru.apmgor.productservice.generic;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.nio.file.Paths;

@Testcontainers
public abstract class BaseTest {

    private static final String CURRENT_DIR = Paths.get("").toAbsolutePath().toString();

    private static final Service MONGO = new Service(
            "mongo",
            27017,
            CURRENT_DIR + "/init/init-product-mongo.js",
            "/docker-entrypoint-initdb.d/init-product-mongo.js",
            "mongodb://product_user:product_pwd@%s:%s/product_db"
    );

    @Container
    private static final GenericContainer<?> mongo =
            new GenericContainer<>(DockerImageName.parse(MONGO.name()))
                    .withExposedPorts(MONGO.port())
                    .withFileSystemBind(MONGO.resourcePath(), MONGO.containerPath(), BindMode.READ_ONLY)
                    .waitingFor(Wait.forListeningPort());

    @DynamicPropertySource
    private static void mongoProperties(final DynamicPropertyRegistry registry) {
        System.out.println(Paths.get("").toAbsolutePath());
        var mongoUri = String.format(MONGO.uri(), mongo.getHost(), mongo.getMappedPort(MONGO.port()));
        registry.add("spring.data.mongodb.uri", () -> mongoUri);
    }
}
