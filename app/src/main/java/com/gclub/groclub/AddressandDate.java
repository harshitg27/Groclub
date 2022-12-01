package com.gclub.groclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gclub.groclub.Databasehelper.Addresshelper;
import com.gclub.groclub.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddressandDate extends AppCompatActivity {

    TextView name , houseno , area , landmark , city , pincode  ;
    private TextView day1 , wday1 , day2 , wday2 , day3 , wday3 , day4 , wday4 , day5 , wday5 ;
    Button makepayment , newaddress;
    private DatabaseReference addressref;
    RelativeLayout addresslayout;
    LinearLayout newaddresslayout , addressandtimeslot;
    private String amount;
    private RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    String date;
    String weekdays;
    String deliverydate;
    RadioGroup timeslotrg;
    RadioButton timeradio;
    private RelativeLayout date1 , date2 , date3 , date4 , date5 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressand_date);

        name = findViewById(R.id.name);
        houseno = findViewById(R.id.houseno);
        area = findViewById(R.id.area);
        landmark = findViewById(R.id.landmark);
        city = findViewById(R.id.city);
        pincode = findViewById(R.id.pincode);

        // layout or radiogrp hooks
        newaddresslayout = findViewById(R.id.newaddresslayout);
        addressandtimeslot = findViewById(R.id.addressandtimeslot);
        makepayment = findViewById(R.id.makepayment);
        timeslotrg = findViewById(R.id.timeslotrg);

