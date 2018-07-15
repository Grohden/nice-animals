package com.grohden.niceanimals.ui.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.grohden.niceanimals.R;
import com.grohden.niceanimals.realm.entities.NiceAnimal;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryViewHolder extends RecyclerView.ViewHolder implements Callback {

    @BindView(R.id.gallery_image)
    ImageView mAnimalImage;

    @BindView(R.id.gallery_image_loader)
    ProgressBar mProgressBar;

    public GalleryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindAnimal(NiceAnimal animal) {
        Picasso.get()
                .load(animal.getPictureUrl())
                .fit()
                .centerInside()
                .into(mAnimalImage, this);
    }

    @Override
    public void onSuccess() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(Exception e) {
        mProgressBar.setVisibility(View.GONE);
    }
}