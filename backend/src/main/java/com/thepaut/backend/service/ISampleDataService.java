package com.thepaut.backend.service;

import com.thepaut.backend.dto.SampleDataDto;

import java.util.List;

public interface ISampleDataService {

    List<SampleDataDto> getSampleDatas(String categoryName, String key, String value, boolean isBlobValue) ;

    SampleDataDto rollbackToPreviousVersion();

    SampleDataDto rollbackToVersion(Long version);

    SampleDataDto createSampleData(SampleDataDto sampleDataDto);

    SampleDataDto updateSampleData(SampleDataDto sampleDataDto);

    boolean deleteSampleData(String category, String key);

    boolean deleteSampleData(Long id);

}
