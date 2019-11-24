package com.example.safehouse;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.safehouse.ui.main.PageViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class PropertyActivity extends Fragment {

    private ListView propertiesListView;
    private PageViewModel pageViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState) {
        pageViewModel = ViewModelProviders.of(getActivity()).get(PageViewModel.class);
        return inflater.inflate(R.layout.property_activity, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        propertiesListView = (ListView)view.findViewById(R.id.property_list_view);
        String propertiesPath = "/u/u" + 1 + "/p";
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
                            propertiesListView.setAdapter(propertyAdapter);
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

        propertiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int propertyId = position+1;
                pageViewModel.setmPropertyId(propertyId);
                System.out.println("Property: " + propertyId);
            }
        });
    }
}
