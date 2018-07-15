package com.grohden.niceanimals.ui.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grohden.niceanimals.R;
import com.grohden.niceanimals.realm.entities.NiceAnimal;
import com.grohden.niceanimals.ui.holders.GalleryViewHolder;

import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class GalleryAdapter extends RealmRecyclerViewAdapter<NiceAnimal, GalleryViewHolder> {

    public GalleryAdapter(RealmResults<NiceAnimal> results) {
        super(results, true);
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(
                R.layout.gallery_image,
                parent,
                false
        );

        return new GalleryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        NiceAnimal animalURL = getItem(position);
        holder.bindAnimal(animalURL);
    }
}
