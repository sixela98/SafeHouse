package com.example.safehouse.object_classes;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class User {

    private int id;
    private MutableLiveData<String> email;
    private ArrayList<MutableLiveData<Property>> propertyArrayList;
    private boolean selected;
    private final DatabaseReference mDatabase;


    public User(int id, boolean selected) {
        this.id = id;
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.email = setEmail();
        this.propertyArrayList = new ArrayList<>();
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public MutableLiveData<String> setEmail() {
        MutableLiveData<String> name = new MutableLiveData<>();
        mDatabase.child("u").child("u" + id).child("n").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String n = dataSnapshot.getValue(String.class);
                name.setValue(n);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return name;
    }

    public ArrayList<MutableLiveData<Property>> getProperties() {
        return propertyArrayList;
    }

    public void setProperties(ArrayList<MutableLiveData<Property>> properties) {
        this.propertyArrayList = properties;
    }

    public void addProperty(MutableLiveData<Property> property) {
        this.propertyArrayList.add(property);
    }

    public MutableLiveData<Property> getProperty(int id) {
        return this.propertyArrayList.get(id);
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
