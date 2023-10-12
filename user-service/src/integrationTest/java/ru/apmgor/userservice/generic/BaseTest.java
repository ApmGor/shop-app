package ru.apmgor.userservice.generic;

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

    private static final String INIT_PATH =
            Paths.get("").toAbsolutePath().toString();

    private static final Service POSTGRES = new Service(
            "postgres",
            5432,
            INIT_PATH + "/init",
            "/docker-entrypoint-initdb.d",
            "r2dbc:postgresql://user_user:user_pwd@%s:%s/user_db"
    );

    @Container
    private static final GenericContainer<?> postgres =
            new GenericContainer<>(DockerImageName.parse(POSTGRES.name()))
                    .withEnv("POSTGRES_USER", "user_user")
                    .withEnv("POSTGRES_PASSWORD", "user_pwd")
                    .withEnv("POSTGRES_DB", "user_db")
                    .withExposedPorts(POSTGRES.port())
                    .withFileSystemBind(POSTGRES.resourcePath(), POSTGRES.containerPath(), BindMode.READ_ONLY)
                    .waitingFor(Wait.forListeningPort());

    @DynamicPropertySource
    private static void postgresProperties(final DynamicPropertyRegistry registry) {
        System.out.println(INIT_PATH + "/init/init-user-service.sql");
        var url = String.format(POSTGRES.uri(), postgres.getHost(), postgres.getMappedPort(POSTGRES.port()));
        registry.add("spring.r2dbc.url", () -> url);
    }
}
