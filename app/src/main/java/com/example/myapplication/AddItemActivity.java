package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Models.Item;

import java.io.Serializable;
import java.util.LinkedList;

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

    private EditText nameOfItemEditText;
    private EditText priceOfItemEditText;
    private EditText quantityOfItemEditText;
    private ImageView homePhoto;
    private TextView addItem;
    public  static LinkedList<Item> list;
    private Intent myIntent12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        nameOfItemEditText = findViewById(R.id.nameOfItemEditText);
        priceOfItemEditText = findViewById(R.id.priceOfItemEditText);
        quantityOfItemEditText = findViewById(R.id.quantityOfItemEditText);
        homePhoto = findViewById(R.id.homePhoto);
        addItem = findViewById(R.id.addItem);


        addItem.setOnClickListener(this);
        homePhoto.setOnClickListener(this);

        list = new LinkedList<Item>();
    }

    @Override
    public void onClick(View v)
    {
        if (v == addItem)
        {
            Item item = new Item(nameOfItemEditText.getText().toString(),priceOfItemEditText.getText().toString(),quantityOfItemEditText.getText().toString());
            list.add(item);
            nameOfItemEditText.setText("");
            priceOfItemEditText.setText("");
            quantityOfItemEditText.setText("");
        }

        if (v == homePhoto)
        {
             myIntent12 = new Intent(this, ShopActivity.class);
             startActivity(myIntent12);
        }

    }
}