package com.jcy.trackingshipment.extension

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

fun <T> MediatorLiveData<T>.addSourceList(vararg liveDataList: MutableLiveData<*>, onChanged: () -> T) {
    liveDataList.forEach {
        this.addSource(it) { value = onChanged() }
    }
}
