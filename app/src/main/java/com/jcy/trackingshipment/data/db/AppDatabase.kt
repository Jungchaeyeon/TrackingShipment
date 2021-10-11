package com.jcy.trackingshipment.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jcy.trackingshipment.data.entity.ShippingCompany
import com.jcy.trackingshipment.data.entity.TrackingItem
import com.jcy.trackingshipment.data.entity.model.Delivery

@Database(
    entities = [ShippingCompany::class, Delivery::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun shippingCompanyDao(): ShippingCompanyDao
    abstract fun trackingItemDao(): TrackingItemDao

    companion object{
        private const val DATABASE_NAME ="tracking.db"

        fun build(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()

    }

}