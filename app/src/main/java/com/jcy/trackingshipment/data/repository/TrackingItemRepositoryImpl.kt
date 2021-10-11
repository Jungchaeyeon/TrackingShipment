package com.jcy.trackingshipment.data.repository

import com.jcy.trackingshipment.data.api.SweetTrackerApi
import com.jcy.trackingshipment.data.db.TrackingItemDao
import com.jcy.trackingshipment.data.entity.Level
import com.jcy.trackingshipment.data.entity.TrackingInfo
import com.jcy.trackingshipment.data.entity.TrackingItem
import com.jcy.trackingshipment.data.entity.model.Delivery
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class TrackingItemRepositoryImpl(
    private val trackerApi: SweetTrackerApi,
    private val trackingItemDao: TrackingItemDao,
    private val dispatcher: CoroutineDispatcher
) : TrackingItemRepository{


    override fun getAllTrackingItems(): Flow<List<Delivery>> =
        trackingItemDao.getAllTrackingItems().distinctUntilChanged().flowOn(dispatcher)


    override suspend fun getTrackingInformation(companyCode: String, invoice: String): TrackingInfo? =
        trackerApi.getTrackingApi(companyCode, invoice)
            .body()
            ?.sortTrackingDetailsByTimeDescending()

    override suspend fun insertTrackingItem(trackingItem: TrackingItem) = withContext(dispatcher) {
        val trackingInfo = trackerApi.getTrackingApi(
            trackingItem.company.code,
            trackingItem.invoice
        ).body()

        val trackingDetail = trackingInfo?.firstDetail

        if (!trackingInfo!!.errorMessage.isNullOrBlank()) {
            throw RuntimeException(trackingInfo.errorMessage)
        }

        trackingItemDao.insert(
            Delivery(
                id= null,
                itemName = trackingInfo.itemName.toString(),
                status = trackingInfo.lastStateDetail?.level ?: Level.PREPARE,
                invoice = trackingItem.invoice,
                company = trackingItem.company,
                senderName = trackingInfo.senderName?:"",
                receiverName = trackingInfo.receivername?:"",
                carrierName = trackingDetail?.manName?:""
        ))
    }
    override suspend fun updateAll(deliveryList: List<Delivery>) = trackingItemDao.updateAll(deliveryList)

    override suspend fun deleteTrackingItem(delivery: Delivery) =trackingItemDao.delete(delivery)


    override suspend fun rollback(delivery: Delivery) = trackingItemDao.rollback(delivery)
    private fun TrackingInfo.sortTrackingDetailsByTimeDescending() =
        copy(trackingDetails = trackingDetails?.sortedByDescending {
            it.time ?: 0
        })


}