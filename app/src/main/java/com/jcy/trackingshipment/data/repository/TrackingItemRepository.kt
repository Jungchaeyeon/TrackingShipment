package com.jcy.trackingshipment.data.repository

import com.jcy.trackingshipment.data.entity.TrackingInfo
import com.jcy.trackingshipment.data.entity.TrackingItem
import com.jcy.trackingshipment.data.entity.model.Delivery
import kotlinx.coroutines.flow.Flow

interface TrackingItemRepository {


    //DB에서 저장된 아이템 가져옥;
    fun getAllTrackingItems(): Flow<List<Delivery>>

    fun getAllTrackingItemList() : List<Delivery>

    //서버에서 조회 정보 가져오기
    suspend fun getTrackingInformation(companyCode: String, invoice: String): TrackingInfo?

    //DB에 아이템 저장
    suspend fun insertTrackingItem(trackingItem: TrackingItem)

    //DB에 저장된 아이템 삭제
    suspend fun deleteTrackingItem(delivery: Delivery)

    //DB업데이트
    suspend fun updateAll(deliveryList: List<Delivery>)

    //유저가 삭제한 아이템 복구
    suspend fun rollback(delivery: Delivery)
}