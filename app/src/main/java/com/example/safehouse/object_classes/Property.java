package com.example.safehouse.object_classes;

import java.util.ArrayList;

public class Property {

    private int id;
    private String name;
    private ArrayList<WaterSensor> sensors;

    public Property(int id, String name) {
        this.id = id;
        this.name = name;
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

    public ArrayList<WaterSensor> getSensors() {
        return sensors;
    }

    public void setSensors(ArrayList<WaterSensor> sensors) {
        this.sensors = sensors;
    }

    public void addSensor(WaterSensor waterSensor) {
        this.sensors.add(waterSensor);
    }

    public WaterSensor getSensor(int index) {
        return this.sensors.get(index);
    }
}
