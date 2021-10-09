package com.jcy.trackingshipment.data.entity


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackingInfo(
    @SerializedName("adUrl")
    val adUrl: String?,
    @SerializedName("complete")
    val complete: String?,
    @SerializedName("completeYN")
    val completeYN: String?,
    @SerializedName("estimate")
    val estimate: String?,
    @SerializedName("firstDetail")
    val firstDetail: TrackingDetail?=null,
    @SerializedName("invoiceNo")
    val invoiceNo: String?,
    @SerializedName("itemImage")
    val itemImage: String?,
    @SerializedName("itemName")
    val itemName: String?,
    @SerializedName("lastDetail")
    val lastDetail:TrackingDetail?=null,
    @SerializedName("lastStateDetail")
    val lastStateDetail:TrackingDetail?=null,
    @SerializedName("level")
    val level: Level?,
    @SerializedName("orderNumber")
    val orderNumber: String?,
    @SerializedName("productInfo")
    val productInfo: String?,
    @SerializedName("result")
    val result: String?,
    @SerializedName("trackingDetails")
    val trackingDetails: List<TrackingDetail>?,
    @SerializedName("senderName")
    val senderName : String?,
    @SerializedName("receiverName")
    val receivername: String?,
    @SerializedName("zipCode")
    val zipCode: String?,
    @SerializedName("msg")
val errorMessage: String? = null
): Parcelable