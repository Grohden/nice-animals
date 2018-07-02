package com.grohden.niceanimals;

import android.app.Application;

import com.grohden.niceanimals.components.DaggerNetComponent;
import com.grohden.niceanimals.components.NetComponent;
import com.grohden.niceanimals.modules.AppModule;
import com.grohden.niceanimals.modules.NetModule;

import io.realm.Realm;

public class NiceApplication extends Application {
    private static final String BASE_SHIBE_URL = "http://shibe.online/api/";

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        mNetComponent = DaggerNetComponent
                .builder()
                .netModule(new NetModule(BASE_SHIBE_URL))
                .appModule(new AppModule(this))
                .build();
    }


    public NetComponent getNetComponent() {
        return mNetComponent;
    }

}
