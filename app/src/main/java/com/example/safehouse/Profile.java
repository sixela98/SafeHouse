package com.example.safehouse;


public class Profile {

    private String name;
    private String password;

    public Profile(String name) {
        this.name = name;
    }
    public Profile(String name, String password) {
        this.name = name;
        this.password = password;
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getPassword(){ return password;}


    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password){
        this.password = password;
    }


}
