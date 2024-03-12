package com.thepaut.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thepaut.backend.containers.keycloaktestcontainer.KeycloakTestContainer;
import com.thepaut.backend.containers.postgressqltestcontainer.PostgresSqlTestContainer;
import com.thepaut.backend.dto.SampleDataCategoryCreationDto;
import com.thepaut.backend.dto.SampleDataCategoryDto;
import com.thepaut.backend.dto.audit.SampleDataCategoryAuditDto;
import com.thepaut.backend.mapper.SampleDataCategoryMapper;
import com.thepaut.backend.model.data.SampleDataCategory;
import com.thepaut.backend.model.data.audit.SampleDataCategoryAudit;
import com.thepaut.backend.utils.EntityTestUtils;
import com.thepaut.backend.utils.SampleDataCategoryTestUtils;
import com.thepaut.backend.utils.TestConstants;
import org.junit.jupiter.api.AfterAll;
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class SampleDataCategoryControllerTest {

    @LocalServerPort
    protected int port;

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
    void integrationTest() throws IOException {
        final String accessTokenUser1 = keycloakContainer.generateAccessToken(TestConstants.KEYCLOAK_USERNAME,TestConstants.KEYCLOAK_USERNAME_PASSWORD);
        final String accessTokenUser2 = keycloakContainer.generateAccessToken(TestConstants.KEYCLOAK_USERNAME_2,TestConstants.KEYCLOAK_USERNAME_PASSWORD_2);

        final String baseUrl = "http://localhost:" + port;
        var sampleDataCategory = new SampleDataCategoryCreationDto(TestConstants.CATEGORY_NAME_1);

        var createdSampleDataCategory = EntityTestUtils.postRequest(baseUrl + "/api/sample-data-categories" , sampleDataCategory,accessTokenUser1, objectMapper, SampleDataCategoryDto.class);

        List<SampleDataCategoryAudit> versionsExpected = new ArrayList<>();
        versionsExpected.add(convertUpdateDtoToAudit(createdSampleDataCategory));

        assertEquals(TestConstants.CATEGORY_NAME_1, createdSampleDataCategory.getName());
        assertNotNull(createdSampleDataCategory.getId());
        assertIterableEquals(versionsExpected, createdSampleDataCategory.getVersions().stream().map(this::convertAuditDtoToAuditEntity).toList());
        assertEquals(0L, createdSampleDataCategory.getVersion());
        assertEquals(TestConstants.KEYCLOAK_USERNAME_ID, createdSampleDataCategory.getModifiedBy());

        // find by Id
        createdSampleDataCategory = EntityTestUtils.getRequestById(baseUrl+ "/api/sample-data-categories/" + createdSampleDataCategory.getId(), accessTokenUser1, objectMapper, SampleDataCategoryDto.class);

        assertEquals(TestConstants.CATEGORY_NAME_1, createdSampleDataCategory.getName());
        assertNotNull(createdSampleDataCategory.getId());
        assertIterableEquals(versionsExpected, createdSampleDataCategory.getVersions().stream().map(this::convertAuditDtoToAuditEntity).toList());
        assertEquals(0L, createdSampleDataCategory.getVersion());
        assertEquals(TestConstants.KEYCLOAK_USERNAME_ID, createdSampleDataCategory.getModifiedBy());

        // premier update
        LocalDateTime updatedDateTime = LocalDateTime.now();
        var updatedSampleDataCategory = SampleDataCategoryTestUtils.copyDto(createdSampleDataCategory, TestConstants.CATEGORY_NAME_2);

        updatedSampleDataCategory = EntityTestUtils.updateRequest(baseUrl + "/api/sample-data-categories/" + updatedSampleDataCategory.getId(), updatedSampleDataCategory, accessTokenUser2, objectMapper, SampleDataCategoryDto.class);

        versionsExpected.add(0, convertUpdateDtoToAudit(updatedSampleDataCategory));

        assertEquals(TestConstants.CATEGORY_NAME_2, updatedSampleDataCategory.getName());
        assertEquals(createdSampleDataCategory.getId(), updatedSampleDataCategory.getId());
        assertIterableEquals(versionsExpected, updatedSampleDataCategory.getVersions().stream().map(this::convertAuditDtoToAuditEntity).toList());
        assertEquals(1L, updatedSampleDataCategory.getVersion());
        assertEquals(TestConstants.KEYCLOAK_USERNAME_ID_2, updatedSampleDataCategory.getModifiedBy());
        assertTrue(updatedSampleDataCategory.getModifiedAt().isAfter(updatedDateTime));

        // deuxième update
        updatedDateTime = LocalDateTime.now();
        updatedSampleDataCategory = SampleDataCategoryTestUtils.copyDto(updatedSampleDataCategory, TestConstants.CATEGORY_NAME_3);

        updatedSampleDataCategory = EntityTestUtils.updateRequest(baseUrl + "/api/sample-data-categories/" + updatedSampleDataCategory.getId(), updatedSampleDataCategory, accessTokenUser2, objectMapper, SampleDataCategoryDto.class);

        versionsExpected.add(0, convertUpdateDtoToAudit(updatedSampleDataCategory));

        assertEquals(TestConstants.CATEGORY_NAME_3, updatedSampleDataCategory.getName());
        assertEquals(createdSampleDataCategory.getId(), updatedSampleDataCategory.getId());
        assertIterableEquals(versionsExpected, updatedSampleDataCategory.getVersions().stream().map(this::convertAuditDtoToAuditEntity).toList());
        assertEquals(2L, updatedSampleDataCategory.getVersion());
        assertEquals(TestConstants.KEYCLOAK_USERNAME_ID_2, updatedSampleDataCategory.getModifiedBy());
        assertTrue(updatedSampleDataCategory.getModifiedAt().isAfter(updatedDateTime));

        // Troisième update
        updatedDateTime = LocalDateTime.now();
        updatedSampleDataCategory = SampleDataCategoryTestUtils.copyDto(updatedSampleDataCategory, TestConstants.CATEGORY_NAME_4);

        updatedSampleDataCategory = EntityTestUtils.updateRequest(baseUrl + "/api/sample-data-categories/" + updatedSampleDataCategory.getId(), updatedSampleDataCategory, accessTokenUser2, objectMapper, SampleDataCategoryDto.class);

        versionsExpected.add(0, convertUpdateDtoToAudit(updatedSampleDataCategory));

        assertEquals(TestConstants.CATEGORY_NAME_4, updatedSampleDataCategory.getName());
        assertEquals(createdSampleDataCategory.getId(), updatedSampleDataCategory.getId());
        assertIterableEquals(versionsExpected, updatedSampleDataCategory.getVersions().stream().map(this::convertAuditDtoToAuditEntity).toList());
        assertEquals(3L, updatedSampleDataCategory.getVersion());
        assertEquals(TestConstants.KEYCLOAK_USERNAME_ID_2, updatedSampleDataCategory.getModifiedBy());
        assertTrue(updatedSampleDataCategory.getModifiedAt().isAfter(updatedDateTime));

        // Quatrième update
        updatedDateTime = LocalDateTime.now();
        updatedSampleDataCategory = SampleDataCategoryTestUtils.copyDto(updatedSampleDataCategory, TestConstants.CATEGORY_NAME_5);

        updatedSampleDataCategory = EntityTestUtils.updateRequest(baseUrl + "/api/sample-data-categories/" + updatedSampleDataCategory.getId(), updatedSampleDataCategory, accessTokenUser2, objectMapper, SampleDataCategoryDto.class);

        versionsExpected.add(0, convertUpdateDtoToAudit(updatedSampleDataCategory));

        assertEquals(TestConstants.CATEGORY_NAME_5, updatedSampleDataCategory.getName());
        assertEquals(createdSampleDataCategory.getId(), updatedSampleDataCategory.getId());
        assertIterableEquals(versionsExpected, updatedSampleDataCategory.getVersions().stream().map(this::convertAuditDtoToAuditEntity).toList());
        assertEquals(4L, updatedSampleDataCategory.getVersion());
        assertEquals(TestConstants.KEYCLOAK_USERNAME_ID_2, updatedSampleDataCategory.getModifiedBy());
        assertTrue(updatedSampleDataCategory.getModifiedAt().isAfter(updatedDateTime));


        // rollback to previous version
        versionsExpected.remove(0);

        updatedSampleDataCategory = EntityTestUtils.rollbackToPreviousVersionRequest(baseUrl + "/api/sample-data-categories/" + updatedSampleDataCategory.getId() + "/rollback-to-previous-version", accessTokenUser2, objectMapper, SampleDataCategoryDto.class);

        assertEquals(TestConstants.CATEGORY_NAME_4, updatedSampleDataCategory.getName());
        assertEquals(createdSampleDataCategory.getId(), updatedSampleDataCategory.getId());
        assertIterableEquals(versionsExpected, updatedSampleDataCategory.getVersions().stream().map(this::convertAuditDtoToAuditEntity).toList());
        assertEquals(3L, updatedSampleDataCategory.getVersion());
        assertEquals(TestConstants.KEYCLOAK_USERNAME_ID_2, updatedSampleDataCategory.getModifiedBy());
        assertTrue(updatedSampleDataCategory.getModifiedAt().isBefore(updatedDateTime));


        // rollback to version 1
        updatedSampleDataCategory = EntityTestUtils.rollbackToSpecificVersionRequest(baseUrl + "/api/sample-data-categories/" + updatedSampleDataCategory.getId() + "/rollback-to-version/" + 0L, accessTokenUser2, objectMapper, SampleDataCategoryDto.class);

        versionsExpected = List.of(convertUpdateDtoToAudit(createdSampleDataCategory));

        assertEquals(TestConstants.CATEGORY_NAME_1, updatedSampleDataCategory.getName());
        assertEquals(createdSampleDataCategory.getId(), updatedSampleDataCategory.getId());
        assertIterableEquals(versionsExpected, updatedSampleDataCategory.getVersions().stream().map(this::convertAuditDtoToAuditEntity).toList());
        assertEquals(0L, updatedSampleDataCategory.getVersion());
        assertEquals(TestConstants.KEYCLOAK_USERNAME_ID, updatedSampleDataCategory.getModifiedBy());
        assertTrue(updatedSampleDataCategory.getModifiedAt().isBefore(updatedDateTime));

        // création seconde category
        var sampleDataCategory2 = new SampleDataCategoryCreationDto(TestConstants.CATEGORY_NAME_3);
        var createdSampleDataCategory2 = EntityTestUtils.postRequest(baseUrl + "/api/sample-data-categories", sampleDataCategory2,accessTokenUser1, objectMapper, SampleDataCategoryDto.class);
        List<SampleDataCategoryDto> categoryDtos =  EntityTestUtils.getRequestAll(baseUrl+ "/api/sample-data-categories", null, accessTokenUser1, objectMapper, SampleDataCategoryDto.class);

        assertIterableEquals(List.of(createdSampleDataCategory, createdSampleDataCategory2), categoryDtos);

        // Suppression des categories

        EntityTestUtils.deleteRequest(baseUrl + "/api/sample-data-categories/" + createdSampleDataCategory.getId(), accessTokenUser2);
        EntityTestUtils.deleteRequest(baseUrl + "/api/sample-data-categories/" + createdSampleDataCategory2.getId(), accessTokenUser1);
    }


    public SampleDataCategoryAudit convertUpdateDtoToAudit(SampleDataCategoryDto dto) {
        return convertEntityToAudit(convertUpdateDtoToEntity(dto));
    }

    public SampleDataCategoryAudit convertEntityToAudit(SampleDataCategory entity) {
        return SampleDataCategoryMapper.INSTANCE.convertEntityToAudit(entity);
    }

    public SampleDataCategory convertUpdateDtoToEntity(SampleDataCategoryDto updateDto) {
        return  SampleDataCategoryMapper.INSTANCE.convertUpdateDtoToEntity(updateDto);
    }

    public SampleDataCategoryAudit convertAuditDtoToAuditEntity(SampleDataCategoryAuditDto updateDto) {
        return  SampleDataCategoryMapper.INSTANCE.convertUpdateDtoToEntity(updateDto);
    }
}