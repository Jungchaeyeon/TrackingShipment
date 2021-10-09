package com.jcy.trackingshipment

import android.app.Application
import android.content.Context
import com.jcy.trackingshipment.di.appModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TrackerApplication : Application(){

    companion object {
        lateinit var appContext: Context
    }
    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        startKoin {
            androidLogger(
                if (BuildConfig.DEBUG) {
                    Level.DEBUG
                } else {
                    Level.NONE
                }
            )
            androidContext(this@TrackerApplication)
            modules(appModule)
        }
    }
}