package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.RoomDatabase.AppPcDatabase;
import com.example.myapplication.RoomDatabase.Pc;
import com.example.myapplication.RoomDatabase.PcDao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final int PERMISSION_REQUEST_CODE = 200 ;
    private ImageView logOutPhoto;
    private TextView helloTextView;
    private Button signInButton;
    private Button logInButton;
    private Button addButton;
    private Button shopButton;
    private Button addPCButton;
    private Button shopPCButton;
    private Button deletePCButton;
    private EditText deletePCEditText;
    private Button cameraButtonSignIn;
    private Button galleryButtonSignIn;
    private Dialog sign_in_dialog;
    private Dialog log_in_dialog;
    private Dialog add_pc_dialog;
    private EditText mailEditTextSignIn;
    private EditText passwordEditTextSignIn;
    private EditText mailEditTextLogIn;
    private EditText passwordEditTextLogIn;
    private EditText nameEditTextSignIn;
    private EditText pcNameEditText;
    private EditText processorNameEditText;
    private EditText ramEditText;
    private Button submitButtonSignIn;
    private Button submitButtonLogIn;
    private Button addPcButtonPcDialog;
    private Button cameraButtonPc;
    private Button galleryButtonPc;
    private Bitmap bitmapUser;
    private Bitmap bitmapPc;
    private ImageView userPhoto;
    private Uri uriUser;
    private Uri uriPc;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppPcDatabase.getAppPcDatabase(this);

        boolean isPermission = CheckPermissions(this);
        if(!isPermission)
        {
            Toast.makeText(this,"you do not have permission",Toast.LENGTH_LONG).show();
            RequestPermissions(this);
        }


        sp = getSharedPreferences("informationName",MODE_PRIVATE);

        logOutPhoto = findViewById(R.id.logOutPhoto);
        helloTextView = findViewById(R.id.helloTextView);
        signInButton = findViewById(R.id.signInButton);
        logInButton = findViewById(R.id.logInButton);
        addButton = findViewById(R.id.addButton);
        shopButton = findViewById(R.id.shopButton);
        userPhoto = findViewById(R.id.userPhoto);
        addPCButton = findViewById(R.id.addPCButton);
        shopPCButton = findViewById(R.id.shopPCButton);
        deletePCButton = findViewById(R.id.deletePCButton);
        deletePCEditText = findViewById(R.id.deletePCEditText);


        logOutPhoto.setOnClickListener(this);
        signInButton.setOnClickListener(this);
        logInButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        shopButton.setOnClickListener(this);
        shopPCButton.setOnClickListener(this);
        addPCButton.setOnClickListener(this);
        deletePCButton.setOnClickListener(this);



        sign_in_dialog = new Dialog(this);
        log_in_dialog = new Dialog(this);
        add_pc_dialog = new Dialog(this);

        sign_in_dialog.setContentView(R.layout.sign_in_dialog);

        mailEditTextSignIn = sign_in_dialog.findViewById(R.id.mailEditText);
        passwordEditTextSignIn = sign_in_dialog.findViewById(R.id.passwordEditText);
        nameEditTextSignIn = sign_in_dialog.findViewById(R.id.nameEditText);
        submitButtonSignIn = sign_in_dialog.findViewById(R.id.submitButton);
        cameraButtonSignIn = sign_in_dialog.findViewById(R.id.cameraButton);
        galleryButtonSignIn = sign_in_dialog.findViewById(R.id.galleryButton);

        log_in_dialog.setContentView(R.layout.log_in_dialog);

        mailEditTextLogIn = log_in_dialog.findViewById(R.id.mailEditText);
        passwordEditTextLogIn = log_in_dialog.findViewById(R.id.passwordEditText);
        submitButtonLogIn = log_in_dialog.findViewById(R.id.submitButton);

        add_pc_dialog.setContentView(R.layout.add_pc_dialog);

        pcNameEditText = add_pc_dialog.findViewById(R.id.pcNameEditText);
        processorNameEditText = add_pc_dialog.findViewById(R.id.processorNameEditText);
        ramEditText = add_pc_dialog.findViewById(R.id.ramEditText);
        addPcButtonPcDialog = add_pc_dialog.findViewById(R.id.addButton);
        cameraButtonPc = add_pc_dialog.findViewById(R.id.cameraButtonPc);
        galleryButtonPc = add_pc_dialog.findViewById(R.id.galleryButtonPc);

        submitButtonSignIn.setOnClickListener(this);
        submitButtonLogIn.setOnClickListener(this);
        cameraButtonSignIn.setOnClickListener(this);
        galleryButtonSignIn.setOnClickListener(this);
        addPcButtonPcDialog.setOnClickListener(this);
        cameraButtonPc.setOnClickListener(this);
        galleryButtonPc.setOnClickListener(this);
        addPcButtonPcDialog.setOnClickListener(this);


        boolean isActive =  sp.getBoolean("isActive",false);
        if (isActive == true)
        {
            try {
                SuccessfulLogIn();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onClick(View v)
    {
        boolean isActive =  sp.getBoolean("isActive",false);
        String isMail = sp.getString("userMail",null);

      if (v == logOutPhoto && isActive == true)
      {
            edit = sp.edit();
            edit.putBoolean("isActive",false);
            edit.commit();
            Drawable defaultImage = getResources().getDrawable(R.drawable.jesus_welcome);
            userPhoto.setImageDrawable(defaultImage);
            helloTextView.setText("hello guest");
            VisibleObjects();
        }

      if (v == signInButton && isMail != null)
      {
           log_in_dialog.show();
        }

      if (v == submitButtonLogIn)
      {
            if (ValidateSame())
            {
                edit = sp.edit();
                edit.putBoolean("isActive", true);
                edit.commit();
                log_in_dialog.dismiss();
                try {
                    SuccessfulLogIn();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

      if (v == signInButton && isMail == null)
      {
            sign_in_dialog.show();
        }

      if (v == logInButton && isMail !=null)
      {
            log_in_dialog.show();
        }

      if (v == submitButtonSignIn)
      {
          if (uriUser == null)
          {
              Toast toast = Toast.makeText(this,"you have to choose or take a photo",Toast.LENGTH_SHORT);
              toast.show();
          }
          else
              {
                  saveBitmapInFolder(bitmapUser);
                  try {
                      EditSpSignIn();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }

        }

      if (v  == cameraButtonSignIn)
      {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,1);
        }

      if (v == galleryButtonSignIn)
      {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,2);
      }

      if (v == addButton && isActive == false)
      {
          Toast toast = Toast.makeText(this,"you have to be logged in to access this part",Toast.LENGTH_SHORT);
          toast.show();
      }

      if (v == addButton && isActive == true)
      {
          Intent myIntent = new Intent(this, AddItemActivity.class);
          startActivity(myIntent);
      }

      if (v == shopButton && isActive == false)
      {
          Toast toast = Toast.makeText(this,"you have to be logged in to access this part",Toast.LENGTH_SHORT);
          toast.show();
      }

      if (v == shopButton && isActive == true)
      {
            Intent myIntent = new Intent(this, ShopActivity.class);
            startActivity(myIntent);
        }

      if (v == addPCButton && isActive == true)
      {
          add_pc_dialog.show();
      }

      if (v == shopPCButton && isActive == true)
      {
          Intent myIntent = new Intent(this, PcShopActivity.class);
          startActivity(myIntent);
      }

      if (v  == cameraButtonPc)
      {
          Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          startActivityForResult(intent,3);
      }

      if (v == galleryButtonPc)
      {
          Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
          startActivityForResult(intent,4);
      }

      if (v == addPcButtonPcDialog)
      {
          if (uriPc == null)
          {
              Toast toast = Toast.makeText(this,"you have to choose or take a photo",Toast.LENGTH_SHORT);
              toast.show();
          }
          else
              {
                  String pcName = pcNameEditText.getText().toString();
                  String processorName = processorNameEditText.getText().toString();
                  String StringUri = uriPc.toString();
                  int ramAmount = Integer.parseInt(ramEditText.getText().toString());

                  Pc pc = new Pc(StringUri,pcName,processorName,ramAmount);
                  AppPcDatabase.getAppPcDatabase(this).pcDao().insert(pc);
                  add_pc_dialog.dismiss();

              }
      }

      if (v == deletePCButton && deletePCEditText.getText() != null)
      {
          AppPcDatabase.getAppPcDatabase(this).pcDao().deletePcById(Long.parseLong(deletePCEditText.getText().toString()));
      }
    }

    public boolean ValidateSame()
    {
        String mailSP = sp.getString("userMail", null);
        String passwordSP = sp.getString("userPassword", null);
        if ( !(mailEditTextLogIn.getText().toString().equals(mailSP)))
        {
            Toast toast=Toast.makeText(this,"the mails have to match",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        if ( passwordEditTextLogIn.getText().toString().equals(passwordSP))
        {
            Toast toast=Toast.makeText(this,"the password have to match",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }

    public void EditSpSignIn() throws IOException {
        String mail = mailEditTextSignIn.getText().toString();
        String password = passwordEditTextSignIn.getText().toString();
        String name = nameEditTextSignIn.getText().toString();
        edit = sp.edit();
        edit.putString("userMail",mail);
        edit.putString("userPassword",password);
        edit.putString("userName",name);
        edit.putString("userPhoto", uriUser.toString());
        edit.putBoolean("isActive",true);
        edit.commit();
        sign_in_dialog.dismiss();
        SuccessfulLogIn();
    }


    public void SuccessfulLogIn() throws IOException {
        Uri uriLogIn = Uri.parse(sp.getString("userPhoto",null));
        Bitmap logInBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriLogIn);
        userPhoto.setImageBitmap(logInBitmap); ;
        helloTextView.setText("hello - " + sp.getString("userName",null));
        signInButton.setVisibility(View.GONE);
        logInButton.setVisibility(View.GONE);
    }

    public void VisibleObjects()
    {
        signInButton.setVisibility(View.VISIBLE);
        logInButton.setVisibility(View.VISIBLE);
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
                bitmapUser = (Bitmap)data.getExtras().get("data");
                uriUser = getImageUri(getApplicationContext(), bitmapUser);

                userPhoto.setImageBitmap(bitmapUser);
            }
        }

        if (requestCode == 2)
        {
            if (resultCode == RESULT_OK)
            {

                uriUser = data.getData();

                try {
                    bitmapUser = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriUser);
                    userPhoto.setImageBitmap(bitmapUser);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (requestCode == 3)
        {
            if (resultCode == RESULT_OK)
            {
                bitmapPc = (Bitmap)data.getExtras().get("data");
                uriPc = getImageUri(getApplicationContext(), bitmapPc);
            }
        }

        if (requestCode == 4)
        {
            if (resultCode == RESULT_OK)
            {
                uriPc = data.getData();

                try {
                    bitmapPc = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriPc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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

}