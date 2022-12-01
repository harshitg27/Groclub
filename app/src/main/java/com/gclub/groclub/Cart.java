package com.gclub.groclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.gclub.groclub.Databasehelper.cartitem;
import com.gclub.groclub.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Cart extends AppCompatActivity {
    RecyclerView cartitemsrecycler ;
    TextView mrptv ,  discounytv , deliverytv , subtotal , finalcost  ;
    LinearLayout checkout , amountdetail;
    DatabaseReference cartref , reference ;
    private FirebaseRecyclerAdapter adapter;
    ProgressBar progressBar;
    ImageView goback;
    private ProgressDialog loadingbar;
    private int mrp = 0 ;
    private int save = 0 ;
    private int total = 0 ;
    private int quan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        progressBar = findViewById(R.id.progressBar2);
        cartitemsrecycler =  findViewById(R.id.cartitems);
        mrptv = findViewById(R.id.mrptv);
        discounytv = findViewById(R.id.discounttv);
        deliverytv = findViewById(R.id.deliverytv);
        subtotal = findViewById(R.id.subtotaltv);
        finalcost = findViewById(R.id.finalcost);
        checkout = findViewById(R.id.checkout);
        amountdetail = findViewById(R.id.linearLayout);
        goback = findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Homepage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        loadingbar = new ProgressDialog(this);

        amountdetail.setVisibility(View.GONE);
        checkout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

//        loadingbar.setTitle("Already loggin");
//        loadingbar.setMessage("Please wait....");
//        loadingbar.setCanceledOnTouchOutside(false);
//        loadingbar.show();

        cartitemsrecycler.setHasFixedSize(true);
        cartitemsrecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        cartref = FirebaseDatabase.getInstance().getReference("Cart List").child("User View");
        Query query =cartref.child(Prevalent.userphonekey);

        FirebaseRecyclerOptions<cartitem> options = new FirebaseRecyclerOptions.Builder<cartitem>()
                .setQuery(query, cartitem.class ).build();
//        {
//                    @NonNull
//                    @Override
//                    public cartitem parseSnapshot(@NonNull DataSnapshot snapshot) {
//                        return new cartitem(snapshot.child("discount").getValue().toString(),
//                                snapshot.child("categories").getValue().toString(),
//                                snapshot.child("price").getValue().toString(),
//                                snapshot.child("mrp").getValue().toString(),
//                                snapshot.child("productKey").getValue().toString(),
//                                snapshot.child("product_desc").getValue().toString(),
//                                snapshot.child("product_image").getValue().toString(),
//                                snapshot.child("product_name").getValue().toString(),
//                                snapshot.child("quantity").getValue().toString(),
//                                snapshot.child("save").getValue().toString());
//                    }
//                }).build();

           adapter = new FirebaseRecyclerAdapter<cartitem, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CartViewHolder holder, int position, @NonNull final cartitem model) {
                holder.price.setText(" ₹" + model.getPrice());
                holder.mrpandsaving.setText("M.R.P. ₹ " + model.getMrp() + " and save ₹" + model.getSave());
                holder.productdes.setText(model.getProduct_desc());
                holder.quantity.setText(model.getQuantity());
                holder.discount.setText(model.getDiscount());
                Picasso.get().load(model.getProduct_image()).into(holder.productimage);

                String num = holder.quantity.getText().toString();
                quan = Integer.parseInt(num);
                holder.plus.setVisibility(View.GONE);
                holder.minus.setVisibility(View.GONE);

                reference = FirebaseDatabase.getInstance().getReference("Cart List").child("User View").child(Prevalent.userphonekey);
                final HashMap<String,Object> updatemap =  new HashMap<>();

                if (quan == 1){
                    holder.minus.setVisibility(View.GONE);
//            Toast.makeText(getApplicationContext(),"You don't add more Items in Your cart",Toast.LENGTH_SHORT).show();
                }
                holder.plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ++quan;
                        holder.quantity.setText(String.valueOf(quan));
                        holder.minus.setVisibility(View.VISIBLE);
                        if (quan == 7){
                            holder.plus.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"You don't add more Items in Your cart",Toast.LENGTH_SHORT).show();
                        }
                        updatemap.put("quantity",String.valueOf(quan));
                        reference.child(model.getProductKey()).updateChildren(updatemap);
                    }
                });
                holder.minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        --quan;
                        holder.quantity.setText(String.valueOf(quan));
                        holder.plus.setVisibility(View.VISIBLE);
                        if (quan == 1){
                            holder.minus.setVisibility(View.GONE);
//            Toast.makeText(getApplicationContext(),"You don't add more Items in Your cart",Toast.LENGTH_SHORT).show();
                        }
                        updatemap.put("quantity",String.valueOf(quan));
                        reference.child(model.getProductKey()).updateChildren(updatemap);
                    }
                });



                int oneitemmrp = Integer.valueOf(model.getMrp()) * Integer.valueOf(model.getQuantity());
                mrp =  mrp + oneitemmrp;

                int oneitemsave = Integer.valueOf(model.getSave()) * Integer.valueOf(model.getQuantity());
                save = save + oneitemsave;

                int oneitemprice = Integer.valueOf(model.getPrice()) * Integer.valueOf(model.getQuantity());
                total =  total + oneitemprice;

                final String categories = model.getCategories();

                amountdetail.setVisibility(View.VISIBLE);
                checkout.setVisibility(View.VISIBLE);

                mrptv.setText(String.valueOf(mrp));
                discounytv.setText(String.valueOf(save));
                subtotal.setText(String.valueOf(total));
                finalcost.setText(String.valueOf(total));


//                progressBar.setVisibility(View.GONE);
                loadingbar.dismiss();

                holder.cartitemcard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ProductDetail.class);
                        intent.putExtra("Pcategory", categories);
                        intent.putExtra("Pname", model.getProduct_name());
                        startActivity(intent);
                        finish();
                    }
                });

                checkout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),AddressandDate.class);
                        intent.putExtra("Totalprice",String.valueOf(total));
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem_card, parent, false);
                CartViewHolder cartViewHolder = new CartViewHolder(view);
                return cartViewHolder;
            }
        };
        cartitemsrecycler.setAdapter(adapter);

//        mrptv.setText(String.valueOf(mrp));
//        discounytv.setText(String.valueOf(save));
//        subtotal.setText(String.valueOf(total));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{
        public CardView cartitemcard;
        public ImageView productimage;
        public TextView productdes , price , discount, mrpandsaving,quantity;
        public Button plus , minus;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartitemcard = itemView.findViewById(R.id.productcard);
            productimage = itemView.findViewById(R.id.productimage);
            price = itemView.findViewById(R.id.price);
            mrpandsaving = itemView.findViewById(R.id.mrpandsaving);
            productdes = itemView.findViewById(R.id.productdescr);
            quantity = itemView.findViewById(R.id.quantity);
            discount = itemView.findViewById(R.id.discount);
            plus = itemView.findViewById(R.id.plusbutton);
            minus = itemView.findViewById(R.id.minusbutton);

        }
    }
}