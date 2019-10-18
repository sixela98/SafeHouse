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

        protected boolean edit = true;                                      //Boolean that will serve to turn the editing mode on/off
        protected boolean correctInputs;                                    //Boolean which is set to false if an input is incorrect
        protected Button save_button = null;                                //Button to save the profile
        protected TextInputEditText editTextName;                           //Text that contains the name which can be editing
        protected TextInputEditText editTextAge;                            //Text that contains the age which can be editing
        protected TextInputEditText editTextStudentID;                      //Text that contains the student ID which can be editing
        protected TextInputLayout layoutName;                               //Layout of the name
        protected TextInputLayout layoutAge;                                //Layout of the age
        protected TextInputLayout layoutStudentID;                          //Layout of the student ID
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
                    if(editTextStudentID.getText().toString().isEmpty()) {
                        layoutStudentID.setError("Student ID cannot be empty");
                        correctInputs = false;
                    }else {
                        layoutStudentID.setError(null);
                    }
                    if(editTextAge.getText().toString().isEmpty()) {
                        layoutAge.setError("Age cannot be empty");
                        correctInputs = false;
                    }else if(!isAgeCorrect(Integer.parseInt(editTextAge.getText().toString()))){
                        layoutAge.setError("Age must be between 18 and 99");
                        correctInputs = false;
                    }else {
                        layoutAge.setError(null);
                    }
                    if(correctInputs) {
                        Profile profile = new Profile(editTextName.getText().toString(), Integer.parseInt(editTextAge.getText().toString()), Integer.parseInt(editTextStudentID.getText().toString()));
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
            editTextAge = findViewById(R.id.editTextAge);
            editTextStudentID = findViewById(R.id.editTextStudentID);
            save_button = findViewById(R.id.saveButton);
            layoutName = findViewById(R.id.layoutName);
            layoutAge = findViewById(R.id.layoutAge);
            layoutStudentID = findViewById(R.id.layoutStudentID);
            sharedPreferenceHelper = new SharedPreferenceHelper(LogginActivity.this);
        }

        //Return true if Age matches criteria
        protected boolean isAgeCorrect(int age) {
            return (age >= 18 && age <= 99);
        }

        //Launch the main activity
        void launchMainActivity(){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        //Set the profile information
        protected void setProfileInformation() {
            if(!(sharedPreferenceHelper.getProfileName() == null) && !(Integer.toString(sharedPreferenceHelper.getProfileAge()).isEmpty()) && !(Integer.toString(sharedPreferenceHelper.getProfileStudentID()).isEmpty())) {
                editTextName.setText(sharedPreferenceHelper.getProfileName());
                editTextAge.setText(Integer.toString(sharedPreferenceHelper.getProfileAge()));
                editTextStudentID.setText(Integer.toString(sharedPreferenceHelper.getProfileStudentID()));
                edit = false;
                setEdit(edit);
            }
        }

        //Set edit on/off
        protected void setEdit(boolean edit) {
            if(edit){
                editTextName.setFocusableInTouchMode(edit);
                editTextAge.setFocusableInTouchMode(edit);
                editTextStudentID.setFocusableInTouchMode(edit);
                save_button.setVisibility(View.VISIBLE);
            }else {
                editTextName.setFocusable(edit);
                editTextAge.setFocusable(edit);
                editTextStudentID.setFocusable(edit);
                save_button.setVisibility(View.GONE);
            }
        }
    }

