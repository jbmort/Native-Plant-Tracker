package com.example.demo.dto;

public class PlantReportDTO {
    private int id;
    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;
    private String description;
    private int num_instances;
    private String datePlanted;

    public PlantReportDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNum_instances() {
        return num_instances;
    }

    public void setNum_instances(int num_instances) {
        this.num_instances = num_instances;
    }

    public String getDatePlanted() {
        return datePlanted;
    }

    public void setDatePlanted(String datePlanted) {
        this.datePlanted = datePlanted;
    }
}

