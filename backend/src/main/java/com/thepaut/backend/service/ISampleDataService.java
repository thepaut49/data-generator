package com.thepaut.backend.service;

import com.thepaut.backend.dto.SampleDataDto;

import java.util.List;

public interface ISampleDataService {

    List<SampleDataDto> getSampleDatas(String categoryName, String key, String value, boolean isBlobValue);

    SampleDataDto getSampleData(String categoryName, String key);

    SampleDataDto rollbackToPreviousVersion(String categoryName, String key);

    SampleDataDto rollbackToVersion(String categoryName, String key, Long version);

    SampleDataDto createSampleData(String categoryName, SampleDataDto sampleDataDto);

    SampleDataDto updateSampleData(String categoryName, String key, SampleDataDto sampleDataDto);

    boolean deleteSampleDataByCategoryNameAndKey(String category, String key);

}
