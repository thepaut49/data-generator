package com.thepaut.backend.service;

import com.thepaut.backend.dto.SampleDataDto;
import com.thepaut.backend.mapper.SampleDataMapper;
import com.thepaut.backend.model.data.SampleData;
import com.thepaut.backend.model.data.SampleDataCategory;
import com.thepaut.backend.repository.data.SampleDataCategoryRepository;
import com.thepaut.backend.repository.data.SampleDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SampleDataService implements ISampleDataService{

    public static final String CATEGORIE_INCONNU = "Catégorie inconnu : ";
    @Autowired
    SampleDataRepository sampleDataRepository;

    @Autowired
    SampleDataCategoryRepository sampleDataCategoryRepository;


    @Override
    public List<SampleDataDto> getSampleDatas(String categoryName, String key, String value , boolean isBlobValue) {
        SampleDataCategory sampleDataCategory = sampleDataCategoryRepository.findFirstByNameOrderByVersionDesc(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORIE_INCONNU + categoryName ));

        List<SampleData> results;
        Sort sort = Sort.by(Sort.Direction.ASC, "key").and(Sort.by(Sort.Direction.DESC, "version"));
        // catagory + key + value
        if (sampleDataCategory != null && StringUtils.hasText(key) && StringUtils.hasText(value) && isBlobValue) {
            results = sampleDataRepository.findByCategoryAndKeyContainingIgnoreCaseAndBlobValueContainingIgnoreCase(sampleDataCategory, key, value, sort);
        } // catagory + key + blobvalue
        else if (sampleDataCategory != null && StringUtils.hasText(key) && StringUtils.hasText(value) && !isBlobValue) {
            results = sampleDataRepository.findByCategoryAndKeyContainingIgnoreCaseAndValueContainingIgnoreCase(sampleDataCategory, key, value, sort);
        } // catagory + value
        else if (sampleDataCategory != null && StringUtils.hasText(value) && !isBlobValue) {
            results = sampleDataRepository.findByCategoryAndValueContainingIgnoreCase(sampleDataCategory, value, sort);
        } // catagory + blobvalue
        else if (sampleDataCategory != null && StringUtils.hasText(value) && isBlobValue) {
            results = sampleDataRepository.findByCategoryAndBlobValueContainingIgnoreCase(sampleDataCategory, value, sort);
        } // key + value
        else if (StringUtils.hasText(key) && StringUtils.hasText(value) && !isBlobValue) {
            results = sampleDataRepository.findByKeyContainingIgnoreCaseAndValueContainingIgnoreCase(key, value, sort);
        } // key + blobvalue
        else if (StringUtils.hasText(key) &&sampleDataCategory != null && StringUtils.hasText(value) && isBlobValue) {
            results = sampleDataRepository.findByKeyContainingIgnoreCaseAndBlobValueContainingIgnoreCase(key, value, sort);
        } // category + key
        else if (sampleDataCategory != null && StringUtils.hasText(key)) {
            results = sampleDataRepository.findByCategoryAndKeyContainingIgnoreCase(sampleDataCategory, key, sort);
        } // category
        else if (sampleDataCategory != null) {
            results = sampleDataRepository.findByCategory(sampleDataCategory, sort);
        } // key
        else if (StringUtils.hasText(key)) {
            results = sampleDataRepository.findByKeyContainingIgnoreCase(key, sort);
        } // value
        else if (StringUtils.hasText(value) && !isBlobValue) {
            results = sampleDataRepository.findByValueContainingIgnoreCase(value, sort);
        } // blobvalue
        else if (StringUtils.hasText(value) && isBlobValue) {
            results = sampleDataRepository.findByBlobValueContainingIgnoreCase(value, sort);
        } else {
            results = sampleDataRepository.findAll(sort);
        }
        results = getOnlyLastVersion(results);
        return results.stream().map(SampleDataMapper.INSTANCE::convert).toList();
    }

    private List<SampleData> getOnlyLastVersion(List<SampleData> datas) {
        List<SampleData> filteredDatas = new ArrayList<>();
        boolean firstElement = true;
        SampleData currentData = new SampleData();
        for(SampleData data : datas) {
            if (firstElement) {
                firstElement = false;
                filteredDatas.add(data);
                currentData = data;
            }
            else {
                if (data.getKey().equals(currentData.getKey())) {
                    currentData.addVersion(data);
                }
                else {
                    filteredDatas.add(data);
                    currentData = data;
                }
            }
        }
        return filteredDatas;
    }

    @Override
    public SampleDataDto getSampleData(String categoryName, String key) {
        SampleData sampleData = sampleDataRepository.findFirstByCategoryNameAndKeyOrderByVersionDesc(categoryName, key)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORIE_INCONNU + categoryName ));
        sampleData.setSampleDataVersions(sampleDataRepository.findByCategoryNameAndKeyOrderByVersionDesc(categoryName, key));
        return SampleDataMapper.INSTANCE.convert(sampleData);
    }

    @Override
    public SampleDataDto rollbackToPreviousVersion(String categoryName, String key) {
        sampleDataRepository.deleteFirstByCategoryNameAndKeyOrderByVersionDesc(categoryName, key);
        SampleData sampleData = sampleDataRepository.findFirstByCategoryNameAndKeyOrderByVersionDesc(categoryName, key).orElseThrow( () -> new ResourceNotFoundException("Catégorie " + categoryName + " clé : " + key + "  inconnu !" ));
        Long previousVersion = sampleData.getVersion();
        sampleData.setSampleDataVersions(sampleDataRepository.findByCategoryNameAndKeyAndVersionLessThanEqualOrderByVersionDesc(categoryName, key, previousVersion));
        return SampleDataMapper.INSTANCE.convert(sampleData);
    }

    @Override
    public SampleDataDto rollbackToVersion(String categoryName, String key, Long version) {
        sampleDataRepository.deleteByCategoryNameAndKeyAndVersionGreaterThan(categoryName, key, version);
        SampleData sampleData = sampleDataRepository.findByCategoryNameAndKeyAndVersion(categoryName, key, version).orElseThrow( () -> new ResourceNotFoundException("Clé version " + version + " introuvable !" ));
        sampleData.setSampleDataVersions(sampleDataRepository.findByCategoryNameAndKeyAndVersionLessThanEqualOrderByVersionDesc(categoryName, key, version));
        return SampleDataMapper.INSTANCE.convert(sampleData);
    }

    @Override
    public SampleDataDto createSampleData(String categoryName, SampleDataDto sampleDataDto) {
        SampleDataCategory sampleDataCategory = sampleDataCategoryRepository.findFirstByNameOrderByVersionDesc(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORIE_INCONNU + categoryName ));
        SampleData sampleData = SampleDataMapper.INSTANCE.convert(sampleDataDto);
        sampleData.setCategory(sampleDataCategory);
        sampleData.setModifiedBy(UserService.getUserId());
        sampleData.addVersion(sampleData);
        return SampleDataMapper.INSTANCE.convert(sampleDataRepository.save(sampleData));
    }

    @Override
    public SampleDataDto updateSampleData(String categoryName, String key, SampleDataDto sampleDataDto) {
        SampleData sampleData = SampleDataMapper.INSTANCE.convert(sampleDataDto);
        sampleData.setVersion(newVersion(categoryName, key));
        sampleData.setModifiedBy(UserService.getUserId());
        sampleData = sampleDataRepository.save(sampleData);
        sampleData.setSampleDataVersions(sampleDataRepository.findByCategoryNameAndKeyOrderByVersionDesc(categoryName, key));
        return SampleDataMapper.INSTANCE.convert(sampleData);
    }

    @Override
    public boolean deleteSampleDataByCategoryNameAndKey(String categoryName, String key) {
        return sampleDataRepository.deleteByCategoryNameAndKey(categoryName, key) > 0;
    }

    private Long newVersion(String categoryName, String key) {
        Optional<SampleData> sampleData = sampleDataRepository.findFirstByCategoryNameAndKeyOrderByVersionDesc(categoryName, key);
        if (sampleData.isPresent()) {
            return sampleData.get().getVersion() + 1;
        }
        else {
            return 0L;
        }
    }
}
