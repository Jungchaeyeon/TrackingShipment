package com.jcy.trackingshipment.work

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jcy.trackingshipment.R
import com.jcy.trackingshipment.data.entity.Level
import com.jcy.trackingshipment.data.entity.model.Delivery
import com.jcy.trackingshipment.data.repository.TrackingItemRepository
import com.jcy.trackingshipment.presentation.trackingItem.TrackingActivity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TrackingCheckWorker (
    val context: Context,
    workerParams: WorkerParameters,
    private val trackingItemRepository: TrackingItemRepository,
    private val dispatcher: CoroutineDispatcher
): CoroutineWorker(context, workerParams){

    //오래걸리는 처리를 작성하는 곳, 작업이 연기되서 후에 처리되어야 하는 작업을 작성하는 곳
    override suspend fun doWork(): Result = withContext(dispatcher){
        try{
            val startedTrackingItems = trackingItemRepository.getAllTrackingItemList()
                .filter {
                    it.status == Level.START
                }

            if(startedTrackingItems.toList().isNotEmpty()){
                //배송 출발 상태인 아이템이 있다면
                createNotificationChannelIfNeeded() //채널 생성

                val representativeItem : Delivery= startedTrackingItems.first() //첫번째 아이템에서 정보를 가져오기위해
                NotificationManagerCompat
                    .from(context)
                    .notify(
                        NOTIFICATION_ID,
                        createNotification(
                            "${representativeItem.itemName}(${representativeItem.company.name}) " +
                                    "외 ${startedTrackingItems.size - 1}건의 택배가 배송 출발하였습니다."
                        )
                    )
            }
            Result.success()
        }catch (exception: Exception) {
        Result.failure()
    }
    }
    private fun createNotificationChannelIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_DESCRIPTION

            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun createNotification(
        message: String?
    ): Notification {
        val intent = Intent(context, TrackingActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_shipping_24)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

    companion object {
        private const val CHANNEL_NAME = "Daily Tracking Updates"
        private const val CHANNEL_DESCRIPTION = "매일 배송 출발한 상품을 알려줍니다."
        private const val CHANNEL_ID = "Channel Id"
        private const val NOTIFICATION_ID = 99
    }

}
