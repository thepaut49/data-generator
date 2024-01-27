package com.thepaut.backend.dto;

public record SampleDataCreationDto(Long categoryId, String key, String value, boolean isBlobTypeValue, String blobValue) {
}
