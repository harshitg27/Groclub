package com.gclub.groclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.gclub.groclub.Databasehelper.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Itemdisplay extends AppCompatActivity {
    private String categories;
    private DatabaseReference ProductRef;
    private RecyclerView productrecycler;
    ArrayList<Product> list;
    private FirebaseRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdisplay);

        productrecycler = findViewById(R.id.productrecycler);
        layoutManager = new LinearLayoutManager(this);
        productrecycler.setLayoutManager(layoutManager);
        productrecycler.setHasFixedSize(true);

        categories =  getIntent().getStringExtra("category");
        Log.d("data", categories);


//        new NetworkCheck(Itemdisplay.this).checkConnection();

        ProductRef = FirebaseDatabase.getInstance().getReference("Products").child(categories);
        fetch();

//        ProductRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    Log.i("data", "onDataChange: snapshopexist"+snapshot.getChildrenCount());
//
//
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        ProductRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()) {
//                    String pnam = snapshot.child("dettol").child("product_name").getValue(String.class);
//                    Log.d("data", "egye");
//                    Log.i("data", "onDataChange: "+ pnam);
//
//                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                        String pname = snapshot1.child("product_name").getValue(String.class);
//                        String pdes = snapshot1.child("description").getValue(String.class);
//                        String sp = snapshot1.child("sellPrice").getValue(String.class);
//                        String image = snapshot1.child("image").getValue(String.class);
//
//                        Log.i("data", "onDataChange:"+ pname +pdes + sp + image);
//////                    Product p = snapshot1.getValue(Product.class);
////                        list.add(new Product(pname, pdes, sp, image));
//                    }
////                    adapter = new Productadapter(list);
////                    productrecycler.setAdapter(adapter);
//                }else {
//                    Log.d("data", "onDataChange:data does not exist");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(),"opssss.... Something has wrong",Toast.LENGTH_SHORT).show();
//
//            }
//        });

    }

    private void fetch() {
        FirebaseRecyclerOptions<Product>  options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(ProductRef , Product.class).build();

        adapter = new FirebaseRecyclerAdapter<Product, ProductViewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewholder holder, int position, @NonNull final Product model) {

                holder.Productname.setText(model.getProduct_name());
                holder.ProductDescription.setText(model.getDescription());
                holder.Productcurrentprice.setText("Rs " + model.getSellPrice());
                Picasso.get().load(model.getImage()).into(holder.Productimage);
                Log.d("data", "onBindViewHolder: " + model.getProduct_name());
                holder.productcard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productDetails = new Intent(Itemdisplay.this, ProductDetail.class);
                        productDetails.putExtra("Pname",model.getProduct_name());
                        productDetails.putExtra("Pdescription",model.getDescription());
                        productDetails.putExtra("Pdiscount",model.getDiscount());
                        productDetails.putExtra("Pimage",model.getImage());
                        productDetails.putExtra("Psellprice",model.getSellPrice());
                        productDetails.putExtra("Pmrp",model.getMrp());
                        productDetails.putExtra("Psaving",model.getSaving());
                        productDetails.putExtra("Pkey",model.getKey());
                        productDetails.putExtra("Pcategory",model.getCategory());
                        startActivity(productDetails);
                    }
                });

            }

            @NonNull
            @Override
            public ProductViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                Log.d("data", "onStart: HArshit ");
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productitem1stlayout , parent, false);
                ProductViewholder holder = new ProductViewholder(view);
                return holder;
            }
        };
        productrecycler.setAdapter(adapter);
    }


    //    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d("data", "onStart: ");
//        categories =  getIntent().getExtras().get("category").toString();
//        ProductRef = FirebaseDatabase.getInstance().getReference("Products").child(categories);
//
//        FirebaseRecyclerOptions<Product>  options = new FirebaseRecyclerOptions.Builder<Product>()
//                .setQuery(ProductRef , Product.class).build();
//
//        FirebaseRecyclerAdapter<Product , ProductViewholder> adapter = new FirebaseRecyclerAdapter<Product, ProductViewholder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull ProductViewholder holder, int position, @NonNull final Product model) {
//
//                holder.Productname.setText(model.getProduct_name());
//                holder.ProductDescription.setText(model.getDescription());
//                holder.Productcurrentprice.setText("Rs " + model.getSellPrice());
//                Picasso.get().load(model.getImage()).into(holder.Productimage);
//                Log.d("data", "onBindViewHolder: " + model.getProduct_name());
//                holder.productcard.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent productDetails = new Intent(Itemdisplay.this, ProductDetail.class);
//                        productDetails.putExtra("Pname",model.getProduct_name());
//                        productDetails.putExtra("Pdescription",model.getDescription());
//                        productDetails.putExtra("Pdiscount",model.getDiscount());
//                        productDetails.putExtra("Pimage",model.getImage());
//                        productDetails.putExtra("Psellprice",model.getSellPrice());
//                        productDetails.putExtra("Pmrp",model.getMrp());
//                        productDetails.putExtra("Psaving",model.getSaving());
//                        productDetails.putExtra("Pkey",model.getKey());
//                        productDetails.putExtra("Pcategory",model.getCategory());
//                        startActivity(productDetails);
//                    }
//                });
//
//
//            }
//
//            @NonNull
//            @Override
//            public ProductViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//                Log.d("data", "onStart: HArshit ");
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productitem1stlayout , parent, false);
//                ProductViewholder holder = new ProductViewholder(view);
//                return holder;
//            }
//        };
//        productrecycler.setAdapter(adapter);
//        adapter.startListening();
//    }


    public class ProductViewholder extends RecyclerView.ViewHolder{
        View view;
        CardView productcard ;
        public ImageView Productimage;
        public TextView Productname , ProductDescription , Productcurrentprice;

        public ProductViewholder(@NonNull View itemView) {
            super(itemView);
            Productimage = itemView.findViewById(R.id.ProductImage);
            Productname = itemView.findViewById(R.id.Productname);
            ProductDescription = itemView.findViewById(R.id.ProductDesc);
            Productcurrentprice = itemView.findViewById(R.id.ProductCurrentPrice);
            productcard = itemView.findViewById(R.id.productcard);
        }



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,Homepage.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}