package com.example.lfuzzatti.poc1.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lfuzzatti.poc1.R;
import com.example.lfuzzatti.poc1.model.Pet;

import java.util.List;

public class PetDataAdapter extends RecyclerView.Adapter<PetDataAdapter.PetViewHolder> {

    public List<Pet> listPets;

    public class PetViewHolder extends RecyclerView.ViewHolder {

        private TextView name, breed;

        public PetViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            breed = itemView.findViewById(R.id.breed);
        }
    }

    public PetDataAdapter(List<Pet> pets) {
        this.listPets = pets;
    }

    /**
     * Popula a view que será exibida no recyclerview
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(PetViewHolder holder, int position) {

        Pet mPet = listPets.get(position);
        holder.name.setText(mPet.getNome());
        holder.breed.setText(mPet.getRaca());
    }

    /**
     * Carrega o layout que representará o item na lista
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public PetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pet_row, parent, false);

        return new PetViewHolder(itemView);
    }

    /**
     * Retorna a quantidade de elementos que serão exibidos numa lista
     * @return int total de elementos da lista
     */
    @Override
    public int getItemCount() {
        return listPets.size();
    }
}
