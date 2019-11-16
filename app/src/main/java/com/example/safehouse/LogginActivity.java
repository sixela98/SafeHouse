package com.example.safehouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LogginActivity extends AppCompatActivity {

    protected boolean correctInputs;                                    //Boolean which is set to false if an input is incorrect
    protected Button save_button = null;                                //Button to save the profile
    protected TextInputEditText editTextName;                           //Text that contains the name which can be editing
    protected TextInputLayout layoutName;                               //Layout of the name
    protected SharedPreferenceHelper sharedPreferenceHelper;            //SharedPreferenceHelper that will handle saving/editing the user profile

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);
        setupUI();
        setProfileInformation();

        save_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                correctInputs = true;
                if(editTextName.getText().toString().isEmpty()) {
                    layoutName.setError("Name cannot be empty");
                    correctInputs = false;
                }else {
                    layoutName.setError(null);
                }
                if(correctInputs) {
                    Profile profile = new Profile(editTextName.getText().toString());
                    sharedPreferenceHelper.saveProfile(profile);
                    Toast toast = Toast.makeText(getApplicationContext(), "saved" , Toast.LENGTH_LONG);
                    toast.show();
                    launchMainActivity();
                }
            }
        });
    }

    //Setup the UI with the desired element
    protected void setupUI() {
        editTextName = findViewById(R.id.editTextName);
        save_button = findViewById(R.id.saveButton);
        layoutName = findViewById(R.id.layoutName);
        sharedPreferenceHelper = new SharedPreferenceHelper(LogginActivity.this);
    }

    //Launch the main activity
    void launchMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Set the profile information
    protected void setProfileInformation() {
        if(!(sharedPreferenceHelper.getProfileName() == null)) {
            editTextName.setText(sharedPreferenceHelper.getProfileName());
        }
    }
}

