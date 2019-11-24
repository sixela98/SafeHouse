package com.example.safehouse;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.safehouse.ui.main.PageViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class RoomActivity extends Fragment {

    private ListView roomsListView;
    private PageViewModel pageViewModel;
    private int propertyId = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState) {
        pageViewModel = ViewModelProviders.of(getActivity()).get(PageViewModel.class);
        return inflater.inflate(R.layout.room_activity, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomsListView = (ListView)view.findViewById(R.id.rooms_list_view);
        pageViewModel.getmPropertyId().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                propertyId = integer;
                System.out.println("Property#: " + propertyId);
                String roomsPath = "/u/u" + 1 + "/p/p" + propertyId + "/r";
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
                                    roomsListView.setAdapter(roomAdapter);
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
        });
        roomsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int roomId = position+1;
                pageViewModel.setmRoomId(roomId);
                System.out.println("Room: " + roomId);
            }
        });
    }
}
