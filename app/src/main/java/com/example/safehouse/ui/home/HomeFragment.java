package com.example.safehouse.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.safehouse.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ArrayList<String> listProperty = new ArrayList<>();
    private ArrayList<String> listHome = new ArrayList<>();
    private ArrayAdapter homeAdapter;

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
        final ListView propertyListView = root.findViewById(R.id.listView_property);
        final ListView homeListView = root.findViewById(R.id.listView_home);
        listProperty.add("Property");
        //listHome.add("Home");
        int numdevices = 2;
        for(int i = 1; i <= numdevices; i++) {
            observeDevice(i, homeListView);
        }
        ArrayAdapter propertyAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listProperty);
        propertyListView.setAdapter(propertyAdapter);

        return root;
    }

    public void observeDevice(int id, ListView homeListView) {
        homeViewModel.getmDevices().get(id-1).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(listHome.size() < id) {
                    listHome.add(s);
                }else {
                    listHome.set(id-1, s);
                }
                homeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listHome);
                homeListView.setAdapter(homeAdapter);
            }
        });
    }
}