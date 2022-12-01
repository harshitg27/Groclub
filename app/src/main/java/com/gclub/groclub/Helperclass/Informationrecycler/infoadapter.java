package com.gclub.groclub.Helperclass.Informationrecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gclub.groclub.R;

import java.util.ArrayList;

public class infoadapter extends RecyclerView.Adapter<infoadapter.infoviewholder> {
    ArrayList<Infohelperclass> infolocation;

    public infoadapter(ArrayList<Infohelperclass> infolocation) {
        this.infolocation = infolocation;
    }

    @NonNull
    @Override
    public infoviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_card_design,parent,false);
        infoviewholder infoviewholder = new infoviewholder(view);

        return infoviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull infoviewholder holder, int position) {
        Infohelperclass infohelperclass = infolocation.get(position);
        holder.infoimage.setImageResource(infohelperclass.getImage());
        holder.shortdes.setText(infohelperclass.getShortdes());

    }

    @Override
    public int getItemCount() {
        return infolocation.size();
    }

    public static class infoviewholder extends RecyclerView.ViewHolder{

        ImageView infoimage;
        TextView shortdes;

        public infoviewholder(@NonNull View itemView) {
            super(itemView);

            //hooks
            infoimage = itemView.findViewById(R.id.infoimage);
            shortdes = itemView.findViewById(R.id.shortdes);

        }
    }
}
