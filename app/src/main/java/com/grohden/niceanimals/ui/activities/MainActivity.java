package com.grohden.niceanimals.ui.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.grohden.niceanimals.NiceApplication;
import com.grohden.niceanimals.R;
import com.grohden.niceanimals.realm.entities.NiceAnimal;
import com.grohden.niceanimals.services.NiceAnimalsService;
import com.grohden.niceanimals.shibe.service.ShibeService;
import com.grohden.niceanimals.ui.adapters.NAAdapter;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @BindColor(R.color.colorPrimaryDark)
    int colorPrimaryDark;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    @BindView(R.id.nice_animals_rv)
    RecyclerView mNiceRecycleView;

    @BindView(R.id.infinite_scroll_progress)
    CardView mProgressBar;

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
        configureRefresher();
    }

    private void configureRefresher() {
        mSwipeRefresh.setColorSchemeColors(colorPrimaryDark);
        mSwipeRefresh.setOnRefreshListener(() -> {
            //FIXME: what happens when you try to refresh while loading more?
            niceAnimalsService
                    .refreshAnimals()
                    .thenRun(() -> mSwipeRefresh.setRefreshing(false));
        });
    }

    private void configureNiceAnimalsRV() {
        final LinearLayoutManager rvLayoutManager = new GridLayoutManager(this, 2);
        final RealmResults<NiceAnimal> niceAnimals = Realm.getDefaultInstance()
                .where(NiceAnimal.class)
                .findAll();

        final NAAdapter naAdapter = new NAAdapter(niceAnimals);

        mNiceRecycleView.setHasFixedSize(true);
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
                        //Need to animate the loader properly with a move animation
                        mProgressBar.setVisibility(View.VISIBLE);

                        loadMoreNiceImages();
                    }
                }
            }
        });
    }

    private void loadMoreNiceImages() {
        niceAnimalsService
                .fetchAndPersistAllTypes()
                .thenRunAsync(() -> {
                    isLoadingMore = false;
                    mProgressBar.setVisibility(View.GONE);
                });
    }
}
