package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import static com.example.myapplication.AddItemActivity.list;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener,Serializable {

private ImageView homePhoto;
private TextView shopTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        homePhoto = findViewById(R.id.homePhoto);
        shopTextView = findViewById(R.id.shopTextView);

        homePhoto.setOnClickListener(this);

        if (list!=null)
        {
            for (int i = 0; i <AddItemActivity.list.size() ; i++)
            {
                shopTextView.setText(shopTextView.getText().toString()+"\n"+AddItemActivity.list.get(i).getName()+ "  "+AddItemActivity.list.get(i).getPrice()+ "  "+AddItemActivity.list.get(i).getQuantity() + "  "+ i);
            }
        }


    }

    @Override
    public void onClick(View view)
    {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
}