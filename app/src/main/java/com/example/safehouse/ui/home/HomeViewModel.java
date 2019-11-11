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
    private ArrayList<MutableLiveData<String>> mDevices = new ArrayList<>();
    private final DatabaseReference mDatabase;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("sensor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List notes = new ArrayList<>();
                Boolean water_sensor = dataSnapshot.getValue(Boolean.class);
                if(water_sensor==true){
                    mText.setValue("Water Sensor is Wet ");
                }
                if(water_sensor==false){
                    mText.setValue("Water Sensor is Dry");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        updateWaterSensors();

        //setMyProperties();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public ArrayList<MutableLiveData<String>> getmDevices() {
        return mDevices;
    }

    public MutableLiveData<String> updateWaterSensor(String porperty, int id) {
        final MutableLiveData<String> sensor = new MutableLiveData<>();
        mDatabase.child(porperty).child("Water sensor " + id).child("State").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean water_sensor = dataSnapshot.getValue(Boolean.class);
                if(water_sensor==true){
                    sensor.setValue("Water Sensor " + id + " is Wet");
                }
                if(water_sensor==false){
                    sensor.setValue("Water Sensor " + id + " is Dry");
                }
            }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    });
        return sensor;
}
    public void updateWaterSensors() {
        mDevices.clear();
        int numDevices = 2;
        for(int i = 1; i <= numDevices; i++) {
            mDevices.add(updateWaterSensor("Property1", i));
        }
    }
}