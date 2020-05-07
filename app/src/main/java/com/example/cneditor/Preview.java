package com.example.cneditor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Preview extends AppCompatActivity {

    TextView heading , description , date , venue , department , college;
    ImageView preview_image;
    MessageDetails preview_details;
    Intent from_main;
    String image_uri_string;
    DatabaseReference mdatabase;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    Uri image_uri;
    SharedPreferencesConfig sharedPreferencesConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);


        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());

        ActionBar actionBar = getSupportActionBar();
        try {
            actionBar.setTitle("Preview");
        }catch (NullPointerException e){}

        mdatabase = FirebaseDatabase.getInstance().getReference();

        from_main =getIntent();
        preview_details = (MessageDetails) from_main.getSerializableExtra("PreviewDetails");
        Bundle b = getIntent().getExtras();
        image_uri_string = b.getString("image_uri");


        heading = findViewById(R.id.preview_heading);
        description = findViewById(R.id.preview_description);
        date = findViewById(R.id.preview_date);
        venue = findViewById(R.id.preview_venue);
        college = findViewById(R.id.preview_college);
        department = findViewById(R.id.preview_department);
        preview_image = findViewById(R.id.preview_image);

        heading.setText(preview_details.getTitle());
        department.setText(preview_details.getDepartment());
        description.setText(preview_details.getDescription());
        date.setText(preview_details.getDate());
        college.setText(preview_details.getCollege());
        venue.setText(preview_details.getVenue());

        image_uri = Uri.parse(image_uri_string);
        preview_image.setImageURI(image_uri);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        preview_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_image_url();
            }
        });


    }

    void uploadData()
    {
        preview_details.setCollege(sharedPreferencesConfig.getpreferences().getCollege());
        preview_details.setDepartment(sharedPreferencesConfig.getpreferences().getDepartment());
        preview_details.setUser_key(sharedPreferencesConfig.getpreferences().getUser_key());
        mdatabase.child("News").push().setValue(preview_details);
    }

    void uploadimage()
    {
        if (image_uri!=null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();

            mdatabase.child("News").push().setValue(preview_details);

            StorageReference upload_image = storageReference.child("images/"+preview_details.getMessage_id());
            upload_image.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            uploadData();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext() , "Uploadedsafsfs",Toast.LENGTH_SHORT).show();

                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext() , "FAILEDDDDD",Toast.LENGTH_SHORT).show();
                }
            }).removeOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                    double progress;
                    progress = (100.0*taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    Toast.makeText(getApplicationContext() , "Uploaded " + (int)progress + "%" ,Toast.LENGTH_SHORT).show();
                    progressDialog.setTitle("Uploaded " + (int)progress + "%");

                }
            });
        }
    }

    void get_image_url()
    {
       storageReference.child("images/"+preview_details.getMessage_id()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
           @Override
           public void onSuccess(Uri uri) {

               Toast.makeText(getApplicationContext() , uri.toString() , Toast.LENGTH_SHORT).show();
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(getApplicationContext() , "IMAGE URL FAILEDD DUE TO EXCEPTION : " + e.toString() , Toast.LENGTH_SHORT).show();
           }
       });

    }

    void dialogbox()
    {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        materialAlertDialogBuilder.setTitle("Are You Sure u want to upload :");
        materialAlertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext(),  "YESSSSSS",Toast.LENGTH_SHORT).show();
                    uploadimage();
            }
        });
        materialAlertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext() , "NOOOOOOOOOOOOO" , Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog materialDialogs = materialAlertDialogBuilder.create();
        materialDialogs.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.preview_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.upload_news : dialogbox();
                                    break;
        }

        return super.onOptionsItemSelected(item);
    }
}
