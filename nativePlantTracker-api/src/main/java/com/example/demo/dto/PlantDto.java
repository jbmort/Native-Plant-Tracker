package com.example.demo.dto;

// import com.example.demo.repository.TypesRepository;

public record PlantDto(
        long externalId,
        String typeName,
        String commonName,
        String scientificName,
        String imageUrl,
        String description
) {

//    public long getId() {
//        return externalId;
//    }
//
//    public void setId(long id) {
//        this.externalId = id;
//    }
//
//    private long externalId;
//
//
//    public String getTypeName() {
//        return typeName;
//    }
//
//    public void setTypeName(String typeName) {
//        this.typeName = typeName;
//    }
//
//    private String typeName;
//
//    private String commonName;
//    private String scientificName;
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
//
//    private String imageUrl;
//    private String description;
//
//
//    public PlantDto(long id, String common_name, String sci_name, String imageUrl, String description) {
//        this.externalId = id;
//        this.commonName = common_name;
//        this.scientificName = sci_name;
//        this.imageUrl = imageUrl;
//        this.description = description;
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
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

}
