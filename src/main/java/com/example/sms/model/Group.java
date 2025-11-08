package com.example.sms.model;

public class Group extends BaseEntity {
    private String name;
    private Integer year;

    public Group() {}
    public Group(Integer id, String name, Integer year) {
        this.id = id; this.name = name; this.year = year;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
}