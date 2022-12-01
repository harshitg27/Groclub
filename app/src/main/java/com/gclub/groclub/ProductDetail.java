package com.gclub.groclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gclub.groclub.Databasehelper.Product;
import com.gclub.groclub.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetail extends AppCompatActivity {
    TextView productname ,productdesc, discount, mrpandsaving, sellprice, stock ;
    TextView quantity;
    ImageView productimage;
    Button addtocart ,plus , minus;
    private String Category, Key , Pname ,Pimage;
    DatabaseReference reference;
    int quan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

//        hooks
        productname = findViewById(R.id.productname);
        productdesc = findViewById(R.id.productdesc);
        discount = findViewById(R.id.discount);
        mrpandsaving = findViewById(R.id.mrpandsaving);
        sellprice = findViewById(R.id.sellprice);
        stock = findViewById(R.id.stock);
        quantity = findViewById(R.id.quantity);
        productimage = findViewById(R.id.productimage);
        addtocart = findViewById(R.id.addtocart);
        plus = findViewById(R.id.plusbutton);
        minus = findViewById(R.id.minusbutton);

        quantitysystem();


        Pname = getIntent().getExtras().get("Pname").toString();
//        Key = getIntent().getExtras().get("Pkey").toString();
        Category = getIntent().getExtras().get("Pcategory").toString();
//        Pimage = getIntent().getExtras().get("Pimage").toString();

        reference = FirebaseDatabase.getInstance().getReference("Products").child(Category);
        getProductdetail();


    }

    private void quantitysystem() {
        String num = quantity.getText().toString();
        quan = Integer.parseInt(num);
        if (quan == 1){
            minus.setVisibility(View.GONE);
//            Toast.makeText(getApplicationContext(),"You don't add more Items in Your cart",Toast.LENGTH_SHORT).show();
        }
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++quan;
                quantity.setText(String.valueOf(quan));
                minus.setVisibility(View.VISIBLE);
                if (quan == 7){
                    plus.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"You don't add more Items in Your cart",Toast.LENGTH_SHORT).show();
                }
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                --quan;
                quantity.setText(String.valueOf(quan));
                plus.setVisibility(View.VISIBLE);
                if (quan == 1){
                    minus.setVisibility(View.GONE);
//            Toast.makeText(getApplicationContext(),"You don't add more Items in Your cart",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getProductdetail() {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products").child(category);
        reference.child(Pname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Product product = snapshot.getValue(Product.class);
                    productname.setText(product.getProduct_name());
                    productdesc.setText(product.getDescription());
                    discount.setText(product.getDiscount() + " Off");
                    sellprice.setText("Buy At ₹ " + product.getSellPrice());
                    mrpandsaving.setText("MRP: ₹ " + product.getMrp() + "  save ₹ " + product.getSaving());
                    Picasso.get().load(product.getImage()).into(productimage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void gotocart(View view) {

        final String savecurrentdate , savecurrenttime ;

        Calendar callfordate = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        savecurrentdate = dateFormat.format(callfordate.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime = currenttime.format(callfordate.getTime());
        Log.i("time", "gotocart: " + savecurrenttime);

        reference.child(Pname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Product product = snapshot.getValue(Product.class);

                    DatabaseReference cartlistreference = FirebaseDatabase.getInstance().getReference("Cart List");

                    final HashMap<String , Object> cartmap =  new HashMap<>();
                    cartmap.put("productKey",product.getKey());
                    cartmap.put("product_name",product.getProduct_name());
                    cartmap.put("product_desc",product.getDescription());
                    cartmap.put("price",product.getSellPrice());
                    cartmap.put("categories",product.getCategory());
                    cartmap.put("date",savecurrentdate);
                    cartmap.put("time",savecurrenttime);
                    cartmap.put("quantity",quantity.getText().toString());
                    cartmap.put("mrp",product.getMrp());
                    cartmap.put("save",product.getSaving());
                    cartmap.put("discount",discount.getText().toString());
                    cartmap.put("product_image",product.getImage());

                    cartlistreference.child("User View").child(Prevalent.userphonekey).child(product.getKey()).setValue(cartmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(getApplicationContext(),Homepage.class);
                            intent.putExtra("category",Category);
                            startActivity(intent);
                            finish();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void buynow(View view) {

    }
}