package com.jcy.trackingshipment.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jcy.trackingshipment.data.entity.TrackingDetail


object TypeConverters {
    @TypeConverter
    fun listToJson(value: List<TrackingDetail>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<TrackingDetail>::class.java).toList()
}
