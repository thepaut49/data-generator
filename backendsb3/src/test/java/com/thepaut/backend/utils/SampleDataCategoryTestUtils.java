package com.thepaut.backend.utils;

import com.thepaut.backend.dto.SampleDataCategoryDto;
import org.jetbrains.annotations.NotNull;

public class SampleDataCategoryTestUtils {

    @NotNull
    public static SampleDataCategoryDto copyDto(SampleDataCategoryDto createdSampleDataCategory, String categoryName) {
        var updatedSampleDataCategory = new SampleDataCategoryDto();
        updatedSampleDataCategory.setVersions(createdSampleDataCategory.getVersions());
        updatedSampleDataCategory.setId(createdSampleDataCategory.getId());
        updatedSampleDataCategory.setName(categoryName);
        updatedSampleDataCategory.setModifiedBy(TestConstants.MODIFIED_BY_USER_2);
        updatedSampleDataCategory.setModifiedAt(createdSampleDataCategory.getModifiedAt());
        updatedSampleDataCategory.setVersion(createdSampleDataCategory.getVersion());
        return updatedSampleDataCategory;
    }
}
