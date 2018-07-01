package com.grohden.niceanimals.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.grohden.niceanimals.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(
                this::goToMain,
                2000
        );
    }

    private void goToMain() {
        Intent intent = new Intent(
                SplashScreenActivity.this,
                MainActivity.class
        );

        startActivity(intent);
        finish();
    }
}
