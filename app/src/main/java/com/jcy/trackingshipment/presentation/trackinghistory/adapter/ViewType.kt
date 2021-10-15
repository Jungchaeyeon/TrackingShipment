package com.jcy.trackingshipment.presentation.trackinghistory.adapter

interface ViewType {

    fun getViewType(): Int

    companion object {
        val ITEM = 1
        val LINE = 2
    }

}

