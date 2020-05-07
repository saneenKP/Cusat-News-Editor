package com.example.cneditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class EditNews extends AppCompatActivity {

    FragmentManager fragmentManager , fragmentManager2;
    FragmentTransaction fragmentTransaction ,fragmentTransaction2;
    Check_userkey_fragment check_userkey_fragment;
    ViewNewstoEdit_Fragement viewNewstoEdit_fragement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_news);

        fragmentManager = getSupportFragmentManager();
        fragmentManager2 = getSupportFragmentManager();

        if (findViewById(R.id.edit_news_container)!=null)
        {
            if (savedInstanceState!=null)
            {
                return;
            }
            fragmentTransaction = fragmentManager.beginTransaction();
            check_userkey_fragment = new Check_userkey_fragment();
            fragmentTransaction.add(R.id.edit_news_container , check_userkey_fragment , null);
            fragmentTransaction.commit();

        }
    }


}
