package com.ernestovaldez.brastlewark;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private List<Gnome> mDataset;
    private int cont = 0;
    private ViewGroup _parent;
    View view;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;

        public MyViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.imageView);
        }

        public ImageView getImage(){ return this.image;}
    }

    public ListAdapter(List<Gnome> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        _parent = parent;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_component, parent, false);

        TextView txt1 = view.findViewById(R.id.txtName);
        txt1.setText("Name: " +mDataset.get(cont).Name);

        TextView txt2 = view.findViewById(R.id.txtAge);
        txt2.setText("Age: " +String.valueOf(mDataset.get(cont).Age));

        TextView txt3 = view.findViewById(R.id.txtHair);
        txt3.setText("Hair Color: " +mDataset.get(cont).HairColor);

        TextView txt4 = view.findViewById(R.id.txtProf);
        txt4.setText("Professions: " +mDataset.get(cont).Professions);

        TextView txt5 = view.findViewById(R.id.txtFriends);
        txt5.setText("Friends: " +mDataset.get(cont).Friends);

        TextView txt6 = view.findViewById(R.id.txtWeight);
        txt6.setText("W: " + String.valueOf(mDataset.get(cont).Weight));

        TextView txt7 = view.findViewById(R.id.txtHeight);
        txt7.setText("H: " +String.valueOf(mDataset.get(cont).Height));

        cont++;
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(_parent.getContext())
                .load(mDataset.get(position).Photo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.getImage());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
