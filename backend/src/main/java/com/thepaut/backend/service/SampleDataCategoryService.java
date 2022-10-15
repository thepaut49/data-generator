package com.thepaut.backend.service;

import com.thepaut.backend.dto.SampleDataCategoryCreationDto;
import com.thepaut.backend.dto.SampleDataCategoryDto;
import com.thepaut.backend.mapper.SampleDataCategoryMapper;
import com.thepaut.backend.model.data.SampleDataCategory;
import com.thepaut.backend.repository.data.SampleDataCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SampleDataCategoryService implements ISampleDataCategoryService {

    @Autowired
    SampleDataCategoryRepository sampleDataCategoryRepository;


    @Override
    public List<SampleDataCategoryDto> getSampleDataCategories(String categoryName) {
        List<SampleDataCategory> categories;
        if (StringUtils.hasText(categoryName)) {
            categories = sampleDataCategoryRepository.findByNameContainingIgnoreCase(categoryName);
        }
        else {
            categories = sampleDataCategoryRepository.findAll();
        }
        return categories.stream().map(SampleDataCategoryMapper.INSTANCE::convert).toList();
    }

    @Override
    public SampleDataCategoryDto getSampleDataCategory(String categoryName) {
        SampleDataCategory sampleDataCategory = sampleDataCategoryRepository.findFirstByNameOrderByVersionDesc(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie inconnu : " + categoryName ));
        sampleDataCategory.setSampleDataCategoryVersions(sampleDataCategoryRepository.findByNameOrderByVersionDesc(categoryName));
        return SampleDataCategoryMapper.INSTANCE.convert(sampleDataCategory);
    }

    @Override
    public SampleDataCategoryDto rollbackToPreviousVersion(String categoryName) {
        sampleDataCategoryRepository.deleteFirstByNameOrderByVersionDesc(categoryName);
        SampleDataCategory sampleDataCategory = sampleDataCategoryRepository.findFirstByNameOrderByVersionDesc(categoryName).orElseThrow( () -> new ResourceNotFoundException("Catégorie " + categoryName + " inconnu !" ));
        Long previousVersion = sampleDataCategory.getVersion();
        sampleDataCategory.setSampleDataCategoryVersions(sampleDataCategoryRepository.findByNameAndVersionLessThanEqualOrderByVersionDesc(categoryName, previousVersion));
        return SampleDataCategoryMapper.INSTANCE.convert(sampleDataCategory);
    }

    @Override
    public SampleDataCategoryDto rollbackToVersion(String categoryName, Long version) {
        sampleDataCategoryRepository.deleteByNameAndVersionGreaterThan(categoryName,version);
        SampleDataCategory sampleDataCategory = sampleDataCategoryRepository.findByNameAndVersion(categoryName, version).orElseThrow( () -> new ResourceNotFoundException("Catégorie version " + version + " introuvable !" ));
        sampleDataCategory.setSampleDataCategoryVersions(sampleDataCategoryRepository.findByNameAndVersionLessThanEqualOrderByVersionDesc(categoryName, version));
        return SampleDataCategoryMapper.INSTANCE.convert(sampleDataCategory);
    }

    @Override
    public SampleDataCategoryDto createSampleDataCategory(@Valid SampleDataCategoryCreationDto sampleDataCategoryDto) {
        SampleDataCategory sampleDataCategory = SampleDataCategoryMapper.INSTANCE.convertFromCreationDto(sampleDataCategoryDto) ;
        sampleDataCategory.setModifiedBy(UserService.getUserId());
        sampleDataCategory = sampleDataCategoryRepository.save(sampleDataCategory);
        sampleDataCategory.addVersion(sampleDataCategory);
        return SampleDataCategoryMapper.INSTANCE.convert(sampleDataCategory);
    }

    @Override
    public SampleDataCategoryDto updateSampleDataCategory(String categoryName, @Valid SampleDataCategoryCreationDto sampleDataCategoryDto) {
        SampleDataCategory sampleDataCategory = SampleDataCategoryMapper.INSTANCE.convertFromCreationDto(sampleDataCategoryDto);
        sampleDataCategory.setVersion(newVersion(categoryName));
        sampleDataCategory.setModifiedBy(UserService.getUserId());
        sampleDataCategory = sampleDataCategoryRepository.save(sampleDataCategory);
        sampleDataCategory.setSampleDataCategoryVersions(sampleDataCategoryRepository.findByNameOrderByVersionDesc(categoryName));
        return SampleDataCategoryMapper.INSTANCE.convert(sampleDataCategory);
    }

    @Override
    public boolean deleteSampleDataCategoryByName(String categoryName) {
        return sampleDataCategoryRepository.deleteByName(categoryName) > 0;
    }

    private Long newVersion(String categoryName) {
        Optional<SampleDataCategory> sampleDataCategory = sampleDataCategoryRepository.findFirstByNameOrderByVersionDesc(categoryName);
        if (sampleDataCategory.isPresent()) {
            return sampleDataCategory.get().getVersion() + 1;
        }
        else {
            return 0L;
        }

    }

}
