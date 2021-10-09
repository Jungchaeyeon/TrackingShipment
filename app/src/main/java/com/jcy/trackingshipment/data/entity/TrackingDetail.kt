package com.jcy.trackingshipment.data.entity


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackingDetail(
    @SerializedName("code")
    val code: String?,
    @SerializedName("kind")
    val kind: String?,
    @SerializedName("level")
    val level: Level?,
    @SerializedName("manName")
    val manName: String?,
    @SerializedName("manPic")
    val manPic: String?,
    @SerializedName("remark")
    val remark: String?,
    @SerializedName("telno")
    val telno: String?,
    @SerializedName("telno2")
    val telno2: String?,
    @SerializedName("time")
    val time: Long?,
    @SerializedName("timeString")
    val timeString: String?,
    @SerializedName("trans_telno")
    val transTelno: String?,
    @SerializedName("trans_telno2")
    val transTelno2: String?,
    @SerializedName("trans_time")
    val transTime: String?,
    @SerializedName("trans_where")
    val transWhere: String?,
    @SerializedName("where")
    val where: String?
): Parcelable