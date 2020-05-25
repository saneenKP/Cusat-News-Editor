package com.example.cneditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChooseOption extends AppCompatActivity {

    Button Create_news , Edit_news , Delete_news;
    Intent open , i;
    FirebaseAuth auth;
    EditNews editNews;
    SharedPreferencesConfig sharedPreferencesConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_option);

        auth = FirebaseAuth.getInstance();
        Create_news = findViewById(R.id.Create_news);
        Edit_news = findViewById(R.id.Edit_news);
        Delete_news = findViewById(R.id.Delte_news);


        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());

        Create_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open = new Intent(ChooseOption.this,MainActivity.class);
                startActivity(open);
            }
        });

        Edit_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open = new Intent(ChooseOption.this , EditNews.class);
                startActivity(open);

            }
        });
        Delete_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.news_menu,menu);
        return true;
    }

    void Toast(String s)
    {
        Toast.makeText(getApplicationContext() , s , Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.logout :
                Toast(sharedPreferencesConfig.GetPreferences().get("college"));
                sharedPreferencesConfig.clear_preferences();
                auth.signOut();
                i = new Intent(ChooseOption.this,login.class);
                startActivity(i);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