//        recyclerView = findViewById(R.id.recyclerview);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//        showdate();
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
//        Date d = new Date();
//        Calendar calendar = new GregorianCalendar();
//        calendar.add(Calendar.DAY_OF_WEEK, 1);
//        String day = sdf.format(calendar.getTime());
//        String dayOfTheWeek = sdf.format(d);
//        Toast.makeText(this,day,Toast.LENGTH_SHORT).show();

        // layout or radiogrp for visibility
        addressandtimeslot.setVisibility(View.GONE);
        makepayment.setVisibility(View.GONE);
        newaddresslayout.setVisibility(View.VISIBLE);
        timeslotrg.setVisibility(View.GONE);

        // hooks of date slots
        day1 = findViewById(R.id.day1);
        day2 = findViewById(R.id.day2);
        day3 = findViewById(R.id.day3);
        day4 = findViewById(R.id.day4);
        day5 = findViewById(R.id.day5);
        wday1 = findViewById(R.id.wday1);
        wday2 = findViewById(R.id.wday2);
        wday3 = findViewById(R.id.wday3);
        wday4 = findViewById(R.id.wday4);
        wday5 = findViewById(R.id.wday5);
        //set date in slot
        dateslots();
        tapintextview();
        timeslotrg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                timeradio = findViewById(checkedId);
                makepayment.setVisibility(View.VISIBLE);

            }
        });

        amount = getIntent().getExtras().get("Totalprice").toString();

        addressref = FirebaseDatabase.getInstance().getReference("Address").child(Prevalent.userphonekey);

        addressref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    newaddresslayout.setVisibility(View.INVISIBLE);
                    addressandtimeslot.setVisibility(View.VISIBLE);

                    Addresshelper address = snapshot.getValue(Addresshelper.class);
                    name.setText(address.getName());
                    houseno.setText(address.getHouseDetail());
                    area.setText(address.getArea());
                    landmark.setText(address.getLandmark());
                    city.setText(address.getCity());
                    pincode.setText(address.getPincode());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void dateslots() {

        Calendar time = Calendar.getInstance();
        int hours = time.get(Calendar.HOUR_OF_DAY);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        SimpleDateFormat weekday = new SimpleDateFormat("EEE");
        Calendar calendar = new GregorianCalendar();
        if(hours < 20){
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            date = sdf.format(calendar.getTime());
            weekdays = weekday.format(calendar.getTime());
            wday1.setText(weekdays);
            day1.setText(date);

            calendar.add(Calendar.DAY_OF_WEEK, 1);
            date = sdf.format(calendar.getTime());
            weekdays = weekday.format(calendar.getTime());
            wday2.setText(weekdays);
            day2.setText(date);

            calendar.add(Calendar.DAY_OF_WEEK, 1);
            date = sdf.format(calendar.getTime());
            weekdays = weekday.format(calendar.getTime());
            wday3.setText(weekdays);
            day3.setText(date);

            calendar.add(Calendar.DAY_OF_WEEK, 1);
            date = sdf.format(calendar.getTime());
            weekdays = weekday.format(calendar.getTime());
            wday4.setText(weekdays);
            day4.setText(date);

            calendar.add(Calendar.DAY_OF_WEEK, 1);
            date = sdf.format(calendar.getTime());
            weekdays = weekday.format(calendar.getTime());
            wday5.setText(weekdays);
            day5.setText(date);

        }else {
                calendar.add(Calendar.DAY_OF_WEEK, 2);
                date = sdf.format(calendar.getTime());
                weekdays = weekday.format(calendar.getTime());
                wday1.setText(weekdays);
                day1.setText(date);

                calendar.add(Calendar.DAY_OF_WEEK, 1);
                date = sdf.format(calendar.getTime());
                weekdays = weekday.format(calendar.getTime());
                wday2.setText(weekdays);
                day2.setText(date);

                calendar.add(Calendar.DAY_OF_WEEK, 1);
                date = sdf.format(calendar.getTime());
                weekdays = weekday.format(calendar.getTime());
                wday3.setText(weekdays);
                day3.setText(date);

                calendar.add(Calendar.DAY_OF_WEEK, 1);
                date = sdf.format(calendar.getTime());
                weekdays = weekday.format(calendar.getTime());
                wday4.setText(weekdays);
                day4.setText(date);

                calendar.add(Calendar.DAY_OF_WEEK, 1);
                date = sdf.format(calendar.getTime());
                weekdays = weekday.format(calendar.getTime());
                wday5.setText(weekdays);
                day5.setText(date);

            }


    }

    private void tapintextview(){
        date1 = findViewById(R.id.date1);
        date2 = findViewById(R.id.date2);
        date3 = findViewById(R.id.date3);
        date4 = findViewById(R.id.date4);
        date5 = findViewById(R.id.date5);
        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliverydate = day1.getText().toString();
                timeslotrg.setVisibility(View.VISIBLE);
                timeslotrg.clearCheck();
                makepayment.setVisibility(View.GONE);

            }
        });
        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliverydate = day2.getText().toString();
                timeslotrg.setVisibility(View.VISIBLE);
                timeslotrg.clearCheck();
                makepayment.setVisibility(View.GONE);

            }
        });
        date3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliverydate = day3.getText().toString();
                timeslotrg.setVisibility(View.VISIBLE);
                timeslotrg.clearCheck();
                makepayment.setVisibility(View.GONE);

            }
        });
        date4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliverydate = day4.getText().toString();
                timeslotrg.setVisibility(View.VISIBLE);
                timeslotrg.clearCheck();
                makepayment.setVisibility(View.GONE);

            }
        });
        date5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliverydate = day5.getText().toString();
                timeslotrg.setVisibility(View.VISIBLE);
                timeslotrg.clearCheck();
                makepayment.setVisibility(View.GONE);

            }
        });

    }

    private void showdate() {
        ArrayList<String> results = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        SimpleDateFormat weekday = new SimpleDateFormat("EEE");
        for (int i = 1; i < 9; i++) {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            String date = sdf.format(calendar.getTime());
            String weekdays = weekday.format(calendar.getTime());
//            tv1.setText(weekdays + "\n" + date);
//            Toast.makeText(this,day,Toast.LENGTH_SHORT).show();
//            results.add(day);
        }

    }

    public void addadress(View view) {
        Intent intent = new Intent(this ,Addnewaddress.class);
        startActivity(intent);
    }

    public void gotopayment(View view) {
        Intent intent = new Intent(this ,Paymentmethod.class);
        intent.putExtra("Totalprice",amount);
        intent.putExtra("Delivery date",deliverydate);
        startActivity(intent);
    }

    public void changeaddress(View view) {
        Intent intent = new Intent(this ,Addnewaddress.class);
        startActivity(intent);
    }
}