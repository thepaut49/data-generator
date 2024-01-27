package com.thepaut.backend.service;

import com.thepaut.backend.dto.SampleDataCategoryCreationDto;
import com.thepaut.backend.dto.SampleDataCategoryDto;
import com.thepaut.backend.exception.EntityNotFoundException;
import com.thepaut.backend.exception.EntityVersionNotFoundException;
import com.thepaut.backend.mapper.SampleDataCategoryMapper;
import com.thepaut.backend.model.data.SampleDataCategory;
import com.thepaut.backend.model.data.audit.SampleDataCategoryAudit;
import com.thepaut.backend.repository.data.SampleDataCategoryRepository;
import com.thepaut.backend.repository.data.audit.SampleDataCategoryAuditRepository;
import com.thepaut.backend.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SampleDataCategoryService implements ISampleDataCategoryService, IGenericEntityService<SampleDataCategoryCreationDto, SampleDataCategoryDto, SampleDataCategory, SampleDataCategoryAudit> {
    
    private final SampleDataCategoryRepository entityRepository;
    private final SampleDataCategoryAuditRepository auditRepository;

    @Override
    public SampleDataCategoryDto getEntityById(String path, Long id) {
        SampleDataCategory sampleDataCategory = entityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException( id, SampleDataCategory.class.getName(),
                        path,
                        Constants.CODE_SAMPLE_DATA_CATEGORY_NOT_FOUND));
        sampleDataCategory.setVersions(auditRepository.findByIdOrderByVersionDesc(id));
        sampleDataCategory.addVersion(convertEntityToAudit(sampleDataCategory));
        return convertEntityToUpdateDto(sampleDataCategory);
    }

    @Transactional
    @Override
    public SampleDataCategoryDto rollbackToPreviousVersion(String path,Long id) {
        SampleDataCategory currentSampleDataCategory = entityRepository.findById(id).orElseThrow(() ->  new EntityNotFoundException( id, SampleDataCategory.class.getName(),
                path,
                Constants.CODE_SAMPLE_DATA_CATEGORY_NOT_FOUND));
        SampleDataCategory previousVersion = getPreviousVersion(path, id,currentSampleDataCategory.getVersion() - 1);
        previousVersion = entityRepository.save(previousVersion);
        auditRepository.deleteFirstByIdOrderByVersionDesc(id);
        return convertEntityToUpdateDto(previousVersion);
    }

    @Transactional
    @Override
    public SampleDataCategoryDto rollbackToVersion(String path, Long id, Long version) {
        SampleDataCategory specificVersion = getSpecificVersion(path, id,version);
        specificVersion = entityRepository.save(specificVersion);
        auditRepository.deleteByIdAndVersionGreaterThanEqual(id,version);
        return convertEntityToUpdateDto(specificVersion);
    }

    @Override
    public SampleDataCategoryDto createEntity(String path, @Valid SampleDataCategoryCreationDto creationDto) {
        SampleDataCategory sampleDataCategory = convertCreationDtoToEntity(creationDto);
        sampleDataCategory.setModifiedBy(UserService.getUserId());
        sampleDataCategory = entityRepository.save(sampleDataCategory);
        sampleDataCategory.addVersion(convertEntityToAudit(sampleDataCategory));
        return convertEntityToUpdateDto(sampleDataCategory);
    }

    @Transactional
    @Override
    public SampleDataCategoryDto updateEntity(String path, Long id, @Valid SampleDataCategoryDto updateDto) {
        // Sauvegarde de l'ancienne version
        SampleDataCategory oldVersion = entityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id,SampleDataCategory.class.getName(),
                path,
                Constants.CODE_SAMPLE_DATA_CATEGORY_NOT_FOUND));
        auditRepository.save(convertEntityToAudit(oldVersion));
        // Mise à jour de l'SampleDataCategory
        List<SampleDataCategoryAudit> versions = auditRepository.findByIdOrderByVersionDesc(id);
        SampleDataCategory updatedSampleDataCategory = convertUpdateDtoToEntity(updateDto);
        updatedSampleDataCategory.setVersion(updateDto.getVersion() + 1);
        updatedSampleDataCategory.setModifiedBy(UserService.getUserId());
        updatedSampleDataCategory = entityRepository.save(updatedSampleDataCategory);
        // Construction de la liste des versions
        updatedSampleDataCategory.setVersions(versions);
        updatedSampleDataCategory.addVersion(convertEntityToAudit(updatedSampleDataCategory));
        return convertEntityToUpdateDto(updatedSampleDataCategory);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        entityRepository.deleteById(id);
        auditRepository.deleteById(id);
        return true;
    }

    @Override
    public SampleDataCategory getPreviousVersion(String path, Long id, Long version) {
        List<SampleDataCategoryAudit> versions = auditRepository.findByIdOrderByVersionDesc(id);
        if (versions.isEmpty()) {
            throw new EntityVersionNotFoundException(id, version, SampleDataCategory.class.getName(),
                    path,
                    Constants.CODE_SAMPLE_DATA_CATEGORY_VERSION_NOT_FOUND);
        } else {
            SampleDataCategoryAudit previousVersion = versions.get(0);
            SampleDataCategory sampleDataCategory = convertAuditToEntity(path, previousVersion);
            sampleDataCategory.setVersions(versions);
            auditRepository.deleteFirstByIdOrderByVersionDesc(id);
            return sampleDataCategory;
        }
    }

    @Override
    public SampleDataCategory getSpecificVersion(String path, Long id, Long version) {
        List<SampleDataCategoryAudit> versions = auditRepository.findByIdAndVersionLessThanEqualOrderByVersionDesc(id,version);
        if (versions.isEmpty()) {
            throw new EntityVersionNotFoundException(id, version, SampleDataCategory.class.getName(),
                    path,
                    Constants.CODE_SAMPLE_DATA_CATEGORY_VERSION_NOT_FOUND);
        } else {
            SampleDataCategoryAudit specificVersion = versions.get(0);
            SampleDataCategory category = convertAuditToEntity(path, specificVersion);
            category.setVersions(versions);
            auditRepository.deleteByIdAndVersionGreaterThanEqual(id, version);
            return category;
        }
    }

    @Override
    public List<SampleDataCategoryDto> getSampleDataCategories(String categoryName) {
        return getAllSampleDataCategories(categoryName).stream().map(this::convertEntityToUpdateDto).toList();
    }

    /**
     * Récupère l'ensemble des catégories
     *
     * @param categoryName si présent il faut récupérer unqquement celle qui corresponde
     * @return
     */
    private List<SampleDataCategory> getAllSampleDataCategories(String categoryName) {
        List<SampleDataCategory> categories;
        if (StringUtils.hasText(categoryName)) {
            categories  = entityRepository.findByNameContainingIgnoreCase(categoryName, Sort
                    .by(Sort.Direction.ASC, "name"));
        } else {
            categories = entityRepository.findAll(Sort
                    .by(Sort.Direction.ASC, "name"));
        }
        // search versions for each categories
        for(SampleDataCategory category : categories) {
            category.setVersions(auditRepository.findByIdOrderByVersionDesc(category.getId()));
            category.addVersion(convertEntityToAudit(category));
        }
        return categories;
    }

    @Override
    public SampleDataCategory convertAuditToEntity(String path, SampleDataCategoryAudit audit) {
        return SampleDataCategoryMapper.INSTANCE.convertAuditToEntity(audit);
    }

    @Override
    public SampleDataCategoryAudit convertEntityToAudit(SampleDataCategory entity) {
        return SampleDataCategoryMapper.INSTANCE.convertEntityToAudit(entity);
    }

    @Override
    public SampleDataCategoryDto convertEntityToUpdateDto(SampleDataCategory entity) {
        return SampleDataCategoryMapper.INSTANCE.convertEntityToUpdateDto(entity);
    }

    @Override
    public SampleDataCategory convertUpdateDtoToEntity(SampleDataCategoryDto updateDto) {
        return  SampleDataCategoryMapper.INSTANCE.convertUpdateDtoToEntity(updateDto);
    }

    @Override
    public  SampleDataCategory convertCreationDtoToEntity(SampleDataCategoryCreationDto creationDto) {
        return SampleDataCategoryMapper.INSTANCE.convertCreationDtoToEntity(creationDto);
    }
}
