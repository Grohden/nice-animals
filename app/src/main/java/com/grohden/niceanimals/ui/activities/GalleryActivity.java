package com.grohden.niceanimals.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.grohden.niceanimals.R;
import com.grohden.niceanimals.realm.entities.NiceAnimal;
import com.grohden.niceanimals.ui.adapters.GalleryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class GalleryActivity extends AppCompatActivity {
    public static final String IMAGE_URL_EXTRA = "IMAGE_URL_EXTRA";

    @BindView(R.id.full_image_view)
    RecyclerView mNiceImageRV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_image);
        ButterKnife.bind(this);

        int imagePosition = getIntent().getIntExtra(IMAGE_URL_EXTRA, 0);
        configureRV(imagePosition);
    }

    private void configureRV(int position) {
        final RealmResults<NiceAnimal> niceAnimals = Realm.getDefaultInstance()
                .where(NiceAnimal.class)
                .findAll();
        final LinearLayoutManager manager = new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
        );
        final GalleryAdapter galleryAdapter = new GalleryAdapter(niceAnimals);

        new PagerSnapHelper()
                .attachToRecyclerView(mNiceImageRV);

        mNiceImageRV.setLayoutManager(manager);
        mNiceImageRV.setAdapter(galleryAdapter);
        mNiceImageRV.scrollToPosition(position);
    }
}
