package com.example.safehouse.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.safehouse.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RelativeLayout water_sensor_layout_1;
    private TextView water_sensor_name_1;
    private TextView water_sensor_state_1;
    private RelativeLayout water_sensor_layout_2;
    private TextView water_sensor_name_2;
    private TextView water_sensor_state_2;
    private TextView water_sensor_title;
    private int numdevices = 2;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
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
        return root;
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
                            water_sensor_layout_1.setBackgroundColor(getResources().getColor(R.color.green));                   }else if(s.contains("Wet")) {
                        } else if(s.contains("Wet")){
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
                            water_sensor_layout_2.setBackgroundColor(Color.GREEN);
                        }else if(s.contains("Wet")){
                            water_sensor_layout_2.setBackgroundColor(Color.RED);
                        }
                    }
                });
                break;
        }

    }
}