package com.example.safehouse.object_classes;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Room {
    private int id;
    private int userId;
    private int propertyId;
    private MutableLiveData<String> name;
    private boolean selected;
    private ArrayList<MutableLiveData<WaterSensor>> waterSensorArrayList;
    private ArrayList<MutableLiveData<AirQualitySensor>> airQualitySensorArrayList;
    private final DatabaseReference mDatabase;


    public Room(int userId, int propertyId, int id, boolean selected) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.id = id;
        this.userId = userId;
        this.propertyId = propertyId;
        this.name = setName();
        this.selected = selected;
        this.waterSensorArrayList = new ArrayList<>();
        this.airQualitySensorArrayList = new ArrayList<>();
    }

    public void setName(String name) {
        mDatabase.child("u").child("u" + userId).child("p").child("p" + propertyId).child("r").child("r" + id).child("n").setValue(name);
    }

    public MutableLiveData<String> setName() {
        MutableLiveData<String> name = new MutableLiveData<>();
        mDatabase.child("u").child("u" + userId).child("p").child("p" + propertyId).child("r").child("r" + id).child("n").addValueEventListener(new ValueEventListener() {
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

    public ArrayList<MutableLiveData<WaterSensor>> getWaterSensorArrayList() {
        return waterSensorArrayList;
    }

    public void setWaterSensorArrayList(ArrayList<MutableLiveData<WaterSensor>> waterSensorArrayList) {
        this.waterSensorArrayList = waterSensorArrayList;
    }

    public void addWaterSensor(MutableLiveData<WaterSensor> waterSensor) {
        this.waterSensorArrayList.add(waterSensor);
    }

    public MutableLiveData<WaterSensor> getWaterSensor(int id) {
        return waterSensorArrayList.get(id);
    }

    public ArrayList<MutableLiveData<AirQualitySensor>> getAirQualitySensorArrayList() {
        return airQualitySensorArrayList;
    }

    public void setAirQualitySensorArrayList(ArrayList<MutableLiveData<AirQualitySensor>> airQualitySensorArrayList) {
        this.airQualitySensorArrayList = airQualitySensorArrayList;
    }

    public void addAirQuality(MutableLiveData<AirQualitySensor> airQualitySensor) {
        this.airQualitySensorArrayList.add(airQualitySensor);
    }

    public MutableLiveData<AirQualitySensor> getAirQualitySensor(int id) {
        return airQualitySensorArrayList.get(id);
    }

}
