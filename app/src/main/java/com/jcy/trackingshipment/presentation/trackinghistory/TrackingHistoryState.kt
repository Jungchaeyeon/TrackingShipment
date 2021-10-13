package com.jcy.trackingshipment.presentation.trackinghistory

import androidx.annotation.StringRes

sealed class TrackingHistoryState{

    object Uninitialized: TrackingHistoryState()

    object Loading: TrackingHistoryState()

    object Success: TrackingHistoryState()


    data class Error(
        @StringRes val messageId: Int
    ): TrackingHistoryState()
}