package com.example.demo.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record apiResponsePlantDto(
        Long id,
        String name,
        String slug,
        List<PlantDataDto> data,
        String description,
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonProperty("updated_at")
        LocalDateTime updatedAt,
        @JsonProperty("scientific_name")
        String scientificName,
        @JsonProperty("parent_id")
        Long parentId,
        @JsonProperty("adopter_id")
        Long adopterId,
        Integer version,
        String type,
        String link,
        PlantImagesDto images
) {
    @Override
    public Long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String slug() {
        return slug;
    }

    @Override
    public List<PlantDataDto> data() {
        return data;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public LocalDateTime createdAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime updatedAt() {
        return updatedAt;
    }

    @Override
    public String scientificName() {
        return scientificName;
    }

    @Override
    public Long parentId() {
        return parentId;
    }

    @Override
    public Long adopterId() {
        return adopterId;
    }

    @Override
    public Integer version() {
        return version;
    }

    @Override
    public String type() {
        return type;
    }

    @Override
    public String link() {
        return link;
    }

    @Override
    public PlantImagesDto images() {
        return images;
    }
}

