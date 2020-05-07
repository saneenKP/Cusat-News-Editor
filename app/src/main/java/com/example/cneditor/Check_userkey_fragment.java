package com.example.cneditor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Check_userkey_fragment extends Fragment
{

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText get_key;
    Button check_key;
    SharedPreferencesConfig sharedPreferencesConfig;
    Context context;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    EditNews editNews;
    ViewNewstoEdit_Fragement viewNewstoEdit_fragement;
    ViewNewsToDelete_Fragment viewNewsToDelete_fragment;

    public Check_userkey_fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        viewNewstoEdit_fragement = new ViewNewstoEdit_Fragement();
        context = getContext();
        editNews = new EditNews();

        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();


        sharedPreferencesConfig = new SharedPreferencesConfig(context);
        View v = inflater.inflate(R.layout.get_user_key,container,false);
        check_key = v.findViewById(R.id.checkey);
        get_key = v.findViewById(R.id.getkeyeditext);
        get_key.setText("61482359");
        check_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Long.parseLong(get_key.getText().toString())== (sharedPreferencesConfig.getpreferences().getUser_key()))
                {
                        fragmentTransaction.replace(R.id.edit_news_container,viewNewstoEdit_fragement,null);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                }
            }
        });

        return v;

    }
}
