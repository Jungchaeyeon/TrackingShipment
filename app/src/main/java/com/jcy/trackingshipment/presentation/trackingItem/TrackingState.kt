package com.jcy.trackingshipment.presentation.trackingItem

import androidx.annotation.StringRes

sealed class TrackingState{
    object Uninitialized: TrackingState()

    object Loading: TrackingState()

    object Success: TrackingState()


    data class Error(
        @StringRes val messageId: Int
    ): TrackingState()
}