package com.example.cneditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class DeleteNews extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    Check_userkey_fragment check_userkey_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_news);

        check_userkey_fragment = new Check_userkey_fragment();

        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.delete_news_container) !=null)
        {
            if (savedInstanceState!=null)
            {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.delete_news_container ,check_userkey_fragment , null);
                fragmentTransaction.commit();
            }
        }
    }
}
