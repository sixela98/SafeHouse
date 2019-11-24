package com.example.safehouse;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.safehouse.ui.main.PageViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class WaterSensorActivity extends Fragment {

    private ListView watersensorsListView;
    private PageViewModel pageViewModel;
    private int propertyId = 1;
    private int roomId = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState) {
        pageViewModel = ViewModelProviders.of(getActivity()).get(PageViewModel.class);
        return inflater.inflate(R.layout.watersensor_activity, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        watersensorsListView = (ListView)view.findViewById(R.id.watersensors_list_view);
        pageViewModel.getmPropertyId().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                propertyId = integer;
                pageViewModel.getmRoomId().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        roomId = integer;
                        String watersensorsPath = "/u/u" + 1 + "/p/p" + propertyId + "/r/r" + roomId + "/w";
                        new Database().getSize(watersensorsPath, new OnGetDataListener() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                int watersensorSize = (int)dataSnapshot.getChildrenCount();
                                ArrayList<String> watersensorArrayList = new ArrayList<>();
                                for(int i = 1; i <= watersensorSize; i++) {
                                    String watersensorPath = watersensorsPath + "/w";
                                    new Database().readData(watersensorPath + i + "/n", new OnGetDataListener() {
                                        @Override
                                        public void onSuccess(DataSnapshot dataSnapshot) {
                                            String n = dataSnapshot.getValue(String.class);
                                            watersensorArrayList.add(n);
                                            ArrayAdapter watersensorAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, watersensorArrayList);
                                            watersensorsListView.setAdapter(watersensorAdapter);
                                            System.out.println("The size of water sensor: " + watersensorArrayList.size());
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
                });
            }
        });
    }
}
