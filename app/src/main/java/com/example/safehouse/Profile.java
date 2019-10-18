package com.example.safehouse;

public class Profile {

    private String name;
    private int age;
    private int studentID;

    public Profile(String name, int age, int studentID) {
        this.name = name;
        this.age = age;
        this.studentID = studentID;
    }

    //Getters
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public int getStudentID() {
        return studentID;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

}
