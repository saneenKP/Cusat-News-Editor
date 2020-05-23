package com.example.cneditor;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;


/**
 * A simple {@link Fragment} subclass.
 */
public class Add_Details extends Fragment {

    EditText Heading , Description , Date , Time_from , Time_till, Venue;
    String heading , description , date , time_from , time_till , venue;
    Calendar calendar;
    DatePickerDialog.OnDateSetListener listener;
    MessageDetails messageDetails;
    Context mcontext;
    Button Attach_files , Preview;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Add_image_fragment add_image_fragment;
    String current_date;
    CreateKey message_key;
    SharedPreferencesConfig sharedPreferencesConfig;
    boolean heading_flag = true, desc_flag = true, venue_flag = true , timef_flag = true , date_flag = true;
    OnDetailsReadListener onDetailsReadListener;


    public interface OnDetailsReadListener
    {
            public void OnDetailsRead(MessageDetails messageDetails);
    }

    public Add_Details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add__details, container, false);


        current_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Attach_files = view.findViewById(R.id.attach_images);
        Time_from =view.findViewById(R.id.Time_From);
        Time_from.setFocusable(false);
        Time_till =view.findViewById(R.id.Time_Till);
        Time_till.setFocusable(false);
        Description =view.findViewById(R.id.Description);
        Heading =view.findViewById(R.id.name);
        Date  =view.findViewById(R.id.Date);
        Venue =view.findViewById(R.id.Venue);
        mcontext = getContext();

        sharedPreferencesConfig = new SharedPreferencesConfig(mcontext);
        messageDetails = new MessageDetails();
        message_key = new CreateKey();

        calendar = Calendar.getInstance();


        Attach_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getviewstatus())
                {
                    AddUserDetails();
                    Setup_Preview(0);
                    changeFragment();

                }
                else
                {
                    SetViewError();
                }

            }


        });

        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);
                SetEditext();
            }
        };

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(mcontext, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        Time_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetTime(Time_from);
            }
        });
        Time_till.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetTime(Time_till);
            }
        });


        return view;
    }

    void get_editextValues()
    {
        heading = Heading.getText().toString();
        description = Description.getText().toString();
        date = Date.getText().toString();
        time_from = Time_from.getText().toString();
        time_till = Time_till.getText().toString();
        venue = Venue.getText().toString();
    }
    public void AddUserDetails()
    {
        get_editextValues();
        HashMap<String,String> hashMap = sharedPreferencesConfig.getpreferences();

        Log.d("HEADDINGG" , heading);
        Log.d("DESc",description);
        Log.d("DATE",date);
        messageDetails.setCollege(hashMap.get("user_college"));
        messageDetails.setDepartment(hashMap.get("user_department"));
        messageDetails.setMessage_id(message_key.GetKey(heading+description+date));
        messageDetails.setTitle(heading);
        messageDetails.setDate(date);
        messageDetails.setDescription(description);
        messageDetails.setTime_from(time_from);
        messageDetails.setTime_till(time_till);
        messageDetails.setVenue(venue);
        messageDetails.setDate_published(current_date);

    }

    public void Setup_Preview(int flag)
    {
        switch (flag)
        {
            case 0 : onDetailsReadListener.OnDetailsRead(messageDetails);
                     break;
            case 1:  AddUserDetails();
                    onDetailsReadListener.OnDetailsRead(messageDetails);
                    break;
        }
    }

    private void SetEditext()
    {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date.setText(sdf.format(calendar.getTime()));
    }


    void SetTime(final EditText e)
    {
        Calendar Time = Calendar.getInstance();
        int hour = Time.get(Calendar.HOUR_OF_DAY);
        int minute = Time.get(Calendar.MINUTE);

        TimePickerDialog timePicker;
        timePicker = new TimePickerDialog(mcontext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {

                String am_pm;
                if(i<12)
                {
                    i=(i==0?12:i);
                    am_pm = "AM";

                }
                else
                {
                    am_pm = "PM";
                    i = i-12;
                }

                e.setText(i1/10 == 0?(i+" : 0"+i1+" " + am_pm):(i+" : "+i1+" " + am_pm));

            }
        },hour,minute,false);
        timePicker.setTitle("Select Time");
        timePicker.show();
    }

    public void changeFragment()
    {
        add_image_fragment = new Add_image_fragment();
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.replace(R.id.image_fragment_container , add_image_fragment , null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void SetViewError()
    {
        get_editextValues();
        if (TextUtils.isEmpty(heading))
        {
            Heading.setError("Title Cannot be Empty");
            heading_flag = false;
        }
        else
        {
            heading_flag = true;
        }
        if (TextUtils.isEmpty(description))
        {
            Description.setError("Description cannot be empty");
            desc_flag = false;
        }
        else
        {
            desc_flag = true;
        }
        if (TextUtils.isEmpty(venue))
        {
            Venue.setError("The Event Must have a venue");
            venue_flag = false;
        }
        else
        {
            venue_flag = true;
        }
        if (TextUtils.isEmpty(date))
        {
            Date.setError("Date is neccessary");
            date_flag = false;
        }
        else
        {
            date_flag = true;
        }
        if (TextUtils.isEmpty(time_from))
        {
            Time_from.setError("The Event must have at least 1 time stamp");
            timef_flag = false;
        }
        else
        {
            timef_flag = true;
        }
    }

    public boolean getviewstatus()
    {
        return heading_flag && desc_flag && venue_flag && timef_flag && date_flag;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;
        try {
            onDetailsReadListener = (OnDetailsReadListener)activity;
        }catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()+" Must overide OnDetails Read");
        }

    }
}
