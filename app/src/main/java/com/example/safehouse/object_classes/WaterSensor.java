package com.example.safehouse.object_classes;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class WaterSensor {
    private final DatabaseReference mDatabase;
    private int id;
    private int userId;
    private int propertyId;
    private int roomId;
    private MutableLiveData<String> state;
    private MutableLiveData<String> name;

    public WaterSensor(int userId, int propertyId, int roomId, int id) {
        this.id = id;
        this.userId = userId;
        this.propertyId = propertyId;
        this.roomId = roomId;
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.state = setState();
        this.name = setName();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public void setName(String name) {
        mDatabase.child("u").child("u" + userId).child("p").child("p" + propertyId).child("r").child("r" + roomId).child("w").child("w" + id).child("n").setValue(name);
    }

    public MutableLiveData<String> setName() {
        MutableLiveData<String> name = new MutableLiveData<>();
        mDatabase.child("u").child("u" + userId).child("p").child("p" + propertyId).child("r").child("r" + roomId).child("w").child("w" + id).child("n").addValueEventListener(new ValueEventListener() {
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

    public MutableLiveData<String> getState() {
        return state;
    }

    public MutableLiveData<String> setState() {
        MutableLiveData<String> state = new MutableLiveData<>();
        mDatabase.child("u").child("u" + userId).child("p").child("p" + propertyId).child("r").child("r" + roomId).child("w").child("w" + id).child("s").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean s = dataSnapshot.getValue(Boolean.class);
                if(s){
                    state.setValue("Water detected!");
                }else{
                    state.setValue("Nothing to report");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return state;
    }

}
