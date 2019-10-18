package com.example.safehouse;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {

    private SharedPreferences sharedPreferences;
    public SharedPreferenceHelper(Context context) {
     sharedPreferences = context.getSharedPreferences("ProfilePreference", Context.MODE_PRIVATE);
    }

    public void saveProfile(Profile profile) {
        saveProfileAge(profile);
        saveProfileName(profile);
        saveProfileStudentID(profile);
    }

    public void saveProfileName(Profile profile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("profileName", profile.getName());
        editor.commit();
    }

    public void saveProfileAge(Profile profile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("profileAge", profile.getAge());
        editor.commit();
    }

    public void saveProfileStudentID(Profile profile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("profileStudentID", profile.getStudentID());
        editor.commit();
    }

    public String getProfileName() {
        return sharedPreferences.getString("profileName", null);
    }
    public int getProfileAge() {
        return sharedPreferences.getInt("profileAge", -1);
    }
    public int getProfileStudentID() {
        return sharedPreferences.getInt("profileStudentID", -1);
    }

    public void clear() {
        sharedPreferences.edit().clear().commit();
    }

}
