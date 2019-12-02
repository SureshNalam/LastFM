package com.example.lastfm

import android.app.Application
import com.example.lastfm.dependencies.component.AppComponent
import com.example.lastfm.dependencies.component.DaggerAppComponent

class MyApplication : Application() {

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent
        return DaggerAppComponent.create()
    }

}

