package com.example.cneditor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ViewNewstoEdit_Fragement extends Fragment
{
    FirebaseDatabase firebaseDatabase;
    Intent i;
    DatabaseReference databaseReference;
    Button edit;
    EditNews editNews;
    MessageDetails messageDetails;


    public ViewNewstoEdit_Fragement() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        editNews = new EditNews();

        View v = inflater.inflate(R.layout.view_news_to_edit,container,false);
        edit = v.findViewById(R.id.edit);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    i = new Intent(getActivity() , MainActivity.class);
                    startActivity(i);
            }
        });

        return v;

    }
}
