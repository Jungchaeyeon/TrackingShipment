package com.jcy.trackingshipment.data.db

import com.jcy.trackingshipment.data.entity.model.Delivery
import kotlinx.coroutines.flow.Flow

class TrackingItemDaoImpl(
    private  val deliveryDao: TrackingItemDao
    ) : TrackingItemDao{
    override fun getAllTrackingItems(): Flow<List<Delivery>> = deliveryDao.getAllTrackingItems()


    override suspend fun updateAll(deliveryList: List<Delivery>) = deliveryDao.updateAll(deliveryList)

    override suspend fun insert(delivery: Delivery) = deliveryDao.insert(delivery)

    override suspend fun delete(delivery: Delivery)  = deliveryDao.delete(delivery)

    override suspend fun rollback(delivery: Delivery) = deliveryDao.insert(delivery)
}