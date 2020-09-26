package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private final int PERMISSION_REQUEST_CODE = 200;
    private ImageView userPhoto;
    private Button cameraButton,galleryButton,saveButton;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);


        userPhoto = findViewById(R.id.userPhoto);
        cameraButton = findViewById(R.id.cameraButton);
        galleryButton = findViewById(R.id.galleryButton);
        saveButton = findViewById(R.id.saveButton);

        cameraButton.setOnClickListener(this);
        galleryButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);

        boolean isPermission = CheckPermissions(this);
        if(!isPermission)
        {
            Toast.makeText(this,"you do not have permission",Toast.LENGTH_LONG).show();
            RequestPermissions(this);
        }


    }


    public boolean CheckPermissions(Context context)
    {
        int resultCamera = ContextCompat.checkSelfPermission(context, CAMERA);
        int resultReadCamera = ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE);
        int resultWriteStorage = ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE);

        return resultCamera == PackageManager.PERMISSION_GRANTED && resultReadCamera == PackageManager.PERMISSION_GRANTED && resultWriteStorage == PackageManager.PERMISSION_GRANTED;
    }

    public void RequestPermissions(Activity activity)
    {
        ActivityCompat.requestPermissions(activity, new String[]
    {
     CAMERA,READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE
    },PERMISSION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK)
            {
                bitmap = (Bitmap)data.getExtras().get("data");
                userPhoto.setImageBitmap(bitmap);
            }
        }
        if (requestCode == 2)
        {
            if (resultCode == RESULT_OK)
            {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                userPhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }


    }

    private void saveBitmapInFolder(Bitmap bitmap) {
        String timeStamp = new SimpleDateFormat("yyMMdd-HHmmss").format(new Date());
        String fileName = timeStamp+ ".jpg";

        File myDir = new File(Environment.getExternalStorageDirectory(), "/MyProjectPics");
        if(!myDir.exists())myDir.mkdir();
        File dest = new File(myDir, fileName);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(dest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
        try {
            out.flush();
            out.close();
            Toast.makeText(this, "the pic is saved", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "the pic couldn't be saved", Toast.LENGTH_LONG).show();
        }

    }



    @Override
    public void onClick(View v)
    {
        if (v  == cameraButton)
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,1);
        }
        if (v == saveButton)
        {
          saveBitmapInFolder(bitmap);
        }
        if (v == galleryButton)
        {

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,2);
        }
    }
}