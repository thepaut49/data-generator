package com.thepaut.backend.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thepaut.backend.containers.keycloaktestcontainer.KeycloakTestContainer;
import com.thepaut.backend.containers.postgressqltestcontainer.PostgresSqlTestContainer;
import com.thepaut.backend.dto.SampleDataCategoryCreationDto;
import com.thepaut.backend.dto.SampleDataCategoryDto;
import com.thepaut.backend.dto.audit.SampleDataCategoryAuditDto;
import com.thepaut.backend.model.data.SampleDataCategory;
import com.thepaut.backend.model.data.audit.SampleDataCategoryAudit;
import com.thepaut.backend.utils.TestConstants;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class SampleDataCategoryMapperTest {
    private final SampleDataCategoryMapper mapper = SampleDataCategoryMapper.INSTANCE;

    @LocalServerPort
    private int port;

    public static PostgreSQLContainer<PostgresSqlTestContainer> postgreSQLContainer = PostgresSqlTestContainer.getInstance();

    public static KeycloakTestContainer keycloakContainer = KeycloakTestContainer.getInstance();

    protected static final ObjectMapper objectMapper = new ObjectMapper();

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> postgreSQLContainer.getJdbcUrl());
        registry.add("spring.datasource.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> postgreSQLContainer.getPassword());

        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> keycloakContainer.getAuthServerUrl() + "/realms/myrealm");
        registry.add("spring.security.oauth2.resourceserver.jwk-set-uri", () -> keycloakContainer.getAuthServerUrl() + "/realms/myrealm/protocol/openid-connect/certs");
    }

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
        keycloakContainer.start();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
        keycloakContainer.stop();
    }

    @Test
    void convertUpdateDtoToEntity() {
        var now = LocalDateTime.now();
        var yesterday = LocalDateTime.now().minusDays(1);

        var updateDto = createSampleDataCategoryDto(TestConstants.ID, TestConstants.CATEGORY_NAME_5, TestConstants.VERSION_CURRENT, now, yesterday, TestConstants.MODIFIED_BY_USER_1);

        var entity = mapper.convertUpdateDtoToEntity(updateDto);

        Assertions.assertEquals(TestConstants.ID, entity.getId());
        Assertions.assertEquals(TestConstants.CATEGORY_NAME_5, entity.getName());
        Assertions.assertEquals(TestConstants.VERSION_CURRENT, entity.getVersion());
        Assertions.assertEquals(now, entity.getModifiedAt());
        Assertions.assertEquals(TestConstants.MODIFIED_BY_USER_1, entity.getModifiedBy());
        Assertions.assertEquals(1, entity.getVersions().size());
    }

    @Test
    void convertEntityToUpdateDto() {
        var now = LocalDateTime.now();
        var yesterday = LocalDateTime.now().minusDays(1);

        var entity = createEntity(TestConstants.ID, TestConstants.CATEGORY_NAME_5, TestConstants.VERSION_CURRENT, now, yesterday, TestConstants.MODIFIED_BY_USER_1);

        var updateDto = mapper.convertEntityToUpdateDto(entity);

        Assertions.assertEquals(TestConstants.ID, updateDto.getId());
        Assertions.assertEquals(TestConstants.CATEGORY_NAME_5, updateDto.getName());
        Assertions.assertEquals(TestConstants.VERSION_CURRENT, updateDto.getVersion());
        Assertions.assertEquals(now, updateDto.getModifiedAt());
        Assertions.assertEquals(TestConstants.MODIFIED_BY_USER_1, updateDto.getModifiedBy());
        Assertions.assertEquals(1, updateDto.getVersions().size());
    }

    @Test
    void convertCreationDtoToEntity() {
        var creationDto = new SampleDataCategoryCreationDto(TestConstants.CATEGORY_NAME_5);

        var entity = mapper.convertCreationDtoToEntity(creationDto);

        Assertions.assertNull(entity.getId());
        Assertions.assertEquals(TestConstants.CATEGORY_NAME_5, entity.getName());
        Assertions.assertEquals(TestConstants.VERSION_FIRST, entity.getVersion());
        Assertions.assertNull(entity.getModifiedBy());
        Assertions.assertEquals(0, entity.getVersions().size());
    }


    @Test
    void convertAuditToEntity() {
        var now = LocalDateTime.now();
        var audit = createAudit(TestConstants.ID, TestConstants.CATEGORY_NAME_5, TestConstants.VERSION_OLD, now, TestConstants.MODIFIED_BY_USER_1);
        var entity = mapper.convertAuditToEntity(audit);

        Assertions.assertEquals(TestConstants.ID, entity.getId());
        Assertions.assertEquals(TestConstants.CATEGORY_NAME_5, entity.getName());
        Assertions.assertEquals(TestConstants.VERSION_OLD, entity.getVersion());
        Assertions.assertEquals(now, entity.getModifiedAt());
        Assertions.assertEquals(TestConstants.MODIFIED_BY_USER_1, entity.getModifiedBy());
    }

    @Test
    void convertEntityToAudit() {
        var now = LocalDateTime.now();
        var yesterday = LocalDateTime.now().minusDays(1);
        var entity = createEntity(TestConstants.ID, TestConstants.CATEGORY_NAME_5, TestConstants.VERSION_CURRENT, now, yesterday, TestConstants.MODIFIED_BY_USER_1);
        var audit = mapper.convertEntityToAudit(entity);

        Assertions.assertEquals(TestConstants.ID, audit.getId());
        Assertions.assertEquals(TestConstants.CATEGORY_NAME_5, audit.getName());
        Assertions.assertEquals(TestConstants.VERSION_CURRENT, audit.getVersion());
        Assertions.assertEquals(now, audit.getModifiedAt());
        Assertions.assertEquals(TestConstants.MODIFIED_BY_USER_1, audit.getModifiedBy());
    }

    private static SampleDataCategoryCreationDto createSampleDataCategoryCreationDto(String categoryName, Long version, LocalDateTime now, LocalDateTime yesterday, String user) {
        var updateDto = new SampleDataCategoryCreationDto(categoryName);
        return updateDto;
    }


    private static SampleDataCategoryDto createSampleDataCategoryDto(Long id, String categoryName, Long version, LocalDateTime now, LocalDateTime yesterday, String user) {
        var updateDto = new SampleDataCategoryDto();
        updateDto.setId(id);
        updateDto.setName(categoryName);
        updateDto.setVersion(version);
        updateDto.setModifiedAt(now);
        updateDto.setModifiedBy(user);
        updateDto.setVersions(List.of(createAuditDto(TestConstants.ID, TestConstants.CATEGORY_NAME_1, TestConstants.VERSION_OLD, yesterday, TestConstants.MODIFIED_BY_USER_2)));
        return updateDto;
    }

    private static SampleDataCategoryAuditDto createAuditDto(Long id, String name, Long version, LocalDateTime localDateTime, String user ) {
        SampleDataCategoryAuditDto auditDto = new SampleDataCategoryAuditDto();
        auditDto.setId(id);
        auditDto.setName(name);
        auditDto.setVersion(version);
        auditDto.setModifiedAt(localDateTime);
        auditDto.setModifiedBy(user);
        return auditDto;
    }

    private static SampleDataCategory createEntity(Long id, String categoryName, Long version, LocalDateTime now, LocalDateTime yesterday, String user) {
        var entity = new SampleDataCategory();
        entity.setId(id);
        entity.setName(categoryName);
        entity.setVersion(version);
        entity.setModifiedAt(now);
        entity.setModifiedBy(user);
        entity.setVersions(List.of(createAudit(TestConstants.ID, TestConstants.CATEGORY_NAME_1, TestConstants.VERSION_OLD, yesterday, TestConstants.MODIFIED_BY_USER_2)));
        return entity;
    }

    private static SampleDataCategoryAudit createAudit(Long id, String name, Long version, LocalDateTime localDateTime, String user ) {
        SampleDataCategoryAudit auditEntity = new SampleDataCategoryAudit();
        auditEntity.setId(id);
        auditEntity.setName(name);
        auditEntity.setVersion(version);
        auditEntity.setModifiedAt(localDateTime);
        auditEntity.setModifiedBy(user);
        return auditEntity;
    }
}