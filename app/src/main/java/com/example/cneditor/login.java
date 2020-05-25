package com.example.cneditor;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    Button login , create;
    TextInputEditText username , password;
    String usrnm , pass;
    Intent StartCreateAcc , StartLogin;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    FirebaseUser user;
    SharedPreferencesConfig sharedPreferencesConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());
        Toast.makeText(getApplicationContext() , sharedPreferencesConfig.GetPreferences().get("college") , Toast.LENGTH_LONG).show();

        StartLogin = new Intent(login.this , ChooseOption.class);
        StartCreateAcc = new Intent(login.this,CreateAccount.class);


        password = findViewById(R.id.password);
        username = findViewById(R.id.username);

        login = findViewById(R.id.login);
        create = findViewById(R.id.create);

        auth = FirebaseAuth.getInstance();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(StartCreateAcc);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usrnm = username.getText().toString();
                pass = password.getText().toString();

                if (TextUtils.isEmpty(usrnm) || TextUtils.isEmpty(usrnm)) {
                    Toast.makeText(getApplicationContext(), "All feilds are Required", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    progressDialog.show();
                    SignIn(usrnm , pass);
                }
            }
        });

    }

    public void SignIn(String u , String p)
    {
        auth.signInWithEmailAndPassword(u,p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    user = auth.getCurrentUser();
                    StartLogin = new Intent(getApplicationContext(), RecollectPreferences.class);
                    CreateKey cr = new CreateKey();
                    StartLogin.putExtra("key", cr.GetKey(getm(usrnm) + pass));
                    startActivity(StartLogin);
                }
                else
                    {
                        progressDialog.dismiss();
                        Log.w("LOGIN FAILEDDDD", "signInWithEmail:failure", task.getException());
                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }

        });
    }
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

    @Override
    protected void onStart() {
        super.onStart();

        user = auth.getCurrentUser();
        if (user!=null)
        {
            startActivity(StartLogin);
        }
    }
}

