package com.example.stjepan.zavrsnirad_v1.stetho;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Stjepan on 12.9.2017..
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder = Stetho.newInitializerBuilder(this);

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this));

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);

    }
}
