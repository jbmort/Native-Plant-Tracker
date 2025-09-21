package com.example.demo.dto;

public record PlantDataDto(
        String key,
        String value
) {
    @Override
    public String key() {
        return key;
    }

    @Override
    public String value() {
        return value;
    }
}

