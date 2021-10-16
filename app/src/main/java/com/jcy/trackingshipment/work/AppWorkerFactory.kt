package com.jcy.trackingshipment.work

import androidx.work.DelegatingWorkerFactory
import com.jcy.trackingshipment.data.repository.TrackingItemRepository
import kotlinx.coroutines.CoroutineDispatcher

class AppWorkerFactory(
    trackingItemRepository: TrackingItemRepository,
    dispatcher: CoroutineDispatcher
) : DelegatingWorkerFactory() {

    init {
        addFactory(TrackingCheckWorkerFactory(trackingItemRepository, dispatcher))
    }
}
