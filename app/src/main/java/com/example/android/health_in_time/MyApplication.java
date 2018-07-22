package com.example.android.health_in_time;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by samir692 on 11/30/17.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

    // Create an InitializerBuilder
    //Stetho.InitializerBuilder initializerBuilder =
    //        Stetho.newInitializerBuilder(this);



}
