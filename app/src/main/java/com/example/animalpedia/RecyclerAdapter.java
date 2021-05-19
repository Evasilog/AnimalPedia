package com.example.animalpedia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public List<Animal> animalList;

    public RecyclerAdapter(List<Animal> animalList){
        this.animalList = new ArrayList<>();

        for(Animal animal: animalList){
            this.animalList.add(new Animal(animal.getAnimalID(), animal.getContinent(), animal.getAnimalClass(),
                    animal.getName(), animal.getDetails(), animal.getLink(), animal.getImage()));
        }

    }

    //Class that holds the items to be displayed (Views in card_layout)
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.item_image);
            itemTitle = itemView.findViewById(R.id.item_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    /*Snackbar.make(v, "Click detected on item " + position,
                           Snackbar.LENGTH_LONG).show();*/
                }
            });
        }
    }

    //Methods that must be implemented for a RecyclerView.Adapter
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        holder.itemTitle.setText(animalList.get(position).getName());
        holder.itemImage.setImageBitmap(animalList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }
}
