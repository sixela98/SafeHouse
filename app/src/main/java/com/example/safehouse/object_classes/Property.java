package com.example.safehouse.object_classes;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Property {
    private int id;
    private int userId;
    private MutableLiveData<String> name;
    private ArrayList<MutableLiveData<Room>> rooms;
    private MutableLiveData<Boolean> selected;
    private MutableLiveData<Boolean> Default;
    private final DatabaseReference mDatabase;


    public Property(int userId, int id) {
        this.id = id;
        this.userId = userId;
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.name = setName();
        this.rooms = new ArrayList<>();
        this.selected = setSelected();
        this.Default = setDefault();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        mDatabase.child("u").child("u" + userId).child("p").child("p" + id).child("n").setValue(name);
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<String> setName() {
        MutableLiveData<String> name = new MutableLiveData<>();
        mDatabase.child("u").child("u" + userId).child("p").child("p" + id).child("n").addValueEventListener(new ValueEventListener() {
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

    public ArrayList<MutableLiveData<Room>> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<MutableLiveData<Room>> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(MutableLiveData<Room> room) {
        this.rooms.add(room);
    }

    public MutableLiveData<Room> getRoom(int id) {
        return rooms.get(id);
    }

    public MutableLiveData<Boolean> getSelected() {
        return selected;
    }

    public MutableLiveData<Boolean> setSelected() {
        MutableLiveData<Boolean> selected = new MutableLiveData<>();
        mDatabase.child("u").child("u" + userId).child("p").child("p" + id).child("s").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean s = dataSnapshot.getValue(Boolean.class);
                selected.setValue(s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return selected;
    }

    public MutableLiveData<Boolean> setDefault() {
        MutableLiveData<Boolean> aDefault = new MutableLiveData<>();
        mDatabase.child("u").child("u" + userId).child("p").child("p" + id).child("d").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean d = dataSnapshot.getValue(Boolean.class);
                aDefault.setValue(d);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return aDefault;
    }

    public MutableLiveData<Boolean> getDefault() {
        return Default;
    }

}
