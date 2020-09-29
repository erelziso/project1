package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.Models.MySimpleArrayAdapter;
import com.example.myapplication.RoomDatabase.AppPcDatabase;
import com.example.myapplication.RoomDatabase.Pc;
import com.example.myapplication.RoomDatabase.PcDao;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

public class PcShopActivity extends AppCompatActivity implements View.OnClickListener {
   private TextView pcShopTextView;
   private Bitmap bitmap;
   private  List<Pc> pcList;
   private ImageView pcPhoto;
   private ImageView homePhoto;
   private ListView listView;
   private String pcInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pc_shop);

        pcShopTextView = findViewById(R.id.pcShopTextView);
        pcList = AppPcDatabase.getAppPcDatabase(this).pcDao().getAll();
        List<Pc> list = AppPcDatabase.getAppPcDatabase(this).pcDao().getAll();
        homePhoto.setOnClickListener(this);
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, list);
        final ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);

    }


    @Override
    public void onClick(View v)
    {
            Intent myIntent = new Intent(this, MainActivity.class);
            startActivity(myIntent);
    }
}