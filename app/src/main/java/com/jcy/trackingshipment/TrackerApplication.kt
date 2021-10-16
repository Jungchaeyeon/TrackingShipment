package com.jcy.trackingshipment

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import com.jcy.trackingshipment.di.appModule
import com.jcy.trackingshipment.work.AppWorkerFactory
import org.koin.android.BuildConfig
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TrackerApplication : Application(), Configuration.Provider{

    private val workerFactory : AppWorkerFactory by inject()

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

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(
                if(BuildConfig.DEBUG){
                    android.util.Log.DEBUG
                }else{
                    android.util.Log.INFO
                }
            )
            .setWorkerFactory(workerFactory)
            .build()


}