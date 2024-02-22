package com.thepaut.backend.mapper;

import com.thepaut.backend.dto.SampleDataCategoryCreationDto;
import com.thepaut.backend.dto.SampleDataCategoryDto;
import com.thepaut.backend.dto.audit.SampleDataCategoryAuditDto;
import com.thepaut.backend.model.data.SampleDataCategory;
import com.thepaut.backend.model.data.audit.SampleDataCategoryAudit;
import com.thepaut.backend.utils.TestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class SampleDataCategoryMapperTest {
    private final SampleDataCategoryMapper mapper = SampleDataCategoryMapper.INSTANCE;

    @Test
    void convertUpdateDtoToEntity() {
        var now = LocalDateTime.now();
        var yesterday = LocalDateTime.now().minusDays(1);

        var updateDto = createSampleDataCategoryDto(TestConstants.ID, TestConstants.CATEGORY_NAME, TestConstants.VERSION_CURRENT, now, yesterday, TestConstants.MODIFIED_BY_USER_1);

        var entity = mapper.convertUpdateDtoToEntity(updateDto);

        Assertions.assertEquals(TestConstants.ID, entity.getId());
        Assertions.assertEquals(TestConstants.CATEGORY_NAME, entity.getName());
        Assertions.assertEquals(TestConstants.VERSION_CURRENT, entity.getVersion());
        Assertions.assertEquals(now, entity.getModifiedAt());
        Assertions.assertEquals(TestConstants.MODIFIED_BY_USER_1, entity.getModifiedBy());
        Assertions.assertEquals(1, entity.getVersions().size());
    }

    @Test
    void convertEntityToUpdateDto() {
        var now = LocalDateTime.now();
        var yesterday = LocalDateTime.now().minusDays(1);

        var entity = createEntity(TestConstants.ID, TestConstants.CATEGORY_NAME, TestConstants.VERSION_CURRENT, now, yesterday, TestConstants.MODIFIED_BY_USER_1);

        var updateDto = mapper.convertEntityToUpdateDto(entity);

        Assertions.assertEquals(TestConstants.ID, updateDto.getId());
        Assertions.assertEquals(TestConstants.CATEGORY_NAME, updateDto.getName());
        Assertions.assertEquals(TestConstants.VERSION_CURRENT, updateDto.getVersion());
        Assertions.assertEquals(now, updateDto.getModifiedAt());
        Assertions.assertEquals(TestConstants.MODIFIED_BY_USER_1, updateDto.getModifiedBy());
        Assertions.assertEquals(1, updateDto.getVersions().size());
    }

    @Test
    void convertCreationDtoToEntity() {
        var creationDto = new SampleDataCategoryCreationDto(TestConstants.CATEGORY_NAME);

        var entity = mapper.convertCreationDtoToEntity(creationDto);

        Assertions.assertNull(entity.getId());
        Assertions.assertEquals(TestConstants.CATEGORY_NAME, entity.getName());
        Assertions.assertEquals(TestConstants.VERSION_FIRST, entity.getVersion());
        Assertions.assertNull(entity.getModifiedBy());
        Assertions.assertEquals(0, entity.getVersions().size());
    }


    @Test
    void convertAuditToEntity() {
        var now = LocalDateTime.now();
        var audit = createAudit(TestConstants.ID, TestConstants.CATEGORY_NAME, TestConstants.VERSION_OLD, now, TestConstants.MODIFIED_BY_USER_1);
        var entity = mapper.convertAuditToEntity(audit);

        Assertions.assertEquals(TestConstants.ID, entity.getId());
        Assertions.assertEquals(TestConstants.CATEGORY_NAME, entity.getName());
        Assertions.assertEquals(TestConstants.VERSION_OLD, entity.getVersion());
        Assertions.assertEquals(now, entity.getModifiedAt());
        Assertions.assertEquals(TestConstants.MODIFIED_BY_USER_1, entity.getModifiedBy());
    }

    @Test
    void convertEntityToAudit() {
        var now = LocalDateTime.now();
        var yesterday = LocalDateTime.now().minusDays(1);
        var entity = createEntity(TestConstants.ID, TestConstants.CATEGORY_NAME, TestConstants.VERSION_CURRENT, now, yesterday, TestConstants.MODIFIED_BY_USER_1);
        var audit = mapper.convertEntityToAudit(entity);

        Assertions.assertEquals(TestConstants.ID, audit.getId());
        Assertions.assertEquals(TestConstants.CATEGORY_NAME, audit.getName());
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
        updateDto.setVersions(List.of(createAuditDto(TestConstants.ID, TestConstants.CATEGORY_NAME_OLD, TestConstants.VERSION_OLD, yesterday, TestConstants.MODIFIED_BY_USER_2)));
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
        entity.setVersions(List.of(createAudit(TestConstants.ID, TestConstants.CATEGORY_NAME_OLD, TestConstants.VERSION_OLD, yesterday, TestConstants.MODIFIED_BY_USER_2)));
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