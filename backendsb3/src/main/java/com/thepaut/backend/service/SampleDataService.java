package com.thepaut.backend.service;

import com.thepaut.backend.dto.SampleDataCreationDto;
import com.thepaut.backend.dto.SampleDataDto;
import com.thepaut.backend.exception.EntityNotFoundException;
import com.thepaut.backend.exception.EntityVersionNotFoundException;
import com.thepaut.backend.mapper.SampleDataMapper;
import com.thepaut.backend.model.data.SampleData;
import com.thepaut.backend.model.data.SampleDataCategory;
import com.thepaut.backend.model.data.audit.SampleDataAudit;
import com.thepaut.backend.repository.data.SampleDataCategoryRepository;
import com.thepaut.backend.repository.data.SampleDataRepository;
import com.thepaut.backend.repository.data.SampleDataRepositoryCustom;
import com.thepaut.backend.repository.data.audit.SampleDataAuditRepository;
import com.thepaut.backend.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Service
public class SampleDataService implements ISampleDataService, IGenericEntityService<SampleDataCreationDto, SampleDataDto, SampleData, SampleDataAudit> {

    private final SampleDataRepository entityRepository;
    private final SampleDataAuditRepository auditRepository;
    private final SampleDataCategoryRepository sampleDataCategoryRepository;
    private final SampleDataRepositoryCustom sampleDataRepositoryCustom;


    @Override
    public SampleDataDto getEntityById(String path, Long id) {
        SampleData entity = entityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, SampleData.class.getName(),
                        path,
                        Constants.CODE_SAMPLE_DATA_NOT_FOUND));
        entity.setVersions(auditRepository.findByIdOrderByVersionDesc(id));
        entity.addVersion(convertEntityToAudit(entity));
        return convertEntityToUpdateDto(entity);
    }

    @Transactional
    @Override
    public SampleDataDto rollbackToPreviousVersion(String path, Long id) {
        SampleData currentEntity = entityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, SampleData.class.getName(),
                path,
                Constants.CODE_SAMPLE_DATA_NOT_FOUND));
        SampleData previousVersion = getPreviousVersion(path, id, currentEntity.getVersion() - 1);
        var versionsToCopyAfterSave = previousVersion.getVersions();
        previousVersion = entityRepository.save(previousVersion);
        previousVersion.setVersions(versionsToCopyAfterSave);
        auditRepository.deleteFirstByIdOrderByVersionDesc(id);
        return convertEntityToUpdateDto(previousVersion);
    }

    @Transactional
    @Override
    public SampleDataDto rollbackToVersion(String path, Long id, Long version) {
        SampleData specificVersion = getSpecificVersion(path, id, version);
        var versionsToCopyAfterSave = specificVersion.getVersions();
        specificVersion = entityRepository.save(specificVersion);
        specificVersion.setVersions(versionsToCopyAfterSave);
        auditRepository.deleteByIdAndVersionGreaterThanEqual(id, version);
        return convertEntityToUpdateDto(specificVersion);
    }

    @Override
    public SampleDataDto createEntity(String path, @Valid SampleDataCreationDto creationDto) {
        SampleData entity = convertCreationDtoToEntity(creationDto);
        entity.setModifiedBy(UserService.getUserId());
        entity = entityRepository.save(entity);
        entity.addVersion(convertEntityToAudit(entity));
        return convertEntityToUpdateDto(entity);
    }

    @Transactional
    @Override
    public SampleDataDto updateEntity(String path, Long id, @Valid SampleDataDto updateDto) {
        // Sauvegarde de l'ancienne version
        SampleData oldVersion = entityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, SampleData.class.getName(), path,
                Constants.CODE_SAMPLE_DATA_NOT_FOUND));
        auditRepository.save(convertEntityToAudit(oldVersion));
        // Mise à jour de l'entity
        List<SampleDataAudit> versions = auditRepository.findByIdOrderByVersionDesc(id);
        SampleData updatedEntity = convertUpdateDtoToEntity(updateDto);
        updatedEntity.setVersion(updateDto.getVersion() + 1);
        updatedEntity.setModifiedBy(UserService.getUserId());
        updatedEntity.setModifiedAt(LocalDateTime.now());
        updatedEntity = entityRepository.save(updatedEntity);
        // Construction de la liste des versions
        updatedEntity.setVersions(versions);
        updatedEntity.addVersion(convertEntityToAudit(updatedEntity));
        return convertEntityToUpdateDto(updatedEntity);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        entityRepository.deleteById(id);
        auditRepository.deleteById(id);
        return true;
    }

    @Override
    public SampleData getPreviousVersion(String path, Long id, Long version) {
        List<SampleDataAudit> versions = auditRepository.findByIdOrderByVersionDesc(id);
        if (versions.isEmpty()) {
            throw new EntityVersionNotFoundException(id, version, SampleData.class.getName(), path, Constants.CODE_SAMPLE_DATA_VERSION_NOT_FOUND);
        } else {
            SampleDataAudit previousVersion = versions.get(0);
            SampleData genericEntity = convertAuditToEntity(path, previousVersion);
            genericEntity.setVersions(versions);
            auditRepository.deleteFirstByIdOrderByVersionDesc(id);
            return genericEntity;
        }
    }

    @Override
    public SampleData getSpecificVersion(String path, Long id, Long version) {
        List<SampleDataAudit> versions = auditRepository.findByIdAndVersionLessThanEqualOrderByVersionDesc(id, version);
        if (versions.isEmpty()) {
            throw new EntityVersionNotFoundException(id, version, SampleData.class.getName(), path, Constants.CODE_SAMPLE_DATA_VERSION_NOT_FOUND);
        } else {
            SampleDataAudit specificVersion = versions.get(0);
            SampleData sampleData = convertAuditToEntity(path, specificVersion);
            sampleData.setVersions(versions);
            auditRepository.deleteByIdAndVersionGreaterThanEqual(id, version);
            return sampleData;
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<SampleDataDto> getSampleDatas(Long categoryId, String key, String value, Boolean isBlobValue) {
        List<SampleData> results = sampleDataRepositoryCustom.findByCriteria(categoryId, key, isBlobValue, value);
        return results.stream().map(SampleDataMapper.INSTANCE::convertEntityToUpdateDto).toList();
    }

    @Override
    public SampleData convertAuditToEntity(String path, SampleDataAudit audit) {
        SampleDataCategory sampleDataCategory = sampleDataCategoryRepository.findById(audit.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException(audit.getCategoryId(), SampleDataCategory.class.getName(),
                        path,
                        Constants.CODE_SAMPLE_DATA_CATEGORY_NOT_FOUND));
        SampleData sampleData = SampleDataMapper.INSTANCE.convertAuditToEntity(audit);
        sampleData.setCategory(sampleDataCategory);
        return sampleData;
    }

    @Override
    public SampleDataAudit convertEntityToAudit(SampleData entity) {
        return SampleDataMapper.INSTANCE.convertEntityToAudit(entity);
    }

    @Override
    public SampleDataDto convertEntityToUpdateDto(SampleData entity) {
        return SampleDataMapper.INSTANCE.convertEntityToUpdateDto(entity);
    }

    @Override
    public SampleData convertUpdateDtoToEntity(SampleDataDto updateDto) {
        return SampleDataMapper.INSTANCE.convertUpdateDtoToEntity(updateDto);
    }

    @Override
    public SampleData convertCreationDtoToEntity(SampleDataCreationDto creationDto) {
        return SampleDataMapper.INSTANCE.convertCreationDtoToEntity(creationDto);
    }
}
