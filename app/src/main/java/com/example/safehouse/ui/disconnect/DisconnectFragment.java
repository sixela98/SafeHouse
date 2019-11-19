package com.example.safehouse.ui.disconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.safehouse.LogginActivity;
import com.example.safehouse.SignIn;

public class DisconnectFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(), SignIn.class);
        startActivity(intent);
    }
    //@Override

}
