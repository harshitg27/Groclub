package com.gclub.groclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.gclub.groclub.Databasehelper.Myorderhelper;
import com.gclub.groclub.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Myorder extends AppCompatActivity {
    DatabaseReference reference;
    RecyclerView recyclerView;
    private FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reference = FirebaseDatabase.getInstance().getReference("Order Detail").child("For Customer");
        fetch();

//        FirebaseRecyclerOptions<Myorderhelper> options = new FirebaseRecyclerOptions.Builder<Myorderhelper>().setQuery(reference , Myorderhelper.class).build();
//        adapter = new FirebaseRecyclerAdapter<Myorderhelper, OrderViewHolder>(options) {
//           @Override
//           protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull final Myorderhelper model) {
//               Log.i("Harsh", "onBindViewHolder: ");
//               holder.setOrderid("Order id = " + model.getOrderid());
//               holder.setAmount("Amount = " + model.getAmount());
//               holder.setDate("Order on " + model.getDate());
//               holder.setStatus("Order Status is " + model.getOrder_status());
////               holder.cardView.setOnClickListener(new View.OnClickListener() {
////                   @Override
////                   public void onClick(View v) {
////
////                   }
////               });
//
//           }
//
//           @NonNull
//           @Override
//           public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//               Log.i("Harsh", "onCreateViewHolder: ");
//               View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorderlayout, parent, false);
//               OrderViewHolder orderViewHolder = new OrderViewHolder(view);
//               return orderViewHolder;
//           }
//       };
//        recyclerView.setAdapter(adapter);


    }

    private void fetch() {
        Query query = reference.child(Prevalent.userphonekey);
        FirebaseRecyclerOptions<Myorderhelper> options =
                new FirebaseRecyclerOptions.Builder<Myorderhelper>()
                        .setQuery(query, new SnapshotParser<Myorderhelper>() {
                            @NonNull
                            @Override
                            public Myorderhelper parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new Myorderhelper(snapshot.child("address").getValue().toString(),
                                        snapshot.child("amount").getValue().toString(),
                                        snapshot.child("date").getValue().toString(),
                                        snapshot.child("deliverydate").getValue().toString(),
                                        snapshot.child("mobileno").getValue().toString(),
                                        snapshot.child("name").getValue().toString(),
                                        snapshot.child("order_status").getValue().toString(),
                                        snapshot.child("orderid").getValue().toString(),
                                        snapshot.child("paymentmethod").getValue().toString(),
                                        snapshot.child("time").getValue().toString());
                            }
                        }).build();

        adapter = new FirebaseRecyclerAdapter<Myorderhelper , OrderViewHolder>(options) {
            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.myorderlayout, parent , false);
                return new OrderViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, final int position, @NonNull Myorderhelper model) {

                Log.i("Harsh", "onBindViewHolder: ");
                holder.setOrderid("Order id = " + model.getOrderid());
                holder.setAmount("Amount = " + model.getAmount());
                holder.setDate("Order on " + model.getDate());
                holder.setStatus("Order Status is " + model.getOrder_status());
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Myorder.this,String.valueOf(position),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
//        adapter.startListening();
    }



    public static class OrderViewHolder extends RecyclerView.ViewHolder{
        TextView orderid;
        TextView amount;
        TextView date;
        TextView status;
        CardView cardView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderid = itemView.findViewById(R.id.orderid);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);
            cardView = itemView.findViewById(R.id.cardview);
        }
        public void setOrderid(String string){
            orderid.setText(string);
        }
        public void setAmount(String string){
            amount.setText(string);
        }
        public void setDate(String string){
            date.setText(string);
        }
        public void setStatus(String string){
            status.setText(string);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}