package com.jcy.trackingshipment.data.repository

import com.jcy.trackingshipment.data.entity.TrackingInfo
import com.jcy.trackingshipment.data.entity.TrackingItem

interface TrackingItemRepository {

    suspend fun getTrackingInformation(companyCode: String, invoice: String): TrackingInfo?

    suspend fun saveTrackingItem(trackingItem: TrackingItem)

    suspend fun deleteTrackingItem(trackingItem: TrackingItem)
}