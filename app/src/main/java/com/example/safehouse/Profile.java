package com.example.safehouse;

import com.example.safehouse.object_classes.Property;

import java.util.ArrayList;

public class Profile {

    private String name;
    private ArrayList<Property> properties;

    public Profile(String name) {
        this.name = name;
    }

    public void addProperty(Property property) {
        this.properties.add(property);
    }

    public Property getProperty(int index) {
        return this.properties.get(index);
    }

    //Getters
    public String getName() {
        return name;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setProperties(ArrayList<Property> properties) {
        this.properties = properties;
    }


}
