package com.thepaut.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thepaut.backend.containers.keycloaktestcontainer.KeycloakTestContainer;
import com.thepaut.backend.containers.postgressqltestcontainer.PostgresSqlTestContainer;
import com.thepaut.backend.dto.SampleDataCategoryCreationDto;
import com.thepaut.backend.dto.SampleDataCategoryDto;
import com.thepaut.backend.dto.SampleDataCreationDto;
import com.thepaut.backend.dto.SampleDataDto;
import com.thepaut.backend.dto.audit.SampleDataAuditDto;
import com.thepaut.backend.mapper.SampleDataMapper;
import com.thepaut.backend.model.data.SampleData;
import com.thepaut.backend.model.data.audit.SampleDataAudit;
import com.thepaut.backend.utils.EntityTestUtils;
import com.thepaut.backend.utils.SampleDataTestUtils;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class SampleDataControllerTest {

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
        var createdSampleDataCategory = EntityTestUtils.postRequest(baseUrl + "/api/sample-data-categories" , sampleDataCategory, accessTokenUser1, objectMapper, SampleDataCategoryDto.class);

        var sampleDataCategory2 = new SampleDataCategoryCreationDto(TestConstants.CATEGORY_NAME_2);
        var createdSampleDataCategory2 = EntityTestUtils.postRequest(baseUrl + "/api/sample-data-categories" , sampleDataCategory2, accessTokenUser1, objectMapper, SampleDataCategoryDto.class);


        var sampleData = new SampleDataCreationDto(createdSampleDataCategory.getId(), TestConstants.KEY_NAME_1, TestConstants.VALUE, false, null);

        var createdSampleData = EntityTestUtils.postRequest(baseUrl + "/api/sample-datas" , sampleData,accessTokenUser1, objectMapper, SampleDataDto.class);

        List<SampleDataAudit> versionsExpected = new ArrayList<>();
        versionsExpected.add(convertUpdateDtoToAudit(createdSampleData));

        assertEquals(createdSampleDataCategory.getId(), createdSampleData.getCategoryId());
        assertEquals(TestConstants.KEY_NAME_1, createdSampleData.getKey());
        assertEquals(TestConstants.VALUE, createdSampleData.getValue());
        assertNull(createdSampleData.getBlobValue());
        assertNotNull(createdSampleData.getId());
        assertIterableEquals(versionsExpected, createdSampleData.getVersions().stream().map(this::convertAuditDtoToAuditEntity).toList());
        assertEquals(0L, createdSampleData.getVersion());
        assertEquals(TestConstants.KEYCLOAK_USERNAME_ID, createdSampleData.getModifiedBy());

        // find by Id
        createdSampleData = EntityTestUtils.getRequestById(baseUrl+ "/api/sample-datas/" + createdSampleData.getId(), accessTokenUser1, objectMapper, SampleDataDto.class);

        assertEquals(createdSampleDataCategory.getId(), createdSampleData.getCategoryId());
        assertEquals(TestConstants.KEY_NAME_1, createdSampleData.getKey());
        assertEquals(TestConstants.VALUE, createdSampleData.getValue());
        assertNull(createdSampleData.getBlobValue());
        assertNotNull(createdSampleData.getId());
        assertIterableEquals(versionsExpected, createdSampleData.getVersions().stream().map(this::convertAuditDtoToAuditEntity).toList());
        assertEquals(0L, createdSampleData.getVersion());
        assertEquals(TestConstants.KEYCLOAK_USERNAME_ID, createdSampleData.getModifiedBy());

        // premier update
        LocalDateTime updatedDateTime = LocalDateTime.now();
        var updatedSampleData = SampleDataTestUtils.copyDto(createdSampleData, createdSampleData.getCategoryId(), TestConstants.KEY_NAME_2, false, TestConstants.VALUE);

        updatedSampleData = EntityTestUtils.updateRequest(baseUrl + "/api/sample-datas/" + updatedSampleData.getId(), updatedSampleData, accessTokenUser2, objectMapper, SampleDataDto.class);

        versionsExpected.add(0, convertUpdateDtoToAudit(updatedSampleData));

        assertEquals(createdSampleDataCategory.getId(), updatedSampleData.getCategoryId());
        assertEquals(TestConstants.KEY_NAME_2, updatedSampleData.getKey());
        assertEquals(TestConstants.VALUE, updatedSampleData.getValue());
        assertNull(updatedSampleData.getBlobValue());
        assertEquals(createdSampleData.getId(), updatedSampleData.getId());
        assertIterableEquals(versionsExpected, updatedSampleData.getVersions().stream().map(this::convertAuditDtoToAuditEntity).toList());
        assertEquals(1L, updatedSampleData.getVersion());
        assertEquals(TestConstants.KEYCLOAK_USERNAME_ID_2, updatedSampleData.getModifiedBy());
        assertTrue(updatedSampleData.getModifiedAt().isAfter(updatedDateTime));

        // deuxième update
        updatedDateTime = LocalDateTime.now();
        updatedSampleData = SampleDataTestUtils.copyDto(updatedSampleData, updatedSampleData.getCategoryId(), TestConstants.KEY_NAME_3, false, TestConstants.VALUE);

        updatedSampleData = EntityTestUtils.updateRequest(baseUrl + "/api/sample-datas/" + updatedSampleData.getId(), updatedSampleData, accessTokenUser2, objectMapper, SampleDataDto.class);

        versionsExpected.add(0, convertUpdateDtoToAudit(updatedSampleData));

        assertEquals(TestConstants.KEY_NAME_3, updatedSampleData.getKey());
        assertEquals(createdSampleData.getId(), updatedSampleData.getId());
        assertIterableEquals(versionsExpected, updatedSampleData.getVersions().stream().map(this::convertAuditDtoToAuditEntity).toList());
        assertEquals(2L, updatedSampleData.getVersion());
        assertEquals(TestConstants.KEYCLOAK_USERNAME_ID_2, updatedSampleData.getModifiedBy());
        assertTrue(updatedSampleData.getModifiedAt().isAfter(updatedDateTime));

        // Troisième update
        updatedDateTime = LocalDateTime.now();
        updatedSampleData = SampleDataTestUtils.copyDto(updatedSampleData, updatedSampleData.getCategoryId(), TestConstants.KEY_NAME_4, false, TestConstants.VALUE);

        updatedSampleData = EntityTestUtils.updateRequest(baseUrl + "/api/sample-datas/" + updatedSampleData.getId(), updatedSampleData, accessTokenUser2, objectMapper, SampleDataDto.class);

        versionsExpected.add(0, convertUpdateDtoToAudit(updatedSampleData));

        assertEquals(TestConstants.KEY_NAME_4, updatedSampleData.getKey());
        assertEquals(createdSampleData.getId(), updatedSampleData.getId());
        assertIterableEquals(versionsExpected, updatedSampleData.getVersions().stream().map(this::convertAuditDtoToAuditEntity).toList());
        assertEquals(3L, updatedSampleData.getVersion());
        assertEquals(TestConstants.KEYCLOAK_USERNAME_ID_2, updatedSampleData.getModifiedBy());
        assertTrue(updatedSampleData.getModifiedAt().isAfter(updatedDateTime));

        // Quatrième update
        updatedDateTime = LocalDateTime.now();
        updatedSampleData = SampleDataTestUtils.copyDto(updatedSampleData,updatedSampleData.getCategoryId(), TestConstants.KEY_NAME_5, false, TestConstants.VALUE);

        updatedSampleData = EntityTestUtils.updateRequest(baseUrl + "/api/sample-datas/" + updatedSampleData.getId(), updatedSampleData, accessTokenUser2, objectMapper, SampleDataDto.class);

        versionsExpected.add(0, convertUpdateDtoToAudit(updatedSampleData));

        assertEquals(TestConstants.KEY_NAME_5, updatedSampleData.getKey());
        assertEquals(createdSampleData.getId(), updatedSampleData.getId());
        assertIterableEquals(versionsExpected, updatedSampleData.getVersions().stream().map(this::convertAuditDtoToAuditEntity).toList());
        assertEquals(4L, updatedSampleData.getVersion());
        assertEquals(TestConstants.KEYCLOAK_USERNAME_ID_2, updatedSampleData.getModifiedBy());
        assertTrue(updatedSampleData.getModifiedAt().isAfter(updatedDateTime));


        // rollback to previous version
        versionsExpected.remove(0);

        updatedSampleData = EntityTestUtils.rollbackToPreviousVersionRequest(baseUrl + "/api/sample-datas/" + updatedSampleData.getId() + "/rollback-to-previous-version", accessTokenUser2, objectMapper, SampleDataDto.class);

        assertEquals(TestConstants.KEY_NAME_4, updatedSampleData.getKey());
        assertEquals(createdSampleData.getId(), updatedSampleData.getId());
        assertIterableEquals(versionsExpected, updatedSampleData.getVersions().stream().map(this::convertAuditDtoToAuditEntity).toList());
        assertEquals(3L, updatedSampleData.getVersion());
        assertEquals(TestConstants.KEYCLOAK_USERNAME_ID_2, updatedSampleData.getModifiedBy());
        assertTrue(updatedSampleData.getModifiedAt().isBefore(updatedDateTime));


        // rollback to version 1
        updatedSampleData = EntityTestUtils.rollbackToSpecificVersionRequest(baseUrl + "/api/sample-datas/" + updatedSampleData.getId() + "/rollback-to-version/" + 0L, accessTokenUser2, objectMapper, SampleDataDto.class);

        versionsExpected = List.of(convertUpdateDtoToAudit(createdSampleData));

        assertEquals(TestConstants.KEY_NAME_1, updatedSampleData.getKey());
        assertEquals(createdSampleData.getId(), updatedSampleData.getId());
        assertIterableEquals(versionsExpected, updatedSampleData.getVersions().stream().map(this::convertAuditDtoToAuditEntity).toList());
        assertEquals(0L, updatedSampleData.getVersion());
        assertEquals(TestConstants.KEYCLOAK_USERNAME_ID, updatedSampleData.getModifiedBy());
        assertTrue(updatedSampleData.getModifiedAt().isBefore(updatedDateTime));

        // création seconde category
        var sampleData2 = new SampleDataCreationDto(createdSampleDataCategory2.getId(), TestConstants.KEY_NAME_2, TestConstants.VALUE, false, null);
        var createdSampleData2 = EntityTestUtils.postRequest(baseUrl + "/api/sample-datas", sampleData2,accessTokenUser1, objectMapper, SampleDataDto.class);
        createdSampleData2 = EntityTestUtils.getRequestById(baseUrl+ "/api/sample-datas/" + createdSampleData2.getId(), accessTokenUser1, objectMapper, SampleDataDto.class);

        List<SampleDataDto> dataDtos =  EntityTestUtils.getRequestAll(baseUrl+ "/api/sample-datas", null, accessTokenUser1, objectMapper, SampleDataDto.class);

        assertIterableEquals(List.of(createdSampleData, createdSampleData2), dataDtos);

        // Suppression des categories

        EntityTestUtils.deleteRequest(baseUrl + "/api/sample-datas/" + createdSampleData.getId(), accessTokenUser2);
        EntityTestUtils.deleteRequest(baseUrl + "/api/sample-datas/" + createdSampleData.getId(), accessTokenUser1);
    }

    public SampleDataAudit convertUpdateDtoToAudit(SampleDataDto dto) {
        return convertEntityToAudit(convertUpdateDtoToEntity(dto));
    }

    public SampleDataAudit convertEntityToAudit(SampleData entity) {
        return SampleDataMapper.INSTANCE.convertEntityToAudit(entity);
    }

    public SampleData convertUpdateDtoToEntity(SampleDataDto updateDto) {
        return  SampleDataMapper.INSTANCE.convertUpdateDtoToEntity(updateDto);
    }

    public SampleDataAudit convertAuditDtoToAuditEntity(SampleDataAuditDto updateDto) {
        return  SampleDataMapper.INSTANCE.convertUpdateDtoToEntity(updateDto);
    }
}