package com.example.safehouse.object_classes;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.safehouse.Database;
import com.example.safehouse.OnGetDataListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AirQualitySensor {
    private final DatabaseReference mDatabase;
    private int id;
    private int userId;
    private int propertyId;
    private int roomId;
    private MutableLiveData<Double> temp;
    private MutableLiveData<Double> humidity;
    private MutableLiveData<Double> gas;

    public AirQualitySensor(int userId, int propertyId, int roomId, int id) {
        this.id = id;
        this.userId = userId;
        this.propertyId = propertyId;
        this.roomId = roomId;
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.temp = setTemp();
        this.humidity = setHumidity();
        this.gas = setGas();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MutableLiveData<Double> getTemp() {
        return temp;
    }

    public MutableLiveData<Double> setTemp() {
        MutableLiveData<Double> temp = new MutableLiveData<>();
        String path = "/u/u" + userId + "/p/p" + propertyId + "/r/r" + roomId + "/a/t" + id;

        mDatabase.child("u").child("u" + userId).child("p").child("p" + propertyId).child("r").child("r" + roomId).child("a").child("t" + id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double t = dataSnapshot.getValue(Double.class);
                temp.setValue(t);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return temp;
    }

    public MutableLiveData<Double> getHumidity() {
        return humidity;
    }

    public MutableLiveData<Double> setHumidity() {
        MutableLiveData<Double> humidity = new MutableLiveData<>();
        String path = "/u/u" + userId + "/p/p" + propertyId + "/r/r" + roomId + "/a/h" + id;

        mDatabase.child("u").child("u" + userId).child("p").child("p" + propertyId).child("r").child("r" + roomId).child("a").child("h" + id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double h = dataSnapshot.getValue(Double.class);
                humidity.setValue(h);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return humidity;
    }

    public MutableLiveData<Double> getGas() {
        return gas;
    }

    public MutableLiveData<Double> setGas() {
        MutableLiveData<Double> gas = new MutableLiveData<>();
        String path = "/u/u" + userId + "/p/p" + propertyId + "/r/r" + roomId + "/a/g" + id;
        new Database().readData(path, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Double g = dataSnapshot.getValue(Double.class);
                gas.setValue(g);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(DatabaseError databaseError) {

            }
        });
        return gas;
    }
}
