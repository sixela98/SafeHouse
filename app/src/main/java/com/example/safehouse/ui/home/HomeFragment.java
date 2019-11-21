package com.example.safehouse.ui.home;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.safehouse.Database;
import com.example.safehouse.OnGetDataListener;
import com.example.safehouse.R;
import com.example.safehouse.object_classes.AirQualitySensor;
import com.example.safehouse.object_classes.Property;
import com.example.safehouse.object_classes.Room;
import com.example.safehouse.object_classes.User;
import com.example.safehouse.object_classes.WaterSensor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private static HomeFragment instance;
    private RelativeLayout water_sensor_layout_1;
    private TextView water_sensor_name_1;
    private TextView water_sensor_state_1;
    private RelativeLayout water_sensor_layout_2;
    private TextView water_sensor_name_2;
    private TextView water_sensor_state_2;
    private RelativeLayout air_quality_layout;
    private TextView air_quality_name;
    private TextView air_quality_gas;
    private TextView air_quality_humidity;
    private TextView air_quality_temperature;
    private TextView water_sensor_title;
    private TextView air_quality_sensor_title;
    private Button button;
    NotificationManager notificationManager;
    private int numdevices = 3;
    private View root;
    private Observer<User> userObserver;
    private Observer<Property> propertyObserver;
    private Observer<String> roomNameObserver;
    private Observer<WaterSensor> waterSensorObserver;
    private Observer<String> waterSensorNameObserver;
    private Observer<WaterSensor> waterSensorStateObserver;
    private Observer<AirQualitySensor> airQualitySensorObserver;
    private ListView userList;
    private ListView propertyList;
    private ListView roomList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        instance = this;
        notificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        water_sensor_title = root.findViewById(R.id.water_sensor_title);
        air_quality_sensor_title = root.findViewById(R.id.air_quality_sensor_title);
        userList = root.findViewById(R.id.userList);
        propertyList = root.findViewById(R.id.propertyList);
        roomList = root.findViewById(R.id.roomList);
        setUserListView();
        setPropertyListView(1);
        propertyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setRoomListView(1, position+1);
            }
        });
        //setTestText();
        //updateUI(root);
        return root;
    }

    public static HomeFragment getInstance() {
        return instance;
    }

    public void setTestText() {
        String usersPath = "/u";
        new Database().getSize(usersPath, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                int userSize = (int)dataSnapshot.getChildrenCount();
                ArrayList<String> userArrayList = new ArrayList<>();
                for(int i = 1; i <= userSize; i++) {
                    String userPath = usersPath + "/u";
                    new Database().readData(userPath + i + "/e", new OnGetDataListener() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            String n = dataSnapshot.getValue(String.class);
                            userArrayList.add(n);
                            ArrayAdapter userAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, userArrayList);
                            userList.setAdapter(userAdapter);
                            System.out.println("The size of users: " + userArrayList.size());
                            ArrayList<String> propertyArrayList = new ArrayList<>();
                            for(int j = 1; j <= userArrayList.size(); j++) {
                                String propertiesPath = userPath + j + "/p";
                                new Database().getSize(propertiesPath, new OnGetDataListener() {
                                    @Override
                                    public void onSuccess(DataSnapshot dataSnapshot) {
                                        int propertySize = (int)dataSnapshot.getChildrenCount();
                                        for(int k = 1; k <= propertySize; k++) {
                                            String propertyPath = propertiesPath + "/p";
                                            new Database().readData(propertyPath + k + "/n", new OnGetDataListener() {
                                                @Override
                                                public void onSuccess(DataSnapshot dataSnapshot) {
                                                    String n = dataSnapshot.getValue(String.class);
                                                    propertyArrayList.add(n);
                                                    ArrayAdapter propertyAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, propertyArrayList);
                                                    propertyList.setAdapter(propertyAdapter);
                                                    System.out.println("The size of property: " + propertyArrayList.size());
                                                }

                                                @Override
                                                public void onStart() {

                                                }

                                                @Override
                                                public void onFailure(DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onFailure(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFailure(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(DatabaseError databaseError) {

            }
        });
    }

    public void setUserListView() {
        String usersPath = "/u";
        new Database().getSize(usersPath, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                int userSize = (int)dataSnapshot.getChildrenCount();
                ArrayList<String> userArrayList = new ArrayList<>();
                for(int i = 1; i <= userSize; i++) {
                    String userPath = usersPath + "/u";
                    new Database().readData(userPath + i + "/e", new OnGetDataListener() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            String n = dataSnapshot.getValue(String.class);
                            userArrayList.add(n);
                            ArrayAdapter userAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, userArrayList);
                            userList.setAdapter(userAdapter);
                            System.out.println("The size of users: " + userArrayList.size());
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFailure(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(DatabaseError databaseError) {

            }
        });
    }

    public void setPropertyListView(int userId) {
        String propertiesPath = "/u/u" + userId + "/p";
        new Database().getSize(propertiesPath, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                int propertySize = (int)dataSnapshot.getChildrenCount();
                ArrayList<String> propertyArrayList = new ArrayList<>();
                for(int i = 1; i <= propertySize; i++) {
                    String propertyPath = propertiesPath + "/p";
                    new Database().readData(propertyPath + i + "/n", new OnGetDataListener() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            String n = dataSnapshot.getValue(String.class);
                            propertyArrayList.add(n);
                            ArrayAdapter propertyAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, propertyArrayList);
                            propertyList.setAdapter(propertyAdapter);
                            System.out.println("The size of property: " + propertyArrayList.size());
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFailure(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(DatabaseError databaseError) {

            }
        });
    }

    public void setRoomListView(int userId, int propertyId) {
        String roomsPath = "/u/u" + userId + "/p/p" + propertyId + "/r";
        new Database().getSize(roomsPath, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                int roomSize = (int)dataSnapshot.getChildrenCount();
                ArrayList<String> roomArrayList = new ArrayList<>();
                for(int i = 1; i <= roomSize; i++) {
                    String roomPath = roomsPath + "/r";
                    new Database().readData(roomPath + i + "/n", new OnGetDataListener() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            String n = dataSnapshot.getValue(String.class);
                            roomArrayList.add(n);
                            ArrayAdapter roomAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, roomArrayList);
                            roomList.setAdapter(roomAdapter);
                            System.out.println("The size of room: " + roomArrayList.size());
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFailure(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(DatabaseError databaseError) {

            }
        });
    }

    public void updateTest(ArrayList<MutableLiveData<User>> users) {
        //observeDevice(users, 0, 0, 0, "Water Sensor", 0);
    }

    public void observeDevice(ArrayList<MutableLiveData<User>> users, int userId, int propertyId, int roomId, String device, int deviceId) {

      /*  userObserver = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                user.getProperty(0).observe(getActivity(), propertyObserver);
            }
        };
        propertyObserver = new Observer<Property>() {
            @Override
            public void onChanged(Property property) {
                property.getRoom(0).observe(getActivity(), roomObserver);
            }
        };
        roomObserver = new Observer<Room>() {
            @Override
            public void onChanged(Room room) {
                room.getWaterSensor(0).observe(getActivity(), waterSensorNameObserver);
            }
        };
        waterSensorNameObserver = new Observer<WaterSensor>() {
            @Override
            public void onChanged(WaterSensor waterSensor) {
                waterSensor.getName().observe(getActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        water_sensor_name_1.setText(s);
                        System.out.println("Water sensor name: " + s);
                    }
                });
            }
        };

        users.get(0).observe(getActivity(),userObserver);

        userObserver = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                user.getProperty(0).observe(getActivity(), propertyObserver);
            }
        };
        propertyObserver = new Observer<Property>() {
            @Override
            public void onChanged(Property property) {
                property.getRoom(0).observe(getActivity(), roomObserver);
            }
        };
        roomObserver = new Observer<Room>() {
            @Override
            public void onChanged(Room room) {
                room.getWaterSensor(0).observe(getActivity(), waterSensorStateObserver);
            }
        };
        waterSensorStateObserver = new Observer<WaterSensor>() {
            @Override
            public void onChanged(WaterSensor waterSensor) {
                waterSensor.getState().observe(getActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        water_sensor_state_1.setText(s);
                        System.out.println("Water sensor state: " + s);
                        if (s.contains("Dry")) {
                            sendNotification(1, String.valueOf(water_sensor_name_1.getText()), s);
                            water_sensor_layout_1.setBackgroundColor(getResources().getColor(R.color.green));
                            water_sensor_layout_1.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                        } else if (s.contains("Wet")) {
                            sendNotification(1, String.valueOf(water_sensor_name_1.getText()), s);
                            water_sensor_layout_1.setBackgroundColor(getResources().getColor(R.color.red));
                        }
                    }
                });
            }
        };
        users.get(0).observe(getActivity(),userObserver);*/
    }

    public void sendNotification(int id, String title, String text){
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_" + id;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity(), NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.green_logo_round)
                .setContentTitle(title)
                .setContentText(text)
                .setContentInfo("Info");

        notificationManager.notify(/*notification id*/id, notificationBuilder.build());
    }

    public void updateUI(View root){
        water_sensor_title.setText("My water sensors");
        air_quality_sensor_title.setText("My air quality sensors");
        homeViewModel.getSelectedProperty().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.contains("Property1")){
                    for(int i = 1; i <= numdevices; i++) {
                        observeDevice(1, i, root);
                    }
                }else if(s.contains("Property2")) {
                    observeDevice(2,1, root);
                }
            }
        });
    }

    public void observeDevice(int propertyid, int deviceid, View root) {
        switch(propertyid) {
            case 1:
                switch (deviceid) {
                    case 1:
                        water_sensor_layout_1 = root.findViewById(R.id.water_sensor_1);
                        water_sensor_name_1 = water_sensor_layout_1.findViewById(R.id.water_sensor_name);
                        water_sensor_state_1 = water_sensor_layout_1.findViewById(R.id.water_sensor_state);
                        homeViewModel.getmDevice(propertyid, deviceid).get(0).observe(this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                water_sensor_name_1.setText(s);
                            }
                        });
                        homeViewModel.getmDevice(propertyid, deviceid).get(1).observe(this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                water_sensor_state_1.setText(s);
                                if (s.contains("Dry")) {
                                    sendNotification(1, String.valueOf(water_sensor_name_1.getText()), s);
                                    water_sensor_layout_1.setBackgroundColor(getResources().getColor(R.color.green));
                                    water_sensor_layout_1.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                                } else if (s.contains("Wet")) {
                                    sendNotification(1, String.valueOf(water_sensor_name_1.getText()), s);
                                    water_sensor_layout_1.setBackgroundColor(getResources().getColor(R.color.red));
                                }
                            }
                        });
                        break;
                    case 2:
                        water_sensor_layout_2 = root.findViewById(R.id.water_sensor_2);
                        water_sensor_name_2 = water_sensor_layout_2.findViewById(R.id.water_sensor_name);
                        water_sensor_state_2 = water_sensor_layout_2.findViewById(R.id.water_sensor_state);
                        homeViewModel.getmDevice(propertyid, deviceid).get(0).observe(this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                water_sensor_name_2.setText(s);
                            }
                        });
                        homeViewModel.getmDevice(propertyid, deviceid).get(1).observe(this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                water_sensor_state_2.setText(s);
                                if (s.contains("Dry")) {
                                    sendNotification(2, String.valueOf(water_sensor_name_2.getText()), s);
                                    water_sensor_layout_2.setBackgroundColor(getResources().getColor(R.color.green));
                                } else if (s.contains("Wet")) {
                                    sendNotification(2, String.valueOf(water_sensor_name_2.getText()), s);
                                    water_sensor_layout_2.setBackgroundColor(getResources().getColor(R.color.red));
                                }
                            }
                        });
                        break;
                    case 3:
                        air_quality_layout = root.findViewById(R.id.air_quality_1);
                        air_quality_name = air_quality_layout.findViewById(R.id.air_quality_sensor_name);
                        air_quality_gas = air_quality_layout.findViewById(R.id.air_quality_sensor_gas);
                        air_quality_humidity = air_quality_layout.findViewById(R.id.air_quality_sensor_humidity);
                        air_quality_temperature = air_quality_layout.findViewById(R.id.air_quality_sensor_temperature);
                        homeViewModel.getmDevice(propertyid, deviceid).get(0).observe(this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                air_quality_name.setText(s);
                            }
                        });
                        homeViewModel.getmDevice(propertyid, deviceid).get(1).observe(this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                air_quality_gas.setText(s);
                            }
                        });
                        homeViewModel.getmDevice(propertyid, deviceid).get(2).observe(this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                air_quality_humidity.setText(s);
                            }
                        });
                        homeViewModel.getmDevice(propertyid, deviceid).get(3).observe(this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                air_quality_temperature.setText(s);
                            }
                        });
                        break;
                }
                break;
            case 2:
                water_sensor_layout_1 = root.findViewById(R.id.water_sensor_1);
                water_sensor_name_1 = water_sensor_layout_1.findViewById(R.id.water_sensor_name);
                water_sensor_state_1 = water_sensor_layout_1.findViewById(R.id.water_sensor_state);
                homeViewModel.getmDevice(propertyid, deviceid).get(0).observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        water_sensor_name_1.setText(s);
                    }
                });
                homeViewModel.getmDevice(propertyid, deviceid).get(1).observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        water_sensor_state_1.setText(s);
                        if (s.contains("Dry")) {
                            sendNotification(1, String.valueOf(water_sensor_name_1.getText()), s);
                            water_sensor_layout_1.setBackgroundColor(getResources().getColor(R.color.green));

                        } else if (s.contains("Wet")) {
                            sendNotification(1, String.valueOf(water_sensor_name_1.getText()), s);
                            water_sensor_layout_1.setBackgroundColor(getResources().getColor(R.color.red));
                        }
                    }
                });
        }
    }
}