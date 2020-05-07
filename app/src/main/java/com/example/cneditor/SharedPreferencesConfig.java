package com.example.cneditor;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.PrivateKey;

public class SharedPreferencesConfig
{

    private SharedPreferences sharedPreferences;
    Context context;
    MessageDetails PreferenceDetails;
    public SharedPreferencesConfig(Context context)
    {
         this.context = context;
          sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.user_details_preferences2),Context.MODE_PRIVATE);
    }
    public void writepreferences(long key  ,String clg , String dept)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("user_key" , key);
        editor.putString("user_college" , clg);
        editor.putString("user_department" , dept);
        editor.apply();
    }

    MessageDetails getpreferences()
    {
        PreferenceDetails = new MessageDetails();
        PreferenceDetails.setUser_key(sharedPreferences.getLong("user_key",12345));
        PreferenceDetails.setCollege(sharedPreferences.getString("user_college" , "college"));
        PreferenceDetails.setDepartment(sharedPreferences.getString("user_department" ,"department"));
        return PreferenceDetails;
    }

}
