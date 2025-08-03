package com.example.demo.dto;

public class PlantDto {

    private String common_name;
    private String sci_name;
    private String description;

    public PlantDto() {
    }
    public PlantDto(String common_name, String sci_name, String description) {
        this.common_name = common_name;
        this.sci_name = sci_name;
        this.description = description;
    }

    public String getCommon_name() {
        return common_name;
    }

    public void setCommon_name(String common_name) {
        this.common_name = common_name;
    }

    public String getSci_name() {
        return sci_name;
    }

    public void setSci_name(String sci_name) {
        this.sci_name = sci_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
