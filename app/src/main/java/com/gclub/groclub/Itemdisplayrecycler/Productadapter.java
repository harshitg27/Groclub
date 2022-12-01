package com.gclub.groclub.Itemdisplayrecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gclub.groclub.Databasehelper.Product;
import com.gclub.groclub.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Productadapter extends RecyclerView.Adapter<Productadapter.Productviewholder> {

    ArrayList<Product> products ;

    public Productadapter(ArrayList<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public Productviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productitem1stlayout,parent,false);
        Productviewholder productviewholder = new Productviewholder(view);
        return productviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Productviewholder holder, int position) {

        holder.Productname.setText(products.get(position).getProduct_name());
        holder.ProductDescription.setText(products.get(position).getDescription());
        holder.Productcurrentprice.setText(products.get(position).getSellPrice());
        Picasso.get().load(products.get(position).getImage()).into(holder.Productimage);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class Productviewholder extends RecyclerView.ViewHolder{

        public ImageView Productimage;
        public TextView Productname , ProductDescription , Productcurrentprice;
        public Productviewholder(@NonNull View itemView) {
            super(itemView);
            Productimage = itemView.findViewById(R.id.ProductImage);
            Productname = itemView.findViewById(R.id.Productname);
            ProductDescription = itemView.findViewById(R.id.ProductDesc);
            Productcurrentprice = itemView.findViewById(R.id.ProductCurrentPrice);

        }
    }
}
