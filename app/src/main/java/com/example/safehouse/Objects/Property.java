package com.example.safehouse.Objects;

public class Property {
    String name;
    String selected;
    String Default;

    public Property(String name, String selected, String aDefault) {
        this.name = name;
        this.selected = selected;
        Default = aDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getDefault() {
        return Default;
    }

    public void setDefault(String aDefault) {
        Default = aDefault;
    }
}
