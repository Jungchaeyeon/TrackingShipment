package com.jcy.trackingshipment.di

import android.app.Activity
import com.jcy.trackingshipment.data.api.SweetTrackerApi
import com.jcy.trackingshipment.data.api.Url
import com.jcy.trackingshipment.data.db.AppDatabase
import com.jcy.trackingshipment.data.db.TrackingItemDao
import com.jcy.trackingshipment.data.db.TrackingItemDaoImpl
import com.jcy.trackingshipment.data.entity.TrackingInfo
import com.jcy.trackingshipment.data.preference.PreferenceManager
import com.jcy.trackingshipment.data.preference.SharedPreferenceManager
import com.jcy.trackingshipment.data.repository.ShippingCompanyRepository
import com.jcy.trackingshipment.data.repository.ShippingCompanyRepositoryImpl
import com.jcy.trackingshipment.data.repository.TrackingItemRepository
import com.jcy.trackingshipment.data.repository.TrackingItemRepositoryImpl
import com.jcy.trackingshipment.presentation.trackingItem.TrackingActivity
import com.jcy.trackingshipment.presentation.trackingItem.TrackingViewModel
import com.jcy.trackingshipment.presentation.trackinghistory.TrackingHistoryViewModel
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appModule = module{
    single {TrackingViewModel(get(),get()) }
    single{ TrackingHistoryViewModel()}
    single { Dispatchers.IO }

    // Database
    single { AppDatabase.build(androidApplication()) }
    single { get<AppDatabase>().shippingCompanyDao() }
    single { get<AppDatabase>().trackingItemDao() }



    // Preference
    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE) }
    single<PreferenceManager> { SharedPreferenceManager(get()) }


    //Api
    single{
        OkHttpClient()
            .newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if(BuildConfig.DEBUG){
                        HttpLoggingInterceptor.Level.BODY
                    }
                    else{
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
    }
    single<SweetTrackerApi>{
        Retrofit.Builder().baseUrl(Url.SWEET_TRACKER_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create()
    }


    //Repository
    single<ShippingCompanyRepository> { ShippingCompanyRepositoryImpl( get(), get(),get(),get()) }
    single<TrackingItemRepository> { TrackingItemRepositoryImpl(get(), get(), get()) }

}