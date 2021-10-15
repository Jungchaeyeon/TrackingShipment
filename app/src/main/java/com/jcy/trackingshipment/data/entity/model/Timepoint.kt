package com.jcy.trackingshipment.data.entity.model

import com.jcy.trackingshipment.presentation.trackinghistory.adapter.ViewType


data class Timepoint(val timepoint: String,
                     val description: String) : ViewType {

    override fun getViewType(): Int = ViewType.LINE

}
