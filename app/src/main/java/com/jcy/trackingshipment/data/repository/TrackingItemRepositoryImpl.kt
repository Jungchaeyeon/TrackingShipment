package com.jcy.trackingshipment.data.repository

import com.jcy.trackingshipment.data.api.SweetTrackerApi
import com.jcy.trackingshipment.data.db.TrackingItemDao
import com.jcy.trackingshipment.data.entity.TrackingInfo
import com.jcy.trackingshipment.data.entity.TrackingItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TrackingItemRepositoryImpl(
    private val trackerApi: SweetTrackerApi,
    private val trackingItemDao: TrackingItemDao,
    private val dispatcher: CoroutineDispatcher
) : TrackingItemRepository{


    override suspend fun getTrackingInformation(companyCode: String, invoice: String): TrackingInfo? =
        trackerApi.getTrackingApi(companyCode, invoice)
            .body()
            ?.sortTrackingDetailsByTimeDescending()

    override suspend fun saveTrackingItem(trackingItem: TrackingItem) = withContext(dispatcher) {
        val trackingInformation = trackerApi.getTrackingApi(
            trackingItem.company.code,
            trackingItem.invoice
        ).body()

        if (!trackingInformation!!.errorMessage.isNullOrBlank()) {
            throw RuntimeException(trackingInformation.errorMessage)
        }

        trackingItemDao.insert(trackingItem)
    }

    override suspend fun deleteTrackingItem(trackingItem: TrackingItem) {
        trackingItemDao.delete(trackingItem)
    }

    private fun TrackingInfo.sortTrackingDetailsByTimeDescending() =
        copy(trackingDetails = trackingDetails?.sortedByDescending {
            it.time ?: 0
        })


}