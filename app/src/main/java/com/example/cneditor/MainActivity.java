package com.example.cneditor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton upload;
    long User_key;
    String user_college , user_department;
    Intent i , view_preview;
    FragmentManager fragmentManager;
    MessageDetails messageDetails;
    Add_Details add_details_fragment;
    Add_image_fragment add_image_fragment;
    FragmentTransaction fragmentTransaction;
    CreateKey message_key;
    ActionBar actionBar;
    String current_date;
    int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        upload = findViewById(R.id.upload);
        messageDetails = new MessageDetails();
        message_key = new CreateKey();

        current_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        if(findViewById(R.id.fragment_container)!=null)
        {
            if (savedInstanceState!=null)
            {
                return;
            }

            fragmentTransaction = fragmentManager.beginTransaction();
            add_details_fragment = new Add_Details();
            fragmentTransaction.add(R.id.fragment_container , add_details_fragment ,null);
            fragmentTransaction.commit();
        }

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageDetails = add_details_fragment.getmsgdetails();
                messageDetails.setDate_published(current_date);

                if (add_details_fragment.getviewstatus())
                {
                    if (checkdetails())
                    {
                        Toast.makeText(getApplicationContext() , "The event Must have a Date and Time",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        messageDetails.setDate_published(current_date);
                        messageDetails.setCollege("Conchin University");
                        messageDetails.setDepartment("Information Technology");
                        messageDetails.setMessage_id(message_key.GetKey(messageDetails.getTitle()+messageDetails.getDate()+messageDetails.getDescription()));
                        if (count==0)
                        {
                            Commitfragment();
                            count++;
                        }
                        else
                        {
                            if (add_image_fragment.checkimageuri())
                            {
                                Toast.makeText(getApplicationContext(), "Select an image", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Uri image_uri = add_image_fragment.getimageuri();
                                view_preview = new Intent(MainActivity.this , Preview.class);
                                view_preview.putExtra("PreviewDetails" , messageDetails);
                                view_preview.putExtra("image_uri" , image_uri.toString());
                                startActivity(view_preview);
                            }

                        }
                    }

                }


            }
        });

    }

    ActionBar getsupportactionbar()
    {
        actionBar = getSupportActionBar();
        return actionBar;
    }

    void get_user_data()
    {

    }


    //Method to add add_image fragment.
    void Commitfragment()
    {
        add_image_fragment = new Add_image_fragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.replace(R.id.fragment_container,add_image_fragment,null).commit();

    }

    //Method to check if Date and Time is missing or not.
    boolean checkdetails()
    {
        return TextUtils.isEmpty(messageDetails.getDate()) || TextUtils.isEmpty(messageDetails.getTime_from());
    }




}
