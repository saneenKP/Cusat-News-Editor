package com.example.cneditor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;


public class SharedPreferencesConfig
{
    private SharedPreferences sharedPreferences;
    private Context context;
    private SharedPreferences.Editor editor;
    private HashMap<String , String> hashMap;

    public SharedPreferencesConfig( Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.user_details_preferences1),context.MODE_PRIVATE);

    }
    public void WritePreferences(String key , String college , String department)
    {
        Log.d("WRITE PREFEREN STRING" , "KEYYY = " + key + "COLLEGEEE = " + college + "DEPARTMENNT = "+department);
        editor = sharedPreferences.edit();
       editor.putString("preference_key" , key);
        editor.putString("preference_college" , college);
       editor.putString("preference_department" , department);
        editor.apply();
        Log.d("AFTER PREFEREN INSIDE" , "KEYYY = " + sharedPreferences.getString("preference_key" , "KEYYYYYYYY"));
        Log.d("AFTER PREFEREN INSIDE" , "COLLEGEEE = " + sharedPreferences.getString("preference_college" , "COLLEEEGEGE"));
        Log.d("AFTER PREFEREN INSIDE" , "DEPARTMENTT = " + sharedPreferences.getString("preference_department" , "DEAPRRMENTTTT"));

    }

    void clear_preferences()
    {
        if (editor!=null)
        {
            editor.clear();
            editor.apply();
        }

    }

    HashMap<String , String> GetPreferences()
    {
        hashMap = new HashMap<>();
        hashMap.put("key" , sharedPreferences.getString("preference_key" , "keyyy"));
        hashMap.put("college" , sharedPreferences.getString("preference_college" , "collegeee"));
        hashMap.put("department" , sharedPreferences.getString("preference_department" , "departmeetnn"));

        return hashMap;
    }
}
