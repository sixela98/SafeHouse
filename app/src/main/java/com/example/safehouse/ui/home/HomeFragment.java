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

import com.example.safehouse.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RelativeLayout water_sensor_layout_1;
    private TextView water_sensor_name_1;
    private TextView water_sensor_state_1;
    private RelativeLayout water_sensor_layout_2;
    private TextView water_sensor_name_2;
    private TextView water_sensor_state_2;
    private TextView water_sensor_title;
    private Button button;
    NotificationManager notificationManager;
    private int numdevices = 2;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        notificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        water_sensor_title = root.findViewById(R.id.water_sensor_title);
        water_sensor_title.setText("My water sensors");
        for(int i = 1; i <= numdevices; i++) {
            observeDevice(i, root);
        }
        button = (Button)root.findViewById(R.id.air_quality_1);
        return root;
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

    public void observeDevice(int id, View root) {
        switch (id){
            case 1:
                water_sensor_layout_1 = root.findViewById(R.id.water_sensor_1);
                water_sensor_name_1 = water_sensor_layout_1.findViewById(R.id.water_sensor_name);
                water_sensor_state_1 = water_sensor_layout_1.findViewById(R.id.water_sensor_state);
                homeViewModel.getmDevice(id).get(0).observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        water_sensor_name_1.setText(s);
                    }
                });
                homeViewModel.getmDevice(id).get(1).observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        water_sensor_state_1.setText(s);
                        if(s.contains("Dry")){
                            sendNotification(1, String.valueOf(water_sensor_name_1.getText()), s);
                            water_sensor_layout_1.setBackgroundColor(getResources().getColor(R.color.green));

                        } else if(s.contains("Wet")){
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
                homeViewModel.getmDevice(id).get(0).observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        water_sensor_name_2.setText(s);
                    }
                });
                homeViewModel.getmDevice(id).get(1).observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        water_sensor_state_2.setText(s);
                        if(s.contains("Dry")){
                            sendNotification(2, String.valueOf(water_sensor_name_2.getText()), s);
                            water_sensor_layout_2.setBackgroundColor(Color.GREEN);
                        }else if(s.contains("Wet")){
                            sendNotification(2, String.valueOf(water_sensor_name_2.getText()), s);
                            water_sensor_layout_2.setBackgroundColor(Color.RED);
                        }
                    }
                });
                break;
        }

    }
}