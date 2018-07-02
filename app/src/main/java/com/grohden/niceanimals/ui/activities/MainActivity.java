package com.grohden.niceanimals.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.grohden.niceanimals.NiceApplication;
import com.grohden.niceanimals.R;
import com.grohden.niceanimals.realm.entities.NiceAnimal;
import com.grohden.niceanimals.services.NiceAnimalsService;
import com.grohden.niceanimals.shibe.service.ShibeService;
import com.grohden.niceanimals.ui.adapters.NAAdapter;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.nice_animals_rv)
    RecyclerView mNiceRecycleView;

    @BindView(R.id.infinite_scroll_progress_bar)
    ProgressBar mProgressBar;

    @Inject
    NiceAnimalsService niceAnimalsService;

    @Inject
    ShibeService shibeService;

    private boolean isLoadingMore = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ((NiceApplication) getApplication()).getNetComponent().inject(this);

        configureNiceAnimalsRV();

        Picasso.get().setIndicatorsEnabled(true);
    }

    private void configureNiceAnimalsRV() {
        mNiceRecycleView.setHasFixedSize(false);
        mNiceRecycleView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        final RealmResults<NiceAnimal> niceAnimals = Realm.getDefaultInstance()
                .where(NiceAnimal.class)
                .findAll();

        final NAAdapter naAdapter = new NAAdapter(niceAnimals);

        mNiceRecycleView.setAdapter(naAdapter);
    }
}
