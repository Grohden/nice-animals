package com.grohden.niceanimals.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grohden.niceanimals.R;
import com.grohden.niceanimals.realm.entities.NiceAnimal;
import com.grohden.niceanimals.ui.holders.NAViewHolder;

import java.util.List;

public class NAAdapter extends RecyclerView.Adapter<NAViewHolder> {
    private List<NiceAnimal> animals;

    public NAAdapter(List<NiceAnimal> animals) {
        this.animals = animals;
    }

    @NonNull
    @Override
    public NAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(
                R.layout.animal_card,
                parent,
                false
        );

        return new NAViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NAViewHolder niceHolder, int position) {
        NiceAnimal animalURL = animals.get(position);

        niceHolder.bindAnimal(animalURL);
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }
}
