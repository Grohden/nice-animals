package com.grohden.niceanimals.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.grohden.niceanimals.NiceApplication;
import com.grohden.niceanimals.R;
import com.grohden.niceanimals.realm.entities.NiceAnimal;
import com.grohden.niceanimals.services.NiceAnimalsService;
import com.grohden.niceanimals.shibe.service.AnimalType;
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
        final LinearLayoutManager rvLayoutManager = new LinearLayoutManager(this);
        final RealmResults<NiceAnimal> niceAnimals = Realm.getDefaultInstance()
                .where(NiceAnimal.class)
                .findAll();

        final NAAdapter naAdapter = new NAAdapter(niceAnimals);

        mNiceRecycleView.setHasFixedSize(false);
        mNiceRecycleView.setLayoutManager(rvLayoutManager);
        mNiceRecycleView.setAdapter(naAdapter);
        mNiceRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                final boolean isScrollDown = dy > 0;
                if (isScrollDown && !isLoadingMore) {
                    final int visibleItemCount = rvLayoutManager.getChildCount();
                    final int totalItemCount = rvLayoutManager.getItemCount();
                    final int pastVisiblyItems = rvLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisiblyItems) >= totalItemCount) {
                        isLoadingMore = true;
                        mProgressBar.setVisibility(View.VISIBLE);

                        niceAnimalsService
                                .fetchAndPersistMoreAnimals(AnimalType.SHIBES)
                                .thenRunAsync(() -> {
                                    isLoadingMore = false;
                                    mProgressBar.setVisibility(View.GONE);
                                });
                    }
                }
            }
        });

    }
}
