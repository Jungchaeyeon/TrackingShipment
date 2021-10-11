package com.jcy.trackingshipment.data.entity.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jcy.trackingshipment.data.entity.Level
import com.jcy.trackingshipment.data.entity.ShippingCompany
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Delivery(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val carrierName:String,
    val itemName: String,
    val senderName: String,
    val receiverName: String,
    val status: Level,
    val invoice: String,
    @Embedded val company: ShippingCompany
): Parcelable
