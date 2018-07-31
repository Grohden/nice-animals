package com.grohden.niceanimals.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.grohden.niceanimals.NiceApplication
import com.grohden.niceanimals.R
import com.grohden.niceanimals.realm.entities.NiceAnimal
import com.grohden.niceanimals.services.NiceAnimalsService
import io.realm.Realm
import java.util.*
import javax.inject.Inject

/**
 * SplashScreen activity is responsible for initializing the app
 * initial data, it should at least wait 2s before go to another activity
 */
class SplashScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var realm: Realm

    @Inject
    lateinit var niceAnimalsService: NiceAnimalsService

    private fun findFirstAnimal(): Optional<NiceAnimal> {
        val animal = realm
                .where<NiceAnimal>(NiceAnimal::class.java)
                .findFirst()
        return Optional.ofNullable(animal)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        (application as NiceApplication).mNetComponent.inject(this)

        if (findFirstAnimal().isPresent) {
            Handler().postDelayed(
                    { this.goToMainScreen() },
                    DEFAULT_SCREEN_TIME.toLong()
            )
        } else {
            handleEmptyDBInitialization()
        }
    }

    /**
     * Handles app first initialization (actually, it is called only when
     * it doesn't find any animal in realm DB)
     */
    private fun handleEmptyDBInitialization() {
        niceAnimalsService
                .fetchAndPersistAllTypes()
                .thenRun { this.goToMainScreen() }
    }


    private fun goToMainScreen() {
        val intent = Intent(
                this,
                MainActivity::class.java
        )

        startActivity(intent)
        finish()
    }

    companion object {
        private const val DEFAULT_SCREEN_TIME = 1000
    }


}
