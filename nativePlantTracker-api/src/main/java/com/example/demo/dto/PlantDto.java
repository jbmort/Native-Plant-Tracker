package com.example.demo.dto;

// import com.example.demo.repository.TypesRepository;

public class PlantDto {

    public long getId() {
        return externalId;
    }

    public void setId(long id) {
        this.externalId = id;
    }

    private long externalId;


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    private String typeName;

    private String commonName;
    private String sciName;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;
    private String description;
    private long type;

    public String getFlowerColor() {
        return flowerColor;
    }

    public void setFlowerColor(String flowerColor) {
        this.flowerColor = flowerColor;
    }

    private String flowerColor;

    public PlantDto(long id, String common_name, String sci_name, String imageUrl, String description) {
        this.externalId = id;
        this.commonName = common_name;
        this.sciName = sci_name;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getSciName() {
        return sciName;
    }

    public void setSciName(String sciName) {
        this.sciName = sciName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }
}
