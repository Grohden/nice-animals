package com.grohden.niceanimals.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.grohden.niceanimals.R;
import com.grohden.niceanimals.modules.RetrofitModule;
import com.grohden.niceanimals.realm.entities.NiceAnimal;
import com.grohden.niceanimals.shibe.service.AnimalType;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * SplashScreen activity is responsible for initializing the app
 * initial data, it should at least wait 2s before go to another activity
 */
public class SplashScreenActivity extends AppCompatActivity {
    private static final int DEFAULT_SCREEN_TIME = 1000;

    private Optional<NiceAnimal> findFirstAnimal() {
        final NiceAnimal animal = Realm.getDefaultInstance()
                .where(NiceAnimal.class)
                .findFirst();
        return Optional.ofNullable(animal);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Realm.init(this);

        if (findFirstAnimal().isPresent()) {
            new Handler().postDelayed(
                    this::goToMainScreen,
                    DEFAULT_SCREEN_TIME
            );
        } else {
            handleEmptyDBInitialization();
        }
    }

    private void handleEmptyDBInitialization() {
        Call<List<URL>> call = RetrofitModule.getShibeService().fetchNiceImageUrls(
                AnimalType.SHIBES,
                10
        );

        //Ugly response handler... TODO: convert to completable future?
        call.enqueue(new Callback<List<URL>>() {
            @Override
            public void onResponse(@NonNull Call<List<URL>> call, @NonNull Response<List<URL>> response) {
                List<NiceAnimal> animals = response.body()
                        .stream()
                        .map(NiceAnimal::new)
                        .collect(Collectors.toList());

                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealm(animals);
                realm.commitTransaction();

                goToMainScreen();
            }

            @Override
            public void onFailure(@NonNull Call<List<URL>> call, @NonNull Throwable t) {
                //TODO: offline?
            }
        });
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
