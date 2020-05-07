package com.example.cneditor;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class Add_Details extends Fragment {

    EditText Heading , Description , Date , Time_from , Time_till, Venue;
    String heading , description , date , time_from , time_till , venue;
    Calendar calendar;
    DatePickerDialog.OnDateSetListener listener;
    MessageDetails messageDetails;
    MainActivity mainActivity;
    Context mcontext;


    public Add_Details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add__details, container, false);


        try {
            ActionBar ac = mainActivity.getsupportactionbar();
            ac.setTitle("Add Message Details");
        }catch (NullPointerException e){}



        Time_from =view.findViewById(R.id.Time_From);
        Time_from.setFocusable(false);
        Time_till =view.findViewById(R.id.Time_Till);
        Time_till.setFocusable(false);
        Description =view.findViewById(R.id.Description);
        Heading =view.findViewById(R.id.name);
        Date  =view.findViewById(R.id.Date);
        Venue =view.findViewById(R.id.Venue);
        mcontext = getContext();

        messageDetails = new MessageDetails();

        calendar = Calendar.getInstance();


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

    public MessageDetails getmsgdetails()
    {
        heading = Heading.getText().toString();
        description = Description.getText().toString();
        date = Date.getText().toString();
        time_from = Time_from.getText().toString();
        time_till = Time_till.getText().toString();
        venue = Venue.getText().toString();

        messageDetails.setTitle(heading);
        messageDetails.setDate(date);
        messageDetails.setDescription(description);
        messageDetails.setTime_from(time_from);
        messageDetails.setTime_till(time_till);
        messageDetails.setVenue(venue);

        return messageDetails;
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
                Toast.makeText(mcontext,"Hourse = " + Integer.toString(i) + "Minutes = " + Integer.toString(i1),Toast.LENGTH_LONG).show();
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

    public boolean getviewstatus()
    {
        boolean flag = true;
        if (TextUtils.isEmpty(heading))
        {
            Heading.setError("Title Cannot be Empty");
            flag = false;
        }
        if (TextUtils.isEmpty(description))
        {
            Description.setError("Description cannot be empty");
            flag = false;
        }

        if (TextUtils.isEmpty(venue))
        {
            Venue.setError("The Event Must have a venue");
            flag = false;
        }


        return flag;
    }
}
