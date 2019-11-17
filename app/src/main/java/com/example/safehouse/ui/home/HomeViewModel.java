package com.example.safehouse.ui.home;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.safehouse.object_classes.Property;
import com.example.safehouse.object_classes.WaterSensor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private ArrayList<MutableLiveData<String>> water_sensor_1 = new ArrayList<>();
    private ArrayList<MutableLiveData<String>> water_sensor_2 = new ArrayList<>();
    private ArrayList<MutableLiveData<String>> air_quality_1 = new ArrayList<>();
    private final DatabaseReference mDatabase;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        updateWaterSensors();

        //setMyProperties();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public ArrayList<MutableLiveData<String>> getmDevice(int deviceChoice) {
        ArrayList<MutableLiveData<String>> choice = new ArrayList<>();
        switch(deviceChoice){
            case 1:
                choice = water_sensor_1;
                break;
            case 2:
                choice = water_sensor_2;
                break;
            case 3:
                choice = air_quality_1;
                break;
        }
        return choice;
    }

    public MutableLiveData<String> updateWaterSensorState(String property, int id) {
        final MutableLiveData<String> sensor = new MutableLiveData<>();
        mDatabase.child(property).child("Water sensor " + id).child("State").addValueEventListener(new ValueEventListener() {
                @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean water_sensor = dataSnapshot.getValue(Boolean.class);
                if(water_sensor==true){
                    sensor.setValue("Water Sensor is Wet\n");
                }
                if(water_sensor==false){
                    sensor.setValue("Water Sensor is Dry\n");
                }
            }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    });
        return sensor;
}

    public MutableLiveData<String> updateWaterSensorName(String property, int id) {
        final MutableLiveData<String> sensor = new MutableLiveData<>();
        mDatabase.child(property).child("Water sensor " + id).child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                sensor.setValue(name + "\n");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return sensor;
    }
    public void updateWaterSensors() {
        water_sensor_1.clear();
        water_sensor_2.clear();
        int numDevices = 2;
        for(int i = 1; i <= numDevices; i++) {
            switch (i){
                case 1:
                    water_sensor_1.add(updateWaterSensorName("Property1", i));
                    water_sensor_1.add(updateWaterSensorState("Property1", i));
                    break;
                case 2:
                    water_sensor_2.add(updateWaterSensorName("Property1", i));
                    water_sensor_2.add(updateWaterSensorState("Property1", i));
                    break;
            }
        }
    }
}