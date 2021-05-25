package com.example.animalpedia;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.annotation.Native;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {

    private List<Animal> animalList;
    public List<Animal> animalListFull;

    private int mode;

    public RecyclerAdapter(List<Animal> animalList, int mode){
        this.animalListFull = new ArrayList<>(animalList);
        this.animalList = new ArrayList<>();
        this.mode = mode;
        for(Animal animal: animalList){
            this.animalList.add(new Animal(animal.getAnimalID(), animal.getContinent(), animal.getAnimalClass(),
                    animal.getName(), animal.getDetails(), animal.getLink(), animal.getImage(), animal.getFavorite()));
        }
    }

    //Class that holds the items to be displayed (Views in card_layout)
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemTitle = itemView.findViewById(R.id.item_title);
            context = itemView.getContext();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Intent intent = new Intent(context,AnimalView.class);

                    intent.putExtra("AnimalId", animalList.get(position));
                    context.startActivity(intent);

                    /*Snackbar.make(v, "Click detected on item " + position,
                           Snackbar.LENGTH_LONG).show();*/
                }
            });

        }
    }


    //Methods that must be implemented for a RecyclerView.Adapter
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (mode==1){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        }
        else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        }
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        byte[] img = animalList.get(position).getImage();
        Bitmap image = BitmapFactory.decodeByteArray(img, 0, img.length);
        holder.itemTitle.setText(animalList.get(position).getName());
        holder.itemImage.setImageBitmap(image);
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    @Override
    public Filter getFilter() {
        return animalFilter;
    }

    private Filter animalFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Animal> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(animalListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Animal an : animalListFull) {
                    if (an.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(an);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            animalList.clear();
            animalList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
