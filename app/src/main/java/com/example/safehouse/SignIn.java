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

public class SignIn extends AppCompatActivity {

protected Button signinButton;
protected Button createProfileButton;

protected TextInputEditText InputNameText;
protected TextInputEditText InputPasswordText;
protected SharedPreferenceHelper sharedPreferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_signin);
        setupUI();

        signinButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //if user name and password match the shared preferences profile then go back to main activity
                if(onMatching(InputNameText.getText().toString(),InputPasswordText.getText().toString()))
                {
                    launchMainActivity();
                }
                else{
                    Toast toast =
                            Toast.makeText(getApplicationContext(), "Username or Password do not match", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });
        createProfileButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                launchLogginActivity();
            }
            });

}
    @Override
    public void onBackPressed(){
        //disables the back button press
    }

//Setup the UI with the desired element
    protected void setupUI() {
        InputNameText = findViewById(R.id.InputNameText);
        InputPasswordText = findViewById(R.id.InputPasswordText);
        signinButton = findViewById(R.id.signinButton);
        createProfileButton = findViewById(R.id.createProfileButton);
        sharedPreferenceHelper = new SharedPreferenceHelper(SignIn.this);

        InputNameText.setText(sharedPreferenceHelper.getProfileName());
}

//Launch the main activity
    void launchMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
}
    void launchLogginActivity(){
        Intent intent = new Intent(this, LogginActivity.class);
        startActivity(intent);
    }
//Returns true if name and password match
    boolean onMatching(String name, String password){
        if (name.equals(sharedPreferenceHelper.getProfileName())
                &&
                password.equals(sharedPreferenceHelper.getProfilePassword())){
            return true;
        }
        else{
            return false;
        }

    }

}