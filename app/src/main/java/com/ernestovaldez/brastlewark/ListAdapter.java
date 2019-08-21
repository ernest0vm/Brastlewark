package com.ernestovaldez.brastlewark;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private ArrayList<Gnome> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public View view;
        TextView txt1;
        TextView txt2;
        TextView txt3;
        TextView txt4;
        TextView txt5;
        TextView txt6;
        TextView txt7;

        public MyViewHolder(View v) {
            super(v);
            view = v;
            image = view.findViewById(R.id.imageView);
            txt1 = view.findViewById(R.id.txtName);
            txt2 = view.findViewById(R.id.txtAge);
            txt3 = view.findViewById(R.id.txtHair);
            txt4 = view.findViewById(R.id.txtProf);
            txt5 = view.findViewById(R.id.txtFriends);
            txt6 = view.findViewById(R.id.txtWeight);
            txt7 = view.findViewById(R.id.txtHeight);
        }
    }

    public ListAdapter(ArrayList<Gnome> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_component, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(holder.view.getContext())
                .load(mDataset.get(position).Photo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);

        holder.txt1.setText("Name: " + mDataset.get(position).Name);
        holder.txt2.setText("Age: " + mDataset.get(position).Age);
        holder.txt3.setText("Hair Color: " + mDataset.get(position).HairColor);
        holder.txt4.setText("Professions: " + mDataset.get(position).Professions);
        holder.txt5.setText("Friends: " + mDataset.get(position).Friends);
        holder.txt6.setText("W: " + mDataset.get(position).Weight.substring(0,6));
        holder.txt7.setText("H: " + mDataset.get(position).Height.substring(0,6));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
