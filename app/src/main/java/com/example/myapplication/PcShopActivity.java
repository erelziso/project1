package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.RoomDatabase.AppPcDatabase;
import com.example.myapplication.RoomDatabase.Pc;
import com.example.myapplication.RoomDatabase.PcDao;

import java.io.IOException;
import java.util.List;

public class PcShopActivity extends AppCompatActivity implements View.OnClickListener {
   private TextView pcShopTextView;
   private Bitmap bitmap;
   private  List<Pc> pcList;
   private ImageView pcPhoto;
   private ImageView homePhoto;
   private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pc_shop);

        listView = findViewById(R.id.listView);
        pcShopTextView = findViewById(R.id.pcShopTextView);
        pcPhoto = findViewById(R.id.pcPhoto);
        homePhoto = findViewById(R.id.homePhoto);
        pcList = AppPcDatabase.getAppPcDatabase(this).pcDao().getAll();

        homePhoto.setOnClickListener(this);

        if (pcList != null)
        {
            for (int i = 0; i <pcList.size() ; i++)
            {
                String stringUri = pcList.get(i).getUri();
                Uri uri = Uri.parse(stringUri);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                pcPhoto.setImageBitmap(bitmap);
                pcShopTextView.setText(+ pcList.get(i).getPcId() + " - "+ pcList.get(i).getPcName() + " - " + pcList.get(i).getProcessorName() + " - " + pcList.get(i).getRam());
            }
        }
    }


    @Override
    public void onClick(View v)
    {
            Intent myIntent = new Intent(this, MainActivity.class);
            startActivity(myIntent);
    }
}