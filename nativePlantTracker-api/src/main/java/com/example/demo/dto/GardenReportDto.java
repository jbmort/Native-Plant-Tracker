package com.example.demo.dto;

public class GardenReportDto {

    private long id;
    private String garden_name;
    private String description;
    private int num_plants;

    public String getDate_started() {
        return date_started;
    }

    public void setDate_started(String date_started) {
        this.date_started = date_started;
    }

    private String date_started;

    public GardenReportDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGarden_name() {
        return garden_name;
    }

    public void setGarden_name(String garden_name) {
        this.garden_name = garden_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNum_plants() {
        return num_plants;
    }

    public void setNum_plants(int num_plants) {
        this.num_plants = num_plants;
    }

}
