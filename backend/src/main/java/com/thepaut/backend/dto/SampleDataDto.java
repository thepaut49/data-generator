package com.thepaut.backend.dto;

import java.time.LocalDateTime;

public record SampleDataDto(String category, String key, String value,Long version, LocalDateTime modifiedAt, String modifiedBy, boolean isBlobValue, String blobValue) {
}
