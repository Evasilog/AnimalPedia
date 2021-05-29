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
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * Η κλάση αυτή διαχειρίζεται τα δεδομένα προς εμφάνιση
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {

    private List<Animal> animalList;
    private List<Animal> animalListFull;

    private int mode; //αντικείμενο για τον έλεγχο λειτουργίας

    public RecyclerAdapter(List<Animal> animalList, int mode){
        this.animalListFull = new ArrayList<>(animalList);
        this.animalList = new ArrayList<>();
        this.mode = mode;
        for(Animal animal: animalList){
            this.animalList.add(new Animal(animal.getAnimalID(), animal.getContinent(), animal.getAnimalClass(),
                    animal.getName(), animal.getDetails(), animal.getLink(), animal.getImage(), animal.getFavorite()));
        }
    }

    //κλάση που περιέχει τα στοιχεία για την τοποθέτηση τους στο animal_view
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

                }
            });

        }
    }


    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        //αν το mode == 1 τότε επιλέγεται η προβολή των δεδομένων σε row.xml αλλίως σε item.xml
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
        //μετατροπή των δεδομένων της φωτογραφίας από byte[] σε Bitmap
        byte[] img = animalList.get(position).getImage();
        Bitmap image = BitmapFactory.decodeByteArray(img, 0, img.length);
        holder.itemTitle.setText(animalList.get(position).getName());
        holder.itemImage.setImageBitmap(image);
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }


    //μέθοδος που γίνεται implement από το Filterable και χρησιμοποιείται για την αναζήτηση
    @Override
    public Filter getFilter() {
        return animalFilter;
    }

    //παίρνει τα γράμματα που πληκτρολογεί ο χρήστης και δημιουργεί τη λίστα με τα κατάλληλα ζώα
    private Filter animalFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Animal> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(animalListFull); //όταν ο χρήστης δεν πληκτρολογέι τίποτα του επιστρέφεται η λίστα με όλα τα ζώα
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Animal an : animalListFull) {
                    if (an.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(an); //προσθήκη ζώα που ταιρίαζουν με τη συμβολοσειρά που έχει πληκτρολογήσει ο χρήστης
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        //ανανέωση της λίστα που προβάλεται στον χρήστη
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            animalList.clear();
            animalList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
