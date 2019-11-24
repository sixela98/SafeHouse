package com.example.safehouse.object_classes;

public class WaterSensor {

    private int id;
    private boolean state;
    private String name;

    public WaterSensor(int id, boolean state, String name) {
        this.id = id;
        this.state = state;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
