package com.gclub.groclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Categories extends AppCompatActivity {
    private LinearLayout shopandshampoo , facecare , perfume;
    private LinearLayout breakfastitem ,noodlesandsauce ,snacksitems;
    private LinearLayout masale , kirana , dryfruits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
//        hooks of items
        shopandshampoo = findViewById(R.id.shopandshampoo);
        facecare = findViewById(R.id.facecare);
        perfume = findViewById(R.id.perfume);
        breakfastitem = findViewById(R.id.breakfastitem);
        noodlesandsauce = findViewById(R.id.noodlesandsauce);
        snacksitems = findViewById(R.id.snacks);
        masale = findViewById(R.id.masala);
        kirana = findViewById(R.id.kirana);
        dryfruits = findViewById(R.id.dryfruit);

//        functions of items
        shopandshampoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this,Itemdisplay.class);
                intent.putExtra("category","Soap");
                startActivity(intent);
            }
        });
        facecare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this,Itemdisplay.class);
                intent.putExtra("category","Face Care");
                startActivity(intent);
            }
        });
        perfume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this,Itemdisplay.class);
                intent.putExtra("category","Perfumes");
                startActivity(intent);
            }
        });
        breakfastitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this,Itemdisplay.class);
                intent.putExtra("category","Breakfast Items");
                startActivity(intent);
            }
        });
        noodlesandsauce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this,Itemdisplay.class);
                intent.putExtra("category","Noodles and Sauce");
                startActivity(intent);
            }
        });
        snacksitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this,Itemdisplay.class);
                intent.putExtra("category","Snacks");
                startActivity(intent);
            }
        });
        masale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this,Itemdisplay.class);
                intent.putExtra("category","Masale");
                startActivity(intent);
            }
        });
        kirana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this,Itemdisplay.class);
                intent.putExtra("category","Kirana");
                startActivity(intent);
            }
        });
        dryfruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this,Itemdisplay.class);
                intent.putExtra("category","Dry Fruit");
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,Homepage.class);
        startActivity(intent);
        finish();

    }
}