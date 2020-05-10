package com.example.cneditor;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Add_Details.OnDetailsReadListener {


    FragmentManager fragmentManager;
    Add_Details add_details_fragment;
    Add_image_fragment add_image_fragment;
    FragmentTransaction fragmentTransaction;
    Button preview;
    ImageButton fragment_switch;
    MessageDetails get_details_for_preview = new MessageDetails();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            getSupportActionBar().hide();
        }catch (NullPointerException e){}


        fragmentManager = getSupportFragmentManager();
        add_details_fragment = new Add_Details();
        preview =  findViewById(R.id.top_preview_button);
        fragment_switch = findViewById(R.id.Fragment_switch);


        if(findViewById(R.id.image_fragment_container)!=null)
        {
            if (savedInstanceState!=null)
            {
                return;
            }

            fragmentTransaction = fragmentManager.beginTransaction();
            add_details_fragment = new Add_Details();
            fragmentTransaction.add(R.id.image_fragment_container , add_details_fragment ,null);
            fragmentTransaction.commit();
        }







        fragment_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Fragment current_fragment = getSupportFragmentManager().findFragmentById(R.id.image_fragment_container);

                    if (current_fragment instanceof Add_Details)
                    {
                        Intent i = new Intent(MainActivity.this,ChooseOption.class);

                        startActivity(i);
                    }
                    else
                    {
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentManager.popBackStack();
                    }

            }
        });

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add_details_fragment.SetViewError();
                if (add_details_fragment.getviewstatus())
                {
                    add_details_fragment.Setup_Preview(1);
                    Intent preview_from_msg_details = new Intent(MainActivity.this,Preview.class);
                    preview_from_msg_details.putExtra("PreviewDetails",get_details_for_preview);
                    startActivity(preview_from_msg_details);
                }
                else
                {
                    add_details_fragment.SetViewError();
                }
            }
        });

        /*upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (add_details_fragment.getviewstatus())
                {
                    if (checkdetails())
                    {
                        Toast.makeText(getApplicationContext() , "The event Must have a Date and Time",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        /*if (count==0)
                        {
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
        });*/

    }

    @Override
    public void OnDetailsRead(MessageDetails messageDetails)
    {
        get_details_for_preview = messageDetails;
    }




    //Method to add add_image fragment.


    //Method to check if Date and Time is missing or not.




}
