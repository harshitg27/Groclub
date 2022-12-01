package com.gclub.groclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gclub.groclub.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Addnewaddress extends AppCompatActivity {

    EditText addfullname , housedetail , area , landmark ;
    TextView city , pincode ;
    Button addaddress;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewaddress);

        addfullname = findViewById(R.id.addfullname);
        housedetail = findViewById(R.id.housenum);
        area = findViewById(R.id.area);
        landmark = findViewById(R.id.landmark);
        city = findViewById(R.id.city);
        pincode = findViewById(R.id.pincode);
        addaddress = findViewById(R.id.addaddress);

        reference = FirebaseDatabase.getInstance().getReference("Address");

        addfullname.setText(Prevalent.custumername);
    }

    public Boolean validatefullname(){
        String val = addfullname.getText().toString();
        if(val.isEmpty()){
            addfullname.setError("Field cannot be empty");
            return false;
        } else {
            addfullname.setError(null);
            return true;
        }
    }

    public Boolean validatehousedetail(){
        String val = housedetail.getText().toString();
        if(val.isEmpty()){
            housedetail.setError("Field cannot be empty");
            return false;
        } else {
            housedetail.setError(null);
            return true;
        }
    }

    public Boolean validatearea(){
        String val = area.getText().toString();
        if(val.isEmpty()){
            area.setError("Field cannot be empty");
            return false;
        } else {
            area.setError(null);
            return true;
        }
    }

    public Boolean validatelandmark(){
        String val = landmark.getText().toString();
        if(val.isEmpty()){
             landmark.setError("Field cannot be empty");
            return false;
        } else {
            landmark.setError(null);
            return true;
        }
    }

    public void saveaddress (View view){
        if(!validatefullname() | !validatehousedetail() | !validatearea() | !validatelandmark()){
            return;
        }

        final String Fullname = addfullname.getText().toString();
        final String houseno = housedetail.getText().toString();
        final String Area = area.getText().toString();
        final String Landmark = landmark.getText().toString();
        final String City = city.getText().toString();
        final String Pincode = pincode.getText().toString();

        final HashMap<String , Object> address =  new HashMap<>();
        address.put("name",Fullname);
        address.put("houseDetail",houseno);
        address.put("area",Area);
        address.put("landmark",Landmark);
        address.put("city",City);
        address.put("pincode",Pincode);

        reference.child(Prevalent.userphonekey).updateChildren(address).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(getApplicationContext() , AddressandDate.class);
                startActivity(intent);
                finish();
            }
        });

    }
}