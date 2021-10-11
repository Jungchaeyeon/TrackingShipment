package com.jcy.trackingshipment.data.db

import androidx.room.*
import com.jcy.trackingshipment.data.entity.TrackingItem
import com.jcy.trackingshipment.data.entity.model.Delivery
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackingItemDao {

    @Query("SELECT * FROM Delivery")
    fun getAllTrackingItems(): Flow<List<Delivery>>

    @Update
    suspend fun updateAll(deliveryList: List<Delivery>)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(delivery: Delivery)

    @Delete
    suspend fun delete(delivery: Delivery)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun rollback(delivery: Delivery)

}