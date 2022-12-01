package com.gclub.groclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gclub.groclub.Databasehelper.Addresshelper;
import com.gclub.groclub.Databasehelper.cartitem;
import com.gclub.groclub.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class Paymentmethod extends AppCompatActivity {

    private RadioGroup paymentmethods;
    private Button confirmpayamount;
    private String amount;
    private TextView finalamount;
    private DatabaseReference orderref;
    private final int UPI_PAYMENT = 0;
    private String Key;
    private DatabaseReference addressref;
    private String savecurrentdate , savecurrenttime ;
    private String deliverydate , paymenymode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentmethod);
        paymentmethods = findViewById(R.id.paymentmethod);
        confirmpayamount = findViewById(R.id.cnfpay);
        finalamount = findViewById(R.id.finalamount);

        amount = getIntent().getExtras().get("Totalprice").toString();
        deliverydate = getIntent().getStringExtra("Delivery date");
        finalamount.setText("Amount = " + amount);

        orderref = FirebaseDatabase.getInstance().getReference("Order Detail");


    }

    public void cnfamountpay(View view) {
        int checkedid = paymentmethods.getCheckedRadioButtonId();
        if(checkedid == -1){
            // no radio button selected
            Toast.makeText(this,"Select Any Payment Method Then Proceed",Toast.LENGTH_SHORT).show();
        }else {
            // radio button selected
            paymentfuctions(checkedid);
        }
    }

    private void paymentfuctions(int checkedid) {
        switch (checkedid){
            case R.id.cod :
                paymenymode = "Cash On Delivery";
                saveorder();
//                saveorder();
                break;
            case R.id.upi :
                makepaybyupi();
                break;
            case R.id.paytm :
                Toast.makeText(this, "This Method not Exist This Time Coming soon",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void movingproduct() {

        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("Cart List").child("User View").child(Prevalent.userphonekey);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    DatabaseReference orderreference = FirebaseDatabase.getInstance().getReference("Orders").child(Prevalent.userphonekey).child(Key);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){

                        cartitem cartlist = snapshot1.getValue(cartitem.class);

                        final HashMap<String , Object> cartmap =  new HashMap<>();
                        cartmap.put("productKey",cartlist.getProductKey());
                        cartmap.put("product_name",cartlist.getProduct_name());
                        cartmap.put("product_desc",cartlist.getProduct_desc());
                        cartmap.put("price",cartlist.getPrice());
                        cartmap.put("categories",cartlist.getCategories());
                        cartmap.put("date",savecurrentdate);
                        cartmap.put("time",savecurrenttime);
                        cartmap.put("quantity",cartlist.getQuantity());
                        cartmap.put("mrp",cartlist.getMrp());
                        cartmap.put("save",cartlist.getSave());
                        cartmap.put("discount",cartlist.getDiscount());
                        cartmap.put("product_image",cartlist.getProduct_image());
                        orderreference.child(cartlist.getProductKey()).updateChildren(cartmap);
//                        Toast.makeText(getApplicationContext(),snapshot1.child("product_name").getValue(String.class) , Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void saveorder(){
        addressref = FirebaseDatabase.getInstance().getReference("Address").child(Prevalent.userphonekey);

        addressref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    final Addresshelper addresshelper = snapshot.getValue(Addresshelper.class);
                    final String address = addresshelper.getHouseDetail() + " "
                                    + addresshelper.getArea() + " "
                                    + addresshelper.getLandmark() + " "
                                    + addresshelper.getCity() + " " + addresshelper.getPincode();


                    Calendar callfordate = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                    savecurrentdate = dateFormat.format(callfordate.getTime());

                    SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
                    savecurrenttime = currenttime.format(callfordate.getTime());

                    Key = UUID.randomUUID().toString().substring(4,23);

                    HashMap<String, Object> ordermap = new HashMap<>();
                    ordermap.put("amount",amount);
                    ordermap.put("orderid",Key);
                    ordermap.put("deliverydate",deliverydate);
                    ordermap.put("name",addresshelper.getName());
                    ordermap.put("address",address);
                    ordermap.put("mobileno",Prevalent.userphonekey);
                    ordermap.put("date",savecurrentdate);
                    ordermap.put("time",savecurrenttime);
                    ordermap.put("payment_method",paymenymode);
                    ordermap.put("order_status","Not Shipped");

                    orderref.child("For Customer").child(Prevalent.userphonekey).child(Key).updateChildren(ordermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
//                                DatabaseReference datebyorder = FirebaseDatabase.getInstance().getReference("Order Detail");
                                HashMap<String, Object> ordermap = new HashMap<>();
                                ordermap.put("amount",amount);
                                ordermap.put("orderid",Key);
                                ordermap.put("deliverydate",deliverydate);
                                ordermap.put("name",addresshelper.getName());
                                ordermap.put("address",address);
                                ordermap.put("mobileno",Prevalent.userphonekey);
                                ordermap.put("date",savecurrentdate);
                                ordermap.put("time",savecurrenttime);
                                ordermap.put("payment_method",paymenymode);
                                ordermap.put("order_status","Not Shipped");

                                orderref.child("DateByorder").child(savecurrentdate).child(deliverydate).child(Key).updateChildren(ordermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        movingproduct();
                                        FirebaseDatabase.getInstance().getReference("Cart List")
                                                .child("User View")
                                                .child(Prevalent.userphonekey)
                                                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(getApplicationContext(),"Your order is Successfully Placed",Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(getApplicationContext(),Homepage.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        });
                                    }
                                });

                            }

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void makepaybyupi() {

        String amount = "1";
        String note = "For Your Order";
        String name = "Harshit";
        String upiId = "h9532341922@paytm";
        payUsingUpi(amount, upiId, name, note);

    }

    void payUsingUpi(String amount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(Paymentmethod.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(Paymentmethod.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(Paymentmethod.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(Paymentmethod.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(Paymentmethod.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Paymentmethod.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

}