package com.gclub.groclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gclub.groclub.Prevalent.Prevalent;

public class Profilepage extends AppCompatActivity {
    private TextView name , phonenum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);

        name = findViewById(R.id.nameText);
        phonenum = findViewById(R.id.phone_number);
        name.setText(Prevalent.custumername);
        phonenum.setText("+91" + Prevalent.userphonekey);
    }

    public void goback(View view) {
        Intent intent = new Intent(this , Homepage.class);
        startActivity(intent);
        finish();
    }
}