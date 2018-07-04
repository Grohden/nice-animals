package com.grohden.niceanimals.ui.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.grohden.niceanimals.R;
import com.grohden.niceanimals.realm.entities.NiceAnimal;
import com.grohden.niceanimals.ui.activities.FullscreenImageActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.grohden.niceanimals.ui.activities.FullscreenImageActivity.IMAGE_URL_EXTRA;

public class NAViewHolder extends RecyclerView.ViewHolder implements Callback {

    @BindView(R.id.pic_image_view)
    ImageView mAnimalView;

    @BindView(R.id.pic_progress_bar)
    ProgressBar mProgressBar;

    private NiceAnimal mNiceAnimal;

    public NAViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void bindAnimal(NiceAnimal animal) {
        mNiceAnimal = animal;
        mProgressBar.setVisibility(View.VISIBLE);

        Picasso.get()
                .load(animal.getPictureUrl())
                .resizeDimen(R.dimen.min_image_width, R.dimen.min_image_height)
                .centerCrop()
                .into(mAnimalView, this);
    }

    @OnClick({R.id.pic_image_view})
    public void openFullscreenImageActivity(ImageView view) {
        final Context context = view.getContext();
        final Intent intent = new Intent(
                context,
                FullscreenImageActivity.class
        );

        intent.putExtra(IMAGE_URL_EXTRA, mNiceAnimal.getPictureUrl());

        context.startActivity(intent);
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
