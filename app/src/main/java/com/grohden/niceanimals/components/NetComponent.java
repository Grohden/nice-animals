package com.grohden.niceanimals.components;

import com.grohden.niceanimals.modules.AppModule;
import com.grohden.niceanimals.modules.NetModule;
import com.grohden.niceanimals.ui.activities.MainActivity;
import com.grohden.niceanimals.ui.activities.SplashScreenActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetModule.class, AppModule.class})
public interface NetComponent {
    void inject(SplashScreenActivity activity);

    void inject(MainActivity activity);
}
