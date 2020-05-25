package com.example.cneditor;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;

public class RecollectPreferences extends AppCompatActivity {

    DatabaseReference mdatabase;
    SharedPreferencesConfig sharedPreferencesConfig;
    ProgressBar progress_spinner;
    String key ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recollect_preferences);

        try {
            getSupportActionBar().hide();
        }catch (NullPointerException e){}

        progress_spinner = findViewById(R.id.spinner_progress);
        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());
        Bundle b = getIntent().getExtras();
        key = b.getString("key");

        mdatabase = FirebaseDatabase.getInstance().getReference().child("users");

        getdata(key);

        Intent i = new Intent(RecollectPreferences.this , ChooseOption.class);
        startActivity(i);
        finish();
    }

    void getdata(String key)
    {

        progress_spinner.setVisibility(View.VISIBLE);
        mdatabase.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AccDetails accDetails = dataSnapshot.getValue(AccDetails.class);

                SetSharedPreferences(accDetails.getCollege() , accDetails.getDepartment());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    void SetSharedPreferences(String college , String department)
    {
        sharedPreferencesConfig.clear_preferences();
        Toast(key);
        Toast(college);
        Toast(department);
        sharedPreferencesConfig.WritePreferences(key , college , department);
        progress_spinner.setVisibility(View.GONE);
    }


    void Toast(String s)
    {
        Toast.makeText(getApplicationContext() , s ,Toast.LENGTH_LONG).show();
    }


}
