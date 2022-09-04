package com.thepaut.backend.dto;

import java.time.LocalDateTime;

public record SampleDataCategoryDto(Long id, String categoryName, Long version, String modifiedBy, LocalDateTime modifiedAt) {
}
