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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.safehouse.MyNotificationManager;
import com.example.safehouse.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
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
    private int numdevices = 3;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        water_sensor_title = root.findViewById(R.id.water_sensor_title);
        air_quality_sensor_title = root.findViewById(R.id.air_quality_sensor_title);
        updateUI(root);
        return root;
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
                                    MyNotificationManager.sendNotification(getContext(), 1, String.valueOf(water_sensor_name_1.getText()), s);
                                    water_sensor_layout_1.setBackgroundColor(getResources().getColor(R.color.green));
                                    water_sensor_layout_1.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                                } else if (s.contains("Wet")) {
                                    MyNotificationManager.sendNotification(getActivity(),1, String.valueOf(water_sensor_name_1.getText()), s);
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
                                    MyNotificationManager.sendNotification(getActivity(), 2, String.valueOf(water_sensor_name_2.getText()), s);
                                    water_sensor_layout_2.setBackgroundColor(getResources().getColor(R.color.green));
                                } else if (s.contains("Wet")) {
                                    MyNotificationManager.sendNotification(getActivity(), 2, String.valueOf(water_sensor_name_2.getText()), s);
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
                                if(s.contains("Excellent Quality")){
                                    air_quality_gas.setTextColor(getResources().getColor(R.color.green));
                                }
                                if(s.contains("Great Quality")){
                                    air_quality_gas.setTextColor(getResources().getColor(R.color.green));
                                }
                                if(s.contains("Good Quality")){
                                    air_quality_gas.setTextColor(getResources().getColor(R.color.yellow));
                                }
                                if(s.contains("Bad Quality")){
                                    air_quality_gas.setTextColor(getResources().getColor(R.color.orange));
                                }
                                if(s.contains("HARMFUL GAS DETECTED")){
                                    air_quality_gas.setTextColor(getResources().getColor(R.color.red));                                }
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
                            MyNotificationManager.sendNotification(getActivity(),1, String.valueOf(water_sensor_name_1.getText()), s);
                            water_sensor_layout_1.setBackgroundColor(getResources().getColor(R.color.green));

                        } else if (s.contains("Wet")) {
                            MyNotificationManager.sendNotification(getActivity(), 1, String.valueOf(water_sensor_name_1.getText()), s);
                            water_sensor_layout_1.setBackgroundColor(getResources().getColor(R.color.red));
                        }
                    }
                });
        }
    }
}