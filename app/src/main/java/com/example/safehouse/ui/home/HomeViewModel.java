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
    private MutableLiveData<ArrayList<String>> mDevices;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mDevices = new MutableLiveData<>();
        final DatabaseReference mDatabase;
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

  /*      mDatabase.child("Property1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long numChildren = dataSnapshot.getChildrenCount();
                final ArrayList<String> newDev = new ArrayList<>();
                for(long i = 1; i <= numChildren; i++) {
                    mDatabase.child("Property1").child("Water sensor " + i).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Boolean water_sensor = dataSnapshot.getValue(Boolean.class);
                            if(water_sensor==true){
                                newDev.add("Water Sensor is Wet ");
                            }
                            if(water_sensor==false){
                                newDev.add("Water Sensor is Dry");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                mDevices.setValue(newDev);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });*/

        //setMyProperties();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<ArrayList<String>> getmDevices() {
        return mDevices;
    }
}