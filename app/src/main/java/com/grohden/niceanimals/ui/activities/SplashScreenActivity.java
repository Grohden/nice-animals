package com.grohden.niceanimals.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.grohden.niceanimals.NiceApplication;
import com.grohden.niceanimals.R;
import com.grohden.niceanimals.realm.entities.NiceAnimal;
import com.grohden.niceanimals.services.NiceAnimalsService;
import com.grohden.niceanimals.shibe.service.AnimalType;
import com.grohden.niceanimals.shibe.service.ShibeService;

import java.util.Optional;

import javax.inject.Inject;

import io.realm.Realm;
import retrofit2.Retrofit;

/**
 * SplashScreen activity is responsible for initializing the app
 * initial data, it should at least wait 2s before go to another activity
 */
public class SplashScreenActivity extends AppCompatActivity {
    private static final int DEFAULT_SCREEN_TIME = 1000;

    @Inject
    Retrofit retrofit;

    @Inject
    Realm realm;

    @Inject
    ShibeService shibeService;

    @Inject
    NiceAnimalsService niceAnimalsService;

    private Optional<NiceAnimal> findFirstAnimal() {
        final NiceAnimal animal = realm
                .where(NiceAnimal.class)
                .findFirst();
        return Optional.ofNullable(animal);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ((NiceApplication) getApplication()).getNetComponent().inject(this);

        if (findFirstAnimal().isPresent()) {
            new Handler().postDelayed(
                    this::goToMainScreen,
                    DEFAULT_SCREEN_TIME
            );
        } else {
            handleEmptyDBInitialization();
        }
    }

    /**
     * Handles app first initialization (actually, it is called only when
     * it doesn't find any animal in realm DB)
     */
    private void handleEmptyDBInitialization() {
        niceAnimalsService
                .fetchAndPersistMoreAnimals(AnimalType.SHIBES)
                .thenRunAsync(this::goToMainScreen);
    }


    private void goToMainScreen() {
        final Intent intent = new Intent(
                SplashScreenActivity.this,
                MainActivity.class
        );

        startActivity(intent);
        finish();
    }


}
