package com.thepaut.backend.service;

import com.thepaut.backend.dto.SampleDataCategoryDto;
import com.thepaut.backend.dto.SampleDataDto;
import com.thepaut.backend.mapper.SampleDataCategoryMapper;
import com.thepaut.backend.mapper.SampleDataMapper;
import com.thepaut.backend.model.data.SampleData;
import com.thepaut.backend.model.data.SampleDataCategory;
import com.thepaut.backend.repository.data.SampleDataCategoryRepository;
import com.thepaut.backend.repository.data.SampleDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SampleDataService implements ISampleDataService{

    @Autowired
    SampleDataRepository sampleDataRepository;

    @Autowired
    SampleDataCategoryRepository sampleDataCategoryRepository;


    @Override
    public List<SampleDataDto> getSampleDatas(String categoryName, String key, String value , boolean isBlobValue) {
        SampleDataCategory sampleDataCategory = sampleDataCategoryRepository.findByName(categoryName).orElseThrow(() -> new ResourceNotFoundException("Catégorie inconnu : " + categoryName ));

        List<SampleData> results;
        // catagory + key + value
        if (sampleDataCategory != null && StringUtils.hasText(key) && StringUtils.hasText(value) && isBlobValue) {
            results = sampleDataRepository.findByCategoryAndKeyContainingIgnoreCaseAndBlobValueContainingIgnoreCase(sampleDataCategory, key, value);
        } // catagory + key + blobvalue
        else if (sampleDataCategory != null && StringUtils.hasText(key) && StringUtils.hasText(value) && !isBlobValue) {
            results = sampleDataRepository.findByCategoryAndKeyContainingIgnoreCaseAndValueContainingIgnoreCase(sampleDataCategory, key, value);
        } // catagory + value
        else if (sampleDataCategory != null && StringUtils.hasText(value) && !isBlobValue) {
            results = sampleDataRepository.findByCategoryAndValueContainingIgnoreCase(sampleDataCategory, value);
        } // catagory + blobvalue
        else if (sampleDataCategory != null && StringUtils.hasText(value) && isBlobValue) {
            results = sampleDataRepository.findByCategoryAndBlobValueContainingIgnoreCase(sampleDataCategory, value);
        } // key + value
        else if (StringUtils.hasText(key) && StringUtils.hasText(value) && !isBlobValue) {
            results = sampleDataRepository.findByKeyContainingIgnoreCaseAndValueContainingIgnoreCase(key, value);
        } // key + blobvalue
        else if (StringUtils.hasText(key) &&sampleDataCategory != null && StringUtils.hasText(value) && isBlobValue) {
            results = sampleDataRepository.findByKeyContainingIgnoreCaseAndBlobValueContainingIgnoreCase(key, value);
        } // category + key
        else if (sampleDataCategory != null && StringUtils.hasText(key)) {
            results = sampleDataRepository.findByCategoryAndKeyContainingIgnoreCase(sampleDataCategory, key);
        } // category
        else if (sampleDataCategory != null) {
            results = sampleDataRepository.findByCategory(sampleDataCategory);
        } // key
        else if (StringUtils.hasText(key)) {
            results = sampleDataRepository.findByKeyContainingIgnoreCase(key);
        } // value
        else if (StringUtils.hasText(value) && !isBlobValue) {
            results = sampleDataRepository.findByValueContainingIgnoreCase(value);
        } // blobvalue
        else if (StringUtils.hasText(value) && isBlobValue) {
            results = sampleDataRepository.findByBlobValueContainingIgnoreCase(value);
        } else {
            results = sampleDataRepository.findAll();
        }
        return results.stream().map(sampleData -> SampleDataMapper.INSTANCE.convert(sampleData)).toList();
    }

    @Override
    public SampleDataDto rollbackToPreviousVersion() {
        return null;
    }

    @Override
    public SampleDataDto createSampleData(SampleDataDto sampleDataDto) {
        SampleData sampleData = SampleDataMapper.INSTANCE.convert(sampleDataDto);
        return SampleDataMapper.INSTANCE.convert(sampleDataRepository.save(sampleData));
    }

    @Override
    public SampleDataDto updateSampleData(SampleDataDto sampleDataDto) {
        SampleData sampleData = SampleDataMapper.INSTANCE.convert(sampleDataDto);
        return null;
    }

    @Override
    public boolean deleteSampleData(String category, String key) {
        return false;
    }

    @Override
    public boolean deleteSampleData(Long id) {
        sampleDataRepository.deleteById(id);
        return true;
    }
}
