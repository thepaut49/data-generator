package com.thepaut.backend.utils;

import com.thepaut.backend.dto.SampleDataDto;
import org.jetbrains.annotations.NotNull;

public class SampleDataTestUtils {

    @NotNull
    public static SampleDataDto copyDto(SampleDataDto createdSampleData, Long categoryId, String key, boolean isBlobValue, String value) {
        var updatedSampleData = new SampleDataDto();
        updatedSampleData.setVersions(createdSampleData.getVersions());
        updatedSampleData.setId(createdSampleData.getId());
        updatedSampleData.setKey(key);
        if (isBlobValue) {
            updatedSampleData.setBlobValue(value);
        } else {
            updatedSampleData.setValue(value);
        }
        updatedSampleData.setCategoryId(categoryId);
        updatedSampleData.setModifiedBy(TestConstants.MODIFIED_BY_USER_2);
        updatedSampleData.setModifiedAt(createdSampleData.getModifiedAt());
        updatedSampleData.setVersion(createdSampleData.getVersion());
        return updatedSampleData;
    }
}
