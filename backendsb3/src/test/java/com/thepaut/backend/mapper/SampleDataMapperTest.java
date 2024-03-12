package com.thepaut.backend.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thepaut.backend.containers.keycloaktestcontainer.KeycloakTestContainer;
import com.thepaut.backend.containers.postgressqltestcontainer.PostgresSqlTestContainer;
import com.thepaut.backend.dto.SampleDataCreationDto;
import com.thepaut.backend.dto.SampleDataDto;
import com.thepaut.backend.dto.audit.SampleDataAuditDto;
import com.thepaut.backend.model.data.SampleData;
import com.thepaut.backend.model.data.SampleDataCategory;
import com.thepaut.backend.model.data.audit.SampleDataAudit;
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
class SampleDataMapperTest {
    private final SampleDataMapper mapper = SampleDataMapper.INSTANCE;

    @LocalServerPort
    private int port;

    public static PostgreSQLContainer<PostgresSqlTestContainer> postgreSQLContainer = PostgresSqlTestContainer.getInstance();

    public static KeycloakTestContainer keycloakContainer = KeycloakTestContainer.getInstance();

    private static final ObjectMapper objectMapper = new ObjectMapper();

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

        var updateDto = createSampleDataDto(TestConstants.ID, TestConstants.CATEGORY_ID, TestConstants.KEY_NAME, TestConstants.VALUE, true, TestConstants.VERSION_CURRENT, now, yesterday, TestConstants.MODIFIED_BY_USER_1);

        var entity = mapper.convertUpdateDtoToEntity(updateDto);

