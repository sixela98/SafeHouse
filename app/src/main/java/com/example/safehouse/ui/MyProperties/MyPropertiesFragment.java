package com.example.safehouse.ui.MyProperties;

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

public class MyPropertiesFragment extends Fragment {

    private MyPropertiesViewModel myPropertiesViewModel;
    private ArrayList<String> listProperty = new ArrayList<>();
    private ArrayAdapter propertyAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myPropertiesViewModel =
                ViewModelProviders.of(this).get(MyPropertiesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myproperties, container, false);
        final TextView textView = root.findViewById(R.id.text_myproperties);
        myPropertiesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        final ListView propertyListView = root.findViewById(R.id.listView_property);
        int numproperty = 2;
        for(int i = 1; i <= numproperty; i++) {
            observeProperty(i, propertyListView);
        }
        return root;
    }
    public void observeProperty(int id, ListView propertyListView) {
        myPropertiesViewModel.getmProperties().get(id-1).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(listProperty.size() < id) {
                    listProperty.add(s);
                }else {
                    listProperty.set(id-1, s);
                }
                propertyAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listProperty);
                propertyListView.setAdapter(propertyAdapter);
            }
        });

    }
}