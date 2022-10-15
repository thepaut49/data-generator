package com.thepaut.backend.dto;

public record SampleDataCreationDto(String key, String value, Long version, boolean isBlobValue, String blobValue) {
}
