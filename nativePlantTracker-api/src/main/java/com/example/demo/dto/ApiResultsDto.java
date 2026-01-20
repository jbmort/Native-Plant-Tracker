package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiResultsDto(
        Long id,
        String commonName,
        String scientificName,
        String imageUrl
) {
    public ApiResultsDto(Long id, String commonName, String scientificName, String imageUrl) {
        this.id = id;
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.imageUrl = imageUrl;
    };

    public ApiResultsDto(){
        this(0L, "Unknown", "Unknown", "Unknown");
    }
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getCommonName() {
//        return commonName;
//    }
//
//    public void setCommonName(String commonName) {
//        this.commonName = commonName;
//    }
//
//    public String getScientificName() {
//        return scientificName;
//    }
//
//    public void setScientificName(String scientificName) {
//        this.scientificName = scientificName;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }

}
