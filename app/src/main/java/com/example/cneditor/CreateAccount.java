package com.example.cneditor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText eml , pss , Cpss;
    ProgressDialog progress;
    Button save;
    String email , password , Cpassword , preferences;
    public static final int SPINNER_ID = 726336;
    String college , department;
    DatabaseReference mdatabase;
    ArrayAdapter<CharSequence> ClgAdapter , SDadapter , CDadapter , Madapter;
    Spinner clg,dept;
    AccDetails accDetails;
    SharedPreferencesConfig sharedPreferencesConfig;
    FirebaseAuth auth;
    LinearLayout containerlayout,rootlayout;
    Long key;
    Intent BackToLogin;
    CreateKey createKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        try{
            getSupportActionBar().hide();
        }catch (NullPointerException e){}

        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);

        containerlayout = findViewById(R.id.container);
        rootlayout = findViewById(R.id.root);
        accDetails = new AccDetails();

        createKey = new CreateKey();
        eml = findViewById(R.id.email);
        pss = findViewById(R.id.pass);
        Cpss = findViewById(R.id.cpass);
        save = findViewById(R.id.create);
        clg = findViewById(R.id.college);
        auth = FirebaseAuth.getInstance();
        clg.setOnItemSelectedListener(this);
        mdatabase = FirebaseDatabase.getInstance().getReference();

        dept = new Spinner(this);
        dept.setOnItemSelectedListener(this);
        dept.setId(SPINNER_ID);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,59);
        dept.setLayoutParams(layoutParams);

        pss.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String s = editable.toString();
                if (s.length()<6)
                {
                    pss.setError("Password length must be minimum 6.");
                }
                else
                {
                    switch (passcheck(editable.toString()))
                    {

                        case 1 : pss.setError("must contain atleast 1 Integer and 1 special character ($,#,@,&,*)");
                            break;
                        case 2 : pss.setError("must contain atleast 1 alphabet and 1 special character ($,#,@,&,*)");
                            break;
                        case 3 : pss.setError("must contain atleast 1 alphabet and 1 Integer");
                            break;
                        case 4 : pss.setError("must contain atleast 1 special character ($,#,@,&,*)");
                            break;
                        case 5 : pss.setError("must contain atleast 1 Integer");
                            break;
                        case 6 : pss.setError("must contain atleast 1 alphabet");

                    }
                }


            }
        });


        ClgAdapter = ArrayAdapter.createFromResource(this,R.array.college,android.R.layout.simple_spinner_item);
        ClgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clg.setAdapter(ClgAdapter);

        SDadapter = ArrayAdapter.createFromResource(this,R.array.Sdepartments,android.R.layout.simple_spinner_item);
        SDadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        Madapter = ArrayAdapter.createFromResource(this,R.array.Mdepartments,android.R.layout.simple_spinner_item);
        Madapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        CDadapter = ArrayAdapter.createFromResource(this,R.array.Cdepartments,android.R.layout.simple_spinner_item);
        CDadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = eml.getText().toString();
                password = pss.getText().toString();
                Cpassword = Cpss.getText().toString();


                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(Cpassword))
                {
                    toast("All Fields Are Mandatory");
                }
                else if (!Cpassword.equals(password))
                {
                    Cpss.setError("Password Doesnt Match!");
                }
                else if (!mailcheck(email))
                {
                    eml.setError("Invalid Format");
                }
                else
                {
                    key = Long.parseLong(createKey.GetKey(getm(email)+password));
                    accDetails.setEmail(email);
                    accDetails.setPassword(password);
                    accDetails.setCollege(college);


                    accDetails.setDepartment(department);

                    accDetails.setKey(key);
                    sharedPreferencesConfig.writepreferences(key , college , department);
                    progress.show();
                    createacc(email , password);

                }

            }
        });

    }

    //Method to check if the password format is correct or not.
    int passcheck(String p)
    {
        int err = 0;
        if (p.matches("[a-zA-Z]+"))
        {err = 1;}
        else if (p.matches("[0-9]+"))
        {err = 2;}
        else if (p.matches("[$#@&*]+"))
        {err=3;}
        else if (p.matches("[a-zA-Z0-9]+"))
        {err=4;}
        else if (p.matches("[a-zA-Z$#@&*]+"))
        {err=5;}
        else if (p.matches("[0-9$#@&*]+"))
        {err=6;}
        else {err=0;}
        return err;
    }

    //Method to check if the email address format is correct or not.
    boolean mailcheck(String s)
    {
        return s.matches("[a-zA-Z0-9-*._]+@\\w+(.com)");
    }

    //Function to create a dialog box.
    void createDialog()
    {
        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
        alertDialogBuilder.setMessage("\nSave the Above key for future references : ");
        alertDialogBuilder.setTitle("\t\t\t"+key);
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        BackToLogin = new Intent(CreateAccount.this,ChooseOption.class);
                        startActivity(BackToLogin);
                        finish();

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //Method to create Account in FireBase Database using email and password Credentials.
    void createacc(String s , String p)
    {
        auth.createUserWithEmailAndPassword(s , p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    progress.dismiss();
                    Snackbar snackbar = Snackbar.make(rootlayout,"Account Created",Snackbar.LENGTH_LONG);
                    snackbar.show();
                    mdatabase.child("users").push().setValue(accDetails);
                    mdatabase.child("keys").push().setValue(key);
                    createDialog();
                }
                else
                {
                    progress.dismiss();
                    toast("FAILED");
                    Log.w("REAAASSONNN : ", "createUserWithEmail:failure", task.getException());
                }
            }
        });
    }

    //Function to get the specific email address to create key.
    String getm(String e)
    {

        String l = new String();
        int i=0;
        while(i< e.length())
        {
            if(e.charAt(i) == '@')
            {
                break;
            }
            else
            {
                l = l + e.charAt(i);
                i++;
            }
        }
        return l;
    }

    void toast(String s)
    {
        Snackbar.make(rootlayout,s, BaseTransientBottomBar.LENGTH_LONG).show();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getId())
        {
            case R.id.college :
            {
                college = adapterView.getItemAtPosition(i).toString();
                toast(college);
                switch (i)
                {
                    case 1 :
                    {
                        if (containerlayout==null) {
                            dept.setAdapter(SDadapter);
                            containerlayout.addView(dept);
                        }
                        else
                        {
                            containerlayout.removeView(dept);
                            dept.setAdapter(SDadapter);
                            containerlayout.addView(dept);
                        }
                    }
                    break;

                    case 2 :
                    {
                        if (containerlayout==null)
                        {
                            dept.setAdapter(Madapter);
                            containerlayout.addView(dept);
                        }
                        else
                        {
                            containerlayout.removeView(dept);
                            dept.setAdapter(Madapter);
                            containerlayout.addView(dept);
                        }
                        break;
                    }
                    case 3 :
                    {
                        if (containerlayout==null)
                        {
                            dept.setAdapter(CDadapter);
                            containerlayout.addView(dept);
                        }
                        else
                        {
                            containerlayout.removeView(dept);
                            dept.setAdapter(CDadapter);
                            containerlayout.addView(dept);
                        }
                        break;

                    }
                    case 4 :
                    {
                        if (containerlayout!=null)
                        {
                            containerlayout.removeView(dept);
                        }
                        break;
                    }
                }
            }

            case SPINNER_ID : department = adapterView.getItemAtPosition(i).toString();
                toast(department);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

