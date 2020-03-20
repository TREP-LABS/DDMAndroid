package com.treplabs.ddm

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.treplabs.ddm.di.AppComponent
import com.treplabs.ddm.di.DaggerAppComponent
import timber.log.Timber

/**
 * Created by Rasheed Sulayman.
 */

class App : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        FirebaseFirestore.setLoggingEnabled(true);
        component = DaggerAppComponent.builder()
            .application(this)
            .build()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
