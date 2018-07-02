package com.grohden.niceanimals.ui.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grohden.niceanimals.R;
import com.grohden.niceanimals.realm.entities.NiceAnimal;
import com.grohden.niceanimals.ui.holders.NAViewHolder;

import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class NAAdapter extends RealmRecyclerViewAdapter<NiceAnimal, NAViewHolder> {

    public NAAdapter(RealmResults<NiceAnimal> results) {
        super(results, true);
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
        NiceAnimal animalURL = getItem(position);
        niceHolder.bindAnimal(animalURL);
    }
}
