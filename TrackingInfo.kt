package com.jcy.trackingshipment.data.entity

import com.google.gson.annotations.SerializedName

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
    val firstDetail: TrackingDetail?,
    @SerializedName("invoiceNo")
    val invoiceNo: String?,
    @SerializedName("itemImage")
    val itemImage: String?,
    @SerializedName("itemName")
    val itemName: String?,
    @SerializedName("lastDetail")
    val lastDetail: TrackingDetail?,
    @SerializedName("lastStateDetail")
    val lastStateDetail: TrackingDetail?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("orderNumber")
    val orderNumber: String?,
    @SerializedName("productInfo")
    val productInfo: String?,
    @SerializedName("result")
    val result: String?,
    @SerializedName("trackingDetails")
    val trackingDetails: List<TrackingDetail>?,
    @SerializedName("zipCode")
    val zipCode: String?
)