package com.example.safehouse.ui.MyProperties;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.safehouse.object_classes.Property;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyPropertiesViewModel extends ViewModel {

    private MutableLiveData<String> mSelected;
    private MutableLiveData<String> mDefault;
    private ArrayList<MutableLiveData<String>> mProperty_1 = new ArrayList<>();
    private ArrayList<MutableLiveData<String>> mProperty_2 = new ArrayList<>();
    private final DatabaseReference mDatabase;

    public MyPropertiesViewModel() {
        mSelected = new MutableLiveData<>();
        mDefault= new MutableLiveData<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        updateProperties();
    }

    public ArrayList<MutableLiveData<String>> getmProperty(int propertyChoice) {
        ArrayList<MutableLiveData<String>> choice = new ArrayList<>();
        switch(propertyChoice){
            case 1:
                choice = mProperty_1;
                break;
            case 2:
                choice = mProperty_2;
                break;
        }
        return choice;
    }


    public LiveData<String> getSelected() {
        return mSelected;
    }

    public MutableLiveData<String> getmDefault() {
        return mDefault;
    }

    public ArrayList<MutableLiveData<String>> getmProperty_1() {
        return mProperty_1;
    }

    public ArrayList<MutableLiveData<String>> getmProperty_2() {
        return mProperty_2;
    }

    public MutableLiveData<String> updatePropertyName(int id) {
        final MutableLiveData<String> propertyName = new MutableLiveData<>();
        mDatabase.child("Property" + id).child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                propertyName.setValue(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return propertyName;
    }

    public MutableLiveData<String> updatePropertyDefault(int id) {
        final MutableLiveData<String> propertyDefault = new MutableLiveData<>();
        mDatabase.child("Property" + id).child("Default").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean Default = dataSnapshot.getValue(Boolean.class);
                if(Default==true){
                    propertyDefault.setValue("Default: True ");
                }
                if(Default==false){
                    propertyDefault.setValue("Default: False");
                }            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return propertyDefault;
    }

    public MutableLiveData<String> updatePropertySelected(int id) {
        final MutableLiveData<String> propertySelected = new MutableLiveData<>();
        mDatabase.child("Property" + id).child("Selected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean Selected = dataSnapshot.getValue(Boolean.class);
                if(Selected==true){
                    propertySelected.setValue("Selected: True ");
                }
                if(Selected==false){
                    propertySelected.setValue("Selected: False");
                }            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return propertySelected;
    }

   /* public MutableLiveData<String> updatePropertyDevices(int id) {
        final MutableLiveData<String> propertyName = new MutableLiveData<>();
        mDatabase.child("Property" + id).child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                propertyName.setValue(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return propertyName;
    }*/

    public void updateProperties() {
        mProperty_1.clear();
        mProperty_2.clear();
        int numProperties = 2;
        for(int i = 1; i <= numProperties; i++) {
            switch (i){
                case 1:
                    mProperty_1.add(updatePropertyName(i));
                    mProperty_1.add(updatePropertySelected(i));
                    mProperty_1.add(updatePropertyDefault(i));
                    break;
                case 2:
                    mProperty_2.add(updatePropertyName(i));
                    mProperty_2.add(updatePropertySelected(i));
                    mProperty_2.add(updatePropertyDefault(i));
                    break;
            }
        }
    }
}