package com.grohden.niceanimals.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.grohden.niceanimals.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NAViewHolder extends RecyclerView.ViewHolder implements Callback {

    @BindView(R.id.pic_image_view)
    ImageView mAnimalView;

    @BindView(R.id.pic_progress_bar)
    ProgressBar mProgressBar;

//    @BindView(R.id.url_text)
//    TextView mUrlText;

    public NAViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void bindAnimalPic(URL url) {
        mProgressBar.setVisibility(View.VISIBLE);

        Picasso.get()
                .load(url.toString())
                .into(mAnimalView, this);
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
