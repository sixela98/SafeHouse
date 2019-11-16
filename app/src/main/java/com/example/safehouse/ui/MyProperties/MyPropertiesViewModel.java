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

    private MutableLiveData<String> mText;
    private ArrayList<MutableLiveData<String>> mProperties = new ArrayList<>();
    private final DatabaseReference mDatabase;


    public MyPropertiesViewModel() {
        mText = new MutableLiveData<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mText.setValue(updateDefault() + "\n " + updateSelected());
        updateProperties();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public ArrayList<MutableLiveData<String>> getmProperties() {
        return mProperties;
    }

    public boolean isDefault(int id){
        final MutableLiveData<Boolean> isDefault = new MutableLiveData<>();
        mDatabase.child("Property" + id).child("Default").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean default_sensor = dataSnapshot.getValue(boolean.class);
                if(default_sensor == true) {
                    isDefault.setValue(true);
                }
                if(default_sensor == false) {
                    isDefault.setValue(false);
                }
                System.out.println("default" + id + " " + default_sensor + " " + isDefault.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       // boolean result = isDefault.getValue();
        return true;//result;
    }

    public String updateDefault() {
        int numProperties = 2;
        String defaultText  = "Not set";
        for(int i = 1; i <= numProperties; i++) {
            if(isDefault(i)) {
                defaultText = "Default property: Property" + i;
                break;
            }
        }
        return defaultText;
    }

    public boolean isSelected(int id) {
        ArrayList<Boolean> isSelected = new ArrayList<>();
        mDatabase.child("Property" + id).child("Selected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean selected_sensor = dataSnapshot.getValue(boolean.class);
                isSelected.add(selected_sensor);
                System.out.println("selected" + id + " " + selected_sensor + " " + isSelected.get(0));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return true;//isSelected.get(0);
    }

    public String updateSelected() {
        int numProperties = 2;
        String selectedText  = "Not set";
        for(int i = 1; i <= numProperties; i++) {
            if(isSelected(i)) {
                selectedText = "Selected property: Property" + i;
                break;
            }
        }
        return selectedText;
    }

    public MutableLiveData<String> updateProperty(int id) {
        MutableLiveData<String> property = new MutableLiveData<>();
        property.setValue("Default: " + isDefault(id) +
                            "\nSelected: " + isSelected(id));
        return property;
    }
    public void updateProperties() {
        mProperties.clear();
        int numProperties = 2;
        for(int i = 1; i <= numProperties; i++) {
            mProperties.add(updateProperty(i));
        }
    }
}