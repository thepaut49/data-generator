package com.thepaut.backend.containers.postgressqltestcontainer;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresSqlTestContainer extends PostgreSQLContainer<PostgresSqlTestContainer> {

    static PostgresSqlTestContainer postgresContainer;

    public static final String DOCKER_IMAGE_NAME_POSTGRESQL = "postgres:16.2";

    private PostgresSqlTestContainer() {
        super(DOCKER_IMAGE_NAME_POSTGRESQL);
    }

    public static PostgresSqlTestContainer getInstance() {
        if (postgresContainer == null) {
            postgresContainer = new PostgresSqlTestContainer();
        }
        return postgresContainer;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}