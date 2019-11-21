package com.example.safehouse.ui.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.safehouse.OnGetDataListener;
import com.example.safehouse.object_classes.AirQualitySensor;
import com.example.safehouse.object_classes.Property;
import com.example.safehouse.object_classes.Room;
import com.example.safehouse.object_classes.User;
import com.example.safehouse.object_classes.WaterSensor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private ArrayList<MutableLiveData<String>> water_sensor_1_1 = new ArrayList<>();
    private ArrayList<MutableLiveData<String>> water_sensor_2_1 = new ArrayList<>();
    private ArrayList<MutableLiveData<String>> water_sensor_2 = new ArrayList<>();
    private ArrayList<MutableLiveData<String>> air_quality_1 = new ArrayList<>();
    private final DatabaseReference mDatabase;
    private MutableLiveData<String> mSelectedProperty;
    private MutableLiveData<String> mDefaultProperty;
    private ArrayList<MutableLiveData<User>> userArrayList;


    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mSelectedProperty = new MutableLiveData<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userArrayList = new ArrayList<>();
        updateWaterSensors();
        updateAirSensors();
        setUpUser(new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                int size = (int)dataSnapshot.getChildrenCount();
                userArrayList.clear();
                for(int i = 1; i <= size; i++) {
                    final int userId = i;
                    MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
                    User user = new User(i, true);
                    setUpProperty(i, new OnGetDataListener() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            ArrayList<MutableLiveData<Property>> propertyArrayList = new ArrayList<>();
                            int size = (int)dataSnapshot.getChildrenCount();
                            propertyArrayList.clear();
                            for(int i = 1; i <= size; i++) {
                                final int propertyId = i;
                                MutableLiveData<Property> propertyMutableLiveData = new MutableLiveData<>();
                                Property property = new Property(userId, propertyId);
                                setUpRoom(userId, propertyId, new OnGetDataListener() {
                                    @Override
                                    public void onSuccess(DataSnapshot dataSnapshot) {
                                        ArrayList<MutableLiveData<Room>> roomArrayList = new ArrayList<>();
                                        int size = (int)dataSnapshot.getChildrenCount();
                                        roomArrayList.clear();
                                        for(int i = 1; i <= size; i++) {
                                            final int roomId = i;
                                            MutableLiveData<Room> roomMutableLiveData = new MutableLiveData<>();
                                            Room room = new Room(userId, propertyId, roomId, true);
                                            setUpWaterSensor(userId, propertyId, roomId, new OnGetDataListener() {
                                                @Override
                                                public void onSuccess(DataSnapshot dataSnapshot) {
                                                    ArrayList<MutableLiveData<WaterSensor>> waterSensorArrayList = new ArrayList<>();
                                                    int size = (int)dataSnapshot.getChildrenCount();
                                                    waterSensorArrayList.clear();
                                                    for(int j = 1; j <= size; j++) {
                                                        MutableLiveData<WaterSensor> waterSensorMutableLiveData = new MutableLiveData<>();
                                                        WaterSensor waterSensor = new WaterSensor(userId, propertyId, roomId, j);
                                                        waterSensorMutableLiveData.setValue(waterSensor);
                                                        waterSensorArrayList.add(waterSensorMutableLiveData);
                                                    }
                                                    room.setWaterSensorArrayList(waterSensorArrayList);
                                                }

                                                @Override
                                                public void onStart() {
                                                    System.out.println("onStart Water");
                                                }

                                                @Override
                                                public void onFailure() {
                                                    System.out.println("onFail Water");
                                                }
                                            });
                                            setUpAirQuality(userId, propertyId, i, new OnGetDataListener() {
                                                @Override
                                                public void onSuccess(DataSnapshot dataSnapshot) {
                                                    ArrayList<MutableLiveData<AirQualitySensor>> airQualitySensorArrayList= new ArrayList<>();
                                                    int size = (int)dataSnapshot.getChildrenCount()/3;
                                                    airQualitySensorArrayList.clear();
                                                    for(int j = 1; j <= size; j++) {
                                                        MutableLiveData<AirQualitySensor> airQualitySensorMutableLiveData = new MutableLiveData<>();
                                                        AirQualitySensor airQualitySensor = new AirQualitySensor(userId, propertyId, roomId, j);
                                                        airQualitySensorMutableLiveData.setValue(airQualitySensor);
                                                        airQualitySensorArrayList.add(airQualitySensorMutableLiveData);
                                                    }
                                                    room.setAirQualitySensorArrayList(airQualitySensorArrayList);
                                                    HomeFragment.getInstance().updateTest(userArrayList);
                                                }

                                                @Override
                                                public void onStart() {
                                                    System.out.println("onStart Air");
                                                }

                                                @Override
                                                public void onFailure() {
                                                    System.out.println("onFail Air");
                                                }
                                            });
                                            roomMutableLiveData.setValue(room);
                                            roomArrayList.add(roomMutableLiveData);
                                        }
                                        property.setRooms(roomArrayList);
                                    }

                                    @Override
                                    public void onStart() {
                                        System.out.println("onStart Room");
                                    }

                                    @Override
                                    public void onFailure() {
                                        System.out.println("onFail Room");
                                    }
                                });
                                propertyMutableLiveData.setValue(property);
                                propertyArrayList.add(propertyMutableLiveData);
                            }
                            user.setProperties(propertyArrayList);
                        }

                        @Override
                        public void onStart() {
                            System.out.println("onStart Property");
                        }

                        @Override
                        public void onFailure() {
                            System.out.println("onFail Property");
                        }
                    });
                    userMutableLiveData.setValue(user);
                    userArrayList.add(userMutableLiveData);
                }
            }

            @Override
            public void onStart() {
                System.out.println("onStart User");
            }

            @Override
            public void onFailure() {
                System.out.println("onFail User");
            }
        });
    }

    public ArrayList<MutableLiveData<User>> getUserArrayList() {
        return userArrayList;
    }

    public void setUserArrayList(ArrayList<MutableLiveData<User>> users) {
        this.userArrayList = users;
    }

    public void setUpUser(final OnGetDataListener listener) {
        listener.onStart();
        System.out.println("Just before entering\n");
        mDatabase.child("u").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure();
            }
        });
    }

    public void setUpProperty(int userId, final OnGetDataListener listener) {
        listener.onStart();
        mDatabase.child("u").child("u" + userId).child("p").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure();
            }
        });
    }


    public void setUpRoom(int userId, int propertyId, final OnGetDataListener listener) {
        listener.onStart();
        mDatabase.child("u").child("u" + userId).child("p").child("p" + propertyId).child("r").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure();
            }
        });
    }

    public void setUpWaterSensor(int userId, int propertyId, int roomId, final OnGetDataListener listener) {
        listener.onStart();
        mDatabase.child("u").child("u" + userId).child("p").child("p" + propertyId).child("r").child("r" + roomId).child("w").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure();
            }
        });
    }

    public void setUpAirQuality(int userId, int propertyId, int roomId, final OnGetDataListener listener) {
        listener.onStart();
        mDatabase.child("u").child("u" + userId).child("p").child("p" + propertyId).child("r").child("r" + roomId).child("a").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure();
            }
        });
    }

    public LiveData<String> getText() {
        return mText;
    }

    public ArrayList<MutableLiveData<String>> getmDevice(int propertyChoice, int deviceChoice) {
        ArrayList<MutableLiveData<String>> choice = new ArrayList<>();
        switch(deviceChoice){
            case 1:
                if(propertyChoice == 1) {
                    choice = water_sensor_1_1;
                    break;
                }else if(propertyChoice == 2){
                    choice = water_sensor_2_1;
                    break;
                }
            case 2:
                choice = water_sensor_2;
                break;
            case 3:
                choice = air_quality_1;
                break;
        }
        return choice;
    }

    public MutableLiveData<String> updateWaterSensorName(String property, int id) {
        final MutableLiveData<String> sensor = new MutableLiveData<>();
        mDatabase.child(property).child("Water sensor " + id).child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                sensor.setValue(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return sensor;
    }

    public MutableLiveData<String> updateWaterSensorState(String property, int id) {
        final MutableLiveData<String> sensor = new MutableLiveData<>();
        System.out.println("Property: " + property  + " Id: " + id);
        mDatabase.child(property).child("Water sensor " + id).child("State").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean water_sensor = dataSnapshot.getValue(Boolean.class);
                if(water_sensor==true){
                    sensor.setValue("Water Sensor is Wet");
                }
                if(water_sensor==false){
                    sensor.setValue("Water Sensor is Dry");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return sensor;
    }

    public MutableLiveData<String> getSelectedProperty() {
        for(int i = 1; i <= 2; i++) {
            mDatabase.child("Property" + i).child("Selected").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean selected = dataSnapshot.getValue(Boolean.class);
                    if(selected){
                        mSelectedProperty.setValue(dataSnapshot.getRef().getParent().toString());
                        System.out.println("Selected property " + mSelectedProperty.getValue());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        return mSelectedProperty;
    }

    public MutableLiveData<String> getDefaultProperty() {
        for(int i = 1; i <= 2; i++) {
            mDatabase.child("Property" + i).child("Default").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean Default = dataSnapshot.getValue(Boolean.class);
                    if(Default){
                        mDefaultProperty.setValue(dataSnapshot.getRef().getParent().toString());
                        System.out.println("Default property " + mDefaultProperty.getValue());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        return mDefaultProperty;
    }

    public MutableLiveData<String> updateAirSensorName(String property, int id) {
        final MutableLiveData<String> sensor = new MutableLiveData<>();
        mDatabase.child(property).child("Air quality " + id).child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                sensor.setValue(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return sensor;
    }

    public MutableLiveData<String> updateAirSensorGas(String property, int id) {
        final MutableLiveData<String> sensor = new MutableLiveData<>();
        mDatabase.child(property).child("Air quality " + id).child("Gas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int gas = dataSnapshot.getValue(Integer.class);
                sensor.setValue("Current gas level: " + gas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return sensor;
    }

    public MutableLiveData<String> updateAirSensorHumidity(String property, int id) {
        final MutableLiveData<String> sensor = new MutableLiveData<>();
        mDatabase.child(property).child("Air quality " + id).child("Humidity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int humidity = dataSnapshot.getValue(Integer.class);
                sensor.setValue("Current humidity level: " + humidity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return sensor;
    }

    public MutableLiveData<String> updateAirSensorTemperature(String property, int id) {
        final MutableLiveData<String> sensor = new MutableLiveData<>();
        mDatabase.child(property).child("Air quality " + id).child("Temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int temperature = dataSnapshot.getValue(Integer.class);
                sensor.setValue("Current temperature level: " + temperature);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return sensor;
    }

    public void updateWaterSensors() {
        water_sensor_1_1.clear();
        water_sensor_2_1.clear();
        water_sensor_2.clear();
        int numDevices = 2;
        for(int i = 1; i <= numDevices; i++) {
            switch (i){
                case 1:
                    water_sensor_1_1.add(updateWaterSensorName("Property1", i));
                    water_sensor_1_1.add(updateWaterSensorState("Property1", i));
                    water_sensor_2_1.add(updateWaterSensorName("Property2", i));
                    water_sensor_2_1.add(updateWaterSensorState("Property2", i));
                    break;
                case 2:
                    water_sensor_2.add(updateWaterSensorName("Property1", i));
                    water_sensor_2.add(updateWaterSensorState("Property1", i));
                    break;
            }
        }
    }

    public void updateAirSensors() {
        air_quality_1.clear();
        air_quality_1.add(updateAirSensorName("Property1", 1));
        air_quality_1.add(updateAirSensorGas("Property1", 1));
        air_quality_1.add(updateAirSensorHumidity("Property1", 1));
        air_quality_1.add(updateAirSensorTemperature("Property1", 1));
    }
}