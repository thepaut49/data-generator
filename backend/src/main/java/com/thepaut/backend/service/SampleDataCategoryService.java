package com.thepaut.backend.service;

import com.thepaut.backend.dto.SampleDataCategoryCreationDto;
import com.thepaut.backend.dto.SampleDataCategoryDto;
import com.thepaut.backend.mapper.SampleDataCategoryMapper;
import com.thepaut.backend.mapper.audit.SampleDataCategoryAuditMapper;
import com.thepaut.backend.model.data.SampleDataCategory;
import com.thepaut.backend.model.data.audit.SampleDataCategoryAudit;
import com.thepaut.backend.repository.data.SampleDataCategoryRepository;
import com.thepaut.backend.repository.data.audit.SampleDataCategoryAuditRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SampleDataCategoryService extends GenericEntityService<SampleDataCategoryCreationDto, SampleDataCategoryDto, SampleDataCategory, SampleDataCategoryAudit> implements ISampleDataCategoryService {


    public SampleDataCategoryService(SampleDataCategoryRepository entityRepository, SampleDataCategoryAuditRepository auditRepository) {
        super(entityRepository, auditRepository);
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
            categories  = ((SampleDataCategoryRepository)entityRepository).findByNameContainingIgnoreCase(categoryName, Sort
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
    public SampleDataCategory convertAuditToEntity(SampleDataCategoryAudit entityAudit) {
        return SampleDataCategoryMapper.INSTANCE.convertAuditToEntity(entityAudit);
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
