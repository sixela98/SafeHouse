package com.example.safehouse.ui.MyProperties;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.safehouse.R;

import java.util.ArrayList;

public class MyPropertiesFragment extends Fragment {

    private MyPropertiesViewModel myPropertiesViewModel;
    private TextView selectedProperty;
    private TextView defaultProperty;
    private RelativeLayout property_layout_1;
    private TextView property_name_1;
    private TextView property_selected_1;
    private TextView property_default_1;
    private RelativeLayout property_layout_2;
    private TextView property_name_2;
    private TextView property_selected_2;
    private TextView property_default_2;
    private int numproperty = 2;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myPropertiesViewModel =
                ViewModelProviders.of(this).get(MyPropertiesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myproperties, container, false);
        selectedProperty = root.findViewById(R.id.selectedTextView);
        defaultProperty = root.findViewById(R.id.defaultTextView);;
        for(int i = 1; i <= numproperty; i++) {
            observeProperty(i, root);
        }
        return root;
    }

    public void observeProperty(int id, View root) {
        switch (id) {
            case 1:
                property_layout_1 = root.findViewById(R.id.property_1);
                property_name_1 = property_layout_1.findViewById(R.id.property_name);
                property_selected_1 = property_layout_1.findViewById(R.id.property_selected);
                property_default_1 = property_layout_1.findViewById(R.id.property_default);
                myPropertiesViewModel.getmProperty(id).get(0).observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        property_name_1.setText(s);
                    }
                });
                myPropertiesViewModel.getmProperty(id).get(1).observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        property_selected_1.setText(s);
                        if (s.contains("True")) {
                            selectedProperty.setText("Selected: 1");
                        }
                    }
                });
                myPropertiesViewModel.getmProperty(id).get(2).observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        property_default_1.setText(s);
                        if (s.contains("True")) {
                            defaultProperty.setText("Default: 1");
                        }
                    }
                });
                break;
            case 2:
                property_layout_2 = root.findViewById(R.id.property_2);
                property_name_2 = property_layout_2.findViewById(R.id.property_name);
                property_selected_2 = property_layout_2.findViewById(R.id.property_selected);
                property_default_2 = property_layout_2.findViewById(R.id.property_default);
                myPropertiesViewModel.getmProperty(id).get(0).observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        property_name_2.setText(s);
                    }
                });
                myPropertiesViewModel.getmProperty(id).get(1).observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        property_selected_2.setText(s);
                        if (s.contains("True")) {
                            selectedProperty.setText("Selected: 1");
                        }
                    }
                });
                myPropertiesViewModel.getmProperty(id).get(2).observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        property_default_2.setText(s);
                        if (s.contains("True")) {
                            defaultProperty.setText("Default: 1");
                        }
                    }
                });
                break;
        }
    }
}