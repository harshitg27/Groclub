package com.gclub.groclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gclub.groclub.Helperclass.Informationrecycler.Infohelperclass;
import com.gclub.groclub.Helperclass.Informationrecycler.infoadapter;
import com.gclub.groclub.Prevalent.Prevalent;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import io.paperdb.Paper;

public class Homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ImageView cartview;
    RecyclerView inforecycler;
    RecyclerView.Adapter adapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuicon ;
    String cusname;
    LinearLayout contentView;
    LinearLayout Soap;
    private LinearLayout shopandshampoo , facecare , perfume;
    private LinearLayout breakfastitem ,noodlesandsauce ,snacksitems;
    private LinearLayout masale , kirana , dryfruits;

    static final float END_SCALE = 0.7f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_homepage);

        contentView = findViewById(R.id.contentView);
        inforecycler = findViewById(R.id.informationrecycler);
        cartview = findViewById(R.id.cartview);
        Soap = findViewById(R.id.shop);

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
                Intent intent = new Intent(getApplicationContext(),Itemdisplay.class);
                intent.putExtra("category","Soap");
                startActivity(intent);
            }
        });
        facecare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Itemdisplay.class);
                intent.putExtra("category","Face Care");
                startActivity(intent);
            }
        });
        perfume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Itemdisplay.class);
                intent.putExtra("category","Perfumes");
                startActivity(intent);
            }
        });
        breakfastitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Itemdisplay.class);
                intent.putExtra("category","Breakfast Items");
                startActivity(intent);
            }
        });
        noodlesandsauce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Itemdisplay.class);
                intent.putExtra("category","Noodles and Sauce");
                startActivity(intent);
            }
        });
        snacksitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Itemdisplay.class);
                intent.putExtra("category","Snacks");
                startActivity(intent);
            }
        });
        masale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Itemdisplay.class);
                intent.putExtra("category","Masale");
                startActivity(intent);
            }
        });
        kirana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Itemdisplay.class);
                intent.putExtra("category","Kirana");
                startActivity(intent);
            }
        });
        dryfruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Itemdisplay.class);
                intent.putExtra("category","Dry Fruit");
                startActivity(intent);
            }
        });



        cartview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cartintent = new Intent(getApplicationContext(), Cart.class);
                startActivity(cartintent);
            }
        });

        // menu hooks
        menuicon = findViewById(R.id.startview);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);

        Navigationdrawer();

        View headerview = navigationView.getHeaderView(0);
        TextView customernametv = headerview.findViewById(R.id.Welcomeusername);
        cusname = Prevalent.custumername;
        customernametv.setText("Hello , " + cusname);

        // recyclerView Function Call
        Infomationrecycler();
    }

    private void Navigationdrawer() {
        // Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_address);

        menuicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        animateNavigationDrawer();

    }

    private void animateNavigationDrawer() {
        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        drawerLayout.setScrimColor(getResources().getColor(R.color.navigationside));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
        super.onBackPressed();
    }

    private void Infomationrecycler() {
        inforecycler.setHasFixedSize(true);
        inforecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ArrayList<Infohelperclass> infolocations = new ArrayList<>();
        infolocations.add(new Infohelperclass(R.drawable.grocerystore,"Best Things In Best Rates"));
        infolocations.add(new Infohelperclass(R.drawable.grocerywelcome,"Best Product At Orai"));

        adapter = new infoadapter(infolocations);
        inforecycler.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_Logout :
                Paper.book().destroy();
                Intent intent = new Intent(Homepage.this, WelcomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_categeories :
                Intent intent1 = new Intent(this,Categories.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.nav_cart :
                Intent cartintent = new Intent(this, Cart.class);
                startActivity(cartintent);
                break;
            case R.id.nav_orders :
                Intent intent2 = new Intent(this,Myorder.class);
                startActivity(intent2);
                break;
            case R.id.nav_profile :
                startActivity(new Intent(this, Profilepage.class));
                break;
            case R.id.nav_contact :
                startActivity(new Intent(this,Contactus.class));
                break;

        }

        return true;
    }
}