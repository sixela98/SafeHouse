package com.example.safehouse.Objects;

public class Room {
    String name;
    String temperature;
    String humidity;
    String gas;

    public Room(String name, String temperature, String humidity, String gas) {
        this.name = name;
        this.temperature = temperature;
        this.humidity = humidity;
        this.gas = gas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }
}