        Assertions.assertEquals(TestConstants.ID, entity.getId());
        Assertions.assertEquals(TestConstants.CATEGORY_ID, entity.getCategory().getId());
        Assertions.assertEquals(TestConstants.CATEGORY_NAME_5, entity.getCategory().getName());
        Assertions.assertEquals(TestConstants.KEY_NAME, entity.getKey());
        Assertions.assertNull(entity.getValue());
        Assertions.assertTrue(entity.isBlobTypeValue());
        Assertions.assertEquals(TestConstants.VALUE, entity.getBlobValue());
        Assertions.assertEquals(TestConstants.VERSION_CURRENT, entity.getVersion());
        Assertions.assertEquals(now, entity.getModifiedAt());
        Assertions.assertEquals(TestConstants.MODIFIED_BY_USER_1, entity.getModifiedBy());
        Assertions.assertEquals(1, entity.getVersions().size());
    }

    @Test
    void convertEntityToUpdateDto() {
        var now = LocalDateTime.now();
        var yesterday = LocalDateTime.now().minusDays(1);

        var entity = createEntity(TestConstants.ID, TestConstants.CATEGORY_ID, TestConstants.KEY_NAME, TestConstants.VALUE, false, TestConstants.VERSION_CURRENT, now, yesterday, TestConstants.MODIFIED_BY_USER_1);

        var updateDto = mapper.convertEntityToUpdateDto(entity);

        Assertions.assertEquals(TestConstants.ID, updateDto.getId());
        Assertions.assertEquals(TestConstants.CATEGORY_ID, updateDto.getCategoryId());
        Assertions.assertEquals(TestConstants.CATEGORY_NAME_5, updateDto.getCategoryName());
        Assertions.assertEquals(TestConstants.KEY_NAME, updateDto.getKey());
        Assertions.assertNull(updateDto.getBlobValue());
        Assertions.assertFalse(updateDto.isBlobTypeValue());
        Assertions.assertEquals(TestConstants.VALUE, updateDto.getValue());
        Assertions.assertEquals(TestConstants.VERSION_CURRENT, updateDto.getVersion());
        Assertions.assertEquals(now, updateDto.getModifiedAt());
        Assertions.assertEquals(TestConstants.MODIFIED_BY_USER_1, updateDto.getModifiedBy());
        Assertions.assertEquals(1, updateDto.getVersions().size());
    }

    @Test
    void convertCreationDtoToEntity() {
        var creationDto = new SampleDataCreationDto(TestConstants.CATEGORY_ID, TestConstants.KEY_NAME, null, true, TestConstants.VALUE);

        var entity = mapper.convertCreationDtoToEntity(creationDto);

        Assertions.assertNull(entity.getId());
        Assertions.assertEquals(TestConstants.CATEGORY_ID, entity.getCategory().getId());
        Assertions.assertEquals(TestConstants.KEY_NAME, entity.getKey());
        Assertions.assertNull(entity.getValue());
        Assertions.assertTrue(entity.isBlobTypeValue());
        Assertions.assertEquals(TestConstants.VALUE, entity.getBlobValue());
        Assertions.assertEquals(TestConstants.VERSION_FIRST, entity.getVersion());
        Assertions.assertNull(entity.getModifiedBy());
        Assertions.assertEquals(0, entity.getVersions().size());
    }


    @Test
    void convertAuditToEntity() {
        var now = LocalDateTime.now();
        var audit = createAudit(TestConstants.ID,TestConstants.CATEGORY_ID, TestConstants.KEY_NAME, TestConstants.VALUE_OLD, false, TestConstants.VERSION_OLD, now, TestConstants.MODIFIED_BY_USER_1);
        var entity = mapper.convertAuditToEntity(audit);

        Assertions.assertEquals(TestConstants.ID, entity.getId());
        Assertions.assertEquals(TestConstants.CATEGORY_ID, entity.getCategory().getId());
        Assertions.assertEquals(TestConstants.KEY_NAME, entity.getKey());
        Assertions.assertNull(entity.getBlobValue());
        Assertions.assertFalse(entity.isBlobTypeValue());
        Assertions.assertEquals(TestConstants.VALUE_OLD, entity.getValue());
        Assertions.assertEquals(TestConstants.VERSION_OLD, entity.getVersion());
        Assertions.assertEquals(now, entity.getModifiedAt());
        Assertions.assertEquals(TestConstants.MODIFIED_BY_USER_1, entity.getModifiedBy());
    }

    @Test
    void convertEntityToAudit() {
        var now = LocalDateTime.now();
        var yesterday = LocalDateTime.now().minusDays(1);
        var entity = createEntity(TestConstants.ID, TestConstants.CATEGORY_ID, TestConstants.KEY_NAME, TestConstants.VALUE, true , TestConstants.VERSION_CURRENT, now, yesterday, TestConstants.MODIFIED_BY_USER_1);
        var audit = mapper.convertEntityToAudit(entity);

        Assertions.assertEquals(TestConstants.ID, audit.getId());
        Assertions.assertEquals(TestConstants.CATEGORY_ID, audit.getCategoryId());
        Assertions.assertEquals(TestConstants.KEY_NAME, audit.getKey());
        Assertions.assertNull(audit.getValue());
        Assertions.assertTrue(audit.isBlobTypeValue());
        Assertions.assertEquals(TestConstants.VALUE, audit.getBlobValue());
        Assertions.assertEquals(TestConstants.VERSION_CURRENT, audit.getVersion());
        Assertions.assertEquals(now, audit.getModifiedAt());
        Assertions.assertEquals(TestConstants.MODIFIED_BY_USER_1, audit.getModifiedBy());
    }

    private static SampleDataCreationDto createSampleDataCreationDto(Long categoryId, String key, String value, boolean isBlobValue) {
        if (isBlobValue) {
            return new SampleDataCreationDto(categoryId, key, null, true, value);
        } else {
            return new SampleDataCreationDto(categoryId, key, value, false, null);
        }
    }


    private static SampleDataDto createSampleDataDto(Long id, Long categoryId, String key, String value, boolean isBlobValue, Long version, LocalDateTime now, LocalDateTime yesterday, String user) {
        var updateDto = new SampleDataDto();
        updateDto.setId(id);
        updateDto.setCategoryId(categoryId);
        updateDto.setCategoryName(TestConstants.CATEGORY_NAME_5);
        updateDto.setKey(key);
        updateDto.setBlobTypeValue(isBlobValue);
        if (isBlobValue) {
            updateDto.setBlobValue(value);
        } else {
            updateDto.setValue(value);
        }

        updateDto.setVersion(version);
        updateDto.setModifiedAt(now);
        updateDto.setModifiedBy(user);
        updateDto.setVersions(List.of(createAuditDto(TestConstants.ID, TestConstants.CATEGORY_ID,TestConstants.KEY_NAME_OLD, TestConstants.VALUE_OLD, false, TestConstants.VERSION_OLD, yesterday, TestConstants.MODIFIED_BY_USER_2)));
        return updateDto;
    }

    private static SampleDataAuditDto createAuditDto(Long id, Long categoryId, String key, String value, boolean isBlobValue, Long version, LocalDateTime localDateTime, String user ) {
        SampleDataAuditDto auditDto = new SampleDataAuditDto();
        auditDto.setId(id);
        auditDto.setCategoryId(categoryId);
        auditDto.setCategoryName(TestConstants.CATEGORY_NAME_5);
        auditDto.setKey(key);
        auditDto.setBlobTypeValue(isBlobValue);
        if (isBlobValue) {
            auditDto.setBlobValue(value);
        } else {
            auditDto.setValue(value);
        }
        auditDto.setVersion(version);
        auditDto.setModifiedAt(localDateTime);
        auditDto.setModifiedBy(user);
        return auditDto;
    }

    private static SampleData createEntity(Long id, Long categoryId, String key, String value, boolean isBlobValue, Long version, LocalDateTime now, LocalDateTime yesterday, String user) {
        var categoryEntity = new  SampleDataCategory();
        categoryEntity.setId(categoryId);
        categoryEntity.setName(TestConstants.CATEGORY_NAME_5);
        var entity = new SampleData();
        entity.setId(id);
        entity.setCategory(categoryEntity);
        entity.setKey(key);
        if (isBlobValue) {
            entity.setBlobValue(value);
        } else {
            entity.setValue(value);
        }
        entity.setBlobTypeValue(isBlobValue);
        entity.setVersion(version);
        entity.setModifiedAt(now);
        entity.setModifiedBy(user);
        entity.setVersions(List.of(createAudit(TestConstants.ID, TestConstants.CATEGORY_ID, TestConstants.KEY_NAME_OLD, TestConstants.VALUE_OLD, false, TestConstants.VERSION_OLD, yesterday, TestConstants.MODIFIED_BY_USER_2)));
        return entity;
    }

    private static SampleDataAudit createAudit(Long id, Long categoryId, String key, String value, boolean isBlobValue, Long version, LocalDateTime localDateTime, String user ) {
        SampleDataAudit auditEntity = new SampleDataAudit();
        auditEntity.setId(id);
        auditEntity.setCategoryId(categoryId);
        auditEntity.setKey(key);
        if (isBlobValue) {
            auditEntity.setBlobValue(value);
        } else {
            auditEntity.setValue(value);
        }
        auditEntity.setBlobTypeValue(isBlobValue);
        auditEntity.setVersion(version);
        auditEntity.setModifiedAt(localDateTime);
        auditEntity.setModifiedBy(user);
        return auditEntity;
    }
}