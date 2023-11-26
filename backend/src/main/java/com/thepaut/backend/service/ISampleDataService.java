package com.thepaut.backend.service;

import com.thepaut.backend.dto.SampleDataDto;

import java.util.List;

public interface ISampleDataService {

    List<SampleDataDto> getSampleDatas(Long categoryId, String key, String value, boolean isBlobValue);

    SampleDataDto getSampleData(Long categoryId, String key);

    SampleDataDto rollbackToPreviousVersion(Long categoryId, String key);

    SampleDataDto rollbackToVersion(Long categoryId, String key, Long version);

    SampleDataDto createSampleData(Long categoryId, SampleDataDto sampleDataDto);

    SampleDataDto updateSampleData(Long categoryId, String key, SampleDataDto sampleDataDto);

    boolean deleteSampleDataByCategoryIdAndKey(Long categoryId, String key);

}
