package com.example.demo.dto;

public class PlantReportDTO {
    private int id;
    private String name;
    private String description;
    private int num_instances;
    private double years_present;

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

    public double getYears_present() {
        return years_present;
    }

    public void setYears_present(double years_present) {
        this.years_present = years_present;
    }
}
