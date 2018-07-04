package com.grohden.niceanimals.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.grohden.niceanimals.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullscreenImageActivity extends AppCompatActivity {
    public static final String IMAGE_URL_EXTRA = "IMAGE_URL_EXTRA";

    @BindView(R.id.full_image_view)
    ImageView mNiceImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_image);
        ButterKnife.bind(this);

        String imageURL = getIntent().getStringExtra(IMAGE_URL_EXTRA);

        Picasso.get()
                .load(imageURL)
                .fit()
                .centerInside()
                .into(mNiceImageView);
    }
}
