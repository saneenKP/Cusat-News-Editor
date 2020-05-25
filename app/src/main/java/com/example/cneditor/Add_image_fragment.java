package com.example.cneditor;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Add_image_fragment extends Fragment {

    ImageView image;
    Context mcontext;
    Uri imageuri;
    Button upload_image;
    RecyclerView recyclerView;
    private static int IMAGE_CODE = 1001;
    private static int REQUEST_CODE = 1002;


    public Add_image_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_image_fragment, container, false);

        upload_image = view.findViewById(R.id.upload_image);
        image = view.findViewById(R.id.image);
        recyclerView = view.findViewById(R.id.image_list);
        mcontext = getContext();

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if (mcontext.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions , REQUEST_CODE);
                    }
                    else
                    {
                        PickImage();
                    }
                }
                else
                {
                    PickImage();
                }

            }
        });


        return  view;
    }

    boolean checkimageuri()
    {return imageuri==null;}

    void PickImage()
    {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, IMAGE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

       if(requestCode == REQUEST_CODE)
       {
           if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
           {
               PickImage();
           }
           else
           {
               Toast.makeText(mcontext , "Permission Denied",Toast.LENGTH_SHORT).show();

           }
       }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_CODE)
        {
            imageuri = data.getData();
            Glide
                    .with(mcontext)
                    .load(imageuri)
                    .into(image);

            String image_path = imageuri.getPath();
            File tmp = new File(image_path);
            set_recycler(tmp.getName());
        }
    }

    void set_recycler(String imagename)
    {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        List<String> list = new LinkedList<>();
        list.add(imagename);
        ImageNameAdapter imageNameAdapter = new ImageNameAdapter(list,getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imageNameAdapter);
    }

    public Uri getimageuri()
    {
        return imageuri;
    }
}
