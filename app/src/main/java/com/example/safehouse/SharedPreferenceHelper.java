package com.example.safehouse;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {

    private SharedPreferences sharedPreferences;

    public SharedPreferenceHelper(Context context) {
     sharedPreferences = context.getSharedPreferences("ProfilePreference", Context.MODE_PRIVATE);
    }

    public void saveProfile(Profile profile) {
        saveProfileName(profile);
        saveProfilePassword(profile);
    }

    public void saveProfileName(Profile profile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("profileName", profile.getName());
        editor.commit();
    }
    public void saveProfilePassword(Profile profile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("profilePassword", profile.getPassword());
        editor.commit();
    }


    public String getProfileName() {
        return sharedPreferences.getString("profileName", null);
    }
    public String getProfilePassword() {
        return sharedPreferences.getString("profilePassword", null);
    }

    public void clear() {
        sharedPreferences.edit().clear().commit();
    }

}
